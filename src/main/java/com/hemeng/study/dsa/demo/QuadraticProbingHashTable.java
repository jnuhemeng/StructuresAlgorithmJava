package com.hemeng.study.dsa.demo;

/**
 * 散列表：用平方检测法解决冲突问题
 */
public class QuadraticProbingHashTable<AnyType> 
{
	public HashEntry<AnyType>[] theList; //散列数组
	public final int DEFAULT_SIZE=11; //散列数组的默认长度，应该设置为素数
					//当将这个取值设为1或2的时候，运行程序会出现数组下标越界异常（还没有找出原因）
	private int currentsize; //散列数组的当前长度

	public  QuadraticProbingHashTable() //构造器1
	{
		currentsize=0;
		theList=new HashEntry[DEFAULT_SIZE];
	}

	public	QuadraticProbingHashTable(int size) //构造器2
	{
		currentsize=0;
		//theList=new HashEntry<AnyType>[currentsize];
		theList=new HashEntry[size];
	}

	/**
	 * 判断一个元素是否存在于散列表中
	 * @param x 需要判断的元素
	 * @return 若存在则返回ture
	 */
	public boolean contains(AnyType x)
	{
		int position=findPos(x);
		return isActive(position);
	}

	/**
	 * 将一个数据元素插入到散列表中
	 * @param x 需要插入到散列表表中的元素
	 */
	public void insert(AnyType x)
	{
		int position=findPos(x);
		if(theList[position]==null)
			if(++currentsize>theList.length/2)
				rehash(); //进行扩容（此处需要在补充一个函数）
		theList[position]=new HashEntry(x,true);	
		
	}

	/**
	 * 删除散列表中的一个元素
	 * @param x 需要从散列表中删除的元素
	 */
	public void remove(AnyType x)
	{
		int position=findPos(x);
		if(theList[position]!=null)
			theList[position].isActive=false;
	}

	/**
	 * 返回一个元素在散列表中的位置
	 * @param x 需要查找的元素
	 * @return HashValue 元素的位置。如果找到元素x则返回x对应的位置，如果没有找到x则返回一个空位置
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
	 * 判断散列表中某个位置对应的元素是否存在
	 * @param position 需要判断的位置
	 * @return 若为ture则表示该位置对应的元素存在，否则（即位置为空或位置上的元素的isActive数据与为false）返回false
	 */
	private boolean isActive(int position)
	{
		if(theList[position]==null||theList[position].isActive==false)
			return false;
		else
			return true;

	}

	/**
	 * 散列表扩容。如果插入一个元素后散列表的已用容量超过一半，就把散列表的容量扩大一倍。
	 * 值得一提的是，这个函数在insert函数中被调用，而insert函数中也调用了这个函数
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
	 * 散列表中每一项的数据类型
	 * 建立这个对象的目的是为了实现散列表的删除。因为不用链表的散列表的删除操作只
	 * 能是惰性删除，否则会破坏散列表的性质。而惰性删除则需要散列表中的每一项提供
	 * 一个判断是否删除的标志位
	 */
	private class HashEntry<AnyType>
	{
		AnyType element;
		boolean isActive; //标志该数据项是否存在，若已被删除则为false

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
		//myHashTable.remove("Jinan"); //测试remove方法
				
		System.out.println("contains 'Apple'? "+myHashTable.contains("Apple"));
		System.out.println("contains 'orange'? "+myHashTable.contains("orange"));
		System.out.println("contains 'Cherrys'? "+myHashTable.contains("Cherrys"));
		System.out.println("contains 'Strawberry'? "+myHashTable.contains("Strawberry"));

		System.out.println("currentsize is :"+myHashTable.theList.length); 
	}
}
