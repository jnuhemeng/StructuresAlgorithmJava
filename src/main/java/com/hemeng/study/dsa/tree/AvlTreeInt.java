package com.hemeng.study.dsa.tree;

/**
 * Avl��
 * �ڵ�Ԫ�ص���������Ϊint
 */
class AvlTreeInt
{

	AvlNode root; //�����ڵ�


	/**
	 * ɾ��һ���ڵ� 
	 * ����֤ɾ�����Ծ���Avl���Ľṹ�������Ҫ����һ���ĸĽ�
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
	 * �������нڵ����Сֵ
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
	 * ǰ���������ӡ������
	 */
	public void printTreeF(AvlNode root) 
	{
		if(root!=null)
		{
			//System.out.println(root.element); //ǰ�����ʱ�ȴ�ӡ���ڵ�
			System.out.print(root.element+" ");
			printTreeF(root.left);
			printTreeF(root.right);
		}
		else 
			return;
	}

	/**
	 * �����������ӡ������
	 * ���Եõ��ź�����������
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
	 * ����һ���ڵ�
	 */
	public AvlNode insert(int x,AvlNode t)
	{
		if(t==null)
			return new AvlNode(x,null,null);

		int compareResult=x-t.element;
		
		if(compareResult<0) //���뵽��ڵ�
		{
			t.left=insert(x,t.left);
			if((height(t.left)-height(t.right))==2) //����߶Ȳ��1,�ͽ�����ת����
			{
				if((x-t.left.element)<0) //ֻ����е���ת
					t=rotateWithLeftChild(t);
				else //��Ҫ����˫��ת
					t=doubleWithLeftChild(t); 
			}
			
		}
		else if(compareResult>0) //���뵽�ҽڵ�
		{
			t.right=insert(x,t.right);
			if((height(t.right)-height(t.left))==2) //����߶Ȳ��1,�ͽ�����ת����
			{
				if((x-t.right.element)>0) //ֻ����е���ת
					t=rotateWithRightChild(t);
				else //��Ҫ����˫��ת
					t=doubleWithRightChild(t);
			}
		}
		else
			;
		t.height=Math.max(height(t.left),height(t.right))+1; //���µ�ǰ���ڵ�ĸ߶�
		return t;
	}


	/**
	 * Avl������ת�е������ӽڵ���������
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
	 * Avl������ת�е������ӽڵ���������
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
	 * Avl��˫��ת�е������ӽڵ���������
	 */
	private AvlNode doubleWithLeftChild(AvlNode k3)
	{
		k3.left=rotateWithRightChild(k3.left);
		k3=rotateWithLeftChild(k3);

		return k3;
	}

	/**
	 * Avl��˫��ת�е������ӽڵ���������
	 */
	private AvlNode doubleWithRightChild(AvlNode k3)
	{
		k3.right=rotateWithLeftChild(k3.right);
		k3=rotateWithRightChild(k3);

		return k3;
	}

	/**
	 * Avl���Ľڵ����
	 * �ڵ�Ԫ�ص���������Ϊint
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
	 * ����һ������ĸ߶�
	 * �ص����ڿն���Ĵ����ն���ĸ߶ȶ���Ϊ-1(���ڵ�ĸ߶ȶ���Ϊ0)
	 */
	private int height(AvlNode t)
	{
		return t==null?-1:t.height;
	}

	public static void main(String[] args) 
	{
		AvlTreeInt myTreeInt=new AvlTreeInt();
		int[] num={3,2,1,4,5,6,7,16,15,14,13,12,11,10,8,9}; //�����ݽṹ���㷨������P98������
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
		for(int i=0;i<40000;i++) //�����������ת��������ô�ڽڵ�������ų���3000ʱ����ջ���ڵݹ��ӡ̫����������
		{
			myTreeInt.root=myTreeInt.insert(i,myTreeInt.root);
		}
		System.out.println("Inserted!");

		long begin = System.currentTimeMillis(); 
		myTreeInt.printTree(myTreeInt.root);
		long end = System.currentTimeMillis();
		System.out.println("\n��ʱ��" + (end-begin) + "����");*/
	}
}
