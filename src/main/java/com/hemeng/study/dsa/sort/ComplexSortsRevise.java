package com.hemeng.study.dsa.sort;

/**
 * ��ϰ���ָ��ӵ������㷨
 * @author Administrator
 *
 */
public class ComplexSortsRevise {
	/**
	 * ������
	 * 	����ȱ�׼��ʵ�ִ��븴���˺ܶ࣬��Ҫ��һ��ѧϰ��׼��ʵ�ַ�ʽ��
	 * @param array
	 */
	public static void heapSort(int[] array) {
		//1�����������
		int currentSize = array.length;
		int[] tempArray = new int[currentSize + 1];
		int i = 1;
		for(int item : array) {
			tempArray[i++] = item;
		}
		//printArray("\n��������", tempArray);
		for(i = currentSize / 2; i >= 1; i--) {
			//���˲���
			///percolateDown(i);
			percolateDown(tempArray, currentSize, i);
			//printArray("���������", tempArray);
		}
		
		//2������ȡ����ǰ���е����ֵ������֤����ѵĶ����Ժͽṹ��
			//Ϊ�˱���ʹ���������飬���Ӷ����ͷ��ȡ����Ԫ�ط��õ���ǰ����ѵ���ĩβ
		for(i = 1; i <= array.length; i++) {
			int tempItem = tempArray[currentSize];
			tempArray[currentSize] = tempArray[1];
			tempArray[1] = tempItem;
			currentSize--;
			percolateDown(tempArray, currentSize, 1);
		}
		//printArray("����������", tempArray);
		
		for(i = 0; i <= array.length - 1; i++) {
			array[i] = tempArray[i + 1];
		}
	}
	
	/**
	 * �鲢����(��������)
	 * @param array
	 */
	public static void mergeSort(int[] array) {
		int[] tempArray = new int[array.length];
		mergeSort(array, tempArray, 0, array.length - 1);
		/*int i = 0;
		for(int item : tempArray) {
			array[i++] = item;
		}*/
	}
	
	/**
	 * �鲢��������㷨
	 * @param array
	 * @param tempArry
	 * @param left
	 * @param right
	 */
	private static void mergeSort(int[] array, int[] tempArray, int left, int right) {
		// TODO Auto-generated method stub
		//�ֽ׶�
		if(left >= right) {
			return;
		}
		int center = (left + right) / 2;
		mergeSort(array, tempArray, left, center);
		mergeSort(array, tempArray, center + 1, right);
		
		//�ν׶�
		int i = left, j = center + 1, k = left;
		while(i <= center && j <= right) {
			if(array[i] < array[j]) {
				tempArray[k++] = array[i++];
			} else {
				tempArray[k++] = array[j++];
			}
		}
		while(i <= center) {
			tempArray[k++] = array[i++];
		}
		while(j <= right) {
			tempArray[k++] = array[j++];
		}
			//���ź����Ԫ�ذ�˳�򿽱���ԭ����
		i = left; 
		while(i <= right) {
			array[i] = tempArray[i];
			i++;
		}
		
		//printArray("�鲢�����м���̣�", tempArray);
	}

	/** ���ڸ������Եķ�������ӡһ�����鲢��ӱ��� */
	private static void printArray(String title, int[] array) {
		System.out.println(title);
		SimpleSorts.printArray(array);
		System.out.println("\n");
	}
	
	/***
	 * (��������)���ڶ�ָ����С�����ָ��Ԫ�ؽ������˲�����ע�������������������Ѹպ��෴��
	 * 	��ֻ�����ֵС���ӽڵ��Ԫ�ؽ��иò���
	 * @param tempArray
	 * @param currentSize
	 * @param i
	 */
	private static void percolateDown(int[] tempArray, int currentSize, int pos) {
		// TODO Auto-generated method stub
		int child;
		while((child = pos * 2) <= currentSize) {
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
	 *	�������������㷨�Ĺ��ܣ����ڲ���
	 */
	public static void functionDemo(int N) {

		//SimpleSort.functionDemo(N); 
		int[][] array = SimpleSorts.generateIntArray(4,N);
		
		System.out.println("\nԭʼ���ݣ�");
		SimpleSorts.printArray(array[0]);
		
		System.out.println("\n�����򷨣�");
		ComplexSortsRevise.heapSort(array[0]); //�������򷨡�
		SimpleSorts.printArray(array[0]);
		
		System.out.println("\n�鲢���򷨣�"); 
		ComplexSortsRevise.mergeSort(array[1]); //���鲢���򷨡�
		SimpleSorts.printArray(array[1]);

		/*System.out.println("\nJava����е�Arrays�����㷨��"); 
		Arrays.sort(array[2]); //��Java����е�Arrays�����㷨��
		SimpleSorts.printArray(array[2]);

		System.out.println("\n�������򷨣�");
		ComplexSortsRevise.quickSort(array[3]); //���������򷨡�
		SimpleSorts.printArray(array[3]);*/
		
		System.out.println("");
	}
	
	public static void main(String[] args) 
	{
		int N = 20;
		//int N = 10;
		
		//�����Է���
		ComplexSortsRevise.functionDemo(N);
		//ComplexSort.divideToTwoSetsDemo(N);
		
		//�����Է���
		/*N=8000000;
		System.out.println("");
		ComplexSorts.propertyDemo(N);*/
	}

}
