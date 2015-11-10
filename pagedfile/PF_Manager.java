
import java.io.File;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.ArrayList;
public class PF_Manager {
	public static void print(String s) {
		System.out.println(s);
	}
	public static String fileLocation(String filename) {
		return new String(filename);
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
	    int i = a.length - 1,j = 3;
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
	public static double byteArrayToDouble(byte[] b) {  
	    long l;  
	    l = b[0];  
	    l &= 0xff;  
	    l |= ((long) b[1] << 8);  
	    l &= 0xffff;  
	    l |= ((long) b[2] << 16);  
	    l &= 0xffffff;  
	    l |= ((long) b[3] << 24);  
	    l &= 0xffffffffl;  
	    l |= ((long) b[4] << 32);  
	    l &= 0xffffffffffl;  
	    l |= ((long) b[5] << 40);  
	    l &= 0xffffffffffffl;  
	    l |= ((long) b[6] << 48);  
	    l &= 0xffffffffffffffl;  
	    l |= ((long) b[7] << 56);  
	    return Double.longBitsToDouble(l);  
	}
	public static byte[] doubleToByteArray(double x) {  
	    byte[] b = new byte[8];  
	    long l = Double.doubleToLongBits(x);  
	    for (int i = 0; i < 8; i++) {  
	        b[i] = new Long(l).byteValue();  
	        l = l >> 8;  
	    }  
	    return b;
	}  
	public  void creatFile(String filename) throws Exception {
		//print(fileLocation(filename));
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
	public  void destoryFile(String filename) throws Exception {
		File file = new File(fileLocation(filename));
		for (PF_FileHandler fileHandler : fileHandlerList) {
			if (fileHandler.getFilename().equals(filename)) {
				fileHandlerList.remove(fileHandler);
				break;
			}
		}
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
	
	private PF_Manager() {
		fileHandlerList = new ArrayList<PF_FileHandler>();
		buffer = new Buffer();
	}
	static public PF_Manager getInstance() {
		return manager;
	}
	Buffer buffer;
	static PF_Manager manager =  new PF_Manager();
	ArrayList<PF_FileHandler> fileHandlerList;
 }
