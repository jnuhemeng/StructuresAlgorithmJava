package com.hemeng;

import java.util.PriorityQueue;
import java.util.Random;
/**
 *	����ʹ��Java�ı�׼�����ṩ�Ķ��е�ʵ��
 *		Java 1.5�г����˷�����PriorityQueue,�ڸ����У�insert(),findMin()��deleteMin()
 *		ͨ���������е�add(),element()��remove()����������ʾ��
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
			mPriorityQueue.add(mRandom.nextInt(n*10)); //�൱��insert()
		}
		System.out.println("���ȶ��У�");
		System.out.println(mPriorityQueue.toString());
		
		System.out.println("������ε�deleteMin����ֵ��");
		for (int i=1; i<=n; i++)
		{
			System.out.print(mPriorityQueue.remove()); //�൱��deleteMin()
			if (i!=n)
			{
				System.out.print(", ");
			}
		}
		System.out.println();		
	}
}
