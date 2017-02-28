package com.hemeng.study.dsa.binaryheap;

import java.util.Random;
import java.math.BigDecimal; 
/**
 *	测试LeftistHeap（左子堆）的性能
 *		通过比较二叉堆（BinaryHeap）和左子堆（LeftistHeap）的合并操作，验证左子堆merge操作的对数时间复杂度
 *	当随着数据量的增大，二叉堆的合并用时显著增大，而左子堆的合并用时则增加不大。例如：
 *		合并的两个堆的大小为1,000,000和2,000,000时，二叉堆用时（对数时间）448772.282μs，而左子堆的用时
 *		（N倍对数时间）为13.688μs。
 */
class LeftistHeapDemo 
{
	public LeftistHeapDemo() {
	}

	/**
	 *	合并两个二叉堆
	 *	@param bh1 需要合并的二叉堆1
	 *	@param bh2 需要合并的二叉堆2
	 *	@return 合并得到的新二叉堆
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
	 *	合并两个左子堆
	 *	@param lh1 需要合并的左子堆1
	 *	@param lh2 需要合并的左子堆2
	 *	@return 合并得到的新左子堆
	 *	@date 2016.03.15
	 */
	public LeftistHeap mergeLeftistHeap(LeftistHeap lh1, LeftistHeap lh2) {
		lh1.merge(lh2);
		return lh1;
	}
	
	/**
	 *	生成一个含有指定个随机元素的二叉堆
	 *	@param n 即将产生的二叉堆所含元素的个数
	 *	@date 2016.03.15
	 */
	public BinaryHeap generateBinaryHeap(int n) {
		BinaryHeap mBinaryHeap = new BinaryHeap();
		Random mRandom = new Random();
		for (int i=1; i<=n; i++)
		{
			mBinaryHeap.insert(mRandom.nextInt(n*5)); //指定随机数的范围为(0, 5n)
		}
		return mBinaryHeap;
		
	}
	
	/**
	 *	生成一个含有指定个随机元素的左子堆
	 *	@param n 即将产生的左子堆所含元素的个数
	 *	@date 2016.03.15
	 */
	public LeftistHeap generateLeftistHeap(int n) {		
		LeftistHeap mLeftistHeap = new LeftistHeap();
		Random mRandom = new Random();
		for (int i=1; i<=n; i++)
		{
			mLeftistHeap.insert(mRandom.nextInt(n*5)); //指定随机数的范围为(0, 5n)
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

		System.out.println("二叉堆1：" + lhNum1);
		mBinaryHeap.printHeap();
		System.out.println("二叉堆2：" + lhNum2);
		mBinaryHeap2.printHeap();

		//以微秒（μs）为单位计算一段代码的运行时间
		double startTimeUs = ((double)System.nanoTime())/1000;   //获取开始时间  
		mBinaryHeap = mLeftistHeapDemo.mergeBinaryHeap(mBinaryHeap, mBinaryHeap2); //合并二叉堆	
		double endTimeUs = ((double)System.nanoTime())/1000; //获取结束时间 
		BigDecimal bgUs = new BigDecimal(endTimeUs-startTimeUs);  //结果保留3位小数
        double timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		System.out.println("合并后的二叉堆：");
		mBinaryHeap.printHeap();  //按层序打印出所有元素
		//mBinaryHeap.printSortedHeap(); //按升序打印出所有元素
		System.out.println("合并用时： "+timeCostUs+"μs\n"); 
		
		
		

		System.out.println("\n左子堆1：" + bhNum1);
		mLeftistHeap.printLeftistHeap();
		System.out.println("\n左子堆2：" + bhNum2);
		mLeftistHeap2.printLeftistHeap();

		//以微秒（μs）为单位计算一段代码的运行时间
		startTimeUs = ((double)System.nanoTime())/1000;   //获取开始时间  
		mLeftistHeap = mLeftistHeapDemo.mergeLeftistHeap(mLeftistHeap, mLeftistHeap2); //合并左子堆
		endTimeUs = ((double)System.nanoTime())/1000; //获取结束时间 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  //结果保留3位小数
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		System.out.println("\n合并后的左子堆：");
		mLeftistHeap.printLeftistHeap(); 
		//mLeftistHeap.printSortedLeftistHeap();
		System.out.println("\n合并用时： "+timeCostUs+"μs\n"); 	
	}
}
