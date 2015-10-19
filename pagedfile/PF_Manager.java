package pagedfile;

import java.io.File;
import constant.Constant;
import java.util.ArrayList;
import buffermanager.Buffer;
import buffermanager.BufferBlock;
public class PF_Manager {
	public static void creatFile(String filename) throws Exception {
		File file = new File(Constant.FILE_PATH+File.separator+filename);
		if (file.exists()) {
			throw new FileExistExpection();
		}
		file.createNewFile();
	}
	public static void destoryFile(String filename) throws Exception {
		File file = new File(Constant.FILE_PATH+File.separator+filename);
		if (!file.delete()){
			throw new FileRemoveException();
		}
	}
	public  PF_FileHandler openFile(String filename) throws Exception {
		/* file is already open ?
		 */
		for (PF_FileHandler fileHandler : fileHandlerList) {
			if (fileHandler.getFilename().equals(filename)) {
				fileHandler.setCounter(fileHandler.getCounter() + 1);
				return fileHandler;
			}
		}
		/* fileHandlerList is full ?
		 */
		if (fileHandlerList.size() < Constant.MAX_FILE_NUM) {
			PF_FileHandler fileHandler = new PF_FileHandler(filename,fileHandlerList.size(),1);
			fileHandlerList.add(fileHandler);
			return fileHandler;
		}
		/* unpined file is exist ?
		 */
		for (PF_FileHandler fileHandler : fileHandlerList) {
			if ( fileHandler.isEmpty() ) {
				int index = fileHandler.getFileId();
				PF_FileHandler newFileHandler = new PF_FileHandler(filename,index,1);
				fileHandlerList.set(index, newFileHandler);
				return newFileHandler;
			}
		}
		throw new Exception();
	}
	
	public void closeFile(PF_FileHandler fileHandler) {
		//TODO buffer manager 
		//TODO writeback file
		fileHandler.setCounter(0);
		
	}
	public BufferBlock loadPage(PF_FileHandler fileHandler, int pageNum) throws Exception{
		PF_PageHandler page = fileHandler.getPage(pageNum);
		byte[] data = page.getPage();
		if (buffer.isFull()) {
			buffer.deleteLRU();
		}
		BufferBlock block = new BufferBlock(fileHandler.getFileId(),pageNum,page,data);
		buffer.loadBlock(block);
		return block;
	}
	public BufferBlock getBlock(PF_FileHandler fileHandler , int pageNum) throws Exception{
		int fileId = fileHandler.getFileId();
		BufferBlock block = buffer.getBlock(fileId, pageNum);
		if (block != null) {
			return block;
		}
		return loadPage(fileHandler, pageNum);
	}
	public void writeBack(PF_FileHandler fileHandler, int pageNum) throws Exception {
		BufferBlock block = getBlock(fileHandler, pageNum);
		if (block.isDirty()) {
			block.getPage().writePage(block.getData());
			block.NoDirty();
		}
	}
	PF_Manager() {
		fileHandlerList = new ArrayList<PF_FileHandler>();
		buffer = new Buffer();
	}
	
	Buffer buffer;
	ArrayList<PF_FileHandler> fileHandlerList;
 }
