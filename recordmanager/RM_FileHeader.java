
public class RM_FileHeader {
	static int calNumRecPerPage(int recordSize) {
		return (int)((Constant.PAGE_SIZE - 8.0) / (recordSize + 1.0 / 8));
	}
	static int CalBitmapSize(int numRecPerPage) {
		return ((numRecPerPage-1) / 8);
	}
	static RM_FileHeader parseFromData(byte[] fileHeadData) {
		byte[] recordSizeByte = new byte[4];
		byte[] numRecPerPageByte = new byte[4];
		byte[] bitmapOffsetByte = new byte[4];
		byte[] bitmapSizeByte = new byte[4];
		byte[] numPagesByte = new byte[4];
		byte[] firstFreePageByte = new byte[4];
		System.arraycopy(fileHeadData, 0, recordSizeByte, 0, 4);
		System.arraycopy(fileHeadData, 4, numRecPerPageByte, 0, 4);
		System.arraycopy(fileHeadData, 8, bitmapOffsetByte, 0, 4);
		System.arraycopy(fileHeadData, 12, bitmapSizeByte, 0, 4);
		System.arraycopy(fileHeadData, 16, numPagesByte, 0, 4);
		System.arraycopy(fileHeadData, 20, firstFreePageByte, 0, 4);
		RM_FileHeader header = new RM_FileHeader();
		header.recordSize = PF_Manager.byteArrayToint(recordSizeByte);
		header.numRecPerPage = PF_Manager.byteArrayToint(numRecPerPageByte);
		header.bitmapOffset = PF_Manager.byteArrayToint(bitmapOffsetByte);
		header.bitmapSize = PF_Manager.byteArrayToint(bitmapSizeByte);
		header.numPages = PF_Manager.byteArrayToint(numPagesByte);
		header.firstFreePage = PF_Manager.byteArrayToint(firstFreePageByte);
		return header;
	}
	public byte[] toByteArray() {
		//NOTICE return size of byte array is PAGE_SIZE
		byte[] fileHeadData = new byte[Constant.PAGE_SIZE];
		byte[] recordSizeByte = PF_Manager.intTobyteArray(recordSize);
		byte[] numRecPerPageByte = PF_Manager.intTobyteArray(numRecPerPage);
		byte[] bitmapOffsetByte = PF_Manager.intTobyteArray(bitmapOffset);
		byte[] bitmapSizeByte = PF_Manager.intTobyteArray(bitmapSize);
		byte[] numPagesByte = PF_Manager.intTobyteArray(numPages);
		byte[] firstFreePageByte = PF_Manager.intTobyteArray(firstFreePage);
 		int off = 0;
		for (int i = 0; i < 4; i++,off++) {
			fileHeadData[off] = recordSizeByte[i];
		}
		for (int i = 0; i < 4; i++,off++) {
			fileHeadData[off] = numRecPerPageByte[i];
		}
		for (int i = 0; i < 4; i++,off++) {
			fileHeadData[off] = bitmapOffsetByte[i];
		}
		for (int i = 0; i < 4; i++,off++) {
			fileHeadData[off] = bitmapSizeByte[i];
		}
		for (int i = 0; i < 4; i++,off++) {
			fileHeadData[off] = numPagesByte[i];
		}
		for (int i = 0; i < 4; i++,off++) {
			fileHeadData[off] = firstFreePageByte[i];
		}
		return fileHeadData;
	}
	int recordSize;
	int numRecPerPage;
	int bitmapOffset;
	int bitmapSize;
	int numPages;
	int firstFreePage;
}
