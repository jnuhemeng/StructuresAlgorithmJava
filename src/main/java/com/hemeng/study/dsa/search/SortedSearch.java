package com.hemeng.study.dsa.search;

import java.util.Arrays;

import com.hemeng.study.dsa.sort.SimpleSorts;

/**
 * 有序查找：折半查找、插值查找、斐波那契查找
 * 	切记：使用这种查找算法，必须保证序列是排好序的（这里假定按照关键字升序排列）
 * @author Administrator
 * @date 2017.03.01
 */
public class SortedSearch {
	/**
	 * 使用折半查找算法从指定的数组中找出与指定关键字匹配的元素
	 * @param array 可能包含与指定关键字匹配的元素的数组
	 * @param key 需要查找的元素的关键字
	 * @return 匹配的元素在数组中的下标值，如果未找到匹配的元素则返回-1
	 */
	public static int binarySearch(int[] array, int key) {
		int index = -1;
		int low = 0, high = array.length - 1;
		while(low <= high) {
			int mid = (low + high) / 2;
			if(key < array[mid]) {
				high = mid - 1;
			} else if(key > array[mid]) {
				low = mid + 1;
			} else {
				index = mid;
				break;
			}
		}
		return index;
	}
	
	/**
	 * 插值查找：
	 * 	只需将折半查找中的“int mid = (low + high) / 2;”改为
	 * 		if(key < array[low] || key > array[high]) {
				return -1;
			}
			int mid = low + (high - low) * (key - array[low]) / (array[high] - array[low]);
		即可
	 */
	public static int InterpolationSearch(int[] array, int key) {
		int index = -1;
		int low = 0, high = array.length - 1;
		while(low <= high) {
			//int mid = (low + high) / 2;
				//添加如下判断语句，以防止出现数组越界
			if(key < array[low] || key > array[high]) {
				return -1;
			}
			int mid = low + (high - low) * (key - array[low]) / (array[high] - array[low]);
			
			if(key < array[mid]) {
				high = mid - 1;
			} else if(key > array[mid]) {
				low = mid + 1;
			} else {
				index = mid;
				break;
			}
		}
		return index;
	}
	

	/**
	 *	分析各种查找算法的功能，用于测试
	 *	@param N 测试数组所含元素的个数
	 *	@date 2017.03.01
	 */
	public static void functionDemo(int N) {

		int[][] array = SimpleSorts.generateIntArray(1,N);
		//对序列进行排序
		Arrays.sort(array[0]);
		System.out.println("\n原数组：");
		SequentialSearch.printIndexedArray(array[0]);
		
		//随机选一些数据进行查找
		for(int key = 0; key <= 5 * N; key+= N) {
			int index = binarySearch(array[0], key);
			if(index != -1) {
				System.out.println("array[" + index + "]=" + array[0][index]);
				//matches++;
			} else {
				System.out.println(key + ": Not matched!");
			}
		}
		System.out.println("---------\n");
		
		//用原序列中的所有元素的关键字进行查找，看是否会出现遗漏
		int matches = 0;
		for(int key : array[0]) {
			int index = binarySearch(array[0], key);
			if(index != -1) {
				System.out.println("array[" + index + "]=" + array[0][index]);
				matches++;
			} else {
				System.out.println(key + ": Not matched!");
			}
		}
		System.out.println("---------\n" + matches + " matched");
	}
	
	
	public static void main(String[] args) {
		int N = 20;
		
		functionDemo(N);
	}
}
