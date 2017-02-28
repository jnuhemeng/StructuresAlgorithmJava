package com.hemeng.study.dsa.binaryheap;
/**
 * 复习用二叉堆实现优先队列(未完成)
 * @author Administrator
 * @date 2017.02.27
 */
public class BinaryHeapRevise {
	private static final int DEFAULT_CAPACITY = 10;
	private int currentsize;
	private int[] array;
	
	/**
	 * 插入一个新元素
	 * @param element
	 */
	public void insert(int element) {
		if(currentsize >= array.length - 1) {
			enlargeArray(currentsize * 2);
		}
		array[++currentsize] = element;
		///percolateDown(currentsize);
	}
	
	/**
	 * 删除队列中的最小元素
	 * @return
	 */
	public int deleteMin() {
		
		return 0;
	}
	

	/**
	 *	扩大堆的容量（私有方法）
	 *  @param newSize 扩容后堆的容量
	 *	@date 2016.03.14
	 */
	private void enlargeArray(int newSize) {
		if(newSize <= currentsize) {
			return;
		}
		int[] oldArray = array;
		array = new int[newSize];
		for(int i=1;i<=currentsize;i++) {
			array[i] = oldArray[i];
		}
	}
	
}
