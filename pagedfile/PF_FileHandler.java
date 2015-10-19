package pagedfile;
import java.io.File;
import constant.Constant;
import java.io.RandomAccessFile;
public class PF_FileHandler {
	PF_FileHandler(String filename_,int fileID_, int counter_){
		filename = filename_;
		fileId = fileID_;
		counter = counter_;
		file = new File(Constant.FILE_PATH+File.separator+filename);
	}
	public PF_PageHandler getPage(int pageNum) {
		return new PF_PageHandler(this,pageNum,fileId);
	}
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public int getFileId() {
		return fileId;
	}
	
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public boolean isEmpty() {
		return counter == 0;
	}
	
	String filename;
	int fileId;
	int counter; // for a file open multiple times
	int pageNum;
	File file;

}
