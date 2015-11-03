public class RM_Manager {
	public void createFile(String filename, int recordSize) throws Exception {
		// TODO constant is magic 
		// NOTICE recordSize is smaller than PAGE_SIZE
		int numRecPerPage = RM_FileHeader.calNumRecPerPage(recordSize);
		int bitmapSize = RM_FileHeader.CalBitmapSize(numRecPerPage);
		int bitmapOffset = 8;
		assert (Constant.PAGE_SIZE >= bitmapOffset+bitmapSize+recordSize);
		
		pfm.creatFile(filename);
		
		PF_FileHandler fileHandler = pfm.openFile(filename);
		RM_FileHeader header = new RM_FileHeader();
		header.recordSize = recordSize;
		header.numRecPerPage = numRecPerPage;
		header.bitmapSize = bitmapSize;
		header.bitmapOffset = bitmapOffset;
		header.firstFreePage = Constant.NO_FREE_PAGE;
		header.numPages = 1;
		int pageNum = fileHandler.addPage(header.toByteArray());
		assert(pageNum == 0);
		pfm.closeFile(fileHandler);
	}
	public void destoryFile(String filename) throws Exception{
		pfm.destoryFile(filename);
	}
	public RM_FileHandler openFile(String filename) throws Exception{
		PF_FileHandler pf_fileHandler = pfm.openFile(filename);
		byte[] fileHeadData = pf_fileHandler.getBlockData(0);
		RM_FileHeader header = RM_FileHeader.parseFromData(fileHeadData);
		return new RM_FileHandler(header, pf_fileHandler);
	}
	public void closeFile(RM_FileHandler fileHandler) throws Exception {
		PF_FileHandler pf_fileHandler = fileHandler.pfh;
		BufferBlock headblock = pf_fileHandler.getBlock(0);
		headblock.setData(fileHandler.header.toByteArray());
//		System.out.println("bitmapoffset"+fileHandler.header.bitmapOffset);
		pfm.closeFile(pf_fileHandler);
	}
	private RM_Manager() {
		// TODO Auto-generated constructor stub
		pfm = PF_Manager.getInstance();
	}
	public static RM_Manager getInstance() {
		return manager;
	}
	static RM_Manager manager = new RM_Manager();
 	PF_Manager pfm;
}
