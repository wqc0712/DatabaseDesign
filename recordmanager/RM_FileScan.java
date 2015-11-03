
public class RM_FileScan {
	boolean equal(byte[] value1, byte[] value2, Constant.TYPE type, int attrLenghth) {
//		System.out.println("equal"+PF_Manager.byteArrayToint(value1)+" "+PF_Manager.byteArrayToint(value2));
		switch (type) {
		case INT: return (PF_Manager.byteArrayToint(value1) == PF_Manager.byteArrayToint(value2));
		case STRING: return (new String(value1).equals(new String(value2)));
		case DOUBLE: return (PF_Manager.byteArrayToDouble(value1) == PF_Manager.byteArrayToDouble(value2));
		default: return false;
		}
	}
	boolean less(byte[] value1, byte[] value2, Constant.TYPE type, int attrLenghth) {
		switch (type) {
		case INT: return (PF_Manager.byteArrayToint(value1) > PF_Manager.byteArrayToint(value2));
		case STRING: return (new String(value1).compareTo(new String(value2)) > 0);
		case DOUBLE: return (PF_Manager.byteArrayToDouble(value1) > PF_Manager.byteArrayToDouble(value2));
		default: return false;
		}
	}
	boolean great(byte[] value1, byte[] value2, Constant.TYPE type, int attrLenghth) {
		switch (type) {
		case INT: return (PF_Manager.byteArrayToint(value1) < PF_Manager.byteArrayToint(value2));
		case STRING: return (new String(value1).compareTo(new String(value2)) < 0);
		case DOUBLE: return (PF_Manager.byteArrayToDouble(value1) < PF_Manager.byteArrayToDouble(value2));
		default: return false;
		}
	}
	boolean less_equal(byte[] value1, byte[] value2, Constant.TYPE type, int attrLenghth) {
		switch (type) {
		case INT: return (PF_Manager.byteArrayToint(value1) >= PF_Manager.byteArrayToint(value2));
		case STRING: return (new String(value1).compareTo(new String(value2)) >= 0);
		case DOUBLE: return (PF_Manager.byteArrayToDouble(value1) >= PF_Manager.byteArrayToDouble(value2));
		default: return false;
		}
	}
	boolean great_equal(byte[] value1, byte[] value2, Constant.TYPE type, int attrLenghth) {
		switch (type) {
		case INT: return (PF_Manager.byteArrayToint(value1) <= PF_Manager.byteArrayToint(value2));
		case STRING: return (new String(value1).compareTo(new String(value2)) <= 0);
		case DOUBLE: return (PF_Manager.byteArrayToDouble(value1) <= PF_Manager.byteArrayToDouble(value2));
		default: return false;
		}
	}
	boolean not_equal(byte[] value1, byte[] value2, Constant.TYPE type, int attrLenghth) {
		switch (type) {
		case INT: return (PF_Manager.byteArrayToint(value1) != PF_Manager.byteArrayToint(value2));
		case STRING: return (new String(value1).compareTo(new String(value2)) != 0);
		case DOUBLE: return (PF_Manager.byteArrayToDouble(value1) != PF_Manager.byteArrayToDouble(value2));
		default: return false;
		}
	}
	public RM_FileScan(RM_FileHandler fileHandler_,
			Constant.TYPE type_,
			int attrLength_,
			int attrOffset_,
			Constant.COMP_OP compOp_,
			byte[] value) {
		fileHandler = fileHandler_;
		type = type_;
		attrOffset = attrOffset_;
		value1 = value;
		attrLength = attrLength_;
		compOp = compOp_;
		currentPage = 1;
		currentSlot = -1;
		curRid = new RID(1,-1);
	}
	public RM_Record getNextRec() throws Exception{
		RID nextRid = fileHandler.getNextRec(curRid);
		curRid = nextRid;
		if (nextRid == null) return null;
		RM_Record nextRec = fileHandler.getRec(nextRid);
		byte[] value = new byte[attrLength];
		System.arraycopy(nextRec.getData(), attrOffset, value, 0, attrLength);
		switch(compOp) {
		case EQ_OP: if (equal(value1,value,type,attrLength))  return nextRec; break;
		case LT_OP: if (less(value1,value,type,attrLength)) return nextRec; break;
		case GT_OP: if (great(value1,value,type,attrLength)) return nextRec; break;
		case LE_OP: if (less_equal(value1,value,type,attrLength)) return nextRec; break;
		case GE_OP: if (great_equal(value1,value,type,attrLength)) return nextRec; break;
		case NE_OP: if (not_equal(value1,value,type,attrLength)) return nextRec; break;
		case NO_OP: return nextRec; 
		default : break;
		}
		
		return getNextRec();
	}
	RM_FileHandler fileHandler;
	Constant.TYPE type;
	byte[] value1;
	int attrLength;
	int attrOffset;
	Constant.COMP_OP compOp;
	RID curRid;
	int currentPage;
	int currentSlot;
}
