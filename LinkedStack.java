package com.hemeng;

/**
 * 用单链表实现栈类
 */
public class LinkedStack<AnyType> 
{
	private Node<AnyType> top;
	private int theSize;

	public LinkedStack() //构造函数
	{
		clear();
	}

	private void clear()
	{
		top=new Node<AnyType>(null,null);
		theSize=0;
	}


	public boolean push(AnyType data) //入栈，因为采用链表故不会越界
	{
		top=new Node<AnyType>(data,top);
		theSize++;
		return true;
	}

	public AnyType pop() //出栈，可能出现栈为空的情况，一旦出现则抛出异常 
	{
		AnyType data;
		if(theSize>0)
		{
			data=top.data;
			top=top.next;
			theSize--;
		}
		else
			throw new IndexOutOfBoundsException();
		return data;
	}


	private static class Node<AnyType> //链表的节点
	{
		public AnyType data;
		public Node<AnyType> next;

		public Node(AnyType data,Node<AnyType> next)
		{
			this.data=data;
			this.next=next;
		}		
	}


	public static void main(String[] args)
	{
		LinkedStack<Integer> myLinkedStack=new LinkedStack<>();
		System.out.println("入栈数据：");
		for(int i=0;i<=10;i++)
		{
			System.out.print(" "+i);
			myLinkedStack.push(i);
		}
		System.out.println("\n出栈数据：");
		for(int j=0;j<=10;j++)
		{
			System.out.print(" "+myLinkedStack.pop());
		}

		System.out.println();
	}
}
