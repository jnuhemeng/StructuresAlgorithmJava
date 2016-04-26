package com.hemeng;

/**
 *	���ཻ��
 *		��������ʵ�ֵ�������ʾ���ϣ����Ҳ���(find)����·��ѹ�����ϲ�����(union)
 *		�ǻ��ڸ߶ȵġ�
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
	 *	����ָ��Ԫ�����ڵĵȼ��������(��)
	 *		Ԫ�������±����ʽָ�����ȼ��������Ҳ�������±����ʽ����
	 *	@param a ��Ҫ���ҵ�Ԫ�ص�λ���±�
	 *	@return �ȼ��������
	 *	@date 2016/3/20
	 */
	public int find( int a ) {
		if ( array[a] < 0 )
		{
			return a;
		}
		else 
		{	
			array[ a ] = find( array[a] ); //·��ѹ��
			return array[ a ];
		}
	}

	/**
	 *	�ϲ�������ͬ�ȼ���Ϊһ���µĵȼ������������
	 *	@param a ��һ���ȼ����е�����һ��Ԫ��
	 *	@param b �ڶ����ȼ����е�����һ��Ԫ��
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
	 *	���������������飬���ڵ��ԣ�������Ϻ�Ӧ�ùر�����ӿڣ�
	 */
	public int[] getArray() {
		return array;
	}

	/**
	 *	�ϲ�������ͬ�ĵȼ���Ϊһ���µĵȼ��ࣨ�м亯����
	 *		���߶���(union-by-height)
	 *	@param root1 ��һ���ȼ���ĸ���������
	 *	@param root2 �ڶ����ȼ���ĸ���������
	 *	@date 2016/2/20
	 */
	private void union1( int root1, int root2 ) {
		if ( array[ root1 ] < array[ root2] ) //root1�ĸ߶ȸ���
		{
			array[ root2 ] = root1;
		}
		else  
		{
			if ( array[ root1 ] == array[ root2 ] ) //root1��root2�߶���� 
			{
				array[ root2 ]--; //���¸��ڵ�ĸ߶�
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
