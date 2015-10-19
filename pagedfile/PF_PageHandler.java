package pagedfile;
import java.io.File;
import java.io.RandomAccessFile;

import constant.Constant;
import pagedfile.PF_FileHandler;
public class PF_PageHandler {
	PF_PageHandler(PF_FileHandler file_, int pageNum_, int fileId_) {
		file = file_;
//		data = data_;
		pageNum = pageNum_;
		fileId = fileId_;
	}
	
	public byte[] getPage() throws Exception{
		RandomAccessFile randomAccessFile = new RandomAccessFile (Constant.FILE_PATH+File.separator+file.getFilename(), "r");
		byte[] data = new byte[Constant.PAGE_SIZE]; 
		randomAccessFile.read(data,Constant.PAGE_SIZE*pageNum,Constant.PAGE_SIZE);
		randomAccessFile.close();
		return data;
	}
	public void writePage(byte[] data) throws Exception {
		RandomAccessFile randomAccessFile = new RandomAccessFile (Constant.FILE_PATH+File.separator+file.getFilename(), "w");
		randomAccessFile.write(data,Constant.PAGE_SIZE*pageNum,Constant.PAGE_SIZE);
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
}
