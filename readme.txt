PF_Manager��
//�����ļ�ϵͳ��buffer
��Ҫ������
	1. creatFile(String filename);
	�����ļ���Ϊfilename�ļ�
	2. destoryFile(String filename);
	ɾ���ļ���Ϊfilename�ļ�
	3. PF_FileHandler openFile(String filename);
	���ļ���Ϊfilename���ļ��������������� PF_FileHandler
	3. void closeFile(PF_FileHandler);
	�رվ��ΪPF_FileHandler ���ļ�����û���ļ������øþ������buffer�и�
	�ļ��Ŀ飬ȫ��writeBack����unpin
	5. BufferBlock loadPage(PF_FileHandler fileHandler, int pageNum)
	�������е�fileHandler����pageNum�飬д�뵽buffer�С�����һ�㲻�ã��������
	6. BufferBlock getBlock(PF_FileHandler fileHandler , int pageNum)
	���ش����е�fileHandler����pageNum�飬�Զ��ᵽbuffer������������bufferû�У��ٴӴ�����ȥ��
	
	7.byte[] getBlockData(PF_FileHandler fileHandler , int pageNum)
	ͬ�ϲ������ص�block������
	
	8.void writeBack(PF_FileHandler fileHandler, int pageNum)
	��buffer�еĿ�д��
	
	9.void flush(PF_FileHandler fileHandler)
	���ļ��е�������buffer�ڵĿ�д�أ����رգ��ļ��ر�ʱ���á�
	
PF_FileHandler
//�����ļ�
	1. void writeBackHead()��
	���ļ�ͷд�ص�����
	2. void markDirty(int pageNum)
	���ļ��Ŀ�����Ϊdirty
	3. void forcePages(int pageNum)
	д���ļ����п�
	4.int addPage(byte[] data)
	�����ļ��Ŀ飬����Ϊ���pageNum
	5. void deletePage(int pageNum)
	ɾ���ļ����еĿ�
	
BufferBlock
//buffer�ڵĿ�
	1. setData(byte[] data)
	 ����block�е�����

����Bufferģ��������������ò����ģ���ͨ��PF_Manager����
����������Constant�У�
window��mac���ļ��ָ�����һ��Ҫע�⡣
	 
	
	