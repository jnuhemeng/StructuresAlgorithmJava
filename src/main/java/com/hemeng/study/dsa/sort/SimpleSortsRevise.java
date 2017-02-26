package com.hemeng.study.dsa.sort;
/**
 * 复习简单的排序算法
 *
 */
public class SimpleSortsRevise {
	/**
	 * 用 “冒泡法” 对输入的数组进行升序排序（改进）
	 * 	每次都针对剩余序列进行这样的操作：从最右边开始依次一直到最左边，相邻元素两两比较，
	 * 	如果顺序就不进行任何操作，如果逆序就交换它们的位置。
	 * 	重复上述过程，直到剩余序列中元素个数为0.
	 * @param array
	 * @date 2017.02.24
	 */
	public static void bubbleSort(int[] array) {
		//设置一个标志位。当发现当前循环结束时数组已经有序时，就直接结束后续不必要的循环。
		boolean flag = true; 
		for(int i = 0, n = array.length - 1; i <= n - 1 && flag == true; i++) {
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
	 * 用 “选择法” 对输入的数组进行升序排序
	 * 	每次都从剩余序列中通过比较选取最小的元素，并将它移动到剩余序列的最左边。
	 * 	重复上述过程，直到剩余序列中元素个数为0.
	 * @param array
	 * @date 2017.02.24          
	 */
	public static void selectionSort(int[] array) {
		for(int i = 0; i <= array.length - 2; i++) {
			int index = i;
			for(int j = i + 1; j <= array.length - 1; j++) {
				if(array[index] > array[j]) {
					index = j;
				}
			}
			if(index != i) {
				int temp = array[i];
				array[i] = array[index];
				array[index] = temp;
			}
		}
	}
	
	/**
	 * 用 “插入法” 对输入的数组进行升序排序
	 * 	每次都从剩余序列中取出一个元素，并将个元素插入到已经排好序的序列的合适位置上，得到一个
	 * 	元素个数增1的新有序序列。
	 * 	重复上述过程，直到剩余序列中元素个数为0.
	 * @param array
	 * @date 2017.02.24
	 */
	public static void insertionSort(int[] array) {
		for(int i = 1; i <= array.length - 1; i++) {
			int temp = array[i];
			int j;
			for(j = i - 1; j >= 0 && array[j] > temp;  j--) {
				array[j + 1] = array[j];
			}
			array[j + 1] = temp;
		}
	}
	
	/**
	 * 希尔排序的基本实现：用到了4层循环
	 * @param array
	 * @date 2017.02.25
	 */
	/*public static void shellSort(int[] array) {
		final int N = array.length;
		for(int gap = N / 2; gap >= 1; gap /= 2) {
			//遍历每个子序列
			for(int i = 0; i <= gap - 1; i++) {
				//进行插入排序
				for(int j = i + gap; j <= N - 1; j += gap) {
					int temp = array[j];
					int k;
					for(k = j - gap; k >= 0 && array[k] > temp; k -= gap) {
						array[k + gap] = array[k];
					}
					array[k + gap] = temp;
				}
			}
		}
	}*/
	/**
	 * 重构后的希尔排序：只用到了3层循环
	 * 	每次都以一个增量为间隔、将序列分割成多个子序列，并对每个子序列进行插入排序。
	 * 	按一定规律减小增量后，重复上述过程。
	 * 	直到增量变为1，并进行最后一次插入排序，就完成了整个序列的排序。 
	 * @param array
	 * @date 2017.02.25
	 */
	public static void shellSort(int[] array) {
		final int N = array.length;
		for(int gap = N / 2; gap >= 1; gap /= 2) {
			for(int i = gap; i <= N - 1; i++) {
				int temp = array[i];
				int j;
				for(j = i - gap; j >= 0 && array[j] > temp; j -= gap) {
					array[j + gap] = array[j];
				}
				array[j + gap] = temp;
			}
		}
	}
	
	
	
	
	

	/**
	 *	分析各种排序算法的功能，用于测试
	 *	@param N 测试数组所含元素的个数
	 *	@date 2017.02.24
	 */
	public static void functionDemo(int N) {
		int[][] array = SimpleSorts.generateIntArray(4,N);
		System.out.println("原始数组（包含" + array[0].length + "个元素)：");
		SimpleSorts.printArray(array[0]);

		System.out.println("\n插入法：");
		SimpleSortsRevise.insertionSort(array[0]); //“插入法”
		SimpleSorts.printArray(array[0]);
		System.out.println("\n冒泡法：");
		SimpleSortsRevise.bubbleSort(array[1]); //“冒泡法”
		SimpleSorts.printArray(array[1]);
		System.out.println("\n选择法：");
		SimpleSortsRevise.selectionSort(array[2]); //“选择法”
		SimpleSorts.printArray(array[2]);
		System.out.println("\n希尔排序法：");
		SimpleSortsRevise.shellSort(array[3]); //“希尔排序法”
		SimpleSorts.printArray(array[3]);
		System.out.println("");
	}
	
	public static void main(String[] args) {
		int N = 20;
		SimpleSortsRevise.functionDemo(N);
	}
}




