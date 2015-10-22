PF_Manager：
//管理文件系统和buffer
主要函数：
	1. creatFile(String filename);
	创建文件名为filename文件
	2. destoryFile(String filename);
	删除文件名为filename文件
	3. PF_FileHandler openFile(String filename);
	打开文件名为filename的文件，并返回其句柄类 PF_FileHandler
	3. void closeFile(PF_FileHandler);
	关闭句柄为PF_FileHandler 的文件，若没有文件在引用该句柄，则将buffer中该
	文件的块，全部writeBack，并unpin
	5. BufferBlock loadPage(PF_FileHandler fileHandler, int pageNum)
	将磁盘中的fileHandler，第pageNum块，写入到buffer中。对外一般不用，用下面的
	6. BufferBlock getBlock(PF_FileHandler fileHandler , int pageNum)
	返回磁盘中的fileHandler，第pageNum块，自动会到buffer内先搜索，若buffer没有，再从磁盘里去读
	
	7.byte[] getBlockData(PF_FileHandler fileHandler , int pageNum)
	同上不过返回的block的数据
	
	8.void writeBack(PF_FileHandler fileHandler, int pageNum)
	将buffer中的块写回
	
	9.void flush(PF_FileHandler fileHandler)
	将文件中的所有在buffer内的块写回，并关闭，文件关闭时调用。
	
PF_FileHandler
//管理文件
	1. void writeBackHead()；
	将文件头写回到磁盘
	2. void markDirty(int pageNum)
	将文件的块设置为dirty
	3. void forcePages(int pageNum)
	写回文件的中块
	4.int addPage(byte[] data)
	增加文件的块，返回为块的pageNum
	5. void deletePage(int pageNum)
	删除文件的中的块
	
BufferBlock
//buffer内的块
	1. setData(byte[] data)
	 更改block中的数据

其他Buffer模块理论上外界是用不到的，都通过PF_Manager操作
常量都放在Constant中，
window和mac的文件分隔符不一样要注意。
	 
	
	