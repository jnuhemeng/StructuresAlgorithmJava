package com.hemeng.study.dsa.search;

import java.util.Arrays;

import com.hemeng.study.dsa.sort.SimpleSorts;

/**
 * ������ң��۰���ҡ���ֵ���ҡ�쳲���������
 * 	�мǣ�ʹ�����ֲ����㷨�����뱣֤�������ź���ģ�����ٶ����չؼ����������У�
 * @author Administrator
 * @date 2017.03.01
 */
public class SortedSearch {
	/**
	 * ʹ���۰�����㷨��ָ�����������ҳ���ָ���ؼ���ƥ���Ԫ��
	 * @param array ���ܰ�����ָ���ؼ���ƥ���Ԫ�ص�����
	 * @param key ��Ҫ���ҵ�Ԫ�صĹؼ���
	 * @return ƥ���Ԫ���������е��±�ֵ�����δ�ҵ�ƥ���Ԫ���򷵻�-1
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
	 * ��ֵ���ң�
	 * 	ֻ�轫�۰�����еġ�int mid = (low + high) / 2;����Ϊ
	 * 		if(key < array[low] || key > array[high]) {
				return -1;
			}
			int mid = low + (high - low) * (key - array[low]) / (array[high] - array[low]);
		����
	 */
	public static int InterpolationSearch(int[] array, int key) {
		int index = -1;
		int low = 0, high = array.length - 1;
		while(low <= high) {
			//int mid = (low + high) / 2;
				//��������ж���䣬�Է�ֹ��������Խ��
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
	 *	�������ֲ����㷨�Ĺ��ܣ����ڲ���
	 *	@param N ������������Ԫ�صĸ���
	 *	@date 2017.03.01
	 */
	public static void functionDemo(int N) {

		int[][] array = SimpleSorts.generateIntArray(1,N);
		//�����н�������
		Arrays.sort(array[0]);
		System.out.println("\nԭ���飺");
		SequentialSearch.printIndexedArray(array[0]);
		
		//���ѡһЩ���ݽ��в���
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
		
		//��ԭ�����е�����Ԫ�صĹؼ��ֽ��в��ң����Ƿ�������©
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
