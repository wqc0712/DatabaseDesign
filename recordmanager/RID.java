
public class RID {
	public RID() {
		pageNum = Constant.INVALID_PAGE;
		slotNum = Constant.INVALID_SLOT;
	}
	public RID(int pageNum_, int slotNum_) {
		pageNum = pageNum_;
		slotNum = slotNum_;
	}
	public boolean equal(RID rid) {
		if (pageNum == rid.pageNum && slotNum == rid.slotNum) 
			return true;
		return false;
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
