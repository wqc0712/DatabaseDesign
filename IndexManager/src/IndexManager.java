import java.io.File;
import java.util.*;



/*import constant.*;
import buffermanager.*;
import recordmanager.*;
import pagedfile.*;
import CatalogManager.*;
import CatalogManager.src.CatalogManager;
import Interpreter.Value;*/

public class IndexManager{
	private static CatalogManager cm = CatalogManager.getInstance();
	private static RM_Manager rm = RM_Manager.getInstance();
	private static TreeMap<String, Integer> mapIndexRoot = new TreeMap<String, Integer>();
	private static TreeMap<String, TreeSet<Index>> mapName2Index = new TreeMap<String, TreeSet<Index>>();
/*	public static Buffer buf;
	
	IndexManager(Buffer buffer){
		buf = buffer;
	} */
	private static IndexManager Instant = new IndexManager();
    public static IndexManager getInstance() { return Instant;}
	private static byte[] getColumnValue(String tableName, Index index, byte[] row) {
/*		Value table_ = cm.GetTableInformation(tableName);
		Value attr_;
		int st = 0,  en = 0;
		for(int i = 0; i <= table_.getLength(); i++){
			attr_ = cm.GetAttrInformation(tableName, i);
			st = en;
			en += attr_.getLength();
		}*/
		int st = 0, en = 0;
		for (int i = 0; i < index.column; i++) {
			st = en;
			en += cm.GetAttrInformation(tableName, i).getLength();
		}
		byte[] colValue=new byte[en - st];
		for(int j = 0; j < en - st; j++) {
			colValue[j] = row[st + j];
		}
		return colValue;
	}
	
	static boolean mapInsert(String key, Index value) {
		if (mapName2Index.containsKey(key)) {
			Set<Index> set = mapName2Index.get(key);
			if (set.contains(value)) return false;
			set.add(value);
		} else {
			TreeSet<Index> set = new TreeSet<Index>();
			set.add(value);
			mapName2Index.put(key, set);
		}
		return true;
	}
	
	public static void createIndex(String tableName, Index index){
		Value table_ = cm.GetTableInformation(tableName);
		Value attr_;
		BPlusTree thisTree = new BPlusTree(index);
		
		String filename = tableName;
		try{
			MapInsert(tableName, index);
			RM_FileHandler rmf = RM_Manager.getInstance().openFile(filename);
			RM_FileScan rmfs = new RM_FileScan(rmf, Constant.TYPE.INT, 4, 0, Constant.COMP_OP.NO_OP, PF_Manager.intTobyteArray(1));
			RM_Record rmr = rmfs.getNextRec();
			
			while (rmr != null) {
				byte[] Record = rmr.getData();
				byte[] key = getColumnValue(tableName, index, Record);
				thisTree.insert(key, rmr.getRid().getPageNum(), rmr.getRid().getSlotNum());
				rmr = rmfs.getNextRec();
			}
	    } catch (NullPointerException e){
			e.printStackTrace();
	   		System.err.println("must not be null for key.");
	    } catch (Exception e){
			//e.printStackTrace();
	   		System.err.println("the index has not been created.");
	    }
	   
	   //index.rootNum=thisTree.myRootBlock.blockOffset;
  	    setIndexRoot(index.indexName, thisTree.myRootBlock.getPageNum());
		System.out.println("Create index successfully!");
	}
	
	public static void dropIndex(String filename){
		filename += ".index";
		File file = new File(filename);
		
		try{
			if(file.exists()) {
				if(file.delete())   
					System.out.println("The index file has been deleted.");
			} else
				System.out.println("The file "+filename+" has not been found.");
        } catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Delete index unsuccessfully！");
        }
		//buf.setInvalid(filename);  //将buf中所有与此索引相关的缓冲块都置为无效
		System.out.println("Delete index successfully！");
	}
	
	public static void dropTable(String filename) {
		TreeSet<Index> i = mapName2Index.get(filename);
		for (Index j : i) {
			dropIndex(j.indexName);
		}
		mapName2Index.remove(filename, i);
	}
	
	public static RID searchEqual(Index index, byte[] key) throws Exception{
		RID off = new RID();
		try{
			//Index inx=CatalogManager.getIndex(index.indexName);
			BPlusTree thisTree = new BPlusTree(index, index.rootNum);		/*This place has bugs.*/
			off = thisTree.searchKey(key);
			return off;
		} catch(NullPointerException e) {
			System.err.println();
			return null;
		}
	}
	
	static public void insertKey(Index index, byte[] key, int blockOffset, int offset) throws Exception {
		try {
			//Index inx=CatalogManager.getIndex(index.indexName);
			BPlusTree thisTree = new BPlusTree(index, index.rootNum);		/*This place has bugs.*/
			thisTree.insert(key, blockOffset, offset);
			//index.rootNum=thisTree.myRootBlock.blockOffset;
			setIndexRoot(index.indexName, thisTree.myRootBlock.getPageNum());
		} catch (NullPointerException e) {
			System.err.println();
		}	
	}
	
	static public void deleteKey(Index index, byte[] deleteKey) throws Exception{
		try{
			//Index inx=CatalogManager.getIndex(index.indexName);
			BPlusTree thisTree = new BPlusTree(index, index.rootNum);		/*This place has bugs.*/
			thisTree.delete(deleteKey);
			//index.rootNum=thisTree.myRootBlock.blockOffset;
			setIndexRoot(index.indexName, thisTree.myRootBlock.getPageNum());
		} catch (NullPointerException e) {
			System.err.println();
		}	
	}
	
	 static  public void setIndexRoot(String indexName,int number){;
	 	if (mapIndexRoot.containsKey(indexName)) {
	 		mapIndexRoot.replace(indexName, number);
	 	} else 
	 		mapIndexRoot.put(indexName, number);
	}
	
	 //获得索引根块在文件中的block偏移量， 如果该index不存在则返回-1
	 static  public int getIndexRoot(String indexName){
		if (mapIndexRoot.containsKey(indexName))
			return mapIndexRoot.get(indexName);
		else
			return -1;
		
	}
}
