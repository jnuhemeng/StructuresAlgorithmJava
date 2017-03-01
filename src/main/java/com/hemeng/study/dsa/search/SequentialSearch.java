package com.hemeng.study.dsa.search;

import com.hemeng.study.dsa.sort.SimpleSorts;

/**
 * ˳����� O(N)
 * @author Administrator
 * @date 2017.03.01
 *
 */
public class SequentialSearch {
	/**
	 * ʹ��˳������㷨��ָ�����������ҳ���ָ���ؼ���ƥ���Ԫ��
	 * @param array ���ܰ�����ָ���ؼ���ƥ���Ԫ�ص�����
	 * @param key ��Ҫ���ҵ�Ԫ�صĹؼ���
	 * @return ƥ���Ԫ���������е��±�ֵ�����δ�ҵ�ƥ���Ԫ���򷵻�-1
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
	 *	�������ֲ����㷨�Ĺ��ܣ����ڲ���
	 *	@param N ������������Ԫ�صĸ���
	 *	@date 2017.03.01
	 */
	public static void functionDemo(int N) {

		int[][] array = SimpleSorts.generateIntArray(1,N);
		
		System.out.println("\nԭ���飺");
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
