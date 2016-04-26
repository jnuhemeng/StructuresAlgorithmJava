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

	public int size() //���ص�ǰ����ĳ���
	{
		return theSize;
	}

	public boolean enqueue(AnyType data) //��һ����Ԫ�������β
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

	public AnyType dequeue() //��������ǰ���Ԫ�س�����
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

	private static class Node<AnyType> //����Ľڵ�
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
		System.out.println("������ݣ�");
		for(int i=0;i<=10;i++)
		{
			System.out.print(" "+i);
			myLinkedQueue.enqueue(i);
		}
		System.out.println("\n��ǰ���еĳ���Ϊ��"+myLinkedQueue.size());
		System.out.println("\n�������ݣ�");
		for(int j=0;j<=10;j++)
		{
			System.out.print(" "+myLinkedQueue.dequeue());
		}

		System.out.println();
	}
}
