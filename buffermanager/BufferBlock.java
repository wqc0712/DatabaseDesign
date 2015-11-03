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
