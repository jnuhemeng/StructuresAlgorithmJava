package com.hemeng;

public class LinkedQueue<AnyType>  
{
	private Node<AnyType> front;
	private Node<AnyType> back;
	private int theSize;

	public LinkedQueue()
	{
		front=null;
		back=null;
		theSize=0;
	}

	public int size() //返回当前队伍的长度
	{
		return theSize;
	}

	public boolean enqueue(AnyType data) //将一个新元素送入队尾
	{
		Node<AnyType> NewNode=new Node<>(data,null);
		if(theSize>0)
			back.next=NewNode;					
		else
			front=NewNode;
		back=NewNode;
		theSize++;
		return true;
	}

	public AnyType dequeue() //将队伍最前面的元素出队列
	{
		AnyType data;
		if(theSize>0)
		{
			data=front.data;
			front=front.next;
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
		LinkedQueue<Integer> myLinkedQueue=new LinkedQueue<>();
		System.out.println("入队数据：");
		for(int i=0;i<=10;i++)
		{
			System.out.print(" "+i);
			myLinkedQueue.enqueue(i);
		}
		System.out.println("\n当前队列的长度为："+myLinkedQueue.size());
		System.out.println("\n出队数据：");
		for(int j=0;j<=10;j++)
		{
			System.out.print(" "+myLinkedQueue.dequeue());
		}

		System.out.println();
	}
}
