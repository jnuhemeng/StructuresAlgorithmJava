package com.hemeng.study.dsa.binaryheap;
/**
 * ��ϰ�ö����ʵ�����ȶ���(δ���)
 * @author Administrator
 * @date 2017.02.27
 */
public class BinaryHeapRevise {
	private static final int DEFAULT_CAPACITY = 10;
	private int currentsize;
	private int[] array;
	
	/**
	 * ����һ����Ԫ��
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
	 * ɾ�������е���СԪ��
	 * @return
	 */
	public int deleteMin() {
		
		return 0;
	}
	

	/**
	 *	����ѵ�������˽�з�����
	 *  @param newSize ���ݺ�ѵ�����
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
