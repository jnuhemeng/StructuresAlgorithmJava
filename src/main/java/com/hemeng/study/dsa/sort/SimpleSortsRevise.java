package com.hemeng.study.dsa.sort;
/**
 * ��ϰ�򵥵������㷨
 *
 */
public class SimpleSortsRevise {
	/**
	 * �� ��ð�ݷ��� ���������������������򣨸Ľ���
	 * 	ÿ�ζ����ʣ�����н��������Ĳ����������ұ߿�ʼ����һֱ������ߣ�����Ԫ�������Ƚϣ�
	 * 	���˳��Ͳ������κβ������������ͽ������ǵ�λ�á�
	 * 	�ظ��������̣�ֱ��ʣ��������Ԫ�ظ���Ϊ0.
	 * @param array
	 * @date 2017.02.24
	 */
	public static void bubbleSort(int[] array) {
		//����һ����־λ�������ֵ�ǰѭ������ʱ�����Ѿ�����ʱ����ֱ�ӽ�����������Ҫ��ѭ����
		boolean flag = true; 
		for(int i = 0, n = array.length - 1; i <= n - 1 && flag == true; i++) {
			flag = false;
			for(int j = array.length - 1; j > i; j--) {
				if(array[j] < array[j - 1]) {
					int temp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = temp;
					//�����һ����ѭ��û��ִ���κν�����������ô��˵��û�б�Ҫ�ٽ��к����Ĳ�����
					flag = true;
				}
			}
		}
	}
	
	/**
	 * �� ��ѡ�񷨡� ����������������������
	 * 	ÿ�ζ���ʣ��������ͨ���Ƚ�ѡȡ��С��Ԫ�أ��������ƶ���ʣ�����е�����ߡ�
	 * 	�ظ��������̣�ֱ��ʣ��������Ԫ�ظ���Ϊ0.
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
	 * �� �����뷨�� ����������������������
	 * 	ÿ�ζ���ʣ��������ȡ��һ��Ԫ�أ�������Ԫ�ز��뵽�Ѿ��ź�������еĺ���λ���ϣ��õ�һ��
	 * 	Ԫ�ظ�����1�����������С�
	 * 	�ظ��������̣�ֱ��ʣ��������Ԫ�ظ���Ϊ0.
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
	 * ϣ������Ļ���ʵ�֣��õ���4��ѭ��
	 * @param array
	 * @date 2017.02.25
	 */
	/*public static void shellSort(int[] array) {
		final int N = array.length;
		for(int gap = N / 2; gap >= 1; gap /= 2) {
			//����ÿ��������
			for(int i = 0; i <= gap - 1; i++) {
				//���в�������
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
	 * �ع����ϣ������ֻ�õ���3��ѭ��
	 * 	ÿ�ζ���һ������Ϊ����������зָ�ɶ�������У�����ÿ�������н��в�������
	 * 	��һ�����ɼ�С�������ظ��������̡�
	 * 	ֱ��������Ϊ1�����������һ�β������򣬾�������������е����� 
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
	 *	�������������㷨�Ĺ��ܣ����ڲ���
	 *	@param N ������������Ԫ�صĸ���
	 *	@date 2017.02.24
	 */
	public static void functionDemo(int N) {
		int[][] array = SimpleSorts.generateIntArray(4,N);
		System.out.println("ԭʼ���飨����" + array[0].length + "��Ԫ��)��");
		SimpleSorts.printArray(array[0]);

		System.out.println("\n���뷨��");
		SimpleSortsRevise.insertionSort(array[0]); //�����뷨��
		SimpleSorts.printArray(array[0]);
		System.out.println("\nð�ݷ���");
		SimpleSortsRevise.bubbleSort(array[1]); //��ð�ݷ���
		SimpleSorts.printArray(array[1]);
		System.out.println("\nѡ�񷨣�");
		SimpleSortsRevise.selectionSort(array[2]); //��ѡ�񷨡�
		SimpleSorts.printArray(array[2]);
		System.out.println("\nϣ�����򷨣�");
		SimpleSortsRevise.shellSort(array[3]); //��ϣ�����򷨡�
		SimpleSorts.printArray(array[3]);
		System.out.println("");
	}
	
	public static void main(String[] args) {
		int N = 20;
		SimpleSortsRevise.functionDemo(N);
	}
}




