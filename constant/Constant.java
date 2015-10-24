package constant;


public class Constant {
	public final static String FILE_PATH = "file";
	public final static int MAX_FILE_NUM = 100;
	public final static int PAGE_SIZE = 8196;
	public final static int PAGE_HEAD_SIZE = 4;
	public final static int FILE_HEAD_SIZE = 8;
	public final static int MAX_BLOCK_NUM = 128;
	public final static int FILE_FREE_END = -1;
	public final static int ALL_PAGES = -1;
	public final static int PAGE_END = -1;
	public final static int PAGE_UESD = -2;
	
	
// RM 
	public final static int INVALID_PAGE = -1;
	public final static int INVALID_SLOT = -1;
//RM_Record
	public final static int INVALID_SIZE = -1;
//RM_FileHandler
	public final static int NO_FREE_PAGE = -1;
}
