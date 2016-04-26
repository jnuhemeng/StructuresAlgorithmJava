package com.hemeng;

/**
 * ���ȶ��У�������Ķ������ʵ�֣�
 */
public class  BinaryHeap
{
	private static final int DEFAULT_CAPACITY=10; //Ĭ��������С:DEFAULT_CAPACITY
	private int currentsize;
	private int[] array;

	public  BinaryHeap()
	{
		//array=(int[]) new Comparable[DEFAULT_CAPACITY];
		array=(int[]) new int[DEFAULT_CAPACITY];
		//currentsize=1;
		currentsize=0; //��ǰ������С����ʼΪ0
	}
	public  BinaryHeap(int capacity)
	{
		array=(int[]) new int[capacity+1]; //����ѵĽڵ��1������0����ʼ��ţ���������ĵ�һ��Ԫ��δ���õ�
		currentsize=0; //��ǰ������С����ʼΪ0
	}
	public  BinaryHeap(int[] items) //�������е����鴴����
	{
		currentsize = items.length;
		array = new int[currentsize+1];

		for(int i=1;i<=currentsize;i++) {
			array[i] = items[i-1];
		}

		buildHeap(); 
	}
	
	/**
	 *	�������
	 *	@param x the element to insert
	 */
	public void insert(int x)
	{	
		//�ж��Ƿ���Ҫ��������
		if(currentsize+1 >= array.length) {
			enlargeArray(currentsize*2+1); //�����Ҫ���ݣ��ͰѶѵ���������Ϊ��ǰ����Ԫ�ظ���������
		}
		int currentpos = ++currentsize; 
		int temp;
		//array[currentpos]=x;
		//while(currentpos>1&&array[currentpos]��compareTo(array[currentpos/2])<0)
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
	 *	���ض��е���СԪ��
	 *	@date 2016.03.14
	 */
	public int findMin() {
		return array[1];
	}

	/**
	 *	ʹ�����е���Сֵ����
	 *	@date 2016.03.14
	 */
	public int deleteMin()
	{
		/*int Min=array[1];
		int temp=array[currentsize--];
		int pos=1;*/
		/*while((pos*2+1)<=currentsize+1)
		{
			if(temp>array[pos*2]||temp>array[pos*2+1]) //��Ѩ������������������һ���ȶ�βС
			{
				if(array[pos*2]>array[pos*2+1]&&temp>array[pos*2+1]) //�Ҷ���������������С
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
		array[1] = array[currentsize--]; //ȡ��λ�ڸ��ڵ����������С������
		percolateDown(1);
		return Min;

	}

	/**
	 *	��ӡ���е�����Ԫ�أ���ʱ���ӵķ�����ֻ���ڲ��ԣ�
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
	 *	���ض��е�ǰԪ�صĸ���
	 *	���ƻ��˷�װ�ԣ�Ϊ�˷����ⲿ����ʵ�ֺϲ��Ȳ�����
	 *	@date 2016.03.15
	 */
	public int getCurrentSize() {
		return currentsize;
	}
	/**
	 *	���ض��е�����
	 *	���ƻ��˷�װ�ԣ�Ϊ�˷����ⲿ����ʵ�ֺϲ��Ȳ�����
	 *	@date 2016.03.15
	 */
	public int[] getArray() {
		return array;
	}

	/**
	 *	�������ӡ�������������ã�
	 *		����deleteMin������������������ı����Ӷ��е�Ԫ�أ�
	 */
	public void printSortedHeap() {
		int n = currentsize;
		for (int i=1; i<=n; i++)
		{
			System.out.print(deleteMin()); //deleteMin����
			if (i != n)
			{
				System.out.print(", ");
			}
		}		
	}

	/**
	 *	��ָ��λ�ÿ�ʼִ�����˲�����˽�з�����
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
	 *	����ѵ�������˽�з�����
	 *  @param newSize ���ݺ�ѵ�����
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
	 *	����array�Ľṹ��ʹ֮���жѵĽṹ
	 */
	private void buildHeap() {
		for(int i=currentsize/2;i>=1;i--) {
			percolateDown(i);
		}
	}
	

	public static void main(String[] args) 
	{		
		
		BinaryHeap myBinaryHeap=new BinaryHeap();		
		//���Բ������
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
		 
		//������(ns)Ϊ��λ����һ�δ��������ʱ��
		long startTimeNs=System.nanoTime();   //��ȡ��ʼʱ��  
		myBinaryHeap.insert(100);	
		long endTimeNs=System.nanoTime(); //��ȡ����ʱ�� 
		System.out.print("ÿ�β������������ʱ�䣺 "+(endTimeNs-startTimeNs)+"ns\n");  
		
		//����ʹ�����е���Сֵ���еĲ���
		myBinaryHeap.printHeap(); //������е�����Ԫ��
		System.out.print("\nThe minimum element is : " + myBinaryHeap.deleteMin() + "\n");//ʹ�����е���Сֵ����
		myBinaryHeap.printHeap();
		System.out.print("\nThe minimum element is : " + myBinaryHeap.deleteMin() + "\n");//ʹ�����е���Сֵ����
		myBinaryHeap.printHeap();
		
		//���Է��ض���Сֵ
		System.out.print("\n");
		myBinaryHeap.printHeap();
		System.out.print("The minimum elements is : " + myBinaryHeap.findMin());
		System.out.print("\n");
		
		/*//�����õ����ֹ��캯�����ɶ�
		int[] testSamples = {9,5,74,10,23,7,2,19,35,1};
		BinaryHeap myBinaryHeap=new BinaryHeap(testSamples);	
		myBinaryHeap.printHeap();*/

	}
}
