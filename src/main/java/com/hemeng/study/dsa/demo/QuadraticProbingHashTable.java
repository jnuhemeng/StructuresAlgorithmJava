package com.hemeng.study.dsa.demo;

/**
 * ɢ�б���ƽ����ⷨ�����ͻ����
 */
public class QuadraticProbingHashTable<AnyType> 
{
	public HashEntry<AnyType>[] theList; //ɢ������
	public final int DEFAULT_SIZE=11; //ɢ�������Ĭ�ϳ��ȣ�Ӧ������Ϊ����
					//�������ȡֵ��Ϊ1��2��ʱ�����г������������±�Խ���쳣����û���ҳ�ԭ��
	private int currentsize; //ɢ������ĵ�ǰ����

	public  QuadraticProbingHashTable() //������1
	{
		currentsize=0;
		theList=new HashEntry[DEFAULT_SIZE];
	}

	public	QuadraticProbingHashTable(int size) //������2
	{
		currentsize=0;
		//theList=new HashEntry<AnyType>[currentsize];
		theList=new HashEntry[size];
	}

	/**
	 * �ж�һ��Ԫ���Ƿ������ɢ�б���
	 * @param x ��Ҫ�жϵ�Ԫ��
	 * @return �������򷵻�ture
	 */
	public boolean contains(AnyType x)
	{
		int position=findPos(x);
		return isActive(position);
	}

	/**
	 * ��һ������Ԫ�ز��뵽ɢ�б���
	 * @param x ��Ҫ���뵽ɢ�б���е�Ԫ��
	 */
	public void insert(AnyType x)
	{
		int position=findPos(x);
		if(theList[position]==null)
			if(++currentsize>theList.length/2)
				rehash(); //�������ݣ��˴���Ҫ�ڲ���һ��������
		theList[position]=new HashEntry(x,true);	
		
	}

	/**
	 * ɾ��ɢ�б��е�һ��Ԫ��
	 * @param x ��Ҫ��ɢ�б���ɾ����Ԫ��
	 */
	public void remove(AnyType x)
	{
		int position=findPos(x);
		if(theList[position]!=null)
			theList[position].isActive=false;
	}

	/**
	 * ����һ��Ԫ����ɢ�б��е�λ��
	 * @param x ��Ҫ���ҵ�Ԫ��
	 * @return HashValue Ԫ�ص�λ�á�����ҵ�Ԫ��x�򷵻�x��Ӧ��λ�ã����û���ҵ�x�򷵻�һ����λ��
	 */
	private int findPos(AnyType x)
	{
		int offset=1;
		int HashValue=x.hashCode()%theList.length;

		if(HashValue<0)
			HashValue+=theList.length;
		while(theList[HashValue]!=null&&!theList[HashValue].element.equals(x))
		{
			HashValue+=offset;
			offset+=2;
			if(HashValue>=theList.length)
				HashValue-=theList.length;
		}
		return HashValue;
	}

	/**
	 * �ж�ɢ�б���ĳ��λ�ö�Ӧ��Ԫ���Ƿ����
	 * @param position ��Ҫ�жϵ�λ��
	 * @return ��Ϊture���ʾ��λ�ö�Ӧ��Ԫ�ش��ڣ����򣨼�λ��Ϊ�ջ�λ���ϵ�Ԫ�ص�isActive������Ϊfalse������false
	 */
	private boolean isActive(int position)
	{
		if(theList[position]==null||theList[position].isActive==false)
			return false;
		else
			return true;

	}

	/**
	 * ɢ�б����ݡ��������һ��Ԫ�غ�ɢ�б��������������һ�룬�Ͱ�ɢ�б����������һ����
	 * ֵ��һ����ǣ����������insert�����б����ã���insert������Ҳ�������������
	 */
	private void rehash()
	{
		if(currentsize>theList.length/2)
		{
			currentsize=0;
			HashEntry[] theOldList=theList;
			theList=new HashEntry[theOldList.length*2];  //theList=new HashEntry[size]
			for(int i=0;i<theOldList.length;i++)
			{
				if(theOldList[i]!=null&&theOldList[i].isActive)
					insert((AnyType)theOldList[i].element); 
			}
		}
	}

	/**
	 * ɢ�б���ÿһ�����������
	 * ������������Ŀ����Ϊ��ʵ��ɢ�б��ɾ������Ϊ���������ɢ�б��ɾ������ֻ
	 * ���Ƕ���ɾ����������ƻ�ɢ�б�����ʡ�������ɾ������Ҫɢ�б��е�ÿһ���ṩ
	 * һ���ж��Ƿ�ɾ���ı�־λ
	 */
	private class HashEntry<AnyType>
	{
		AnyType element;
		boolean isActive; //��־���������Ƿ���ڣ����ѱ�ɾ����Ϊfalse

		public HashEntry(AnyType element,boolean isActive)
		{
			this.element=element;
			this.isActive=isActive;
		}
		public HashEntry(AnyType element)
		{
			this(element,true);
		}
	}


	public static void main(String[] args) 
	{
		QuadraticProbingHashTable<String> myHashTable=new QuadraticProbingHashTable<>();
		System.out.println("currentsize is :"+myHashTable.theList.length); 

		myHashTable.insert("Apple");
		myHashTable.insert("Orange");
		myHashTable.insert("Banana");
		myHashTable.insert("Strawberry");
		myHashTable.insert("Cherry");
		//myHashTable.remove("Jinan"); //����remove����
				
		System.out.println("contains 'Apple'? "+myHashTable.contains("Apple"));
		System.out.println("contains 'orange'? "+myHashTable.contains("orange"));
		System.out.println("contains 'Cherrys'? "+myHashTable.contains("Cherrys"));
		System.out.println("contains 'Strawberry'? "+myHashTable.contains("Strawberry"));

		System.out.println("currentsize is :"+myHashTable.theList.length); 
	}
}
