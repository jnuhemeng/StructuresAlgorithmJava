package com.hemeng;

/**
 *	ʱ�临�Ӷ�Ϊ����������
 */
class Olog 
{
	public static void main(String[] args) 
	{
		int [] a={1, 2, 3, 4, 5,25,89,122,334};
		int A=1;

		System.out.print("The original sequence is : a={ ");
		for(int i=0;i<a.length;i++)
		{
			System.out.print(a[i]+" ");
		}
		System.out.print("}\n");

		System.out.println("A="+A+" is at: "+binarySearch(a,A));
	}

	/**
	 *	�۰���ҷ�
	 *	�ҵ��򷵻����±꣬δ�ҵ��򷵻�-1
	 */
	public static int binarySearch(int [] a,int A)
	{
		int low=0;
		int high=a.length-1;

		while(low<=high)
		{		
			int middle=(low+high)/2;
			if(a[middle]>A)
			{
				high=middle-1;
			}
			else if(a[middle]<A)
			{
				low=middle+1;
			}
			else
			{
				return middle;
			}
		}
		return -1;
	}
}
