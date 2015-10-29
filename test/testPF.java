package test;
import java.io.File;
import buffermanager.BufferBlock;
import pagedfile.PF_Manager;
import pagedfile.PF_FileHandler;
import pagedfile.PF_PageHandler;
import constant.Constant;
public class testPF {
	static void print(byte[] data) {
		String s = new String(data);
		System.out.println("data:"+s);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PF_Manager pfManager = PF_Manager.getInstance();
		try {
//			PF_Manager.destoryFile("1.tb");
//			PF_Manager.destoryFile("2.tb");
//			PF_Manager.destoryFile("3.tb");
//			PF_Manager.destoryFile("4.tb");
//			PF_Manager.destoryFile("5.tb");
			PF_Manager.creatFile("1.tb");
			PF_Manager.creatFile("2.tb");
			PF_Manager.creatFile("3.tb");
			PF_Manager.creatFile("4.tb");
			PF_Manager.creatFile("5.tb");
			
			PF_FileHandler fileHandler = pfManager.openFile("1.tb");
			PF_FileHandler fileHandler2 = pfManager.openFile("2.tb");
			PF_FileHandler fileHandler3 = pfManager.openFile("3.tb");
			PF_FileHandler fileHandler4 = pfManager.openFile("4.tb");
			byte[] indata = new byte[Constant.PAGE_SIZE];
			indata[1] = 1;
			byte[] indata2 = new byte[Constant.PAGE_SIZE];
			indata2[2] = 2;
			byte[] indata3 = new byte[Constant.PAGE_SIZE];
			indata3[3] = 3;
			byte[] indata4 = new byte[Constant.PAGE_SIZE];
			indata4[4] = 3;
			int pageNum = fileHandler.addPage(indata);
			int pageNum2 = fileHandler.addPage(indata2);
			int pageNum3 = fileHandler.addPage(indata3);
			int pageNum4 = fileHandler.addPage(indata4);
//			int fileId = fileHandler.getFileId();
			
			byte[] data = pfManager.getBlockData(fileHandler, pageNum);
			pfManager.writeBack(fileHandler, pageNum);
			BufferBlock block = pfManager.getBlock(fileHandler, pageNum);
			block.setData(indata2);
			data = pfManager.getBlockData(fileHandler, pageNum);
			
			System.out.println(indata2);
			System.out.println(data);
			
			fileHandler.deletePage(pageNum4);
			fileHandler.deletePage(pageNum3);
			pfManager.closeFile(fileHandler);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
//