import com.sun.org.apache.bcel.internal.generic.Type;
import constant.*;
import buffermanager.*;
import recordmanager.*;
import pagedfile.*;

public class IndexManager{
	 
	public static Buffer  buf;
		
	IndexManager(Buffer buffer){
		buf = buffer;
	}
		
	//找出需要插入的索引值
	private static byte[] getColumnValue(String tableName, Index index, byte[] row) {
		Table table = find_Table(tableName);			/* Need to define a class Table and a function find_Table. */
		int st = 0,  en = 0;
		for(int i = 0; i <= index.column; i++){ 
			st = en;
			//en += table.attributes.get(i).length; //找出记录中第index.column列的长度为该列属性长度的字符串
			en += table.attrlist[i].length;
		}
		
		byte[] colValue=new byte[en - st];
		for(int j = 0; j < en - st; j++) {//返回该子字符串，即为需要插入的索引字符串
			colValue[j] = row[st + j];
		}
		return colValue;
	}
	
	//创建索引
	public static void createIndex(String tableName,Index index){ //需要API提供表和索引信息结构
		Table table = find_Table(tableName);
		BPlusTree thisTree = new BPlusTree(index); //创建一棵新树
		
	    	//开始正式建立索引
		String filename = tableName + ".table";       	
		try{
			RM_FileHandler rmf = RM_Manager.getInstance().openFile(filename);
			RM_FileScan rmfs = new RM_FileScan(rmf, Constant.TYPE.INT, 4, 0, Constant.COMP_OP.NO_OP, PF_Manager.intTobyteArray(1));
			RM_Record rmr = rmfs.getNextRec();
			
			while (rmr != null) {
				
				rmr = rmfs.getNextRec();
			}
//	   		for(int blockOffset = 0; blockOffset < table.blockNum; blockOffset++){
//	   		BufferBlock block = buffer.readBlock(filename, blockOffset);			/*Buffer Manager needs to define a function called readBlock.*/
//	   		for(int offset = 0; offset < block.get_recordNum(); offset++){			/*BufferBlock needs to define a function to get recordNum of a block.*/
//	   				int position = offset * table.get_recordLength();					/*Table class needs to define a get_recoredLength function.*/ 
//	   				byte[] Record = block.getBytes(position, table.recordLength); 		/*Read each record of the block, we need to provide position and each record's Length.*/
//	   				//if(Record.isEmpty()) break;
//	   				byte[] key=getColumnValue(tableName, index, Record); //找出索引值
//	   				thisTree.insert(key, blockOffset, offset); //插入树中
//	   			}
//	   		}

	    } catch (NullPointerException e){
	   		System.err.println("must not be null for key.");
	    } catch (Exception e){
	   		System.err.println("the index has not been created.");
	    }
	   
	   //index.rootNum=thisTree.myRootBlock.blockOffset;
	   	CatalogManager.setIndexRoot(index.indexName, thisTree.myRootBlock.blockOffset);
	   
		System.out.println("Create index successfully!");
	}
	
	//删除索引，即删除索引文件
	public static void dropIndex(String filename){
		filename += ".index";
		File file = new File(filename);
		
		try{
			if(file.exists())
				if(file.delete())   
					System.out.println("The index file has been deleted.");
			else
				System.out.println("The file "+filename+" has not been found.");
        }catch(Exception   e){
            System.out.println(e.getMessage());
            System.out.println("Delete index unsuccessfully！");
        }
			
		//buf.setInvalid(filename);  //将buf中所有与此索引相关的缓冲块都置为无效
		
		System.out.println("Delete index successfully！");
	}
	
	//等值查找
	public static offsetInfo searchEqual(Index index, byte[] key) throws Exception{
		offsetInfo off=new offsetInfo();
		try{
			//Index inx=CatalogManager.getIndex(index.indexName);
			BPlusTree thisTree=new BPlusTree(index,buf,index.rootNum); //创建树访问结构（但不是新树）
			off=thisTree.searchKey(key);  //找到位置信息体，返回给API
			return off;
		}catch(NullPointerException e){
			System.err.println();
			return null;
		}
	}
	
/*
	public List<offsetInfo> searchBetween(Index index, String firstKey,String lastKey){
		List<offsetInfo> offlist=null;
		return null;
	}
*/	
	//插入新索引值，已有索引则更新位置信息
	static public void insertKey(Index index,byte[] key,int blockOffset,int offset) throws Exception{
		try{
			//Index inx=CatalogManager.getIndex(index.indexName);
			BPlusTree thisTree=new BPlusTree(index,buf,index.rootNum);//创建树访问结构（但不是新树）
			thisTree.insert(key, blockOffset, offset);	//插入
			//index.rootNum=thisTree.myRootBlock.blockOffset;//设置根块
			CatalogManager.setIndexRoot(index.indexName, thisTree.myRootBlock.blockOffset);
		}catch(NullPointerException e){
			System.err.println();
		}
		
	}
	
	//删除索引值，没有该索引则什么也不做
	static public void deleteKey(Index index,byte[] deleteKey) throws Exception{
		try{
			//Index inx=CatalogManager.getIndex(index.indexName);
			BPlusTree thisTree=new BPlusTree(index,buf,index.rootNum);//创建树访问结构（但不是新树）
			thisTree.delete(deleteKey);	//删除
			//index.rootNum=thisTree.myRootBlock.blockOffset;//设置根块
			CatalogManager.setIndexRoot(index.indexName, thisTree.myRootBlock.blockOffset);
		}catch(NullPointerException e){
			System.err.println();
		}
		
	}

	
}
