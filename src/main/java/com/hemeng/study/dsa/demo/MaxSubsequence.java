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

		/*计算程序运行时间*/
		long begin = System.currentTimeMillis(); // 这段代码放在程序执行前
		System.out.println("The sum of maxSubsequence is :"+maxSubSum1(a));
		long end = System.currentTimeMillis() - begin; // 这段代码放在程序执行后
		System.out.println("耗时：" + end + "毫秒");
	}

	public static int maxSubSum1(int [] a) //算法一：穷举法，包含三重嵌套，时间复杂度为大O三次的
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

