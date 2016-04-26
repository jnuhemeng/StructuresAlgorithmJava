package com.hemeng;

/**
 * 优先队列（用最常见的二叉堆来实现）
 */
public class  BinaryHeap
{
	private static final int DEFAULT_CAPACITY=10; //默认容量大小:DEFAULT_CAPACITY
	private int currentsize;
	private int[] array;

	public  BinaryHeap()
	{
		//array=(int[]) new Comparable[DEFAULT_CAPACITY];
		array=(int[]) new int[DEFAULT_CAPACITY];
		//currentsize=1;
		currentsize=0; //当前容量大小，初始为0
	}
	public  BinaryHeap(int capacity)
	{
		array=(int[]) new int[capacity+1]; //二叉堆的节点从1（而非0）开始编号，所以数组的第一个元素未被用到
		currentsize=0; //当前容量大小，初始为0
	}
	public  BinaryHeap(int[] items) //根据已有的数组创建堆
	{
		currentsize = items.length;
		array = new int[currentsize+1];

		for(int i=1;i<=currentsize;i++) {
			array[i] = items[i-1];
		}

		buildHeap(); 
	}
	
	/**
	 *	插入操作
	 *	@param x the element to insert
	 */
	public void insert(int x)
	{	
		//判断是否需要进行扩容
		if(currentsize+1 >= array.length) {
			enlargeArray(currentsize*2+1); //如果需要扩容，就把堆的容量扩大为当前堆中元素个数的两倍
		}
		int currentpos = ++currentsize; 
		int temp;
		//array[currentpos]=x;
		//while(currentpos>1&&array[currentpos]。compareTo(array[currentpos/2])<0)
		while(currentpos>1 && x-array[currentpos/2]<0)
		{
			/*temp=array[currentpos/2];
			array[currentpos/2]=array[currentpos];
			array[currentpos]=temp;
			currentpos=currentpos/2;*/
			array[currentpos]=array[currentpos/2];
			currentpos=currentpos/2;
		}
		array[currentpos]=x;
	}
	
	/**
	 *	返回堆中的最小元素
	 *	@date 2016.03.14
	 */
	public int findMin() {
		return array[1];
	}

	/**
	 *	使队列中的最小值出列
	 *	@date 2016.03.14
	 */
	public int deleteMin()
	{
		/*int Min=array[1];
		int temp=array[currentsize--];
		int pos=1;*/
		/*while((pos*2+1)<=currentsize+1)
		{
			if(temp>array[pos*2]||temp>array[pos*2+1]) //空穴的两个儿子中至少有一个比队尾小
			{
				if(array[pos*2]>array[pos*2+1]&&temp>array[pos*2+1]) //右儿子在上述三个最小
				{
					array[pos]=array[pos*2+1];
					pos=pos*2+1;
				}
				else
				{
					array[pos]=array[pos*2];
					pos=pos*2;
				}
			}
			else


			{
				array[pos]=temp;
				return Min;
			}

		}*/
		/*while(pos*2 <= currentsize) {
			int child = pos*2;
			if(child+1<=currentsize && array[child+1]<array[child]) {
				child = child+1;
			}
			if(array[child] < temp) {
				array[pos] = array[child];
				pos = child;
			} else {
				break;
			}
		}

		array[pos] = temp;
		return Min;*/

		int Min=array[1];
		array[1] = array[currentsize--]; //取出位于根节点的数（即最小的数）
		percolateDown(1);
		return Min;

	}

	/**
	 *	打印堆中的所有元素（临时增加的方法，只用于测试）
	 *	@date 2016.03.14
	 */
	public void printHeap()
	{
		//System.out.print("currentsize is : " + currentsize + "\n");
		if( currentsize <= 0 ){
			return;
		}
		for(int i=1;i<currentsize;i++) {
			System.out.print(array[i] + ",");
		}
		System.out.print(array[currentsize] + "\n");
	}
	
	/**
	 *	返回队列当前元素的个数
	 *	（破坏了封装性，为了方便外部方法实现合并等操作）
	 *	@date 2016.03.15
	 */
	public int getCurrentSize() {
		return currentsize;
	}
	/**
	 *	返回队列的数组
	 *	（破坏了封装性，为了方便外部方法实现合并等操作）
	 *	@date 2016.03.15
	 */
	public int[] getArray() {
		return array;
	}

	/**
	 *	按升序打印整棵树（调试用）
	 *		基于deleteMin方法，因而这个函数会改变左子堆中的元素！
	 */
	public void printSortedHeap() {
		int n = currentsize;
		for (int i=1; i<=n; i++)
		{
			System.out.print(deleteMin()); //deleteMin操作
			if (i != n)
			{
				System.out.print(", ");
			}
		}		
	}

	/**
	 *	从指定位置开始执行下滤操作（私有方法）
	 *	@param pos  the index at which the percoloate begins 
	 *	@date 2016.03.14
	 */
	private void percolateDown(int pos) {
		int temp = array[pos];
		while(pos*2 <= currentsize) {
			int child = pos*2;
			if(child+1<=currentsize && array[child+1]<array[child]) {
				child = child+1;
			}
			if(array[child] < temp) {
				array[pos] = array[child];
				pos = child;
			} else {
				break;
			}
		}
		array[pos] = temp;
	}
	
	/**
	 *	扩大堆的容量（私有方法）
	 *  @param newSize 扩容后堆的容量
	 *	@date 2016.03.14
	 */
	private void enlargeArray(int newSize) {
		if(newSize <= currentsize) {
			return;
		}
		int[] oldArray = array;
		array = new int[newSize];
		for(int i=1;i<=currentsize;i++) {
			array[i] = oldArray[i];
		}
	}
	/**
	 *	调整array的结构，使之具有堆的结构
	 */
	private void buildHeap() {
		for(int i=currentsize/2;i>=1;i--) {
			percolateDown(i);
		}
	}
	

	public static void main(String[] args) 
	{		
		
		BinaryHeap myBinaryHeap=new BinaryHeap();		
		//测试插入操作
		myBinaryHeap.insert(9);
		myBinaryHeap.insert(5);
		myBinaryHeap.insert(74);
		myBinaryHeap.insert(10);
		myBinaryHeap.insert(23);
		myBinaryHeap.insert(7);	
		myBinaryHeap.insert(2);
		myBinaryHeap.insert(19);
		myBinaryHeap.insert(35); 
		myBinaryHeap.insert(1);		
		 
		//以纳秒(ns)为单位计算一段代码的运行时间
		long startTimeNs=System.nanoTime();   //获取开始时间  
		myBinaryHeap.insert(100);	
		long endTimeNs=System.nanoTime(); //获取结束时间 
		System.out.print("每次插入操作的运行时间： "+(endTimeNs-startTimeNs)+"ns\n");  
		
		//测试使队列中的最小值出列的操作
		myBinaryHeap.printHeap(); //输出堆中的所有元素
		System.out.print("\nThe minimum element is : " + myBinaryHeap.deleteMin() + "\n");//使队列中的最小值出列
		myBinaryHeap.printHeap();
		System.out.print("\nThe minimum element is : " + myBinaryHeap.deleteMin() + "\n");//使队列中的最小值出列
		myBinaryHeap.printHeap();
		
		//测试返回堆最小值
		System.out.print("\n");
		myBinaryHeap.printHeap();
		System.out.print("The minimum elements is : " + myBinaryHeap.findMin());
		System.out.print("\n");
		
		/*//测试用第三种构造函数生成堆
		int[] testSamples = {9,5,74,10,23,7,2,19,35,1};
		BinaryHeap myBinaryHeap=new BinaryHeap(testSamples);	
		myBinaryHeap.printHeap();*/

	}
}
