package buffermanager;
import pagedfile.*;
import java.util.LinkedList;
import java.util.Hashtable;
import constant.Constant;
public class Buffer {
	class LRUNode {
		public LRUNode(Integer key_) {
			key = key_;
		}
		public Integer getKey() {
			return key;
		}
		Integer key;
	}
	class Hashitem {
		public Hashitem(LRUNode node_,BufferBlock block_) {
			node = node_;
			block = block_;
		}
		LRUNode node;
		BufferBlock block;
	}
	Integer getHashKey(int fileId, int pageNum) {
		//NOTICE: I don't be confident with the key whether equal to this Integer 
		return new Integer(fileId + Constant.MAX_FILE_NUM*pageNum);
	}
	private void moveFront(LRUNode node) {
		LRUList.remove(node);
		LRUList.addFirst(node);
	}
	
	public boolean isFull() {
		return LRUList.size() == Constant.MAX_BLOCK_NUM;
	}
	public void deleteLRU() {
		//TODO not support unpinned
		Integer key = LRUList.getLast().getKey();
		LRUList.removeLast();
		hashTable.remove(key);
	}
	public void loadBlock(BufferBlock block) {
		Integer key = getHashKey(block.getFileId(),block.getPageNum());
		LRUNode node = new LRUNode(key);
		Hashitem item = new Hashitem(node,block);
		LRUList.addFirst(node);
		hashTable.put(key, item);
	}
	public boolean contains(int fileId, int pageNum) {
		Integer key = getHashKey(fileId, pageNum);
		if (hashTable.containsKey(key)) {
			return true;
		}
		return false;
	}
	public BufferBlock getBlock(int fileId, int pageNum) {
		Integer key = getHashKey(fileId, pageNum);
		if (hashTable.containsKey(key)) {
			Hashitem item = hashTable.get(key);
			moveFront(item.node);
			return item.block;
		}
		return null;
	}
	
	LinkedList<LRUNode> LRUList;
	Hashtable<Integer, Hashitem> hashTable;
}
