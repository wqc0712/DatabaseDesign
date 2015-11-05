import com.sun.org.apache.bcel.internal.generic.Type;

import API.src.CatalogManager;
import constant.*;
import buffermanager.*;
import recordmanager.*;
import pagedfile.*;

public class IndexManager{
	private static CatalogManager cm = CatalogManager.getInstance();
		 
	public static Buffer  buf;
		
	IndexManager(Buffer buffer){
		buf = buffer;
	}
		
	private static byte[] getColumnValue(String tableName, Index index, byte[] row) {
		int st = 0,  en = 0;
		for(int i = 0; i <= table_.getLength(); i++){
			attr_ = cm.GetAttrInformation(tableName, i);
			st = en;
			en += attr_.getLength();
		}
		
		byte[] colValue=new byte[en - st];
		for(int j = 0; j < en - st; j++) {
			colValue[j] = row[st + j];
		}
		return colValue;
	}
	
	public static void createIndex(String tableName, String indexName){
		Value table_ = cm.GetTableInformation(tableName);
		Value attr_;
		BPlusTree thisTree = new BPlusTree(indexName);
		
		String filename = tableName + ".table";       	
		try{
			RM_FileHandler rmf = RM_Manager.getInstance().openFile(filename);
			RM_FileScan rmfs = new RM_FileScan(rmf, Constant.TYPE.INT, 4, 0, Constant.COMP_OP.NO_OP, PF_Manager.intTobyteArray(1));
			RM_Record rmr = rmfs.getNextRec();
			
			while (rmr != null) {
				byte[] Record = rmr.getData();
				byte[] key = getColumnValue(tableName, indexName, Record);
				thisTree.insert(key, rmr.getRid().getPageNum(), rmr.getRid().getSlotNum());
				rmr = rmfs.getNextRec();
			}
	    } catch (NullPointerException e){
	   		System.err.println("must not be null for key.");
	    } catch (Exception e){
	   		System.err.println("the index has not been created.");
	    }
	   
	   //index.rootNum=thisTree.myRootBlock.blockOffset;
  	    setIndexRoot(indexName, thisTree.myRootBlock.getPageNum()); 									/*Need to define a function called setIndexRoot.*/
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
	
	public static RID searchEqual(String indexName, byte[] key) throws Exception{
		RID off = new RID();
		try{
			//Index inx=CatalogManager.getIndex(index.indexName);
			BPlusTree thisTree = new BPlusTree(indexName, buf, indexName.rootNum);		/*This place has bugs.*/
			off = thisTree.searchKey(key);
			return off;
		} catch(NullPointerException e) {
			System.err.println();
			return null;
		}
	}
	
	static public void insertKey(String indexName, byte[] key, int blockOffset, int offset) throws Exception {
		try {
			//Index inx=CatalogManager.getIndex(index.indexName);
			BPlusTree thisTree = new BPlusTree(indexName, buf, indexName.rootNum);		/*This place has bugs.*/
			thisTree.insert(key, blockOffset, offset);
			//index.rootNum=thisTree.myRootBlock.blockOffset;
			setIndexRoot(indexName, thisTree.myRootBlock.getPageNum());
		} catch (NullPointerException e) {
			System.err.println();
		}	
	}
	
	static public void deleteKey(String indexName, byte[] deleteKey) throws Exception{
		try{
			//Index inx=CatalogManager.getIndex(index.indexName);
			BPlusTree thisTree = new BPlusTree(indexName, buf, indexName.rootNum);		/*This place has bugs.*/
			thisTree.delete(deleteKey);
			//index.rootNum=thisTree.myRootBlock.blockOffset;
			setIndexRoot(indexName, thisTree.myRootBlock.getPageNum());
		} catch (NullPointerException e) {
			System.err.println();
		}	
	}
	
}
