package com.hemeng;

import java.util.Random;

/**
 *	左式堆的实现
 *	（参考书本，用递归方式来实现）
 *	merge(合并操作)是左子堆最基本的操作，其他操作（deleteMin，insert）都通过这一操作来实现
 *		上述三个操作的时间复杂度都是对数时间
 */
public class LeftistHeap
{
	private Node root; //左式堆保留对根节点的引用

	public LeftistHeap() {
		root=null;
	}

	/**
	 *	将传入的左式堆合并到当前的左式堆中
	 *	@param rhs 需要合并到当前左式堆的另一个左式堆
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
	 *	插入一个节点
	 *	@param x 需要插入的节点的值
	 */
	public void insert(int x) {
		root = merge1(root, new Node(x));
	}

	/**
	 *	返回队列中的最小值
	 */
	public int findMin() {
		return root.element;
	}
	
	/**
	 *	删除队列中的最小值
	 */
	public int deleteMin() {
		int min = root.element;
		root = merge1(root.right, root.left);	
		return min;
	}

	/**
	 *	按升序打印整棵树（调试用）
	 *		基于deleteMin方法，因而这个函数会改变左子堆中的元素！
	 */
	public void printSortedLeftistHeap() {
		while (root != null)
		{
			System.out.print(deleteMin() + ", "); 
		}			
	}

	/**
	 *	打印整棵树驱动程序（调试用）
	 */
	public void printLeftistHeap() {
		printLeftistHeap(root);
	}
	/**
	 *	中序遍历法打印整棵树
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
	 *	迭代将传入的两个左子堆节点进行合并(中间方法)
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
	 *	迭代将第二个参数与第一个参数的右子树进行合并（中间方法）
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
	 *	交换传入节点的左右子树
	 *	@param 需要交换其左右子树的节点
	 */
	private void swapChildren(Node t) {
		Node temp = t.left;
		t.left = t.right;
		t.right = temp;
	}


	/**
	 *	左式堆的节点类
	 *	每个节点包括元素值、左孩子指针、右孩子指针、节点的零路径长
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
		//int n = 4, m = 50000; //两个左子堆中元素的个数
		int n = 4, m = 9; //两个左子堆中元素的个数
		//第一个左子堆
		LeftistHeap mLeftistHeap = new LeftistHeap();
		for (int i=1; i<=n; i++)
		{
			mLeftistHeap.insert(mRandom.nextInt(n*10)); //insert操作
		}
		System.out.println("第一个左子堆：");
		mLeftistHeap.printLeftistHeap(); 
		//第二个左子堆
		LeftistHeap mLeftistHeap2 = new LeftistHeap();
		for (int i=1; i<=m; i++)
		{
			mLeftistHeap2.insert(mRandom.nextInt(m*10)); 
		}
		System.out.println("\n第二个左子堆：");
		mLeftistHeap2.printLeftistHeap();

		mLeftistHeap.merge(mLeftistHeap2); //合并操作

		System.out.println("\n合并后的左子堆：");
		for (int i=1; i<=n+m; i++)
		{
			System.out.print(mLeftistHeap.deleteMin()); //deleteMin操作
			if (i != (n+m))
			{
				System.out.print(", ");
			}
		}

		System.out.println();
	}
}
