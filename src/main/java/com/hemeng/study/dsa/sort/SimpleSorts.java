package com.hemeng.study.dsa.sort;

import java.util.Random;
import java.math.BigDecimal; 
/**
 *	一些简单的排序算法：插入法、冒泡法、选择法
 *	改进的排序算法：希尔排序法
 */
public class  SimpleSorts
{
	/**
	 *	用 “插入法” 对输入的数组进行升序排序
	 *	@param array 待排序的整型数组
	 *	@date 2016.03.16
	 */
	public static void insertionSort(int[] array) {
		for (int i=1; i<array.length; i++)
		{
			int temp = array[i];
			int j;
			for ( j=i-1; j>=0 && array[j]>temp; j--)
			{
				array[j+1] = array[j];
			}
			array[j+1] = temp;
		}
	}

	/**
	 *	用 “冒泡法” 对输入的数组进行升序排序
	 *	@param array 待排序的整型数组
	 *	@date 2016.03.16
	 */
	public static void bubbleSort(int[] array) {
		for (int i=0,n=array.length-1; i<n; i++)
		{
			for (int j=array.length-1; j>i; j--)
			{
				if (array[j] < array[j-1])
				{
					int temp = array[j];
					array[j] = array[j-1];
					array[j-1] = temp;
				}
			}
		}
	}

	/**
	 *	用 “选择法” 对输入的数组进行升序排序
	 *	@param array 待排序的整型数组
	 *	@date 2016.03.16
	 */
	public static void selectSort(int[] array) {
		for ( int i=0,n=array.length-1; i<n; i++)
		{
			int minIndex = i;
			for (int j=i+1; j<array.length; j++)
			{
				if (array[j] < array[minIndex])
				{
					minIndex = j;
				}
			}
			if (minIndex != i)
			{
				int temp = array[i];
				array[i] = array[minIndex];
				array[minIndex] = temp;
			}	
			
			/*//输出每一步的排序结果，用于调试
			System.out.println(" ");
			printArray(array);*/
		}
	}

	/**
	 *	用 “希尔排序法” 对输入的数组进行升序排序
	 *	@param array 待排序的整型数组
	 *	@date 2016.03.16
	 */
	public static void shellSort(int[] array) {
		for (int gap=array.length/2; gap>0; gap/=2)
		{
			for (int j=gap; j<array.length; j++)
			{
				int temp = array[j];
				int k;
				for (k=j-gap; k>=0 && array[k]>temp; k-=gap)
				{
					array[k+gap] = array[k];
				}
				array[k+gap] = temp;
			}
			/*//输出每一步的排序结果，用于调试
			System.out.println("\ngap = " + gap);
			printArray(array);*/
		}
	}

		/**
	 *	返回一个每行含有指定个随机整型数的二维数组（整型数的范围为（0,5n）），
	 *		并且每行的元素以及元素位置完全一样，用于调试
	 *	@param m 返回数组的行数
	 *	@param n 返回数组每行所含元素的个数
	 *	@return 生成的整型二维数组
	 *	@date 2016.03.16
	 */
	public static int[][] generateIntArray(int m,int n) {
		int[][] array = new int[m][n];
		Random mRandom = new Random();
		for ( int i=0; i<n; i++)
		{
			int temp = mRandom.nextInt(n*5);
			for (int j=0; j<m; j++)
			{
				array[j][i] = temp;
			}
		}
		return array;
	}

	/**	
	 *	将传入的整型数组打印到控制台上，用于调试
	 *	@param array 需要打印的整型数组
	 *	@date 2016.03.16
	 */
	public static void printArray(int[] array) {
		for ( int i=0; i<array.length; i++)
		{
			System.out.print(array[i]);
			if (i < array.length-1)
			{
				System.out.print(", ");
			}
		}
	}

	/**
	 *	分析各种排序算法的功能，用于测试
	 *	@param N 测试数组所含元素的个数
	 *	@date 2016.03.16
	 */
	public static void functionDemo(int N) {
		int[][] array = SimpleSorts.generateIntArray(4,N);
		
		System.out.println("原始数组（包含" + array[0].length + "个元素)：");
		SimpleSorts.printArray(array[0]);

		/*String sortName;
		switch (funName)
		{
			case "insertionSort" : 
				SimpleSort.insertionSort(array[0]); //“插入法”
				sortName = "插入法";
				break;
			case "bubbleSort" : 
				SimpleSort.bubbleSort(array[1]); //“冒泡法”
				sortName = "冒泡法";
				break;
			case "selectSort" : 
				SimpleSort.selectSort(array[2]); //“选择法”
				sortName = "选择法";
				break;
			case "shellSort" : 
				SimpleSort.shellSort(array[3]); //“希尔排序法”
				sortName = "希尔排序法";
				break;
			default :
				System.out.println("\n排序算法未定义！");
				return;
		}
		System.out.println("\n排序后的数组为:（" + sortName + ")");
		SimpleSort.printArray(array[1]);*/

		System.out.println("\n插入法：");
		SimpleSorts.insertionSort(array[0]); //“插入法”
		SimpleSorts.printArray(array[0]);

		System.out.println("\n冒泡法：");
		SimpleSorts.bubbleSort(array[1]); //“冒泡法”
		SimpleSorts.printArray(array[1]);

		System.out.println("\n选择法：");
		SimpleSorts.selectSort(array[2]); //“选择法”
		SimpleSorts.printArray(array[2]);

		System.out.println("\n希尔排序法：");
		SimpleSorts.shellSort(array[3]); //“希尔排序法”
		SimpleSorts.printArray(array[3]);
		
		System.out.println("");
	}

	/**
	 *	分析各种排序算法的性能，用于测试
	 *	@param N 测试数组所含元素的个数
	 *	@date 2016.03.16
	 */
	public static void propertyDemo(int N) {
		int[][] array = SimpleSorts.generateIntArray(4,N);

		System.out.println("测试数组所含元素的个数：" + N);	
		double startTimeUs = ((double)System.nanoTime())/1000;   //获取开始时间  
		SimpleSorts.shellSort(array[0]); //“希尔排序法”
		double endTimeUs = ((double)System.nanoTime())/1000; //获取结束时间 
		BigDecimal bgUs = new BigDecimal(endTimeUs-startTimeUs);  //结果保留3位小数
        double timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		System.out.println("希尔排序法排序用时： "+timeCostUs+"μs");

		startTimeUs = ((double)System.nanoTime())/1000;    
		SimpleSorts.insertionSort(array[1]); //“插入法”
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("插入法排序用时： "+timeCostUs+"μs"); 

		startTimeUs = ((double)System.nanoTime())/1000;    
		SimpleSorts.bubbleSort(array[2]); //“冒泡法”
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		System.out.println("冒泡法排序用时： "+timeCostUs+"μs"); 

		startTimeUs = ((double)System.nanoTime())/1000;    
		SimpleSorts.selectSort(array[3]); //“选择法”
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		System.out.println("选择法排序用时： "+timeCostUs+"μs");			
	}

	public static void main(String[] args) 
	{
		int N = 20;
		//insertionSort, bubbleSort, selectSort, shellSort
		SimpleSorts.functionDemo(N); 

		System.out.println();

		N = 2000;
		SimpleSorts.propertyDemo(N);

	}
}
