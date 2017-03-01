package com.hemeng.study.dsa.sort;

import java.math.BigDecimal; 

import java.util.Arrays;

/**
 *	一些稍复杂的排序算法：堆排序（Nlog(N）复杂度）、归并排序、快速排序
 *
 */
public class  ComplexSorts
{
	/**
	 *	用 “堆排序法” 对输入的数组进行升序排序
	 *		先将输入数组通过下滤变成二叉堆，然后通过deleteMin操作得到当前
	 *		最大值并存放到数组的二叉堆数组当前的逻辑末尾
	 *	@param array 待排序的整型数组
	 *	@date 2016.03.17
	 */
	public static void heapSort(int[] array) {
		//以O（N）时间复杂度形成二叉堆结构
		for (int i=array.length/2; i>=0; i--) 
		{
			percDown(array,i,array.length);
		}	
		
		/*//输入形成的二叉堆，用于调试
		System.out.println("\n二叉堆：");
		SimpleSort.printArray(array);*/

		for (int i=array.length-1; i>0; i--)
		{
			//将二叉堆中，当前逻辑末尾的元素与第一个元素交换位置
			int temp = array[i];
			array[i] = array[0];
			array[0] = temp;
			//对二叉堆的第一个元素进行下滤操作
			percDown(array,0,i);
		}
	}

	/**
	 *	用 “归并排序法” 对输入的数组进行升序排序
	 *	@param array 待排序的整型数组
	 *	@date 2016/3/18
	 */
	public static void mergeSort(int[] array) {
		int[] tmpArray = new int[array.length]; 
		mergeSort( array, tmpArray, 0, array.length-1);		
	}

	/**
	 *	用快速排序法对输入数组进行排序
	 *	@param 待排序的整型数组
	 *	@date 2016/3/18
	 */
	public static void quickSort( int[] array ) {
		quickSort( array, 0, array.length-1 );
	}

	/**
	 *	对指定元素执行向下滤操作以保持max堆的结构
	 *	@param array max堆对应的整型数组
	 *	@param i 下滤开始处的元素的下标(该max堆元素下标从0开始)
	 *	@param n max堆的大小
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
	 *	对输入数组中指定位置范围内的元素用“归并排序法”进行升序排序
	 *		（“归并排序法”排序函数的中间函数）
	 *		用递归的方法来实现，为了实现与其它排序法接口的统一，需要为这个函数
	 *		另外写一个驱动程序。
	 *	@param array 整型数组，待排序的数组
	 *	@param tmpArray 整型数组，用来存放中间结果
	 *	@param left 待排序数组中需要排序的范围的左边界下标
	 *	@param right 待排序数组中需要排序的范围的右边界下标
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
	 *	将输入数组中指定位置范围内的、相邻的两组元素合并为一组升序元素并存放到该数组
	 *		中的相同位置。（“归并排序法”排序函数的中间函数）
	 *	@param array 整型数组，待合并的数组
	 *	@param tmpArray 整型数组，用来存放中间结果
	 *	@param leftPos 待合并数组中第一组需要合并的范围的左边界下标
	 *	@param rightPos 待合并数组中第二组需要合并的范围的左边界下标
	 *	@param rightEnd 待合并数组中第二组需要合并的范围的右边界下标
	 *	@date 2016/3/18
	 */
	private static void merge( int[] array, int[] tmpArray, int leftPos, int rightPos, int rightEnd) {
		int tmpPos = leftPos;
		int leftEnd = rightPos - 1;
		int numElements = rightEnd - leftPos + 1;

		//主循环
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
		//两个补充的循环
		while ( leftPos <= leftEnd )
		{
			tmpArray[ tmpPos++ ] = array[ leftPos++ ];
		}
		while ( rightPos <= rightEnd )
		{
			tmpArray[ tmpPos++ ] = array[ rightPos++ ];
		}
		
		//将中间结果中的元素按顺序逐个拷贝回原数组
		for ( int i=1 ; i <= numElements; i++, rightEnd-- )
		{
			array[ rightEnd ] = tmpArray[ rightEnd ];
		}
	}

	/**
	 *	对输入数组的指定位置范围内的子序列进行快速排序
	 *		快速排序算法的中间函数，使用递归函数的方法来实现
	 *	@param array 待排序的整个整型数组
	 *	@param left 需要排序的部分的左边界下标
	 *	@param right 需要排序的部分的右边界下标
	 *	@date 2016/3/18
	 */
	private static void quickSort( int[] array, int left, int right ) {
		if ( right - left >= 1)
		{
			int cutOffPos = divideToTwoSets( array, left, right );

			/*//// 下面为调试代码，调试完毕时应该注释掉
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
	 *	将输入数组的指定位置范围内的子序列分为两部分
	 *		快速排序算法的中间函数
	 *	@param array 待排序的整个整型数组
	 *	@param left 需要排序的部分的左边界下标
	 *	@param right 需要排序的部分的右边界下标
	 *	@return 中枢元素的下标
	 *	@date 2016/3/18
	 */
	public static int divideToTwoSets( int[] array, int left, int right )
	{
		int cutOff;
		int cutOffPos, tempLeft = left;
		//选取中枢元素
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
		
		//以中枢元素为界，将该子序列划分为两个更小的子序列
		cutOffPos = right;
		cutOff = array[ right-- ];

		/*//// 下面为调试代码，调试完毕时应该注释掉
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
				left++; //使left推进一步，而又不至于越界。避免因left和right
						//对应位置的元素相等时引起的死循环
			} 
			else if ( left == right ) //特殊情况的处理
			{
				left++;
				break;
			}
			else //left和right交错时，退出循环
			{
				break;
			}
			/*//// 下面为调试代码，调试完毕时应该注释掉
				System.out.println("\nleft = " + left + ", cutOffPos = " + cutOffPos + ", right = " + right + ", tempLeft = " + tempLeft);
			////*/
		}

		int temp = array[ cutOffPos ];
		array[ cutOffPos ] = array[ left ];
		array[ left ] = temp;

		cutOffPos = left;

		return 	cutOffPos;	//返回中枢元素的下标
		//System.out.println(".");
	}


	/**
	 *	分析各种排序算法的性能，用于测试
	 *	@param N 测试数组所含元素的个数
	 *	@date 2016.03.17
	 */
	public static void propertyDemo(int N) {

		//SimpleSort.propertyDemo(N);

		int[][] array = SimpleSorts.generateIntArray(6,N);
		System.out.println("测试数组所含元素的个数：" + N);	

		double startTimeUs = ((double)System.nanoTime())/1000;   //获取开始时间  
		ComplexSorts.heapSort(array[0]); //“堆排序法”
		double endTimeUs = ((double)System.nanoTime())/1000; //获取结束时间 
		BigDecimal bgUs = new BigDecimal(endTimeUs-startTimeUs);  //结果保留3位小数
        double timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		System.out.println("堆排序用时： "+timeCostUs+"μs");	
		
		startTimeUs = ((double)System.nanoTime())/1000;    
		SimpleSorts.shellSort(array[1]); //“希尔排序法”
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("希尔排序用时： "+timeCostUs+"μs");

		/*startTimeUs = ((double)System.nanoTime())/1000;    
		SimpleSort.insertionSort(array[2]); //“插入法”
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("插入法排序用时： "+timeCostUs+"μs"); */

		startTimeUs = ((double)System.nanoTime())/1000;    
		ComplexSorts.mergeSort(array[3]); //“归并排序法”
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("归并排序用时： "+timeCostUs+"μs");

		startTimeUs = ((double)System.nanoTime())/1000;    
		Arrays.sort(array[4]); //Java类库中的Arrays排序算法
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("Java类库中的Arrays排序算法用时： "+timeCostUs+"μs");
	
		startTimeUs = ((double)System.nanoTime())/1000;    
		ComplexSorts.quickSort(array[5]); //“快速排序法”
		endTimeUs = ((double)System.nanoTime())/1000; 
		bgUs = new BigDecimal(endTimeUs-startTimeUs);  
        timeCostUs = bgUs.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("快速排序法用时： "+timeCostUs+"μs");
	}

	/**
	 *	分析各种排序算法的功能，用于测试
	 *	@param N 测试数组所含元素的个数
	 *	@date 2016.03.17
	 *		运行结果显示：Java类库中的排序算法比其他几种自己写的算法都要慢。
	 *		当数据量比较小时，堆排序算法用时最快；但是当数据量较大时（比如10000）
	 *		，快速排序算法速度最快；而但数据量很大（比如8000000）时，Java类库
	 *		中的排序算法最快，快速排序算法比除Java类库中算法外的其他算法快。
	 */
	public static void functionDemo(int N) {

		//SimpleSort.functionDemo(N); 
		int[][] array = SimpleSorts.generateIntArray(4,N);
		
		System.out.println("\n堆排序法：");
		ComplexSorts.heapSort(array[0]); //“堆排序法”
		SimpleSorts.printArray(array[0]);
		
		System.out.println("\n归并排序法："); 
		ComplexSorts.mergeSort(array[1]); //“归并排序法”
		SimpleSorts.printArray(array[1]);

		System.out.println("\nJava类库中的Arrays排序算法："); 
		Arrays.sort(array[2]); //“Java类库中的Arrays排序算法”
		SimpleSorts.printArray(array[2]);

		System.out.println("\n快速排序法：");
		ComplexSorts.quickSort(array[3]); //“快速排序法”
		SimpleSorts.printArray(array[3]);
		
		System.out.println("");
	}

	/**
	 *	分析快速排序算法的中间函数divideToTwoSetsDemo是否正常工作，用于调试
	 *	@param N 测试数组所含元素的个数
	 *	@date 2016/3/18
	 */
	public static void divideToTwoSetsDemo(int N) {

		int[][] array = SimpleSorts.generateIntArray(1,N);
		System.out.println("原始数组（包含" + array[0].length + "个元素)：");
		SimpleSorts.printArray(array[0]);

		System.out.println("\n中枢元素分段：cutOffPos = " + 
			ComplexSorts.divideToTwoSets( array[0], 0, array[0].length -1 ));
		SimpleSorts.printArray(array[0]);
	}


	public static void main(String[] args) 
	{
		int N = 20;
		
		//功能性分析
		ComplexSorts.functionDemo(N);
		//ComplexSorts.divideToTwoSetsDemo(N);
		
		/*//性能性分析
		N=8000000;
		System.out.println("");
		ComplexSorts.propertyDemo(N);*/
	}
}
