package recordmanager;
import constant.Constant;
import pagedfile.PF_FileHandler;
import buffermanager.BufferBlock;
public class RM_FileHandler {
	RM_FileHandler() {
		count = 0;
		headerChanged = false;
	}
	RM_FileHandler(RM_FileHeader header_, PF_FileHandler pfh_) {
		count = 1;
		header = header_;
		pfh = pfh_;
		headerChanged = false;
		
	}
	public byte[] parseRecData(byte[] pageData,int slotNum) {
		int offset = header.bitmapOffset+header.bitmapSize+header.recordSize*slotNum;
		byte[] data = new byte[header.recordSize];
		System.arraycopy(pageData, offset, data, 0, header.recordSize);
		return data;
	}
	public RM_Record getRec(RID rid) throws Exception{
		int pageNum = rid.getPageNum();
		int slotNum = rid.getSlotNum();
		byte[] pageData = pfh.getBlockData(pageNum);
		RM_PageHandler pageHandler =  RM_PageHandler.parsePageData(pageData,header.bitmapSize);
		RM_Record record  = new RM_Record();
		if (pageHandler.checkRecExist(slotNum)) {
			record.setRecord(rid, parseRecData(pageData,slotNum));
			return record;
		} else {
			//TODO exception handler
			System.out.println("record not exist");
			return null;
		}
	}
	public void insertRec(RID rid, byte[] data) throws Exception {
		// TODO Notice new byte is zero ?
		assert(data.length == header.recordSize);
		if (header.firstFreePage == Constant.NO_FREE_PAGE) {
			int pageNum = pfh.addPage();
			RM_PageHandler pageHandler = new RM_PageHandler();
			pageHandler.recordNum = 0;
			pageHandler.bitmapSize = header.bitmapSize;
			pageHandler.bitmap = new byte[header.bitmapSize]; 
			pageHandler.nextFreePage = Constant.NO_FREE_PAGE;
			byte[] pageData = pfh.getBlockData(pageNum);
			insertHeadData(pageData, pageHandler.toByteArray());
			header.firstFreePage = pageNum; 
		} 
		byte[] pageData = pfh.getBlockData(header.firstFreePage);
		RM_PageHandler pageHandler =  RM_PageHandler.parsePageData(pageData,header.bitmapSize);
		int slotNum = pageHandler.getFirstFreeBit();
		pageHandler.setBit(slotNum);
		pageHandler.recordNum++;
		insertHeadData(pageData, pageHandler.toByteArray());
		insertData(pageData, slotNum, data);
		pfh.markDirty(header.firstFreePage);
		if (pageHandler.recordNum == header.numRecPerPage) {
			header.firstFreePage = pageHandler.nextFreePage;
		}
		
		
		
	}
	public void deleteRec(RID rid) throws Exception{
		int pageNum = rid.getPageNum();
		int slotNum = rid.getSlotNum();
		byte[] pageData = pfh.getBlockData(pageNum);
		RM_PageHandler pageHandler =  RM_PageHandler.parsePageData(pageData,header.bitmapSize);
		pageHandler.setBitFree(slotNum);
		pageHandler.recordNum--;
		if (pageHandler.recordNum == header.numRecPerPage -1) {
			pageHandler.nextFreePage = header.firstFreePage;
			header.firstFreePage = pageNum;
		}
		insertHeadData(pageData, pageHandler.toByteArray());
	}
	public void updateRec(RID rid, byte[] data) throws Exception {
		int pageNum = rid.getPageNum();
		int slotNum = rid.getSlotNum();
		byte[] pageData = pfh.getBlockData(pageNum);
		RM_PageHandler pageHandler =  RM_PageHandler.parsePageData(pageData,header.bitmapSize);
		assert(pageHandler.checkRecExist(slotNum));
		insertData(pageData, slotNum, data);
	}
	public void insertData(byte[] page, int slotNum, byte[] recData) {
		int off = header.bitmapOffset+ header.bitmapSize + slotNum * header.recordSize;
		for (int i = 0; i < header.recordSize; i++) {
			page[i+off] = recData[i];
		}
	}
	public void insertHeadData(byte[] page, byte[] headData) {
		for (int i = 0; i < headData.length; i++) {
			page[i] = headData[i];
		}
	}
	
	int count;
	RM_FileHeader header;
	PF_FileHandler pfh;
	boolean headerChanged;
	
}
