package com.hemeng;

/**
 * �õ�����ʵ��ջ��
 */
public class LinkedStack<AnyType> 
{
	private Node<AnyType> top;
	private int theSize;

	public LinkedStack() //���캯��
	{
		clear();
	}

	private void clear()
	{
		top=new Node<AnyType>(null,null);
		theSize=0;
	}


	public boolean push(AnyType data) //��ջ����Ϊ��������ʲ���Խ��
	{
		top=new Node<AnyType>(data,top);
		theSize++;
		return true;
	}

	public AnyType pop() //��ջ�����ܳ���ջΪ�յ������һ���������׳��쳣 
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
		LinkedStack<Integer> myLinkedStack=new LinkedStack<>();
		System.out.println("��ջ���ݣ�");
		for(int i=0;i<=10;i++)
		{
			System.out.print(" "+i);
			myLinkedStack.push(i);
		}
		System.out.println("\n��ջ���ݣ�");
		for(int j=0;j<=10;j++)
		{
			System.out.print(" "+myLinkedStack.pop());
		}

		System.out.println();
	}
}
