package recordmanager;
import constant.Constant;

public class RID {
	public RID() {
		pageNum = Constant.INVALID_PAGE;
		slotNum = Constant.INVALID_SLOT;
	}
	public RID(int pageNum_, int slotNum_) {
		pageNum = pageNum_;
		slotNum = slotNum_;
	}
	 
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getSlotNum() {
		return slotNum;
	}
	public void setSlotNum(int slotNum) {
		this.slotNum = slotNum;
	}

	int pageNum;
	int slotNum;
}
