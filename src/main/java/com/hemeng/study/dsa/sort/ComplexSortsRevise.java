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
	 * ����������������
	 * @param array
	 */
	public static void quickSort(int[] array) {
		quickSort(array, 0, array.length - 1);
	}

	/**
	 * ���������м䷽��
	 * 	�鱾�ϵ��㷨ʵ�ֿ��Ǹ���ȫ�沢�����Ҳ���Ӿ��£�
	 * 	��1����Ϊ���������м�ʵ�ֳ��򡢽���������������ֵ�ָ��������������ĺ��壩��
	 * 	��2���м�ʵ�ֳ���Ĵ���������������ֵ�ָ��������Ҫ�����������������棺
	 * 		��ֵѡΪpivot����֤�˲������±�Խ�磻
	 * 		++i��++j�����ж�����б�֤���������ѭ����
	 * @param array
	 * @param left
	 * @param right
	 */
	private static void quickSort(int[] array, int left, int right) {
		// TODO Auto-generated method stub
		if(left < right) {
			int pivot = medianOfThree(array, left, right);
			int temp = array[pivot];
			array[pivot] = array[right];
			array[right] = temp;
			pivot = right;
			
			//System.out.println("array[pivot]: " + array[pivot]);
			
			int i = left, j = right - 1;
			//�ν׶εĹ���
			while(true) {
				//����ֱ��i��j����Ĺ���
				while(i < pivot && array[i] <= array[pivot]) {
					i++;
				}
				while(j >= left && array[j] >= array[pivot]) {
					j--;
				}
				//System.out.println("i=" + i + ", j=" + j);
				if(i < j) {
					temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				} else if(i >= j) {
					//���������������ѡ������ԪΪ���е����ֵ
					if(i == j && i == pivot - 1) {
						temp = array[pivot];
						array[pivot] = array[i];
						array[i] = temp;
					}
					break;
				}
				//printArray("", array);
			}
			temp = array[pivot];
			array[pivot] = array[i];
			array[i] = temp;
			
			//*����
			//printArray("���������м���̣�", array);
			
			
			//�ֽ׶εĹ���
			quickSort(array, left, i - 1);
			quickSort(array, i + 1, right);
		}
	}

	/**
	 * ���������м䷽����������ֵ�ָѡȡ��ŦԪ
	 * @param array
	 * @param left
	 * @param right
	 * @return
	 */
	private static int medianOfThree(int[] array, int left, int right) {
		// TODO Auto-generated method stub
		int median = (left + right) / 2;
		int pivot;
		if(array[left] <= array[median]) {
			if(array[median] <= array[right]) {
				pivot = median;
			} else if(array[left] <= array[right]) {
				pivot = left;
			} else {
				pivot = right;
			}
		} else {
			if(array[left] <= array[right]) {
				pivot = left;
			} else if(array[median] <= array[right]) {
				pivot = median;
			} {
				pivot = right;
			}
		}
		
		return pivot;
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
		SimpleSorts.printArray(array[2]);*/

		System.out.println("\n�������򷨣�");
		ComplexSortsRevise.quickSort(array[3]); //���������򷨡�
		SimpleSorts.printArray(array[3]);
		
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
