package recordmanager;
import constant.Constant;
import pagedfile.PF_Manager;
public class RM_PageHandler {
	static RM_PageHandler parsePageData(byte[] pageData,int bitmapSize) {
		//TODO constant for 4
		RM_PageHandler pageHandler = new RM_PageHandler();
		byte[] recordSizeByte = new byte[4];
		System.arraycopy(pageData, 4, recordSizeByte, 0, 4);
		pageHandler.nextFreePage = PF_Manager.byteArrayToint(pageData);
		pageHandler.recordNum = PF_Manager.byteArrayToint(recordSizeByte);
		pageHandler.bitmap = new byte[bitmapSize];
		System.arraycopy(pageData, 8, pageHandler.bitmap, 0, bitmapSize);
		pageHandler.bitmapSize = bitmapSize;
		return pageHandler;
	}
	static int getNextBit(byte[] bitmap, int slotNum) {
		while (slotNum < bitmap.length*8 - 1) {
			slotNum++;
			if (getBit(bitmap,slotNum) == 1) {
				return slotNum;
			}
		}
		return Constant.NO_NEXT_REC;
	}
	static int getBit(byte[] bitmap, int slotNum) {
		int bytePos = slotNum / 8;
		int byteOff = slotNum % 8;
		return (bitmap[bytePos] >>> byteOff) & 1;
	}
	static void setBit(byte[] bitmap, int slotNum) {
		int bytePos = slotNum / 8;
		int byteOff = slotNum % 8;
		bitmap[bytePos] = (byte)(bitmap[bytePos] | (1 << byteOff));
	}
	static void setBitFree(byte[] bitmap, int slotNum) {
		// TODO single test
		int bytePos = slotNum / 8;
		int byteOff = slotNum % 8;
		bitmap[bytePos] = (byte)(bitmap[bytePos] & (~(1 << byteOff)));
	}
	
	
	public int getNextBit(int slotNum) {
		return getNextBit(bitmap,slotNum);
	}
	public void setBit(int slotNum) {
		setBit(bitmap, slotNum);
	}
	public void setBitFree(int slotNum) {
		setBitFree(bitmap, slotNum);
	}
	public boolean checkRecExist(int slotNum) {
		int bit = getBit(bitmap,slotNum);
		if (bit == 1) 
			return true;
		else 
			return false;
	}
	public int getFirstFreeBit() {
//		System.out.println("bitmap"+bitmap[0]);
		for (int i = 0; i < bitmapSize; i++) {
			if (bitmap[i] != 0xff) {
				int off = 0;
				int freeByte = bitmap[i];
				if (freeByte == 0) 
					return i*8;
				while (freeByte != 0) {
					freeByte = freeByte >>> 1;
					off += 1;
					if ((freeByte & 1) == 0){
						return off+i*8;
					}
				}
			}
		}
		return -1;
	}
	public byte[] toByteArray() {
		byte[] byteHead = new byte[bitmapSize + 8];
		byte[] nextFreePageByte = PF_Manager.intTobyteArray(nextFreePage);
		byte[] recordNumByte = PF_Manager.intTobyteArray(recordNum);
		for (int i = 0; i < 4; i++)	
			byteHead[i] = nextFreePageByte[i];
		for (int i = 0; i < 4; i++)	
			byteHead[i+4] = nextFreePageByte[i];
		for (int i = 0; i < bitmapSize; i++)	
			byteHead[i+8] = bitmap[i];
		return byteHead;
 	}
	public RM_PageHandler() {
		// TODO Auto-generated constructor stub
		nextFreePage = -1;
		recordNum = -1;
		bitmap = null;
		bitmapSize = -1;
	}
	int nextFreePage;
	int recordNum;
	byte[] bitmap;
	int bitmapSize;
}
