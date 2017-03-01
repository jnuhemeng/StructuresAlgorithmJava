package com.hemeng.study.dsa.search;

import com.hemeng.study.dsa.sort.SimpleSorts;

/**
 * 顺序查找 O(N)
 * @author Administrator
 * @date 2017.03.01
 *
 */
public class SequentialSearch {
	/**
	 * 使用顺序查找算法从指定的数组中找出与指定关键字匹配的元素
	 * @param array 可能包含与指定关键字匹配的元素的数组
	 * @param key 需要查找的元素的关键字
	 * @return 匹配的元素在数组中的下标值，如果未找到匹配的元素则返回-1
	 */
	public static int sequentialSearch(int[] array, int key) {
		int i = array.length;
		while(--i >= 0 && array[i] != key) {}
		
		return i;
	}
	
	public static void printIndexedArray(int[] array) {
		for ( int i = 0; i < array.length; i++)
		{
			System.out.print("array[" + i + "]=" + array[i]);
			if (i < array.length-1)
			{
				System.out.print(", ");
			}
			if(i % 6 ==0) {
				System.out.println("");
			}
		}
		System.out.println("\n");
	}
	
	/**
	 *	分析各种查找算法的功能，用于测试
	 *	@param N 测试数组所含元素的个数
	 *	@date 2017.03.01
	 */
	public static void functionDemo(int N) {

		int[][] array = SimpleSorts.generateIntArray(1,N);
		
		System.out.println("\n原数组：");
		printIndexedArray(array[0]);
		System.out.println("\n");
		
		/*for(int key = 0; key <= 5 * N; key++) {
			int index = sequentialSearch(array[0], key);
			if(index != -1) {
				System.out.println("array[" + index + "]=" + array[0][index]);
				matches++;
			} else {
				System.out.println(key + ": Not matched!");
			}
		}*/
		for(int key = 0; key <= 5 * N; key+= N) {
			int index = sequentialSearch(array[0], key);
			if(index != -1) {
				System.out.println("array[" + index + "]=" + array[0][index]);
				//matches++;
			} else {
				System.out.println(key + ": Not matched!");
			}
		}
		System.out.println("---------\n");
		int matches = 0;
		for(int key : array[0]) {
			int index = sequentialSearch(array[0], key);
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
