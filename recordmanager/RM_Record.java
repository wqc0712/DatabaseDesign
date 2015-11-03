public class RM_Record {
	RM_Record() {
		//size = Constant.INVALID_SIZE;
		data = null;
	}
	
	public RID getRid() {
		return rid;
	}
	public byte[] getData() {
		if (data != null)
			return data;
		else {
			//TODO throw exception
			System.out.println("get a null record");
			return null;
		}
	}
	public void setRecord(RID rid_, byte[] data_) {
		rid = rid_;
		data = data_;
	}
	RID rid;
	byte[] data;
	//int size;
}
