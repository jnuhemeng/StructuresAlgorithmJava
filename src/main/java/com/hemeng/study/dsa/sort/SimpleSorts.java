package com.hemeng.study.dsa.sort;

import java.util.Random;
import java.math.BigDecimal; 
/**
 *	һЩ�򵥵������㷨�����뷨��ð�ݷ���ѡ��
 *	�Ľ��������㷨��ϣ������
 */
public class  SimpleSorts
{
	/**
	 *	�� �����뷨�� ����������������������
	 *	@param array ���������������
	 *	@date 2016.03.16
	 */
	public static void insertionSort(int[] array) {
		for (int i=1; i<array.length; i++)
		{
			int temp = array[i];
			int j;
			for ( j=i-1; j>=0 && array[j]>temp; j--)
			{
				array[j+1] = array[j];
			}
			array[j+1] = temp;
		}
	}

	/**
	 *	�� ��ð�ݷ��� ����������������������
	 *	@param array ���������������
	 *	@date 2016.03.16
	 */
	public static void bubbleSort(int[] array) {
		for (int i=0,n=array.length-1; i<n; i++)
		{
			for (int j=array.length-1; j>i; j--)
			{
				if (array[j] < array[j-1])
				{
					int temp = array[j];
					array[j] = array[j-1];
					array[j-1] = temp;
				}
			}
		}
	}

	/**
	 *	�� ��ѡ�񷨡� ����������������������
	 *	@param array ���������������
	 *	@date 2016.03.16
	 */
	public static void selectSort(int[] array) {
		for ( int i=0,n=array.length-1; i<n; i++)
		{
			int minIndex = i;
			for (int j=i+1; j<array.length; j++)
			{
				if (array[j] < array[minIndex])
				{
					minIndex = j;
				}
			}
			if (minIndex != i)
			{
				int temp = array[i];
				array[i] = array[minIndex];
				array[minIndex] = temp;
			}	
			
			/*//���ÿһ���������������ڵ���
			System.out.println(" ");
			printArray(array);*/
		}
	}

	/**
	 *	�� ��ϣ�����򷨡� ����������������������
	 *	@param array ���������������
	 *	@date 2016.03.16
	 */
	public static void shellSort(int[] array) {
		for (int gap=array.length/2; gap>0; gap/=2)
		{
			for (int j=gap; j<array.length; j++)
			{
				int temp = array[j];
				int k;
				for (k=j-gap; k>=0 && array[k]>temp; k-=gap)
				{
					array[k+gap] = array[k];
				}
				array[k+gap] = temp;
			}
			/*//���ÿһ���������������ڵ���
			System.out.println("\ngap = " + gap);
			printArray(array);*/
		}
	}

		/**
	 *	����һ��ÿ�к���ָ��������������Ķ�ά���飨�������ķ�ΧΪ��0,5n������
	 *		����ÿ�е�Ԫ���Լ�Ԫ��λ����ȫһ�������ڵ���
	 *	@param m �������������
	 *	@param n ��������ÿ������Ԫ�صĸ���
	 *	@return ���ɵ����Ͷ�ά����
	 *	@date 2016.03.16
	 */
	public static int[][] generateIntArray(int m,int n) {
		int[][] array = new int[m][n];
		Random mRandom = new Random();
		for ( int i=0; i<n; i++)
		{
			int temp = mRandom.nextInt(n*5);
			for (int j=0; j<m; j++)
			{
				array[j][i] = temp;
			}
		}
		return array;
	}

	/**	
	 *	����������������ӡ������̨�ϣ����ڵ���
	 *	@param array ��Ҫ��ӡ����������
	 *	@date 2016.03.16
	 */
	public static void printArray(int[] array) {
		for ( int i=0; i<array.length; i++)
		{
			System.out.print(array[i]);
			if (i < array.length-1)
			{
				System.out.print(", ");
			}
		}
	}

	/**
	 *	�������������㷨�Ĺ��ܣ����ڲ���
	 *	@param N ������������Ԫ�صĸ���
	 *	@date 2016.03.16
	 */
	public static void functionDemo(int N) {
		int[][] array = SimpleSorts.generateIntArray(4,N);
		
		System.out.println("ԭʼ���飨����" + array[0].length + "��Ԫ��)��");
		SimpleSorts.printArray(array[0]);

		/*String sortName;
		switch (funName)
		{
			case "insertionSort" : 
				SimpleSort.insertionSort(array[0]); //�����뷨��
				sortName = "���뷨";
				break;
			case "bubbleSort" : 
				SimpleSort.bubbleSort(array[1]); //��ð�ݷ���
				sortName = "ð�ݷ�";
				break;
			case "selectSort" : 
				SimpleSort.selectSort(array[2]); //��ѡ�񷨡�
				sortName = "ѡ��";
				break;
			case "shellSort" : 
				SimpleSort.shellSort(array[3]); //��ϣ�����򷨡�
				sortName = "ϣ������";
				break;
			default :
				System.out.println("\n�����㷨δ���壡");
				return;
		}
		System.out.println("\n����������Ϊ:��" + sortName + ")");
		SimpleSort.printArray(array[1]);*/

		System.out.println("\n���뷨��");
		SimpleSorts.insertionSort(array[0]); //�����뷨��
		SimpleSorts.printArray(array[0]);

		System.out.println("\nð�ݷ���");
		SimpleSorts.bubbleSort(array[1]); //��ð�ݷ���
		SimpleSorts.printArray(array[1]);

		System.out.println("\nѡ�񷨣�");
		SimpleSorts.selectSort(array[2]); //��ѡ�񷨡�
		SimpleSorts.printArray(array[2]);

		System.out.println("\nϣ�����򷨣�");
		SimpleSorts.shellSort(array[3]); //��ϣ�����򷨡�
		SimpleSorts.printArray(array[3]);
		
		System.out.println("");
	}

	/**
	 *	�������������㷨�����ܣ����ڲ���
	 *	@param N ������������Ԫ�صĸ���
	 *	@date 2016.03.16
	 */
	public static void propertyDemo(int N) {
		int[][] array = SimpleSorts.generateIntArray(4,N);

		System.out.println("������������Ԫ�صĸ�����" + N);	
		double startTimeUs = ((double)System.nanoTime())/1000;   //��ȡ��ʼʱ��  
		SimpleSorts.shellSort(array[0]); //��ϣ�����򷨡�
		double endTimeUs = ((double)System.nanoTime())/1000; //��ȡ����ʱ�� 
		BigDecimal bgUs = new BigDecimal(endTimeUs-startTimeUs);  //�������3λС��
        double timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		System.out.println("ϣ������������ʱ�� "+timeCostUs+"��s");

		startTimeUs = ((double)System.nanoTime())/1000;    
		SimpleSorts.insertionSort(array[1]); //�����뷨��
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("���뷨������ʱ�� "+timeCostUs+"��s"); 

		startTimeUs = ((double)System.nanoTime())/1000;    
		SimpleSorts.bubbleSort(array[2]); //��ð�ݷ���
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		System.out.println("ð�ݷ�������ʱ�� "+timeCostUs+"��s"); 

		startTimeUs = ((double)System.nanoTime())/1000;    
		SimpleSorts.selectSort(array[3]); //��ѡ�񷨡�
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		System.out.println("ѡ��������ʱ�� "+timeCostUs+"��s");			
	}

	public static void main(String[] args) 
	{
		int N = 20;
		//insertionSort, bubbleSort, selectSort, shellSort
		SimpleSorts.functionDemo(N); 

		System.out.println();

		N = 2000;
		SimpleSorts.propertyDemo(N);

	}
}
