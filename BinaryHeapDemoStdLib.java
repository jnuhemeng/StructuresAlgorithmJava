package com.hemeng;

import java.util.PriorityQueue;
import java.util.Random;
/**
 *	测试使用Java的标准库中提供的队列的实现
 *		Java 1.5中出现了泛型类PriorityQueue,在该类中，insert(),findMin()和deleteMin()
 *		通过调用其中的add(),element()和remove()方法而被表示。
 */
class  BinaryHeapDemoStdLib
{
	public static void main(String[] args) 
	{
		PriorityQueue<Integer> mPriorityQueue = new PriorityQueue<>();
		Random mRandom = new Random();
		int n = 10;
		for (int i=1; i<=n; i++)
		{
			mPriorityQueue.add(mRandom.nextInt(n*10)); //相当于insert()
		}
		System.out.println("优先队列：");
		System.out.println(mPriorityQueue.toString());
		
		System.out.println("连续多次的deleteMin输入值：");
		for (int i=1; i<=n; i++)
		{
			System.out.print(mPriorityQueue.remove()); //相当于deleteMin()
			if (i!=n)
			{
				System.out.print(", ");
			}
		}
		System.out.println();		
	}
}
