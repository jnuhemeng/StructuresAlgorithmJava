package com.hemeng;

/**
 *	不相交集
 *		用以数组实现的树来表示集合，查找操作(find)包含路径压缩，合并操作(union)
 *		是基于高度的。
 */
public class DisjSets
{
	private int[] array;

	public DisjSets( int size ) {
		array = new int[ size ];
		for ( int i = 0; i < size; i++ )
		{
			array[ i ] = -1;
		}
	}

	/**
	 *	查找指定元素所在的等价类的类名(根)
	 *		元素数组下标的形式指定，等价类的类名也以数组下标的形式给出
	 *	@param a 需要查找的元素的位置下标
	 *	@return 等价类的类名
	 *	@date 2016/3/20
	 */
	public int find( int a ) {
		if ( array[a] < 0 )
		{
			return a;
		}
		else 
		{	
			array[ a ] = find( array[a] ); //路径压缩
			return array[ a ];
		}
	}

	/**
	 *	合并两个不同等价类为一个新的等价类的驱动程序
	 *	@param a 第一个等价类中的任意一个元素
	 *	@param b 第二个等价类中的任意一个元素
	 *	@date 2016/2/20	 
	 */
	public void union( int a, int b )
	{
		int root1 = find( a );		
		int root2 = find( b );
		
		if ( root1 != root2 )
		{
			union1( root1, root2 );
		}
	}

	/**
	 *	返回整个集合数组，用于调试（调试完毕后，应该关闭这个接口）
	 */
	public int[] getArray() {
		return array;
	}

	/**
	 *	合并两个不同的等价类为一个新的等价类（中间函数）
	 *		按高度求并(union-by-height)
	 *	@param root1 第一个等价类的根（类名）
	 *	@param root2 第二个等价类的根（类名）
	 *	@date 2016/2/20
	 */
	private void union1( int root1, int root2 ) {
		if ( array[ root1 ] < array[ root2] ) //root1的高度更大
		{
			array[ root2 ] = root1;
		}
		else  
		{
			if ( array[ root1 ] == array[ root2 ] ) //root1与root2高度相等 
			{
				array[ root2 ]--; //更新根节点的高度
			}
			array[ root1 ] = root2;
		}		
	}

	public static void main(String[] args) 
	{
		int N = 8;
		DisjSets mDisjSets = new DisjSets( N );
		mDisjSets.union( 4, 5 );
		mDisjSets.union( 6, 7 );
		mDisjSets.union( 4, 6 );
		mDisjSets.union( 3, 6 );

		for ( int i = 0; i < N; i++ )
		{
			System.out.print( mDisjSets.find(i) );
			if ( i < N-1 )
			{
				System.out.print( ", " );
			}
		}
		
		int[] array = mDisjSets.getArray();
		System.out.println( "\n " );
		for ( int i = 0; i < N; i++ )
		{
			System.out.print( array[ i ] );
			if ( i < N-1 )
			{
				System.out.print( ", " );
			}
		}

		System.out.println();
	}
}
