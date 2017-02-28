package com.hemeng.study.dsa.demo;

import java.util.Random;
import java.math.BigDecimal; 

/**
 *	����ѵ�Ӧ�ã��ҳ�N��Ԫ���еĵ�k���Ԫ��
 *	ͨ���ԱȻ�����ͨ������ʵ�ֵĲ�������ڶ���ѵ�ʵ�ֵĲ��ң����ֺ��ߵ�����ʱ���Ϊǰ��
 *		��ǧ��֮һ��
 */
class BinaryHeapDemoFindKth 
{
	private static final int DEFAULT_SIZE = 30000; 
	private int sampleSize; //��������Ĵ�С
	//long[] sampleArray = new long[sampleSize]; //��������
	private int[] sampleArray;

	public BinaryHeapDemoFindKth (int size) {
		sampleSize = size;
		sampleArray = new int[sampleSize];
		Random mRandom = new Random();
		for(int i=0; i<sampleSize; i++) {
			//sampleArray[i] = mRandom.nextLong();
			sampleArray[i] = mRandom.nextInt(sampleSize); //����ָ����СΪ0��ָ��ֵ��Χ�ڵ������				
		}
		//sampleArray = new int[]{3,4,6,23,4,57,56,76,34,4,2,1,90,24,43};
	}

	public BinaryHeapDemoFindKth () {
		this (DEFAULT_SIZE);
	}

	/**
	 *	���������ҳ���k���Ԫ�أ����ڶ���ѣ�
	 *	@param k ��Ҫ���ҵ����Ĵ�С��������λ��
	 *	@date	2016.03.14
	 */
	public int findKth6B(int k) {
		BinaryHeap kBinaryHeap = new BinaryHeap(k);
		for (int i=0; i<k; i++) {
			kBinaryHeap.insert(sampleArray[i]);
		}

		for (int i=k; i<sampleSize; i++)
		{
			if (sampleArray[i] > kBinaryHeap.findMin())
			{
				kBinaryHeap.deleteMin();
				kBinaryHeap.insert(sampleArray[i]);
			}
		}
		
		return kBinaryHeap.findMin();
	}
	
	/**
	 *	���������ҳ���k���Ԫ�أ�������ͨ�����򷨣�	
	 *	@param k ��Ҫ���ҵ����Ĵ�С��������λ��
	 *	@date	2016.03.14
	 */
	public int findKth1A(int k) {
		int[] mSampleArray = new int[sampleSize];
		for (int i=0; i<sampleSize; i++) {
			mSampleArray[i] = sampleArray[i];
		}
		
		for (int i=0, n=sampleSize-1; i<n; i++)
		{
			for (int j=i+1; j<sampleSize; j++)
			{
				if (mSampleArray[i] < mSampleArray[j]) {
					int temp = mSampleArray[i];
					mSampleArray[i] = mSampleArray[j];
					mSampleArray[j] = temp;
				}
			}
		}

		return mSampleArray[k-1];
	}

	
	/**
	 *	��ӡ���������е�����Ԫ��
	 */
	public void printSampleArray() {
		int n=sampleSize-1;
		for (int i=0; i<n; i++)
		{
			System.out.print(sampleArray[i] + ", ");
		}
		System.out.print(sampleArray[n]);


		System.out.println("\nTotally: " + sampleSize);
	}

	public static void main(String[] args) 
	{ 
		int N = 40000; //�������Ԫ�ص��ܸ���
		int k = N/2; //Ҫ���ҵ�Ԫ�صĴ�С�ڶ��е�Ԫ���е�˳��
		BinaryHeapDemoFindKth mFindKth = new BinaryHeapDemoFindKth(N);
		//mFindKth.printSampleArray();
		int sampleKth=0;
	
		//���ڶ���ѵĲ���
		System.out.print("\n���ڶ���ѵĲ���:\n������е�Ԫ�صĸ�����" + N + "\n��" + k + "�������");	
		double startTimeUs = ((double)System.nanoTime())/1000;    
		sampleKth = mFindKth.findKth6B(k);
		double endTimeUs = ((double)System.nanoTime())/1000; 
		System.out.print(sampleKth + ", ");	
		BigDecimal bgUs = new BigDecimal(endTimeUs-startTimeUs);  //�������3λС��    
		double timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		System.out.print("������ʱ�� "+timeCostUs+"��s\n"); 
		/*for (int i=1; i<=15; i++)
		{
			System.out.print(mFindKth.findKth6B(i) + ", ");	
		}*/
		 
		//������ͨ���򷨵Ĳ���
		System.out.print("\n������ͨ���򷨵Ĳ���:\n������е�Ԫ�صĸ�����" + N + "\n��" + k + "�������");	
		long startTimeMs = System.currentTimeMillis();
		sampleKth = mFindKth.findKth1A(k);
		long endTimeMs=System.currentTimeMillis(); //��ȡ����ʱ��  
		System.out.print(sampleKth + ", ");	
		System.out.println("������ʱ�� "+(endTimeMs-startTimeMs)+"ms\n"); 
		/*for (int i=1; i<=15; i++)
		{
			System.out.print(mFindKth.findKth1A(i) + ", ");	
		}*/


	}
}
