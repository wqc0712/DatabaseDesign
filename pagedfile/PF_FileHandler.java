/*package pagedfile;*/

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import java.io.RandomAccessFile;
/*
import buffermanager.*;
import constant.*;
import Interpreter.Value;
*/
public class PF_FileHandler {
	PF_FileHandler(Buffer buffer_, String filename_,int fileID_, int counter_, int pageSize_, int freeFirst_){
		filename = filename_;
		fileId = fileID_;
		counter = counter_;
		pageSize = pageSize_;
		freeFirst = freeFirst_;
		buffer = buffer_;
//		file = new File(Constant.FILE_PATH+File.separator+filename);
	}
	public void writeBackHead() throws Exception{
		int off = 0;
		RandomAccessFile randomAccessFile = new RandomAccessFile (filename, "rw");
//		System.out.println(off);
		randomAccessFile.seek(off);
		randomAccessFile.write(PF_Manager.intTobyteArray(pageSize));
		randomAccessFile.write(PF_Manager.intTobyteArray(freeFirst));
		randomAccessFile.close();
	}
	public void markDirty(int pageNum) {
		buffer.markDirty(fileId, pageNum);
	}
	public void forcePages(int pageNum) throws Exception{
		buffer.writeBack(fileId, pageNum);
	}
	public  BufferBlock getFirstPage() throws Exception {
		return getNextPage(-1);
	}
	public  BufferBlock getNextPage(int pageNum) throws Exception{
		for (int cur = pageNum+1; cur < pageSize; cur++) {
			BufferBlock block = buffer.getBlock(this,cur);
			if (block.getHead() == Constant.PAGE_UESD) {
				return block;
			}
		}
		return null;
	}
	
	public  int addPage(byte[] data) throws Exception {
		assert(data.length == Constant.PAGE_SIZE);
		if (freeFirst != Constant.FILE_FREE_END) {
			BufferBlock block = buffer.getBlock(fileId, freeFirst);
			block.setData(data);
			int pageNum_ = freeFirst;
			freeFirst = block.getHead();
			block.setHead(Constant.PAGE_UESD);
			return pageNum_;
		} 
		else {
			BufferBlock block = new BufferBlock(fileId,pageSize,new PF_PageHandler(this,fileId,pageSize),Constant.PAGE_UESD,data,true);
			buffer.loadBlock(block);
			block.writeBack();
			return ++pageSize-1;
		}
		
	}
	public int addPage() throws Exception {
		return addPage(new byte[Constant.PAGE_SIZE]);
	}
	public void deletePage(int pageNum) throws Exception{
		buffer.deleteBlock(fileId,pageNum);
		getPage(pageNum).writePageHead(freeFirst);
		freeFirst = pageNum;
		
	}
	public PF_PageHandler getPage(int pageNum) {
		return new PF_PageHandler(this,fileId,pageNum);
	}
	public byte[] getBlockData(int pageNum) throws Exception{
		BufferBlock block = buffer.getBlock(this, pageNum);
		return block.getData();
	}
	public BufferBlock getBlock(int pageNum) throws Exception{
		BufferBlock block = buffer.getBlock(this, pageNum);
		return block;
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
	
	public int getPageNum() {
		return pageNum;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public boolean isEmpty() {
		return counter == 0;
	}
	
	String filename;
	int pageSize;
	int freeFirst;
	int fileId;
	int counter; // for a file open multiple times
	int pageNum;
	Buffer buffer;
//	File file;

}
