package buffermanager;

import pagedfile.*;

public class BufferBlock {
	public BufferBlock(int fileId_,int pageNum_,PF_PageHandler page_,int head ,byte[] data_, boolean dirty_) {
		fileId = fileId_;
		pageNum = pageNum_;
		page = page_;
		data = data_;
		dirty = dirty_;
		pinned = false;
		pageHead = head;
	}
	public BufferBlock(int fileId_,int pageNum_,PF_PageHandler page_,int head ,byte[] data_) {
		fileId = fileId_;
		pageNum = pageNum_;
		page = page_;
		data = data_;
		dirty = false;
		pinned = false;
		pageHead = head;
	}
	public void writeBack() throws Exception {
		page.writePageHead(pageHead);
		page.writePage(data);
		NoDirty();
	}
	public int getFileId() {
		return fileId;
	}
	public int getPageNum() {
		return pageNum;
	}
	public boolean isDirty() {
		return dirty;
	}
	public void NoDirty() {
		dirty = false;
	}
	public void Dirty() {
		dirty = true;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
//		System.out.println("Buffer Block set Data");
		Dirty();
	}
	public void setInt(int pos, int length, int sourceInt) {
		for (int i = 0; i < length; i++) {
			data[i + pos] = (byte)(sourceInt >> 8 * (3 - i) & 0xFF);
		}
		dirty = true;
	}
	public  int getInt(int pos, int length){
		int k = 0;
		for (int i = 0; i < length; i++){
			 k += (data[i+pos] & 0xFF) << (8 * (3 - i));
		}
		return k;
	}
	public  void setBytes(int pos, byte[] b){
		for(int i = 0; i < b.length; i++){
			data[pos + i] = b[i];
		}
		dirty = true;
	}
	public  byte[] getBytes(int pos, int length){
		byte[] b = new byte[length];
		for (int i = 0; i < length; i++){
			b[i] = data[pos + i];
		}
		return b;
	}	
	public  void setKeyValues(int pos,byte[] insertKey,int blockOffset,int offset) {
		setInt(pos,4, blockOffset);
		setInt(pos + 4, 4, offset);	
		setBytes(pos + 8,insertKey);
		dirty = true;
	} 
	public int getHead() {
		return pageHead;
	}
	public void setHead(int head) {
		pageHead = head;
		headChanged = true;
	}
	public PF_PageHandler getPage() {
		return page;
	}
	boolean headChanged;
	int pageHead;
	boolean dirty;
	boolean pinned;
	int fileId;
	int pageNum;
	byte[] data;
	PF_PageHandler page;
}
