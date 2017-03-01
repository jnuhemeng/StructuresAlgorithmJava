package com.hemeng.study.dsa.sort;

import java.math.BigDecimal; 

import java.util.Arrays;

/**
 *	һЩ�Ը��ӵ������㷨��������Nlog(N�����Ӷȣ����鲢���򡢿�������
 *
 */
public class  ComplexSorts
{
	/**
	 *	�� �������򷨡� ����������������������
	 *		�Ƚ���������ͨ�����˱�ɶ���ѣ�Ȼ��ͨ��deleteMin�����õ���ǰ
	 *		���ֵ����ŵ�����Ķ�������鵱ǰ���߼�ĩβ
	 *	@param array ���������������
	 *	@date 2016.03.17
	 */
	public static void heapSort(int[] array) {
		//��O��N��ʱ�临�Ӷ��γɶ���ѽṹ
		for (int i=array.length/2; i>=0; i--) 
		{
			percDown(array,i,array.length);
		}	
		
		/*//�����γɵĶ���ѣ����ڵ���
		System.out.println("\n����ѣ�");
		SimpleSort.printArray(array);*/

		for (int i=array.length-1; i>0; i--)
		{
			//��������У���ǰ�߼�ĩβ��Ԫ�����һ��Ԫ�ؽ���λ��
			int temp = array[i];
			array[i] = array[0];
			array[0] = temp;
			//�Զ���ѵĵ�һ��Ԫ�ؽ������˲���
			percDown(array,0,i);
		}
	}

	/**
	 *	�� ���鲢���򷨡� ����������������������
	 *	@param array ���������������
	 *	@date 2016/3/18
	 */
	public static void mergeSort(int[] array) {
		int[] tmpArray = new int[array.length]; 
		mergeSort( array, tmpArray, 0, array.length-1);		
	}

	/**
	 *	�ÿ������򷨶����������������
	 *	@param ���������������
	 *	@date 2016/3/18
	 */
	public static void quickSort( int[] array ) {
		quickSort( array, 0, array.length-1 );
	}

	/**
	 *	��ָ��Ԫ��ִ�������˲����Ա���max�ѵĽṹ
	 *	@param array max�Ѷ�Ӧ����������
	 *	@param i ���˿�ʼ����Ԫ�ص��±�(��max��Ԫ���±��0��ʼ)
	 *	@param n max�ѵĴ�С
	 *	@date 2016.03.17
	 */
	private static void percDown(int[] array, int index, int n) {
		int child;
		while((child=index*2+1)<=n-1)
		{
			if (child+1<=n-1 && array[child+1]>array[child])
			{
				child = child + 1;
			}
			if (array[child] > array[index])
			{
				int temp = array[index];
				array[index] = array[child];
				array[child] = temp;

				index = child;
			} else {
				return;	
			}
		}
	}

	/**	
	 *	������������ָ��λ�÷�Χ�ڵ�Ԫ���á��鲢���򷨡�������������
	 *		�����鲢���򷨡����������м亯����
	 *		�õݹ�ķ�����ʵ�֣�Ϊ��ʵ�����������򷨽ӿڵ�ͳһ����ҪΪ�������
	 *		����дһ����������
	 *	@param array �������飬�����������
	 *	@param tmpArray �������飬��������м���
	 *	@param left ��������������Ҫ����ķ�Χ����߽��±�
	 *	@param right ��������������Ҫ����ķ�Χ���ұ߽��±�
	 *	@date 2016/3/18
	 */
	private static void mergeSort( int[] array, int[] tmpArray, int left, int right ) {
		if (left != right)
		{
			int center = ( left + right ) / 2;
			mergeSort( array, tmpArray, left, center );
			mergeSort( array, tmpArray, center + 1, right );
			merge( array,tmpArray, left, center + 1, right );
		}
	}

	/**
	 *	������������ָ��λ�÷�Χ�ڵġ����ڵ�����Ԫ�غϲ�Ϊһ������Ԫ�ز���ŵ�������
	 *		�е���ͬλ�á������鲢���򷨡����������м亯����
	 *	@param array �������飬���ϲ�������
	 *	@param tmpArray �������飬��������м���
	 *	@param leftPos ���ϲ������е�һ����Ҫ�ϲ��ķ�Χ����߽��±�
	 *	@param rightPos ���ϲ������еڶ�����Ҫ�ϲ��ķ�Χ����߽��±�
	 *	@param rightEnd ���ϲ������еڶ�����Ҫ�ϲ��ķ�Χ���ұ߽��±�
	 *	@date 2016/3/18
	 */
	private static void merge( int[] array, int[] tmpArray, int leftPos, int rightPos, int rightEnd) {
		int tmpPos = leftPos;
		int leftEnd = rightPos - 1;
		int numElements = rightEnd - leftPos + 1;

		//��ѭ��
		while ( leftPos <= leftEnd && rightPos <= rightEnd )
		{
			if ( array[ leftPos ] < array[ rightPos ] )
			{
				tmpArray[ tmpPos++ ] = array[ leftPos++ ];
			}
			else 
			{
				tmpArray[ tmpPos++ ] = array[ rightPos++ ];
			}
		}
		//���������ѭ��
		while ( leftPos <= leftEnd )
		{
			tmpArray[ tmpPos++ ] = array[ leftPos++ ];
		}
		while ( rightPos <= rightEnd )
		{
			tmpArray[ tmpPos++ ] = array[ rightPos++ ];
		}
		
		//���м����е�Ԫ�ذ�˳�����������ԭ����
		for ( int i=1 ; i <= numElements; i++, rightEnd-- )
		{
			array[ rightEnd ] = tmpArray[ rightEnd ];
		}
	}

	/**
	 *	�����������ָ��λ�÷�Χ�ڵ������н��п�������
	 *		���������㷨���м亯����ʹ�õݹ麯���ķ�����ʵ��
	 *	@param array �������������������
	 *	@param left ��Ҫ����Ĳ��ֵ���߽��±�
	 *	@param right ��Ҫ����Ĳ��ֵ��ұ߽��±�
	 *	@date 2016/3/18
	 */
	private static void quickSort( int[] array, int left, int right ) {
		if ( right - left >= 1)
		{
			int cutOffPos = divideToTwoSets( array, left, right );

			/*//// ����Ϊ���Դ��룬�������ʱӦ��ע�͵�
				System.out.println("cutOffPos = " + cutOffPos);

				for ( int i=left; i<=right; i++)
				{
					System.out.print(array[i]);
					if (i < right)
					{
						System.out.print(", ");
					}
				}
			////*/


			quickSort( array, left, cutOffPos - 1 );
			quickSort( array, cutOffPos + 1, right );
		}
		else 
		{
			return;
		}
	}

	/**
	 *	�����������ָ��λ�÷�Χ�ڵ������з�Ϊ������
	 *		���������㷨���м亯��
	 *	@param array �������������������
	 *	@param left ��Ҫ����Ĳ��ֵ���߽��±�
	 *	@param right ��Ҫ����Ĳ��ֵ��ұ߽��±�
	 *	@return ����Ԫ�ص��±�
	 *	@date 2016/3/18
	 */
	public static int divideToTwoSets( int[] array, int left, int right )
	{
		int cutOff;
		int cutOffPos, tempLeft = left;
		//ѡȡ����Ԫ��
		if ( array[ left ] > array[ ( left + right ) /2 ] )
		{
			int temp = array[ left ];
			array[ left ] = array[ ( left + right ) /2 ];
			array[ ( left + right ) / 2 ] = temp;
		}
		if ( array[ ( left + right ) / 2 ] < array[ right ] )
		{
			int temp = array[ ( left + right ) / 2 ];
			array[ ( left + right ) / 2 ] = array[ right ];
			array[ right ] = temp;
		}
		if ( array[ left ] > array[ right ])
		{
			int temp = array[ left ];
			array[ left ] = array[ right ];
			array[ right ] = temp;
		}
		
		//������Ԫ��Ϊ�磬���������л���Ϊ������С��������
		cutOffPos = right;
		cutOff = array[ right-- ];

		/*//// ����Ϊ���Դ��룬�������ʱӦ��ע�͵�
				System.out.println("\ncutOff = " + cutOff + ", ");
		////*/

		while ( true )
		{
			while ( array[ left ] < cutOff && left < cutOffPos )
			{
				left++;	
			};
			while ( array[ right ] > cutOff && right >= tempLeft ) 
			{
				right--;	
			};

			if ( left < right)
			{
				int temp = array[ left ];
				array[ left ] = array[ right ];
				array[ right ] = temp;
				left++; //ʹleft�ƽ�һ�������ֲ�����Խ�硣������left��right
						//��Ӧλ�õ�Ԫ�����ʱ�������ѭ��
			} 
			else if ( left == right ) //��������Ĵ���
			{
				left++;
				break;
			}
			else //left��right����ʱ���˳�ѭ��
			{
				break;
			}
			/*//// ����Ϊ���Դ��룬�������ʱӦ��ע�͵�
				System.out.println("\nleft = " + left + ", cutOffPos = " + cutOffPos + ", right = " + right + ", tempLeft = " + tempLeft);
			////*/
		}

		int temp = array[ cutOffPos ];
		array[ cutOffPos ] = array[ left ];
		array[ left ] = temp;

		cutOffPos = left;

		return 	cutOffPos;	//��������Ԫ�ص��±�
		//System.out.println(".");
	}


	/**
	 *	�������������㷨�����ܣ����ڲ���
	 *	@param N ������������Ԫ�صĸ���
	 *	@date 2016.03.17
	 */
	public static void propertyDemo(int N) {

		//SimpleSort.propertyDemo(N);

		int[][] array = SimpleSorts.generateIntArray(6,N);
		System.out.println("������������Ԫ�صĸ�����" + N);	

		double startTimeUs = ((double)System.nanoTime())/1000;   //��ȡ��ʼʱ��  
		ComplexSorts.heapSort(array[0]); //�������򷨡�
		double endTimeUs = ((double)System.nanoTime())/1000; //��ȡ����ʱ�� 
		BigDecimal bgUs = new BigDecimal(endTimeUs-startTimeUs);  //�������3λС��
        double timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		System.out.println("��������ʱ�� "+timeCostUs+"��s");	
		
		startTimeUs = ((double)System.nanoTime())/1000;    
		SimpleSorts.shellSort(array[1]); //��ϣ�����򷨡�
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("ϣ��������ʱ�� "+timeCostUs+"��s");

		/*startTimeUs = ((double)System.nanoTime())/1000;    
		SimpleSort.insertionSort(array[2]); //�����뷨��
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("���뷨������ʱ�� "+timeCostUs+"��s"); */

		startTimeUs = ((double)System.nanoTime())/1000;    
		ComplexSorts.mergeSort(array[3]); //���鲢���򷨡�
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("�鲢������ʱ�� "+timeCostUs+"��s");

		startTimeUs = ((double)System.nanoTime())/1000;    
		Arrays.sort(array[4]); //Java����е�Arrays�����㷨
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("Java����е�Arrays�����㷨��ʱ�� "+timeCostUs+"��s");
	
		startTimeUs = ((double)System.nanoTime())/1000;    
		ComplexSorts.quickSort(array[5]); //���������򷨡�
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("����������ʱ�� "+timeCostUs+"��s");
	}

	/**
	 *	�������������㷨�Ĺ��ܣ����ڲ���
	 *	@param N ������������Ԫ�صĸ���
	 *	@date 2016.03.17
	 *		���н����ʾ��Java����е������㷨�����������Լ�д���㷨��Ҫ����
	 *		���������Ƚ�Сʱ���������㷨��ʱ��죻���ǵ��������ϴ�ʱ������10000��
	 *		�����������㷨�ٶ���죻�����������ܴ󣨱���8000000��ʱ��Java���
	 *		�е������㷨��죬���������㷨�ȳ�Java������㷨��������㷨�졣
	 */
	public static void functionDemo(int N) {

		//SimpleSort.functionDemo(N); 
		int[][] array = SimpleSorts.generateIntArray(4,N);
		
		System.out.println("\n�����򷨣�");
		ComplexSorts.heapSort(array[0]); //�������򷨡�
		SimpleSorts.printArray(array[0]);
		
		System.out.println("\n�鲢���򷨣�"); 
		ComplexSorts.mergeSort(array[1]); //���鲢���򷨡�
		SimpleSorts.printArray(array[1]);

		System.out.println("\nJava����е�Arrays�����㷨��"); 
		Arrays.sort(array[2]); //��Java����е�Arrays�����㷨��
		SimpleSorts.printArray(array[2]);

		System.out.println("\n�������򷨣�");
		ComplexSorts.quickSort(array[3]); //���������򷨡�
		SimpleSorts.printArray(array[3]);
		
		System.out.println("");
	}

	/**
	 *	�������������㷨���м亯��divideToTwoSetsDemo�Ƿ��������������ڵ���
	 *	@param N ������������Ԫ�صĸ���
	 *	@date 2016/3/18
	 */
	public static void divideToTwoSetsDemo(int N) {

		int[][] array = SimpleSorts.generateIntArray(1,N);
		System.out.println("ԭʼ���飨����" + array[0].length + "��Ԫ��)��");
		SimpleSorts.printArray(array[0]);

		System.out.println("\n����Ԫ�طֶΣ�cutOffPos = " + 
			ComplexSorts.divideToTwoSets( array[0], 0, array[0].length -1 ));
		SimpleSorts.printArray(array[0]);
	}


	public static void main(String[] args) 
	{
		int N = 20;
		
		//�����Է���
		ComplexSorts.functionDemo(N);
		//ComplexSorts.divideToTwoSetsDemo(N);
		
		/*//�����Է���
		N=8000000;
		System.out.println("");
		ComplexSorts.propertyDemo(N);*/
	}
}
