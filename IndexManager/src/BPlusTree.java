import java.io.*;
/*import pagedfile.*;
import buffermanager.*;
import recordmanager.*;*/

public class BPlusTree{
	private static PF_FileHandler pffh;
	private static PF_Manager pfm;
	private static final int POINTERLENGTH = 4;//设指针定长为4个字符，即假设节点在文件中用4个字节就可以标记了
	private int MIN_CHILDREN_FOR_INTERNAL;  //中间节点的最小路标个数
    private int MAX_CHILDREN_FOR_INTERNAL;  //中间节点的最大路标个数
    private int MIN_FOR_LEAF;  //叶子节点的最小索引数
    private int MAX_FOR_LEAF;  //叶子节点的最大索引数
    
	public String filename;
	public BufferBlock myRootBlock;  //根块
	public Index myIndex;  //索引信息体，由外部传入，可更新
  	
	BPlusTree(Index indexInfo){
		try{	
			 filename = indexInfo.indexName + ".index";
			 PF_Manager.getInstance().creatFile(filename);
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("create index failed !");
	    }
		
		try {
			int columnLength=indexInfo.columnLength; 
			MAX_FOR_LEAF=(int)Math.floor((8192.0-1-4-POINTERLENGTH-POINTERLENGTH)/(8+columnLength));
			MIN_FOR_LEAF=(int)Math.ceil(1.0 * MAX_FOR_LEAF/ 2);	
			MAX_CHILDREN_FOR_INTERNAL=MAX_FOR_LEAF; 
			MIN_CHILDREN_FOR_INTERNAL=(int)Math.ceil(1.0 *(MAX_CHILDREN_FOR_INTERNAL)/ 2);
			pffh = PF_Manager.getInstance().openFile(filename);
			
			IndexManager.setIndexRoot(indexInfo.indexName, 0);
			myIndex = indexInfo;
			int blockNum = pffh.addPage();
			new LeafNode(myRootBlock = pffh.getBlock(blockNum));		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	BPlusTree(Index indexInfo, int rootBlockNum){
		try {
			int columnLength = indexInfo.columnLength; 
			MAX_FOR_LEAF=(int)Math.floor((8192.0-1-4-POINTERLENGTH-POINTERLENGTH)/(8+columnLength));
			MIN_FOR_LEAF=(int)Math.ceil(1.0 * MAX_FOR_LEAF/ 2);	
			MAX_CHILDREN_FOR_INTERNAL=MAX_FOR_LEAF; 
			MIN_CHILDREN_FOR_INTERNAL=(int)Math.ceil(1.0 * (MAX_CHILDREN_FOR_INTERNAL)/ 2);
			
			myIndex = indexInfo;	
			filename = myIndex + ".index";
			pffh = PF_Manager.getInstance().openFile(filename);
			new LeafNode(myRootBlock = pffh.getBlock(rootBlockNum), true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void insert(byte[] originalkey, int blockOffset, int offset){
		try {
			if (originalkey == null)
				throw new NullPointerException();  
	
			Node rootNode;
			if(myRootBlock.getData()[0] == 'I'){ 
				rootNode = new InternalNode(myRootBlock,true);
			}
			else{
				rootNode = new LeafNode(myRootBlock,true);
			}
			
	
			byte[] key = new byte[myIndex.columnLength];
			
			for(int j = 0; j < originalkey.length; j++){
				key[j] = originalkey[j];
			}
			
		    for(int j = 0; j < myIndex.columnLength; j++){
				key[j] = '&';
			}
			
			BufferBlock newBlock = rootNode.insert(key, blockOffset, offset);
			if(newBlock!=null)
				myRootBlock=newBlock;
			
			IndexManager.setIndexRoot(myIndex.indexName, myRootBlock.getPageNum());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public RID searchKey(byte[] originalkey){
		try {
			Node rootNode;
			if(myRootBlock.getData()[0] == 'I'){ 
				rootNode = new InternalNode(myRootBlock,true);
			}
			else{
				rootNode = new LeafNode(myRootBlock,true);
			}
			
			byte[] key = new byte[myIndex.columnLength];
			for(int j = 0; j < originalkey.length; j++){
				key[j] = originalkey[j];
			}
			
		    for(int j = 0; j < myIndex.columnLength; j++){
				key[j] = '&';
			}
			
			return rootNode.searchKey(key); //从根节点开始查找
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void delete(byte[] originalkey){
		try {
			if (originalkey == null)    throw new NullPointerException();  
	
			Node rootNode;
			if(myRootBlock.getData()[0]=='I'){ 
				rootNode = new InternalNode(myRootBlock,true);
			}
			else{
				rootNode = new LeafNode(myRootBlock,true);
			}
		
	
			byte[] key = new byte[myIndex.columnLength];
			for(int j = 0; j < originalkey.length; j++){
				key[j] = originalkey[j];
			}
			
		    for(int j = 0; j < myIndex.columnLength; j++){
				key[j]='&';
			}
			
			BufferBlock newBlock = rootNode.delete(key);
	    
			if(newBlock != null)
				myRootBlock = newBlock;
			
			IndexManager.setIndexRoot(myIndex.indexName, myRootBlock.getPageNum());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	abstract class Node {
		BufferBlock block;		
		Node createNode(BufferBlock blk){
			block = blk;
			return this;
		}

		abstract BufferBlock insert(byte[] inserKey, int blockOffset, int offset);
		abstract BufferBlock delete(byte[] deleteKey);
		abstract RID searchKey(byte[] Key);
    }
	
	public int compareTo(byte[] buffer1, byte[] buffer2) {
		try {
			for (int i = 0, j = 0; i < buffer1.length && j < buffer2.length; i++, j++) {
				int a = (buffer1[i] & 0xff);
				int b = (buffer2[j] & 0xff);
				if (a != b) {
					return a - b;
				}
			}
			return buffer1.length - buffer2.length;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return -1;
	}
	
	//中间节点类
	class InternalNode extends Node{	
		InternalNode(BufferBlock blk){		
			try {
				block = blk; //将中间块包装
		    	
		    	block.getData()[0] = 'I';  //标识为中间块
				block.setInt(1, 4, 0);//现在共有0个key值
		    	for(int i = 5; i < 9; i++)
		    		block.getData()[i] = '$';  //说明没有父块标号	
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		InternalNode(BufferBlock blk, boolean t){		
			block = blk; //将中间块包装
		}
		
		//以中间节点为单位的插入
		BufferBlock insert(byte[] insertKey, int blockOffset, int offset){
			try {
				int keyNum = block.getInt(1, 4); //获取路标数
				int i;
				for (i = 0; i < keyNum; i++){
					int pos = 9 + POINTERLENGTH + i * (myIndex.columnLength + POINTERLENGTH);
					if (compareTo(insertKey, block.getBytes(pos, myIndex.columnLength)) < 0) 
						break; //找到了分支位置
				}
				
				//获取分支子块的标号
				int nextBlockNum = block.getInt(9 + i * (myIndex.columnLength + POINTERLENGTH), POINTERLENGTH);
				BufferBlock nextBlock = pffh.getBlock(nextBlockNum);
				//将这个子块进行节点包装
				Node nextNode;
				if (nextBlock.getData()[0] == 'I') 
					nextNode = new InternalNode(nextBlock, true);  
				else 
					nextNode = new LeafNode(nextBlock, true);
				
				return nextNode.insert(insertKey, blockOffset, offset); //进入中间节点的递归查找
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}
		
		//插入过程中有时需要对中间节点进行分支，branchKey为要新插入的路标，leftChild为新路标的左子节点（也就是已经存在的节点），rightChild为新路标的右子节点
		BufferBlock branchInsert(byte[] branchKey, Node leftChild, Node rightChild){
			try {
				int keyNum = block.getInt(1, 4);//获取路标数
				
				if (keyNum == 0){ //全新节点
					keyNum++;
					block.setInt(1, 4, keyNum);
					block.setBytes(9 + POINTERLENGTH, branchKey); 
					block.setInt(9, POINTERLENGTH, leftChild.block.getPageNum());
					block.setInt(9 + POINTERLENGTH + branchKey.length, POINTERLENGTH, rightChild.block.getPageNum());
					
					return this.block; //将该节点块返回作为新根块
				}
				
				if(++keyNum > MAX_CHILDREN_FOR_INTERNAL){  //分裂节点
					boolean half = false; //因为不能创建新内存空间，只能用这种麻烦的方法来分配子块的那些路标和指针
					//创建一个新块并包装
					int newBlockOffset = pffh.getPageNum();
					int blockNum = pffh.addPage();
					BufferBlock newBlock = pffh.getBlock(blockNum);
					myIndex.blockNum++;
					InternalNode newNode = new InternalNode(newBlock);
					
					//我们知道新插入路标使超过了上限，也就是现有路标为MAX+1，我们总是为原来的块保留MIN个路标，这样新开的块有MAX+1-MIN个路标
					block.setInt(1, 4, MIN_CHILDREN_FOR_INTERNAL);
					newBlock.setInt(1, 4, MAX_CHILDREN_FOR_INTERNAL+1-MIN_CHILDREN_FOR_INTERNAL);
					
					for(int i = 0; i < MIN_CHILDREN_FOR_INTERNAL; i++) { //假如新路标需插在原来的块也就是在MIN之内
						int pos = 9 + POINTERLENGTH + i * (myIndex.columnLength + POINTERLENGTH);
						if (compareTo(branchKey, block.getBytes(pos, myIndex.columnLength)) < 0){	//找到了路标插入的位置
									
							System.arraycopy(block.getData(),  //把第MIN_CHILDREN_FOR_INTERNA条开始的记录copy到新block
									9 + (MIN_CHILDREN_FOR_INTERNAL) * (myIndex.columnLength + POINTERLENGTH), 
									newBlock.getData(), 
									9, 
									POINTERLENGTH + (MAX_CHILDREN_FOR_INTERNAL - MIN_CHILDREN_FOR_INTERNAL) * (myIndex.columnLength + POINTERLENGTH));	
							System.arraycopy(block.getData(),  //给新插入条留出位置
									9 + POINTERLENGTH + i * (myIndex.columnLength + POINTERLENGTH), 
									block.getData(), 
									9 + POINTERLENGTH + (i + 1) * (myIndex.columnLength + POINTERLENGTH),
									//注意还要把原块的最后一个路标保留（不用于自身，但要作为父块的新插入路标）
									(MIN_CHILDREN_FOR_INTERNAL - 1 - i) * (myIndex.columnLength + POINTERLENGTH) + myIndex.columnLength);			
							//设置新插入信息
							block.setInternalKey(9 + POINTERLENGTH + i * (myIndex.columnLength + POINTERLENGTH), branchKey, rightChild.block.getPageNum());				
												
							half=true;
							break;
						}
					}				
					if(!half){ //新路标需插在新开的块也就是它的位置超出了MIN
						System.arraycopy(block.getData(),  //把第MIN_CHILDREN_FOR_INTERNA+1条开始的记录copy到新block
								9 + (MIN_CHILDREN_FOR_INTERNAL + 1) * (myIndex.columnLength + POINTERLENGTH), 
								newBlock.getData(),
								9,
								POINTERLENGTH + (MAX_CHILDREN_FOR_INTERNAL - MIN_CHILDREN_FOR_INTERNAL - 1) * (myIndex.columnLength + POINTERLENGTH));
						for(int i = 0; i < MAX_CHILDREN_FOR_INTERNAL - MIN_CHILDREN_FOR_INTERNAL - 1; i++){
							int pos = 9 + POINTERLENGTH + i * (myIndex.columnLength + POINTERLENGTH);
							if (compareTo(branchKey, newBlock.getBytes(pos, myIndex.columnLength)) < 0){
								System.arraycopy(newBlock.getData(),  //给新插入条留出位置
										9 + POINTERLENGTH + i * (myIndex.columnLength + POINTERLENGTH),
										newBlock.getData(), 
										9 + POINTERLENGTH + (i + 1) * (myIndex.columnLength + POINTERLENGTH),
										(MAX_CHILDREN_FOR_INTERNAL - MIN_CHILDREN_FOR_INTERNAL - 1 - i) * (myIndex.columnLength + POINTERLENGTH));
								//设置新插入信息
								newBlock.setInternalKey(9 + POINTERLENGTH + i * (myIndex.columnLength + POINTERLENGTH), branchKey,rightChild.block.getPageNum());
								break;							
							}	
						}
					}
					
					//找出原块与新块之间的路标，提供给父节点做分裂用
					byte[] spiltKey = block.getBytes(9 + POINTERLENGTH + (MIN_CHILDREN_FOR_INTERNAL) * (myIndex.columnLength + POINTERLENGTH), myIndex.columnLength);				
					
					//更新新块的子块的父亲
					for (int j = 0; j <= newBlock.getInt(1, 4); j++){
						int childBlockNum = newBlock.getInt(9 + j * (myIndex.columnLength + POINTERLENGTH), POINTERLENGTH);
						pffh.getBlock(childBlockNum).setInt(5, POINTERLENGTH, newBlockOffset);					
					}	
					
					int parentBlockNum;
					BufferBlock ParentBlock;
					InternalNode ParentNode;
					if (block.getData()[5] == '$'){  //没有父节点，则创建父节点
						//创建新块并包装
						parentBlockNum = pffh.addPage();
						ParentBlock = pffh.getBlock(parentBlockNum);
						myIndex.blockNum++;
						
						//设置父块信息
						block.setInt(5, POINTERLENGTH, parentBlockNum);
						newBlock.setInt(5, POINTERLENGTH, parentBlockNum);
						
						ParentNode=new InternalNode(ParentBlock);
					}
					else{
						parentBlockNum = block.getInt(5, POINTERLENGTH);				
						newBlock.setInt(5, POINTERLENGTH, parentBlockNum); //新块的父亲也就是旧块的父亲
						ParentBlock = pffh.getBlock(parentBlockNum);	
						ParentNode=new InternalNode(ParentBlock, true);
					}
					
					//读出父亲块并为其包装，然后再递归分裂
							
					return  ParentNode.branchInsert(spiltKey, this, newNode);//((InternalNode)createNode(ParentBlock)).branchInsert(spiltKey, this, newNode);
				}
				
				else{  //不需要分裂节点时
					int i;
					for(i = 0;i < keyNum - 1; i++){
						int pos = 9 + POINTERLENGTH + i * (myIndex.columnLength + POINTERLENGTH);
						if (compareTo(branchKey, block.getBytes(pos, myIndex.columnLength)) < 0) { //找到插入的位置
							System.arraycopy(block.getData(),
											9 + POINTERLENGTH + i * (myIndex.columnLength + POINTERLENGTH), 
											block.getData(), 
											9 + POINTERLENGTH + (i + 1) * (myIndex.columnLength + POINTERLENGTH), 
											(keyNum - 1 - i) * (myIndex.columnLength + POINTERLENGTH));
							
							block.setInternalKey(9 + POINTERLENGTH + i * (myIndex.columnLength + POINTERLENGTH), branchKey, rightChild.block.getPageNum());
							block.setInt(1, 4, keyNum);
							
							return null;
						}					
					}
					if(i == keyNum-1) {
							block.setInternalKey(9 + POINTERLENGTH + i * (myIndex.columnLength + POINTERLENGTH), branchKey, rightChild.block.getPageNum());
							block.setInt(1, 4, keyNum);			
							return null;							
					}
				}	
				return null;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}
		
		//以中间节点为单位的查找
		RID searchKey(byte[] key){
			try {
				int keyNum = block.getInt(1, 4);
				int i;
				for(i = 0; i < keyNum; i++){
					int pos = 9 + POINTERLENGTH + i * (myIndex.columnLength + POINTERLENGTH);
					if (compareTo(key, block.getBytes(pos, myIndex.columnLength)) < 0) 
						break;
				}
				int nextBlockNum = block.getInt(9 + i * (myIndex.columnLength + POINTERLENGTH), POINTERLENGTH);
				BufferBlock nextBlock = pffh.getBlock(nextBlockNum);
				//根据块类别进行包装
				Node nextNode;
				if (nextBlock.getData()[0] == 'I') 
					nextNode = new InternalNode(nextBlock, true); 
				else 
					nextNode = new LeafNode(nextBlock, true);
				return nextNode.searchKey(key); //递归查找
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}

		//以中间节点为单位的删除
		BufferBlock delete(byte[] deleteKey){
			try {
				int keyNum = block.getInt(1, 4);
				int i;
				for(i = 0; i < keyNum; i++){
					int pos = 9 + POINTERLENGTH + i * (myIndex.columnLength + POINTERLENGTH);
					if (compareTo(deleteKey, block.getBytes(pos, myIndex.columnLength)) < 0) 
						break;
				}
				int nextBlockNum = block.getInt(9 + i * (myIndex.columnLength + POINTERLENGTH), POINTERLENGTH);
				BufferBlock nextBlock = pffh.getBlock(nextBlockNum);
				Node nextNode;
				if (nextBlock.getData()[0] == 'I') 
					nextNode = new InternalNode(nextBlock, true); 
				else 
					nextNode = new LeafNode(nextBlock, true);
				
				return nextNode.delete(deleteKey); //递归删除
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}
	
		//删除过程中产生的节点合并，this块和after块以及它们之间的unionKey
		BufferBlock union(byte[] unionKey, BufferBlock afterBlock){
			try {
				int keyNum = block.getInt(1, 4);
				int afterkeyNum = afterBlock.getInt(1, 4);
				
				//将after块中的信息拷贝到this块的后面，注意留一个位置给unionKey
				System.arraycopy(afterBlock.getData(),
						9,
						block.getData(),
						9 + (keyNum + 1) * (myIndex.columnLength + POINTERLENGTH),
						POINTERLENGTH + afterkeyNum * (myIndex.columnLength + POINTERLENGTH));
				
				//填入unionKey
				block.setBytes(9 + keyNum * (myIndex.columnLength + POINTERLENGTH) + POINTERLENGTH, unionKey);
				
				//重新计算路标大小（原路标数+after路标数+unionKey）
				keyNum = keyNum + afterkeyNum + 1;		
				block.setInt(1, 4, keyNum);
				
				//找到父块
				int parentBlockNum = block.getInt(5, POINTERLENGTH);
				BufferBlock parentBlock = pffh.getBlock(parentBlockNum); 
				
				//请bufferBlock销毁after块
			//	afterBlock.isvalid=false;
				myIndex.blockNum--;
				
				//在父块中删除after块
				return (new InternalNode(parentBlock, true)).delete(afterBlock);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}
		
		//删除过程中产生的兄弟块内容重排，this块和after块以及它们之间的internalKey,返回的changeKey是为了更新父块中它们两指针中间的键值
		byte[] rearrangeAfter(BufferBlock siblingBlock, byte[] InternalKey){ //兄弟节点在其后
			try {
				int siblingKeyNum = siblingBlock.getInt(1, 4);
				int keyNum = block.getInt(1, 4);
				
				//要转移的一条指针内容
				int blockOffset = siblingBlock.getInt(9,POINTERLENGTH);
				//将internalKey和兄弟块的第一条指针复制到this块的尾部，路标数加1
				block.setInternalKey(9 + POINTERLENGTH + keyNum * (myIndex.columnLength + POINTERLENGTH), InternalKey, blockOffset);
				keyNum++;
				block.setInt(1, 4, keyNum);
				
				//兄弟块的路标数减1，获取兄弟块的第一条键值作为更新父块的键值，再将兄弟块后面的信息向前挪一条指针和路标的距离
				siblingKeyNum--;
				siblingBlock.setInt(1, 4, siblingKeyNum);
				byte[] changeKey = siblingBlock.getBytes(9 + POINTERLENGTH, myIndex.columnLength);
				System.arraycopy(siblingBlock.getData(), 9 + POINTERLENGTH + myIndex.columnLength, siblingBlock.getData(), 9, POINTERLENGTH + siblingKeyNum * (POINTERLENGTH + myIndex.columnLength));
						
				return changeKey;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}

		byte[] rearrangeBefore(BufferBlock siblingBlock, byte[] internalKey){ //兄弟节点在其前
			try {
				int siblingKeyNum = siblingBlock.getInt(1, 4);
				int keyNum = block.getInt(1, 4);
				
				siblingKeyNum--;
				siblingBlock.setInt(1, 4, siblingKeyNum);
				
				//兄弟块的最后一条路标作为父块的更新路标，最后一条指针将转移给this块
				byte[] changeKey = siblingBlock.getBytes(9 + POINTERLENGTH + siblingKeyNum * (POINTERLENGTH + myIndex.columnLength), myIndex.columnLength);	
				int blockOffset = siblingBlock.getInt(9 + (siblingKeyNum + 1) * (POINTERLENGTH + myIndex.columnLength), POINTERLENGTH);
				
				//给新指针和路标让出位置
				System.arraycopy(block.getData(), 9, block.getData(), 9 + POINTERLENGTH + myIndex.columnLength, POINTERLENGTH + keyNum * (POINTERLENGTH + myIndex.columnLength));
				block.setInt(9, POINTERLENGTH, blockOffset); //插入从兄弟块挪来的这条指针
				block.setBytes(9 + POINTERLENGTH, internalKey); //插入internalKey
				keyNum++;
				block.setInt(1, 4, keyNum);
						
				return changeKey;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}

		//修改posBlockNum标号后面的路标
		public void exchange(byte[] changeKey, int posBlockNum){
			try {
				int keyNum = block.getInt(1, 4);
				int i;
				for(i = 0; i < keyNum; i++){
					int pos = 9 + i * (myIndex.columnLength + POINTERLENGTH);
					int blockNum = block.getInt(pos, POINTERLENGTH);
					if (blockNum == posBlockNum) 
						break;
				}
				
				if (i < keyNum) 
					block.setBytes(9 + i * (myIndex.columnLength + POINTERLENGTH) + POINTERLENGTH, changeKey);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		//在中间节点中删除一个子块信息（以及他前面的那条路标）
		BufferBlock	delete(BufferBlock blk){
			try {
				int keyNum = block.getInt(1, 4);
				
				for(int i = 0;i <= keyNum; i++){
					int pos = 9 + i * (myIndex.columnLength + POINTERLENGTH);
					int ptr = block.getInt(pos, POINTERLENGTH);
					if (ptr == blk.getPageNum()){ //如果找到了子块标号
						System.arraycopy(block.getData(), //把这条标号和前面的路标都移除
								9 + POINTERLENGTH + (i - 1) * (myIndex.columnLength + POINTERLENGTH), 
								block.getData(), 
								9 + POINTERLENGTH + i * (myIndex.columnLength + POINTERLENGTH), 
								(keyNum - i) * (myIndex.columnLength + POINTERLENGTH));
						keyNum--;
						block.setInt(1, 4, keyNum);
				
						if (keyNum >= MIN_CHILDREN_FOR_INTERNAL) 
							return null; //? //移除后直接结束
				
						if (block.getData()[5] == '$'){
							if (keyNum == 0){	//没有路标，只有一个子块标号时，把它的子块作为根块，把this块删除
								//block.isvalid=false;
								myIndex.blockNum--;
								try {
									return pffh.getBlock(block.getInt(9, POINTERLENGTH));
								} catch (Exception err) {
									System.out.println("ERROR!");
								}
							}
							return null;
						}
				
						//找到父亲块
						int parentBlockNum = block.getInt(5, POINTERLENGTH);
						BufferBlock parentBlock = null;
						try {
							parentBlock = pffh.getBlock(parentBlockNum);
						} catch (Exception err) {
							System.out.println("ERROR!");
						}
						int parentKeyNum = 0;
						try {
							parentKeyNum = parentBlock.getInt(1, 4);
						} catch (Exception err) {
							System.out.println("ERROR");
						}
						
						int sibling;
						BufferBlock siblingBlock = null;
						int j;
						for(j = 0; j < parentKeyNum; j++){
							int ppos = 9 + j * (myIndex.columnLength + POINTERLENGTH);
							if (block.getPageNum() == parentBlock.getInt(ppos, POINTERLENGTH)){ 
								//读到后续兄弟块
								sibling = parentBlock.getInt(ppos + POINTERLENGTH + myIndex.columnLength, POINTERLENGTH);
								try {
									siblingBlock = pffh.getBlock(sibling);
								} catch (Exception err) {
									System.out.println("ERROR!");
								}
									
								byte[] unionKey = parentBlock.getBytes(ppos + POINTERLENGTH, myIndex.columnLength);
								
								//能够合并
								if ((siblingBlock.getInt(1, 4) + keyNum) <= MAX_CHILDREN_FOR_INTERNAL){				
									return this.union(unionKey, siblingBlock);
								}
								
								//两个节点原来都是MIN_FOR_LEAF，delete一个以后的情况没考虑，这个时候会涉及三个节点
								//这种情况没有处理，要处理的话将涉及到4个兄弟块，因为this块有MIN-1个路标，兄弟块有MIN个路标，向兄弟块挪一个路标显然是白费力气，
								//但是它们却也不一定能够合并，所以很麻烦
								if (siblingBlock.getInt(1, 4) == MIN_CHILDREN_FOR_INTERNAL) 
									return null;
								
								//不能合并，通过从兄弟块转移一个路标来满足B+树路标最小限制
								(new InternalNode(parentBlock, true)).exchange(rearrangeAfter(siblingBlock, unionKey), block.getPageNum());//blockOffset请bufferManager务必设计好
								return null;					
							}				
						}
						
						//找不后续块，只能找前续兄弟块
						sibling = parentBlock.getInt(9 + (parentKeyNum - 1) * (myIndex.columnLength + POINTERLENGTH), POINTERLENGTH);
						siblingBlock = pffh.getBlock(sibling);		
						byte[] unionKey = parentBlock.getBytes(9 + (parentKeyNum - 1) * (myIndex.columnLength + POINTERLENGTH) + POINTERLENGTH, myIndex.columnLength);					
						//能够合并
						if ((siblingBlock.getInt(1, 4) + keyNum) <= MAX_CHILDREN_FOR_INTERNAL) {
							return (new InternalNode(siblingBlock, true)).union(unionKey, block);
						}
							
						//没有考虑的情况
						if (siblingBlock.getInt(1, 4) == MIN_CHILDREN_FOR_INTERNAL) 
							return null;
						
						//重排的情况
						(new InternalNode(parentBlock, true)).exchange(rearrangeBefore(siblingBlock, unionKey), sibling);
						return null;
					}
				}		
				return null;
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}
	}
	
	//叶子节点类
	class LeafNode extends Node{
				
		LeafNode(BufferBlock blk){
			try {
				block = blk;
				
		    	block.getData()[0] = 'L';  //标识为叶子块
		    	int i = 5;
				block.setInt(1, 4, 0);//现在共有0个key值//现在共有0个key值
		    	for(; i < 9; i++)
		    		block.getData()[i] = '$';  //没有父块
		    	for(; i < 13; i++)
		    		block.getData()[i] = '&';  //最后一块叶子块的下一块叶子块标号，相当于null
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		LeafNode(BufferBlock blk, boolean t){
			block = blk;
		}

		//以叶子节点为单位的索引的插入
		BufferBlock insert(byte[] insertKey, int blockOffset, int offset){//键值 块号 块中偏移
			try {
				int keyNum = block.getInt(1, 4);
				if (++keyNum > MAX_FOR_LEAF){  //分裂节点
					boolean half = false;
					BufferBlock newBlock = pffh.getBlock(pffh.addPage());
	//				BufferBlock newBlock = BufferManager.createBlock(filename, myIndex.blockNum);
	//				addIndexBlockNum(myIndex);
					myIndex.blockNum++;
					LeafNode newNode = new LeafNode(newBlock);
					
					for (int i = 0; i < MIN_FOR_LEAF - 1; i++){ //插入原块
						int pos = 17 + i * (myIndex.columnLength + 8);
						if (compareTo(insertKey, block.getBytes(pos, myIndex.columnLength)) < 0){					
							System.arraycopy(block.getData(),  //把第MIN_FOR_LEAF-1条开始的记录copy到新block
									9 + (MIN_FOR_LEAF - 1) * (myIndex.columnLength + 8), 
									newBlock.getData(), 
									9, 
									POINTERLENGTH + (MAX_FOR_LEAF - MIN_FOR_LEAF + 1) * (myIndex.columnLength + 8));	
							System.arraycopy(block.getData(),  //给新插入条留出位置
									9 + i * (myIndex.columnLength + 8), 
									block.getData(), 
									9 + (i + 1) * (myIndex.columnLength + 8),
									POINTERLENGTH + (MIN_FOR_LEAF - 1 - i) * (myIndex.columnLength + 8));	
							//插入新内容
							block.setKeyValues(9 + i * (myIndex.columnLength + 8), insertKey, blockOffset, offset);
							half = true;
							break;
						}
					}				
					if(!half){ //插入新块
						System.arraycopy(block.getData(),  //把第MIN_FOR_LEAF条开始的记录copy到新block
								9+(MIN_FOR_LEAF)*(myIndex.columnLength+8), 
								newBlock.getData(), 
								9, 
								POINTERLENGTH+(MAX_FOR_LEAF-MIN_FOR_LEAF)*(myIndex.columnLength+8));
						int i=0;
						for(;i<MAX_FOR_LEAF-MIN_FOR_LEAF;i++){
							int pos = 17 + i * (myIndex.columnLength + 8);
							if(compareTo(insertKey,newBlock.getBytes(pos,myIndex.columnLength)) < 0){
								System.arraycopy(newBlock.getData(),  //给新插入条留出位置
										9+i*(myIndex.columnLength+8), 
										newBlock.getData(), 
										9+(i+1)*(myIndex.columnLength+8), 
										POINTERLENGTH+(MAX_FOR_LEAF-MIN_FOR_LEAF-i)*(myIndex.columnLength+8));								
								
								//插入新内容
								newBlock.setKeyValues(9+i*(myIndex.columnLength+8),insertKey,blockOffset,offset);
								break;
							}	
						}
						if(i==MAX_FOR_LEAF-MIN_FOR_LEAF){
							System.arraycopy(newBlock.getData(),  //给新插入条留出位置
									9+i*(myIndex.columnLength+8), 
									newBlock.getData(), 
									9+(i+1)*(myIndex.columnLength+8), 
									POINTERLENGTH+(MAX_FOR_LEAF-MIN_FOR_LEAF-i)*(myIndex.columnLength+8));								
							
							//插入新内容
							newBlock.setKeyValues(9+i*(myIndex.columnLength+8),insertKey,blockOffset,offset);
						}
					}
					
					block.setInt(1,4,MIN_FOR_LEAF);
				    newBlock.setInt(1,4,MAX_FOR_LEAF+1-MIN_FOR_LEAF);
				    
				    //原块最后添上新块的块号，以做成所有叶子节点的一串链表
				    block.setInt(9+MIN_FOR_LEAF*(myIndex.columnLength+8), POINTERLENGTH, newBlock.getPageNum());
					
				    int parentBlockNum;
				    BufferBlock ParentBlock;
				    InternalNode ParentNode;
					if(block.getData()[5]=='$'){  //没有父节点，则创建父节点
						parentBlockNum = pffh.addPage();
						ParentBlock = pffh.getBlock(parentBlockNum);
	//				or	ParentBlock = pffh.getBlock(pffh.addPage());
	//					ParentBlock=BufferManager.createBlock(filename, parentBlockNum);
					
	//					addIndexBlockNum(myIndex.indexName);
						myIndex.blockNum++;
			
						block.setInt(5, POINTERLENGTH, parentBlockNum);
						newBlock.setInt(5, POINTERLENGTH, parentBlockNum );
						ParentNode=new InternalNode(ParentBlock);
					}
					else{
						parentBlockNum=block.getInt(5,POINTERLENGTH);				
						newBlock.setInt(5, POINTERLENGTH, parentBlockNum); //新节点的父亲也就是旧节点的父亲
						ParentBlock = pffh.getBlock(parentBlockNum);
						ParentNode=new InternalNode(ParentBlock,true);
					}
				
					//将branchKey(新块的第一个索引键值)提交，让父块分裂出它们两个块
					byte[] branchKey=newBlock.getBytes(17, myIndex.columnLength);
					return  ParentNode.branchInsert(branchKey, this, newNode);
				}			
				else{  //不需要分裂节点时
					if(keyNum-1==0){
						System.arraycopy(block.getData(),
								9, 
								block.getData(), 
								9+(myIndex.columnLength+8), 
								POINTERLENGTH);
				
						block.setKeyValues(9,insertKey,blockOffset,offset);						
						block.setInt(1, 4, keyNum);
				
						return null;
					}
					int i; 
					for(i=0;i<keyNum;i++){
						int pos=17+i*(myIndex.columnLength+8);
						
						if(compareTo(insertKey,block.getBytes(pos,myIndex.columnLength))==0){ //已有键值则更新
							block.setKeyValues(9+i*(myIndex.columnLength+8),insertKey,blockOffset,offset);
							return null;
						}
						
						if(compareTo(insertKey,block.getBytes(pos,myIndex.columnLength)) < 0){ //找到插入的位置
							System.arraycopy(block.getData(),
											9+i*(myIndex.columnLength+8), 
											block.getData(), 
											9+(i+1)*(myIndex.columnLength+8), 
											POINTERLENGTH+(keyNum-1-i)*(myIndex.columnLength+8));
							
							block.setKeyValues(9+i*(myIndex.columnLength+8),insertKey,blockOffset,offset);						
							block.setInt(1, 4, keyNum);
							
							return null;
						}					
					}
					if(i==keyNum){
						System.arraycopy(block.getData(),
								9+(i-1)*(myIndex.columnLength+8), 
								block.getData(), 
								9+i*(myIndex.columnLength+8), 
								POINTERLENGTH);
				
						block.setKeyValues(9+(i-1)*(myIndex.columnLength+8),insertKey,blockOffset,offset);						
						block.setInt(1, 4, keyNum);
				
						return null;
					}
				}
			    return null;	
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}
		
		//以叶子节点为单位的查找索引
		RID searchKey(byte[] originalkey){
			try {
				int keyNum=block.getInt(1, 4); 
				if(keyNum==0) return null; //空块则返回null
			
				byte[] key=new byte[myIndex.columnLength];
				
				int i=0;
				for(;i<originalkey.length;i++){
					key[i]=originalkey[i];
				}
				
			    for(;i<myIndex.columnLength;i++){
					key[i]='&';
				}
				
				//二分查找
				int start=0;
				int end=keyNum-1;
				int middle=0;

				while (start <= end) {  

					middle = (start + end) / 2;
									
        	        byte[] middleKey = block.getBytes(17+middle*(myIndex.columnLength+8), myIndex.columnLength);  
        	        if (compareTo(key,middleKey) == 0){  
        	            break;  
        	        }  
        	          
        	        if (compareTo(key,middleKey) < 0) {  
        	            end = middle-1;  
        	        } else {  
        	            start = middle+1;  
        	        }  
        	        
        	    }  
        	      			
				int pos=9+middle*(myIndex.columnLength+8);
				byte[] middleKey = block.getBytes(8+pos, myIndex.columnLength); 
				
				//将找到的位置上的表文件偏移信息存入off中，返回给上级调用
        	    RID off = new RID();
        	  
        	    off.setPageNum(block.getInt(pos, 4));
        	    off.setSlotNum(block.getInt(pos + 4, 4));
						
        	    return compareTo(middleKey,key) == 0 ? off : null;   //再次确认有没有找到这个索引键值，没有则返回null
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}

		//以叶子节点为单位的块合并
		BufferBlock union(BufferBlock afterBlock){
			try {
				int keyNum = block.getInt(1, 4);
				int afterkeyNum= afterBlock.getInt(1, 4);
				
				//将after块的内容复制到this块的后面
				System.arraycopy(afterBlock.getData(),9,block.getData(),9+keyNum*(myIndex.columnLength+8),POINTERLENGTH+afterkeyNum*(myIndex.columnLength+8));
				
				//更新索引数量
				keyNum+=afterkeyNum;		
				block.setInt(1, 4, keyNum);
							
				//请bufferManager销毁after块
				//afterBlock.isvalid=false;
				myIndex.blockNum--;
				
				//在父节点中删除这个被废弃的after块的信息(标号及它前面的路标)
				int parentBlockNum=block.getInt(5, POINTERLENGTH);
				BufferBlock parentBlock = pffh.getBlock(parentBlockNum); 
				
				return (new InternalNode(parentBlock,true)).delete(afterBlock);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}
		
		//从兄弟节点挪来一条索引进行重排
		byte[] rearrangeAfter(BufferBlock siblingBlock){ //兄弟节点在其后
			try {
				int siblingKeyNum=siblingBlock.getInt(1, 4);
				int keyNum = block.getInt(1, 4);
				
				//要挪过来的索引信息
				int blockOffset=siblingBlock.getInt(9, 4);
				int offset=siblingBlock.getInt(13, 4);
				byte[] Key=siblingBlock.getBytes(17, myIndex.columnLength);
				
				//更新兄弟块的内容
				siblingKeyNum--;
				siblingBlock.setInt(1, 4, siblingKeyNum);
				System.arraycopy(siblingBlock.getData(), 9+8+myIndex.columnLength, siblingBlock.getData(), 9, POINTERLENGTH+siblingKeyNum*(8+myIndex.columnLength));
				
				//this块和兄弟块之间的新路标
				byte[] changeKey=siblingBlock.getBytes(17, myIndex.columnLength);
				
				//添加挪来的索引
				block.setKeyValues(9+keyNum*(myIndex.columnLength+8), Key, blockOffset, offset);
				keyNum++;
				block.setInt(1, 4, keyNum);
				//注意不要漏了链表信息(下一块的标号)
				block.setInt(9+keyNum*(myIndex.columnLength+8), POINTERLENGTH, siblingBlock.getPageNum());
				
				return changeKey;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}
		
		byte[] rearrangeBefore(BufferBlock siblingBlock){  //兄弟节点在其前
			try {
				int siblingKeyNum=siblingBlock.getInt(1, 4);
				int keyNum = block.getInt(1, 4);
				
				siblingKeyNum--;
				siblingBlock.setInt(1, 4, siblingKeyNum);
				
				//要挪来的索引信息
				int blockOffset=siblingBlock.getInt(9+siblingKeyNum*(myIndex.columnLength+8), 4);
				int offset=siblingBlock.getInt(13+siblingKeyNum*(myIndex.columnLength+8), 4);
				byte[] Key=siblingBlock.getBytes(17+siblingKeyNum*(myIndex.columnLength+8), myIndex.columnLength);
				
				//保存好链表信息
				siblingBlock.setInt(9+siblingKeyNum*(myIndex.columnLength+8), POINTERLENGTH, block.getPageNum());
				
				//挪出新索引位置
				System.arraycopy(block.getData(), 9, block.getData(), 9+8+myIndex.columnLength, POINTERLENGTH+keyNum*(8+myIndex.columnLength));
				block.setKeyValues(9, Key, blockOffset, offset); //插入新索引
				keyNum++;
				block.setInt(1, 4, keyNum);
				
				//需要父块的更新成的新路标
				byte[] changeKey=block.getBytes(17, myIndex.columnLength);
				
				return changeKey;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}
		
		//以叶子节点为单位的索引删除
		BufferBlock delete(byte[] deleteKey){
			try {	
				int keyNum = block.getInt(1, 4);
				
				for(int i=0;i<keyNum;i++){
					int pos=17+i*(myIndex.columnLength+8);
					
					if(compareTo(deleteKey,block.getBytes(pos,myIndex.columnLength))<0){ //没找到索引键值
						System.out.println("没有该索引键值");
						return null;
					}
					
					if(compareTo(deleteKey,block.getBytes(pos, myIndex.columnLength)) == 0){ //找到对应的键值					
						
						System.arraycopy(block.getData(), //移除这条索引
										9+(i+1)*(myIndex.columnLength+8), 
										block.getData(), 
										9+i*(myIndex.columnLength+8), 
										POINTERLENGTH+(keyNum-1-i)*(myIndex.columnLength+8));
						keyNum--;
						block.setInt(1, 4, keyNum);
						
						if(keyNum >=MIN_FOR_LEAF) return null; //仍然满足数量要求
						
						if(block.getData()[5]=='$') return null;  //没有父块，本身为根
						
						boolean lastFlag=false;
						if(block.getData()[9+keyNum*(myIndex.columnLength+8)]=='&') lastFlag=true; //叶子块链表的最后一块
						
						int sibling=block.getInt(9+keyNum*(myIndex.columnLength+8), POINTERLENGTH);
						BufferBlock siblingBlock = pffh.getBlock(sibling);
						int parentBlockNum=block.getInt(5, POINTERLENGTH);
						
						if(lastFlag || siblingBlock==null || siblingBlock.getInt(5, POINTERLENGTH)!=parentBlockNum/*虽然有后续块但不是同一个父亲的兄弟块*/){  //没有找到后续兄弟节点
							//通过父块找前续兄弟块
							BufferBlock parentBlock = pffh.getBlock(parentBlockNum);
							int j=0;
							int parentKeyNum=parentBlock.getInt(1, 4);
							for(;j<parentKeyNum;j++){
								int ppos=9+POINTERLENGTH+j*(myIndex.columnLength+POINTERLENGTH);
								if(compareTo(deleteKey,parentBlock.getBytes(ppos, myIndex.columnLength))<0){
									sibling=parentBlock.getInt(ppos-2*POINTERLENGTH-myIndex.columnLength, POINTERLENGTH);
									siblingBlock = pffh.getBlock(sibling);
									break;
								}
							}
							
							//可以合并
							if((siblingBlock.getInt(1, 4)+keyNum)<=MAX_FOR_LEAF){
								return (new LeafNode(siblingBlock,true)).union(block);
							}
										
							//两个节点原来都是MIN_FOR_LEAF，delete一个以后的情况没考虑，这个时候会涉及三个节点
							if(siblingBlock.getInt(1, 4)==MIN_FOR_LEAF) return null;
								
							//重排
							(new InternalNode(parentBlock,true)).exchange(rearrangeBefore(siblingBlock),sibling);
							return null;
						}
				
						//合并
						if((siblingBlock.getInt(1, 4)+keyNum)<=MAX_FOR_LEAF){
							return this.union(siblingBlock);
						}
						
						//没考虑的情况
						if(siblingBlock.getInt(1, 4)==MIN_FOR_LEAF) return null;
						
						//重排
						BufferBlock parentBlock = pffh.getBlock(parentBlockNum);
						(new InternalNode(parentBlock,true)).exchange(rearrangeAfter(siblingBlock),block.getPageNum());//blockOffset请bufferManager务必设计好
						return null;
					}
				}
				
				return null;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}
	}
	
}
