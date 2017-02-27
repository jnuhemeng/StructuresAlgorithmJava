package com.hemeng.study.dsa.binaryheap;

import java.util.Random;
import java.math.BigDecimal; 
/**
 *	����LeftistHeap�����Ӷѣ�������
 *		ͨ���Ƚ϶���ѣ�BinaryHeap�������Ӷѣ�LeftistHeap���ĺϲ���������֤���Ӷ�merge�����Ķ���ʱ�临�Ӷ�
 *	�����������������󣬶���ѵĺϲ���ʱ�������󣬶����Ӷѵĺϲ���ʱ�����Ӳ������磺
 *		�ϲ��������ѵĴ�СΪ1,000,000��2,000,000ʱ���������ʱ������ʱ�䣩448772.282��s�������Ӷѵ���ʱ
 *		��N������ʱ�䣩Ϊ13.688��s��
 */
class LeftistHeapDemo 
{
	public LeftistHeapDemo() {
	}

	/**
	 *	�ϲ����������
	 *	@param bh1 ��Ҫ�ϲ��Ķ����1
	 *	@param bh2 ��Ҫ�ϲ��Ķ����2
	 *	@return �ϲ��õ����¶����
	 *	@date 2016.03.15
	 */
	public BinaryHeap mergeBinaryHeap(BinaryHeap bh1, BinaryHeap bh2) {
		int n = bh2.getCurrentSize();
		int[] array = bh2.getArray();
		for (int i=1; i<=n ; i++)
		{
			bh1.insert(array[i]);
		}
		return bh1;
	}

	/**
	 *	�ϲ��������Ӷ�
	 *	@param lh1 ��Ҫ�ϲ������Ӷ�1
	 *	@param lh2 ��Ҫ�ϲ������Ӷ�2
	 *	@return �ϲ��õ��������Ӷ�
	 *	@date 2016.03.15
	 */
	public LeftistHeap mergeLeftistHeap(LeftistHeap lh1, LeftistHeap lh2) {
		lh1.merge(lh2);
		return lh1;
	}
	
	/**
	 *	����һ������ָ�������Ԫ�صĶ����
	 *	@param n ���������Ķ��������Ԫ�صĸ���
	 *	@date 2016.03.15
	 */
	public BinaryHeap generateBinaryHeap(int n) {
		BinaryHeap mBinaryHeap = new BinaryHeap();
		Random mRandom = new Random();
		for (int i=1; i<=n; i++)
		{
			mBinaryHeap.insert(mRandom.nextInt(n*5)); //ָ��������ķ�ΧΪ(0, 5n)
		}
		return mBinaryHeap;
		
	}
	
	/**
	 *	����һ������ָ�������Ԫ�ص����Ӷ�
	 *	@param n �������������Ӷ�����Ԫ�صĸ���
	 *	@date 2016.03.15
	 */
	public LeftistHeap generateLeftistHeap(int n) {		
		LeftistHeap mLeftistHeap = new LeftistHeap();
		Random mRandom = new Random();
		for (int i=1; i<=n; i++)
		{
			mLeftistHeap.insert(mRandom.nextInt(n*5)); //ָ��������ķ�ΧΪ(0, 5n)
		}
		return mLeftistHeap;
	}

	public static void main(String[] args) 
	{
		//int lhNum1=1000000, lhNum2=2000000, bhNum1=4, bhNum2=5;
		int lhNum1=10, lhNum2=15, bhNum1=4, bhNum2=5;
		bhNum1 = lhNum1;
		bhNum2 = lhNum2;

		LeftistHeapDemo mLeftistHeapDemo = new LeftistHeapDemo();
		BinaryHeap mBinaryHeap = mLeftistHeapDemo.generateBinaryHeap(lhNum1); 
		LeftistHeap mLeftistHeap = mLeftistHeapDemo.generateLeftistHeap(bhNum1);
		BinaryHeap mBinaryHeap2 = mLeftistHeapDemo.generateBinaryHeap(lhNum2); 
		LeftistHeap mLeftistHeap2 = mLeftistHeapDemo.generateLeftistHeap(bhNum2);

		System.out.println("�����1��" + lhNum1);
		mBinaryHeap.printHeap();
		System.out.println("�����2��" + lhNum2);
		mBinaryHeap2.printHeap();

		//��΢�루��s��Ϊ��λ����һ�δ��������ʱ��
		double startTimeUs = ((double)System.nanoTime())/1000;   //��ȡ��ʼʱ��  
		mBinaryHeap = mLeftistHeapDemo.mergeBinaryHeap(mBinaryHeap, mBinaryHeap2); //�ϲ������	
		double endTimeUs = ((double)System.nanoTime())/1000; //��ȡ����ʱ�� 
		BigDecimal bgUs = new BigDecimal(endTimeUs-startTimeUs);  //�������3λС��
        double timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		System.out.println("�ϲ���Ķ���ѣ�");
		mBinaryHeap.printHeap();  //�������ӡ������Ԫ��
		//mBinaryHeap.printSortedHeap(); //�������ӡ������Ԫ��
		System.out.println("�ϲ���ʱ�� "+timeCostUs+"��s\n"); 
		
		
		

		System.out.println("\n���Ӷ�1��" + bhNum1);
		mLeftistHeap.printLeftistHeap();
		System.out.println("\n���Ӷ�2��" + bhNum2);
		mLeftistHeap2.printLeftistHeap();

		//��΢�루��s��Ϊ��λ����һ�δ��������ʱ��
		startTimeUs = ((double)System.nanoTime())/1000;   //��ȡ��ʼʱ��  
		mLeftistHeap = mLeftistHeapDemo.mergeLeftistHeap(mLeftistHeap, mLeftistHeap2); //�ϲ����Ӷ�
		endTimeUs = ((double)System.nanoTime())/1000; //��ȡ����ʱ�� 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  //�������3λС��
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		System.out.println("\n�ϲ�������Ӷѣ�");
		mLeftistHeap.printLeftistHeap(); 
		//mLeftistHeap.printSortedLeftistHeap();
		System.out.println("\n�ϲ���ʱ�� "+timeCostUs+"��s\n"); 	
	}
}
