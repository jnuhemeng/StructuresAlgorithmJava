package com.hemeng.study.dsa.sort;
/**
 * 复习简单的排序算法
 *
 */
public class SimpleSortsRevise {
	/**
	 * 冒泡排序（改进）
	 * @param array
	 * @date 2017.02.24
	 */
	public static void bubbleSort(int[] array) {
		//设置一个标志位。当发现当前循环结束时数组已经有序时，就直接结束后续不必要的循环。
		boolean flag = true; 
		for(int i = 0, n = array.length - 1; i <= n && flag == true; i++) {
			flag = false;
			for(int j = array.length - 1; j > i; j--) {
				if(array[j] < array[j - 1]) {
					int temp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = temp;
					//如果这一轮内循环没有执行任何交换操作，那么就说明没有必要再进行后续的操作。
					flag = true;
				}
			}
		}
	}
	
	

	/**
	 *	分析各种排序算法的功能，用于测试
	 *	@param N 测试数组所含元素的个数
	 *	@date 2012.02.24
	 */
	public static void functionDemo(int N) {
		int[][] array = SimpleSorts.generateIntArray(4,N);
		System.out.println("原始数组（包含" + array[0].length + "个元素)：");
		SimpleSorts.printArray(array[0]);

		/*System.out.println("\n插入法：");
		SimpleSortsV2.insertionSort(array[0]); //“插入法”
		SimpleSorts.printArray(array[0]);*/
		System.out.println("\n冒泡法：");
		SimpleSortsRevise.bubbleSort(array[1]); //“冒泡法”
		SimpleSorts.printArray(array[1]);
		/*System.out.println("\n选择法：");
		SimpleSortsV2.selectSort(array[2]); //“选择法”
		SimpleSorts.printArray(array[2]);
		System.out.println("\n希尔排序法：");
		SimpleSortsV2.shellSort(array[3]); //“希尔排序法”
		SimpleSorts.printArray(array[3]);*/
		System.out.println("");
	}
	
	public static void main(String[] args) {
		int N = 20;
		SimpleSortsRevise.functionDemo(N);
	}
}




