package pagedfile;

import java.io.File;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FileReader;
import constant.Constant;
import java.util.ArrayList;
import buffermanager.Buffer;
import buffermanager.BufferBlock;
public class PF_Manager {
	public static void print(String s) {
		System.out.println(s);
	}
	public static String fileLocation(String filename) {
		return new String(Constant.FILE_PATH+"\\"+filename);
	}
	public static byte[] intTobyteArray(int num) {  
	    byte[] result = new byte[4];  
	    result[0] = (byte)(num >>> 24); 
	    result[1] = (byte)(num >>> 16); 
	    result[2] = (byte)(num >>> 8);  
	    result[3] = (byte)(num );       
	    return result;  
	}
	public static int byteArrayToint(byte[] b){  
	    byte[] a = new byte[4];  
	    int i = a.length - 1,j = b.length - 1;  
	    for (; i >= 0 ; i--,j--) { 
	        if(j >= 0)  
	            a[i] = b[j];  
	        else  
	            a[i] = 0;  
	    }  
	    int v0 = (a[0] & 0xff) << 24; 
	    int v1 = (a[1] & 0xff) << 16;  
	    int v2 = (a[2] & 0xff) << 8;  
	    int v3 = (a[3] & 0xff) ;  
	    return v0 + v1 + v2 + v3;  
	}
	public static void creatFile(String filename) throws Exception {
		print(fileLocation(filename));
		File file = new File(fileLocation(filename));
		if (file.exists()) {
			throw new FileExistExpection();
		}
		file.createNewFile();
		
		/* write head message
		 * 
		 */
		DataOutputStream out=new DataOutputStream(  
                			 new BufferedOutputStream(  
                			 new FileOutputStream(fileLocation(filename)))); 
		out.writeInt(0);
		out.writeInt(Constant.FILE_FREE_END);
		out.close();
	}
	public static void destoryFile(String filename) throws Exception {
		File file = new File(fileLocation(filename));
		if (!file.delete()){
			throw new FileRemoveException();
		}
	}
	public  PF_FileHandler openFile(String filename) throws Exception {
		/* file is already open ?
		 */
		for (PF_FileHandler fileHandler : fileHandlerList) {
			if (fileHandler.getFilename().equals(filename)) {
				fileHandler.setCounter(fileHandler.getCounter() + 1);
				return fileHandler;
			}
		}
		
		/*
		 * read head message from file;
		 */
		DataInputStream in = new DataInputStream(  
   			 				 new BufferedInputStream(  
   			 			     new FileInputStream(fileLocation(filename)))); 
		int pageSize = in.readInt();
		int freeFirst = in.readInt();
		in.close();
		/* fileHandlerList is full ?
		 */
		
		if (fileHandlerList.size() < Constant.MAX_FILE_NUM) {
			PF_FileHandler fileHandler = new PF_FileHandler(buffer,filename,fileHandlerList.size(),1,pageSize,freeFirst);
			fileHandlerList.add(fileHandler);
			return fileHandler;
		}
		/* unused file is exist ?
		 */
		for (PF_FileHandler fileHandler : fileHandlerList) {
			if ( fileHandler.isEmpty() ) {
				int index = fileHandler.getFileId();
				PF_FileHandler newFileHandler = new PF_FileHandler(buffer,filename,index,1,pageSize,freeFirst);
				fileHandlerList.set(index, newFileHandler);
				return newFileHandler;
			}
		}
		throw new Exception();
	}
	
	public void closeFile(PF_FileHandler fileHandler) throws Exception{
		//TODO buffer manager 
		//TODO writeback file
		fileHandler.setCounter(fileHandler.getCounter() - 1);
		if (fileHandler.getCounter() == 0) {
			fileHandler.writeBackHead();
			buffer.flush(fileHandler.getFileId());
		}
		
	}

	public BufferBlock getBlock(PF_FileHandler fileHandler , int pageNum) throws Exception{
		BufferBlock block = buffer.getBlock(fileHandler, pageNum);
		return block;
	}
	public byte[] getBlockData(PF_FileHandler fileHandler , int pageNum) throws Exception{
		BufferBlock block = getBlock(fileHandler, pageNum);
		return block.getData();
	}
	public void writeBack(PF_FileHandler fileHandler, int pageNum) throws Exception {
		BufferBlock block = getBlock(fileHandler, pageNum);
		if (block.isDirty()) {
			block.writeBack();
			block.NoDirty();
		}
	}
	
	public void flush(PF_FileHandler fileHandler) throws Exception {
		fileHandler.writeBackHead();
		buffer.flush(fileHandler.getFileId());
	}
	
	public PF_Manager() {
		fileHandlerList = new ArrayList<PF_FileHandler>();
		buffer = new Buffer();
	}
	
	Buffer buffer;
	ArrayList<PF_FileHandler> fileHandlerList;
 }
