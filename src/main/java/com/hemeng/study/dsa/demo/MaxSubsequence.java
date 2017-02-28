package com.hemeng.study.dsa.demo;

class MaxSubsequence 
{
	public static void main(String[] args) 
	{
		int [] a={-2,11,-4,13,-5,-2};
		System.out.print("The original sequence is : a={ ");
		for(int i=0;i<a.length;i++)
		{
			System.out.print(a[i]+" ");
		}
		System.out.print("}\n");

		/*�����������ʱ��*/
		long begin = System.currentTimeMillis(); // ��δ�����ڳ���ִ��ǰ
		System.out.println("The sum of maxSubsequence is :"+maxSubSum1(a));
		long end = System.currentTimeMillis() - begin; // ��δ�����ڳ���ִ�к�
		System.out.println("��ʱ��" + end + "����");
	}

	public static int maxSubSum1(int [] a) //�㷨һ����ٷ�����������Ƕ�ף�ʱ�临�Ӷ�Ϊ��O���ε�
	{
		int maxSum=0;
		for(int i=0;i<a.length;i++)
		{
			for(int j=i;j<a.length;j++)
			{
				int thisSum=0;
				for(int k=i;k<a.length;k++)
				{
					thisSum+=a[k];
					if(thisSum>maxSum)
						 maxSum=thisSum;
				}
			}
		}
		return maxSum;
	}
}

