package buffermanager;
import pagedfile.PF_PageHandler;
public class BufferBlock {
	public BufferBlock(int fileId_,int pageNum_,PF_PageHandler page_, byte[] data_) {
		fileId =fileId_;
		pageNum = pageNum_;
		page = page_;
		data = data_;
		dirty = false;
		pinned = false;
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
	public PF_PageHandler getPage() {
		return page;
	}
	
	boolean dirty;
	boolean pinned;
	int fileId;
	int pageNum;
	byte[] data;
	PF_PageHandler page;
}
