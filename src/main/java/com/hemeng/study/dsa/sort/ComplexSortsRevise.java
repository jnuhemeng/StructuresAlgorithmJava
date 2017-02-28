package com.hemeng.study.dsa.sort;

import java.util.Arrays;

/**
 * 复习各种复杂的排序算法
 * @author Administrator
 *
 */
public class ComplexSortsRevise {
	/**
	 * 堆排序
	 * @param array
	 */
	public static void heapSort(int[] array) {
		//1、建立二叉堆
		int currentSize = array.length;
		int[] tempArray = new int[currentSize + 1];
		int i = 1;
		for(int item : array) {
			tempArray[i++] = item;
		}
		for(i = currentSize / 2; i >= 1; i--) {
			///percolateDown(i);
			percolateDown(tempArray, currentSize, i);
		}
		SimpleSorts.printArray(tempArray);
		System.out.println("\n");
		
		//2、反复取出当前堆中的最大值，并保证二叉堆的堆序性和结构性
		for(i = 1; i <= array.length; i++) {
			int tempItem = tempArray[currentSize];
			tempArray[currentSize] = tempArray[1];
			tempArray[1] = tempItem;
			currentSize--;
			percolateDown(tempArray, currentSize, 1);
		}
		
		SimpleSorts.printArray(tempArray);
		System.out.println("\n");
		
		array = tempArray;
	}
	
	/***
	 * (辅助方法)用于对指定大小数组的指定元素进行下滤操作，注意这里的排序规则与二叉堆刚好相反，
	 * 	即只会对其值小于子节点的元素进行该操作
	 * @param tempArray
	 * @param currentSize
	 * @param i
	 */
	private static void percolateDown(int[] tempArray, int currentSize, int pos) {
		// TODO Auto-generated method stub
		int child;
		while((child = pos / 2) <= currentSize) {
			if(child + 1 <= currentSize && tempArray[child] < tempArray[child + 1]) {
				child += 1;
			}
			if(tempArray[pos] < tempArray[child]) {
				int tempItem = tempArray[pos];
				tempArray[pos] = tempArray[child];
				tempArray[child] = tempItem;
				pos = child;
			} else {
				return;
			}
		}
	}
	

	/**
	 *	分析各种排序算法的功能，用于测试
	 */
	public static void functionDemo(int N) {

		//SimpleSort.functionDemo(N); 
		int[][] array = SimpleSorts.generateIntArray(4,N);
		
		System.out.println("\n原始数据：");
		SimpleSorts.printArray(array[0]);
		
		System.out.println("\n堆排序法：");
		ComplexSortsRevise.heapSort(array[0]); //“堆排序法”
		SimpleSorts.printArray(array[0]);
		
		/*System.out.println("\n归并排序法："); 
		ComplexSortsRevise.mergeSort(array[1]); //“归并排序法”
		SimpleSorts.printArray(array[1]);

		System.out.println("\nJava类库中的Arrays排序算法："); 
		Arrays.sort(array[2]); //“Java类库中的Arrays排序算法”
		SimpleSorts.printArray(array[2]);

		System.out.println("\n快速排序法：");
		ComplexSortsRevise.quickSort(array[3]); //“快速排序法”
		SimpleSorts.printArray(array[3]);*/
		
		System.out.println("");
	}
	
	public static void main(String[] args) 
	{
		int N = 20;
		
		//功能性分析
		ComplexSortsRevise.functionDemo(N);
		//ComplexSort.divideToTwoSetsDemo(N);
		
		//性能性分析
		/*N=8000000;
		System.out.println("");
		ComplexSorts.propertyDemo(N);*/
	}

}
