import java.io.File;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.RandomAccessFile;
public class PF_PageHandler {
	PF_PageHandler(PF_FileHandler file_, int fileId_,int pageNum_) {
		file = file_;
//		data = data_;
		pageNum = pageNum_;
		fileId = fileId_;
//		int off = (Constant.PAGE_SIZE + Constant.PAGE_HEAD_SIZE)*pageNum + Constant.FILE_HEAD_SIZE;
//		try {
//			DataInputStream in=new DataInputStream(  
//                    new BufferedInputStream(  
//                    new FileInputStream(PF_Manager.fileLocation(file.getFilename()))));
//			byte[] temp = new byte[Constant.PAGE_HEAD_SIZE];
//			in.read(temp , off, Constant.PAGE_HEAD_SIZE);
//			status = PF_Manager.byteArrayToint(temp);
//			in.close();
//		} catch (Exception e) {
//			System.out.println("Page construct error!");
//		}
	}
	
	public byte[] getPage() throws Exception{
		int off = (Constant.PAGE_SIZE + Constant.PAGE_HEAD_SIZE)*pageNum + Constant.FILE_HEAD_SIZE + Constant.PAGE_HEAD_SIZE;
		RandomAccessFile randomAccessFile = new RandomAccessFile (file.getFilename(), "r");
		byte[] data = new byte[Constant.PAGE_SIZE]; 
		randomAccessFile.seek(off);
		randomAccessFile.read(data,0,Constant.PAGE_SIZE);
		randomAccessFile.close();
		return data;
	}
	public int getPageHead() throws Exception{
		int off = (Constant.PAGE_SIZE + Constant.PAGE_HEAD_SIZE)*pageNum + Constant.FILE_HEAD_SIZE;
		RandomAccessFile randomAccessFile = new RandomAccessFile (file.getFilename(), "r");
		byte[] data = new byte[Constant.PAGE_HEAD_SIZE]; 
		randomAccessFile.seek(off);
		randomAccessFile.read(data,0,Constant.PAGE_HEAD_SIZE);
		randomAccessFile.close();
		return PF_Manager.byteArrayToint(data);
	}
	public void writePage(byte[] data) throws Exception {
		int off = (Constant.PAGE_SIZE + Constant.PAGE_HEAD_SIZE)*pageNum + Constant.FILE_HEAD_SIZE + Constant.PAGE_HEAD_SIZE;
		RandomAccessFile randomAccessFile = new RandomAccessFile (file.getFilename(), "rw");
		//randomAccessFile.write(PF_Manager.intTobyteArray(status),off,Constant.PAGE_HEAD_SIZE);
//		System.out.println(off);
		randomAccessFile.seek(off);
		randomAccessFile.write(data);
		randomAccessFile.close();
	}
	public void writePageHead(int head) throws Exception {
		int off = (Constant.PAGE_SIZE + Constant.PAGE_HEAD_SIZE)*pageNum + Constant.FILE_HEAD_SIZE;
		RandomAccessFile randomAccessFile = new RandomAccessFile (file.getFilename(), "rw");
//		System.out.println(off);
		randomAccessFile.seek(off);
		randomAccessFile.write(PF_Manager.intTobyteArray(head));
		randomAccessFile.close();
	}
	public int getPageNum() {
		return pageNum;
	}
	public int getFileId() {
		return fileId;
	}
	public boolean equals(Object o) {
		if (o.getClass() == PF_PageHandler.class) {
			PF_PageHandler page = (PF_PageHandler) o;
			if (pageNum == page.pageNum && fileId == page.fileId) 
				return true;
		}
		return false;
	}
	PF_FileHandler file;
//	byte[] data;
	int pageNum;
	int fileId;
	int status;
}
