package com.hemeng;

import java.util.Random;

/**
 *	��ʽ�ѵ�ʵ��
 *	���ο��鱾���õݹ鷽ʽ��ʵ�֣�
 *	merge(�ϲ�����)�����Ӷ�������Ĳ���������������deleteMin��insert����ͨ����һ������ʵ��
 *		��������������ʱ�临�Ӷȶ��Ƕ���ʱ��
 */
public class LeftistHeap
{
	private Node root; //��ʽ�ѱ����Ը��ڵ������

	public LeftistHeap() {
		root=null;
	}

	/**
	 *	���������ʽ�Ѻϲ�����ǰ����ʽ����
	 *	@param rhs ��Ҫ�ϲ�����ǰ��ʽ�ѵ���һ����ʽ��
	 */
	public void merge(LeftistHeap rhs) {
		if (rhs == this)
		{
			return;
		}
		root = merge1(root, rhs.root);
		rhs.root = null;
	}
	
	/**
	 *	����һ���ڵ�
	 *	@param x ��Ҫ����Ľڵ��ֵ
	 */
	public void insert(int x) {
		root = merge1(root, new Node(x));
	}

	/**
	 *	���ض����е���Сֵ
	 */
	public int findMin() {
		return root.element;
	}
	
	/**
	 *	ɾ�������е���Сֵ
	 */
	public int deleteMin() {
		int min = root.element;
		root = merge1(root.right, root.left);	
		return min;
	}

	/**
	 *	�������ӡ�������������ã�
	 *		����deleteMin������������������ı����Ӷ��е�Ԫ�أ�
	 */
	public void printSortedLeftistHeap() {
		while (root != null)
		{
			System.out.print(deleteMin() + ", "); 
		}			
	}

	/**
	 *	��ӡ�������������򣨵����ã�
	 */
	public void printLeftistHeap() {
		printLeftistHeap(root);
	}
	/**
	 *	�����������ӡ������
	 */
	private void printLeftistHeap(Node t) {
		if (t != null)
		{
			printLeftistHeap(t.left);
			System.out.print(t.element + ", ");
			printLeftistHeap(t.right);
		} else {
			return;
		}
	}
	
	/**
	 *	������������������Ӷѽڵ���кϲ�(�м䷽��)
	 *	@param h1
	 *	@param h2
	 */
	private Node merge1(Node h1, Node h2) {
		if (h1 == null)
		{
			return h2;
		}
		if (h2 == null)
		{
			return h1;
		}

		if (h1.element <= h2.element)
		{
			return merge2(h1,h2);
		} else {
			return merge2(h2,h1);
		}
	}
	
	/**
	 *	�������ڶ����������һ�����������������кϲ����м䷽����
	 *	@param h1
	 *	@param h2
	 */
	private Node merge2(Node h1, Node h2) {
		if (h1.left == null)
		{
			h1.left = h2;
		} else {
			h1.right = merge1(h1.right,h2);
			if (h1.left.npl < h1.right.npl)
			{
				swapChildren(h1);
			}
			h1.npl = h1.right.npl + 1;
		}
		return h1;
	}

	/**
	 *	��������ڵ����������
	 *	@param ��Ҫ���������������Ľڵ�
	 */
	private void swapChildren(Node t) {
		Node temp = t.left;
		t.left = t.right;
		t.right = temp;
	}


	/**
	 *	��ʽ�ѵĽڵ���
	 *	ÿ���ڵ����Ԫ��ֵ������ָ�롢�Һ���ָ�롢�ڵ����·����
	 */
	private class Node
	{
		int element;
		Node left;
		Node right;
		int npl;

		public Node(int theElement) {
			this(theElement,null,null);
		}

		public Node(int theElement,Node theLeft,Node theRight) {
			element = theElement;
			left = theLeft;
			right = theRight;
		}
	}

	public static void main(String[] args) 
	{
		
		Random mRandom = new Random();
		//int n = 4, m = 50000; //�������Ӷ���Ԫ�صĸ���
		int n = 4, m = 9; //�������Ӷ���Ԫ�صĸ���
		//��һ�����Ӷ�
		LeftistHeap mLeftistHeap = new LeftistHeap();
		for (int i=1; i<=n; i++)
		{
			mLeftistHeap.insert(mRandom.nextInt(n*10)); //insert����
		}
		System.out.println("��һ�����Ӷѣ�");
		mLeftistHeap.printLeftistHeap(); 
		//�ڶ������Ӷ�
		LeftistHeap mLeftistHeap2 = new LeftistHeap();
		for (int i=1; i<=m; i++)
		{
			mLeftistHeap2.insert(mRandom.nextInt(m*10)); 
		}
		System.out.println("\n�ڶ������Ӷѣ�");
		mLeftistHeap2.printLeftistHeap();

		mLeftistHeap.merge(mLeftistHeap2); //�ϲ�����

		System.out.println("\n�ϲ�������Ӷѣ�");
		for (int i=1; i<=n+m; i++)
		{
			System.out.print(mLeftistHeap.deleteMin()); //deleteMin����
			if (i != (n+m))
			{
				System.out.print(", ");
			}
		}

		System.out.println();
	}
}
