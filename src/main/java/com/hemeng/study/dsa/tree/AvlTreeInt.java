package com.hemeng.study.dsa.tree;

/**
 * Avl树
 * 节点元素的数据域设为int
 */
class AvlTreeInt
{

	AvlNode root; //树根节点


	/**
	 * 删除一个节点 
	 * 不保证删除后仍具有Avl树的结构，因此需要做进一步的改进
	 */
	public AvlNode remove(int x,AvlNode t)
	{
		if(t==null)
			return t;
		if(x<t.element)
			t.left=remove(x,t.left);
		else if(x>t.element)
			t.right=remove(x,t.right);
		else
		{
			if((t.left!=null)&&(t.right!=null))
			{
				/*AvlNode temp=t;
				t=findMin(t.right);
				t.left=temp.left;
				t.right=temp.right;
				t.right=remove(t.element,t.right);*/
				t.element=findMin(t.right).element;
				t.right=remove(t.element,t.right);
			}
			else
				t=(t.left==null)?t.right:t.left;
		}
		return t;
	}

	/**
	 * 查找树中节点的最小值
	 */
	public AvlNode findMin(AvlNode t)
	{
		if(t==null)
			return t;
		else if(t.left==null)
			return t;
		return findMin(t.left);
	}


	/**
	 * 前序遍历并打印整棵树
	 */
	public void printTreeF(AvlNode root) 
	{
		if(root!=null)
		{
			//System.out.println(root.element); //前序遍历时先打印父节点
			System.out.print(root.element+" ");
			printTreeF(root.left);
			printTreeF(root.right);
		}
		else 
			return;
	}

	/**
	 * 中序遍历并打印整棵树
	 * 可以得到排好序的输出序列
	 */
	public void printTreeM(AvlNode root) 
	{
		if(root!=null)
		{			
			printTreeM(root.left);
			System.out.print(root.element+" ");
			printTreeM(root.right);
		}
		else 
			return;
	}


	/**
	 * 插入一个节点
	 */
	public AvlNode insert(int x,AvlNode t)
	{
		if(t==null)
			return new AvlNode(x,null,null);

		int compareResult=x-t.element;
		
		if(compareResult<0) //插入到左节点
		{
			t.left=insert(x,t.left);
			if((height(t.left)-height(t.right))==2) //如果高度差超过1,就进行旋转调整
			{
				if((x-t.left.element)<0) //只需进行单旋转
					t=rotateWithLeftChild(t);
				else //需要进行双旋转
					t=doubleWithLeftChild(t); 
			}
			
		}
		else if(compareResult>0) //插入到右节点
		{
			t.right=insert(x,t.right);
			if((height(t.right)-height(t.left))==2) //如果高度差超过1,就进行旋转调整
			{
				if((x-t.right.element)>0) //只需进行单旋转
					t=rotateWithRightChild(t);
				else //需要进行双旋转
					t=doubleWithRightChild(t);
			}
		}
		else
			;
		t.height=Math.max(height(t.left),height(t.right))+1; //更新当前根节点的高度
		return t;
	}


	/**
	 * Avl树单旋转中的与左子节点的情况例程
	 */
	private AvlNode rotateWithLeftChild(AvlNode k2)
	{
		AvlNode k1=k2.left;
		k2.left=k1.right;
		k1.right=k2;

		k2.height=Math.max(height(k2.left),height(k2.right))+1;
		k1.height=Math.max(height(k1.left),height(k1.right))+1;

		return k1;
	}

	/**
	 * Avl树单旋转中的与右子节点的情况例程
	 */
	private AvlNode rotateWithRightChild(AvlNode k2)
	{
		AvlNode k1=k2.right;
		k2.right=k1.left;
		k1.left=k2;

		k2.height=Math.max(height(k2.left),height(k2.right))+1;
		k1.height=Math.max(height(k1.left),height(k1.right))+1;

		return k1;
	}

	/**
	 * Avl树双旋转中的与左子节点的情况例程
	 */
	private AvlNode doubleWithLeftChild(AvlNode k3)
	{
		k3.left=rotateWithRightChild(k3.left);
		k3=rotateWithLeftChild(k3);

		return k3;
	}

	/**
	 * Avl树双旋转中的与右子节点的情况例程
	 */
	private AvlNode doubleWithRightChild(AvlNode k3)
	{
		k3.right=rotateWithLeftChild(k3.right);
		k3=rotateWithRightChild(k3);

		return k3;
	}

	/**
	 * Avl树的节点对象
	 * 节点元素的数据域设为int
	 */
	private class AvlNode
	{
		int element;
		AvlNode left;
		AvlNode right;
		int height;

		public AvlNode(int element) 
		{
			this(element,null,null); 
		}
		public AvlNode(int element,AvlNode left,AvlNode right) 
		{
			this.element=element;
			this.left=left;
			this.right=right;
			this.height=0;
		}
	}

	/**
	 * 返回一个对象的高度
	 * 重点在于空对象的处理，空对象的高度定义为-1(根节点的高度定义为0)
	 */
	private int height(AvlNode t)
	{
		return t==null?-1:t.height;
	}

	public static void main(String[] args) 
	{
		AvlTreeInt myTreeInt=new AvlTreeInt();
		int[] num={3,2,1,4,5,6,7,16,15,14,13,12,11,10,8,9}; //《数据结构与算法分析》P98的例子
		//int[] num={2,5,9,3,7}; //OK!

		for(int i=0;i<num.length;i++)
		{
			myTreeInt.root=myTreeInt.insert(num[i],myTreeInt.root);
		}
		System.out.println("Inserted!");

		myTreeInt.printTreeM(myTreeInt.root);
		System.out.println("\n");

		myTreeInt.printTreeF(myTreeInt.root);
		System.out.println("\n");

		myTreeInt.remove(13,myTreeInt.root);
		myTreeInt.printTreeM(myTreeInt.root);
		System.out.println("\n");

		/*AvlTreeInt myTreeInt=new AvlTreeInt();
		for(int i=0;i<40000;i++) //如果不进行旋转调整，那么在节点数量大概超过3000时，堆栈由于递归打印太深而发生溢出
		{
			myTreeInt.root=myTreeInt.insert(i,myTreeInt.root);
		}
		System.out.println("Inserted!");

		long begin = System.currentTimeMillis(); 
		myTreeInt.printTree(myTreeInt.root);
		long end = System.currentTimeMillis();
		System.out.println("\n耗时：" + (end-begin) + "毫秒");*/
	}
}
