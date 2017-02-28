package com.hemeng.study.dsa.demo;

import java.util.Random;
import java.math.BigDecimal; 

/**
 *	二叉堆的应用：找出N个元素中的第k大的元素
 *	通过对比基于普通的排序法实现的查找与基于二叉堆的实现的查找，发现后者的运行时间仅为前者
 *		的千分之一。
 */
class BinaryHeapDemoFindKth 
{
	private static final int DEFAULT_SIZE = 30000; 
	private int sampleSize; //测试数组的大小
	//long[] sampleArray = new long[sampleSize]; //测试数组
	private int[] sampleArray;

	public BinaryHeapDemoFindKth (int size) {
		sampleSize = size;
		sampleArray = new int[sampleSize];
		Random mRandom = new Random();
		for(int i=0; i<sampleSize; i++) {
			//sampleArray[i] = mRandom.nextLong();
			sampleArray[i] = mRandom.nextInt(sampleSize); //生成指定大小为0到指定值范围内的随机数				
		}
		//sampleArray = new int[]{3,4,6,23,4,57,56,76,34,4,2,1,90,24,43};
	}

	public BinaryHeapDemoFindKth () {
		this (DEFAULT_SIZE);
	}

	/**
	 *	从数组中找出第k大的元素（基于二叉堆）
	 *	@param k 需要查找的数的大小在数组中位置
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
	 *	从数组中找出第k大的元素（基于普通的排序法）	
	 *	@param k 需要查找的数的大小在数组中位置
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
	 *	打印测试数组中的所有元素
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
		int N = 40000; //二叉堆中元素的总个数
		int k = N/2; //要查找的元素的大小在堆中的元素中的顺序
		BinaryHeapDemoFindKth mFindKth = new BinaryHeapDemoFindKth(N);
		//mFindKth.printSampleArray();
		int sampleKth=0;
	
		//基于二叉堆的查找
		System.out.print("\n基于二叉堆的查找:\n二叉堆中的元素的个数：" + N + "\n第" + k + "大的数：");	
		double startTimeUs = ((double)System.nanoTime())/1000;    
		sampleKth = mFindKth.findKth6B(k);
		double endTimeUs = ((double)System.nanoTime())/1000; 
		System.out.print(sampleKth + ", ");	
		BigDecimal bgUs = new BigDecimal(endTimeUs-startTimeUs);  //结果保留3位小数    
		double timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		System.out.print("查找用时： "+timeCostUs+"μs\n"); 
		/*for (int i=1; i<=15; i++)
		{
			System.out.print(mFindKth.findKth6B(i) + ", ");	
		}*/
		 
		//基于普通排序法的查找
		System.out.print("\n基于普通排序法的查找:\n二叉堆中的元素的个数：" + N + "\n第" + k + "大的数：");	
		long startTimeMs = System.currentTimeMillis();
		sampleKth = mFindKth.findKth1A(k);
		long endTimeMs=System.currentTimeMillis(); //获取结束时间  
		System.out.print(sampleKth + ", ");	
		System.out.println("查找用时： "+(endTimeMs-startTimeMs)+"ms\n"); 
		/*for (int i=1; i<=15; i++)
		{
			System.out.print(mFindKth.findKth1A(i) + ", ");	
		}*/


	}
}
