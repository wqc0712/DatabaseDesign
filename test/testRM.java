package test;

import constant.Constant;
import pagedfile.PF_Manager;
import recordmanager.RID;
import recordmanager.RM_FileHandler;
import recordmanager.RM_FileScan;
import recordmanager.RM_Manager;
import recordmanager.RM_Record;

public class testRM {
	public static void print(RID rid) {
		System.out.println(rid.getPageNum()+" "+rid.getSlotNum());
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
		RM_Manager rmManager = RM_Manager.getInstance();
		byte[] recData = new byte[10];
		byte[] recData2 = new byte[10];
		byte[] recData3 = new byte[10];
		byte[] recData4 = new byte[10];
		byte[] recData5 = new byte[10];
		System.arraycopy(PF_Manager.intTobyteArray(1), 0, recData, 5, 4);
		System.arraycopy(PF_Manager.intTobyteArray(2), 0, recData2, 5, 4);
		System.arraycopy(PF_Manager.intTobyteArray(3), 0, recData3, 5, 4);
		System.arraycopy(PF_Manager.intTobyteArray(4), 0, recData4, 5, 4);
		System.arraycopy(PF_Manager.intTobyteArray(5), 0, recData5, 5, 4);
		rmManager.destoryFile("1.tb");
		rmManager.createFile("1.tb", 10);
		RM_FileHandler fileHandler = rmManager.openFile("1.tb");
		RID rid = fileHandler.insertRec(recData);
		RID rid2 = fileHandler.insertRec(recData2);
		RID rid3 = fileHandler.insertRec(recData3);
		RID rid4 = fileHandler.insertRec(recData4);
		RID rid5 = fileHandler.insertRec(recData5);
		print(rid); 
		print(rid2);
		print(rid3); print(rid4);
		print(rid5);
		fileHandler.deleteRec(rid3);
		fileHandler.updateRec(rid5, recData);
		RM_FileScan fileScan = new RM_FileScan(fileHandler, Constant.TYPE.INT, 4, 5, Constant.COMP_OP.EQ_OP, PF_Manager.intTobyteArray(1));
		RM_Record rec;
		while ((rec = fileScan.getNextRec()) != null) {
			System.out.println("RID:");
			print(rec.getRid());
		}
		rmManager.closeFile(fileHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
