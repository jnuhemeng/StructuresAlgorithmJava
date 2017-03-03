/**
 * ��ϰͼ��������ȱ�����depth-first search��
 * @date 2016/3/26
 */
package com.hemeng.study.dsa.depthfirstsearch;
import java.util.*;

/**
 * ͼ�Ķ�����
 * @date 2016/3/26
 */
class Vertex {
	public String name; //����
	public boolean visited; //�Ƿ񱻷��ʹ�

	public Vertex( String name ) {
		this.name = name;
	}
			
	@Override  
    public boolean equals(Object obj) {  //����ͼ�ı�ʾ�õ���HashMap��������Ҫ���ʵ�equal����
        if (this == obj)  
            return true;  
        if (obj == null)  
            return false;  
        if (getClass() != obj.getClass())  
            return false;  
        Vertex other = (Vertex) obj;  
        if (name == null) {  
            if (other.name != null)  
                return false;  
        } else if (!name.equals(other.name))  
            return false;  
        return true;  
    }  
}

/** 
 * �ڽӱ��е�Ԫ�ص���
 * @date 2016/4/3
 */
class AdjVertex
{
	public Vertex vertex; //�ڽӶ���
	public boolean visited; //�Ƿ��ѱ����ʹ�

	public AdjVertex( Vertex vertex, boolean visited ) {
		this.vertex = vertex;
		this.visited = visited;
	}
}

/**
 * ͼ��
 * @date 2016/3/26
 */
class Graph
{
	//public HashMap< Vertex, AdjVertex[] > adjacencys; //�ڽӱ�
	public HashMap< Vertex, Vertex[] > adjacencys; //�ڽӱ�
	
	public Graph() {
		//adjacencys = new HashMap< Vertex, AdjVertex[] >();
		adjacencys = new HashMap< Vertex, Vertex[] >();
	}

	/**
	 * ��ȡָ�����ֵĶ���
	 *		����ö��㲻���ڣ����׳��쳣�����ñ��������ң�ʱ�临�Ӷ�ΪO(N)��
	 * @param name ���������
	 * @throws VertexNotFoundException ���ͼ��δ�ҵ�ָ������
	 * @date 2016/3/26
	 */
	public Vertex getVertexByName( String name ) throws VertexNotFoundException {
		Vertex vertexResult = null;
		Set<Vertex> vertexSet = adjacencys.keySet();
		for ( Vertex vertex : vertexSet )
		{
			if ( vertex.name.compareTo( name ) == 0 )
			{
				vertexResult = vertex;
				break;
			}
		}
		if ( vertexResult == null )
		{
			throw new VertexNotFoundException();
		}
		return vertexResult;
	}
	
	/**
	 * ����ͼ�еĵ�һ�����㣬����������������
	 * 
	 */ 
	public Vertex getAVertex() {
		Vertex vertexResult = null;
		Set<Vertex> vertexSet = adjacencys.keySet();
		for ( Vertex vertex : vertexSet )
		{
			if ( vertex != null )
			{
				vertexResult = vertex;
				break;
			}
		}
		return vertexResult;
	}
}

/**
 * �쳣�ࣨ�ö��㲻���ڣ�
 * @date 2016/3/26
 */
class VertexNotFoundException extends Exception 
{
	public VertexNotFoundException()  
    {  
        super( "VertexNotFoundException: There is no such veretex." );  
    }
}

/**
 * ͼ�������������
 * @date 2016/3/26
 */
public class  DepthFirstSearch
{
	public DepthFirstSearch() {
	}

	/**
	 * ͼ������ȱ�������������(��Ĭ�ϵĶ��㿪ʼ)
	 * @param mGraph ͼ
	 * @date 2016/3/26
	 */
	public void dfs( Graph mGraph ) {
		Set<Vertex> vertexSet = mGraph.adjacencys.keySet();
		Vertex vertexTemp = null;
		LinkedList<Vertex> leftVertexs = new LinkedList<>();
		for ( Vertex vertex : vertexSet ) //��ʼ��ͼ�е�visited����Ϊfalse(��δ����)
		{
			vertex.visited = false;
			leftVertexs.offer( vertex );
		}

		while ( leftVertexs.size() > 0 ) //�������нڵ㣬��֤��ʹͼ���в���ͨ�Ľڵ�Ҳ�ܱ����ʵ�
		{
			if ( !(vertexTemp = leftVertexs.poll()).visited  )
			{
				dfs( mGraph, vertexTemp );
			}
		}
		//dfs( mGraph, vertexTemp );
	}

	/**
	 * ͼ������ȱ�������������(��ָ���Ķ��㿪ʼ)
	 * @param mGraph ͼ
	 * @param name ��ʼ���������
	 * @throws VertexNotFoundException ���ָ���Ķ��㲻����
	 * @date 2016/3/26
	 */
	public void dfs( Graph mGraph, String name ) throws VertexNotFoundException {
		Set<Vertex> vertexSet = mGraph.adjacencys.keySet();
		Vertex vertexTemp = null;
		LinkedList<Vertex> leftVertexs = new LinkedList<>(); 
		for ( Vertex vertex : vertexSet ) //��ʼ��ͼ�е�visited����Ϊfalse(��δ����)
		{
			vertex.visited = false;
			if ( vertex.name.compareTo( name ) == 0 )
			{
				vertexTemp = vertex;
			} else {
				leftVertexs.offer( vertex );
			}
			 
		}
		if ( vertexTemp == null )
		{
			throw new VertexNotFoundException();
		}

		dfs( mGraph, vertexTemp ); 
		while ( leftVertexs.size() > 0 ) //�������нڵ㣬��֤��ʹͼ���в���ͨ�Ľڵ�Ҳ�ܱ����ʵ�
		{
			if ( !(vertexTemp = leftVertexs.poll()).visited  )
			{
				dfs( mGraph, vertexTemp );
			}
		}
	}

	/**
	 * ͼ����������������м亯��
	 *	    �ݹ鷽�����������������������
	 * @param mGraph ͼ
	 * @param vertex ���������
	 * @date 2016/3/26
	 */
	private void dfs( Graph mGraph, Vertex vertex ) {		
		vertex.visited = true;

		//do something
		System.out.print( vertex.name + "  " );
		//do something

		Vertex[] vertexs = mGraph.adjacencys.get( vertex );
		for ( int i=0; i<vertexs.length; i++ )
		{
			if ( !vertexs[i].visited )
			{
				dfs( mGraph, vertexs[i] );
			}
		}
	}

	/**
	 * ����һ��ͼ�����ڲ���
	 * @return ͼ
	 * @date 2016/3/26
	 */
	public Graph generateGraph() {

		/*//ͼһ
		Graph mGraph = new Graph();
		Vertex A = new Vertex( "A" );
		Vertex B = new Vertex( "B" );
		Vertex C = new Vertex( "C" );
		Vertex D = new Vertex( "D" );
		Vertex E = new Vertex( "E" );
		mGraph.adjacencys.put( A, new Vertex[]{ B, D, E } );
		mGraph.adjacencys.put( B, new Vertex[]{ A, C, D } );
		mGraph.adjacencys.put( C, new Vertex[]{ B, D, E } );
		mGraph.adjacencys.put( D, new Vertex[]{ A, B, C } );
		mGraph.adjacencys.put( E, new Vertex[]{ A, C } );
		return mGraph;	*/

		/*//ͼ��
		Graph mGraph = new Graph();
		Vertex A = new Vertex( "A" );
		Vertex B = new Vertex( "B" );
		Vertex C = new Vertex( "C" );
		Vertex D = new Vertex( "D" );
		Vertex E = new Vertex( "E" );
		Vertex F = new Vertex( "F" );
		Vertex G = new Vertex( "G" );
		Vertex H = new Vertex( "H" );
		Vertex I = new Vertex( "I" );
		mGraph.adjacencys.put( A, new Vertex[]{ B, F, G } );
		mGraph.adjacencys.put( B, new Vertex[]{ A, C, G, I } );
		mGraph.adjacencys.put( C, new Vertex[]{ B, D, I } );
		mGraph.adjacencys.put( D, new Vertex[]{ C, E, H, G, I } );
		mGraph.adjacencys.put( E, new Vertex[]{ D, F, H } );
		mGraph.adjacencys.put( F, new Vertex[]{ A, E, G } );
		mGraph.adjacencys.put( G, new Vertex[]{ B, D, F, H } );
		mGraph.adjacencys.put( H, new Vertex[]{ D, E, G } );
		mGraph.adjacencys.put( I, new Vertex[]{ B, C, D } );
		return mGraph;*/	

		//ͼ��(ͼ�е������ֻ�����ͨ)
		Graph mGraph = new Graph();
		Vertex A = new Vertex( "A" );
		Vertex B = new Vertex( "B" );
		Vertex C = new Vertex( "C" );
		Vertex D = new Vertex( "D" );
		Vertex E = new Vertex( "E" );

		Vertex F = new Vertex( "F" );
		Vertex G = new Vertex( "G" );
		Vertex H = new Vertex( "H" );

		mGraph.adjacencys.put( A, new Vertex[]{ B, D, E } );
		mGraph.adjacencys.put( B, new Vertex[]{ A, C, D } );
		mGraph.adjacencys.put( C, new Vertex[]{ B, D, E } );
		mGraph.adjacencys.put( D, new Vertex[]{ A, B, C } );
		mGraph.adjacencys.put( E, new Vertex[]{ A, C } );

		mGraph.adjacencys.put( F, new Vertex[]{ G } );
		mGraph.adjacencys.put( G, new Vertex[]{ G, H } );
		mGraph.adjacencys.put( H, new Vertex[]{ G } );

		//ͼ��(����ͼ)
		/*��Graph mGraph = new Graph();
		Vertex v1 = new Vertex( "v1" );
		Vertex v2 = new Vertex( "v2" );
		Vertex v3 = new Vertex( "v3" );
		Vertex v4 = new Vertex( "v4" );
		Vertex v5 = new Vertex( "v5" );
		Vertex v6 = new Vertex( "v6" );
		Vertex v7 = new Vertex( "v7" );

		mGraph.adjacencys.put( v1, new Vertex[]{ v2, v3, v4 } );
		mGraph.adjacencys.put( v2, new Vertex[]{ v4, v5 } );
		mGraph.adjacencys.put( v3, new Vertex[]{ v6 } );
		mGraph.adjacencys.put( v4, new Vertex[]{ v3, v6, v7 } );
		mGraph.adjacencys.put( v5, new Vertex[]{ v4, v7 } );
		mGraph.adjacencys.put( v6, new Vertex[]{ } );
		mGraph.adjacencys.put( v7, new Vertex[]{ v6 } );*/


		return mGraph;
	}

	/**
	 * ��ͼ��ӡ�ڿ���̨�ϣ����ڲ���
	 * @param mGraph ͼ
	 * @date 2016/3/26
	 */
	public void printGraph( Graph mGraph ) {

		Set<Vertex> vertexSet = mGraph.adjacencys.keySet();
		Vertex[] vertexs;

		System.out.print("\nͼ���ڽӱ�\n");
		for ( Vertex vertex : vertexSet )
		{
			System.out.print( vertex.name );		
			System.out.print( "	" );

			vertexs = mGraph.adjacencys.get( vertex );
			for ( int i=0; i<vertexs.length; i++ )
			{
				System.out.print( vertexs[i].name );
				if ( i < vertexs.length - 1 )
				{
					System.out.print(",  ");
				}
			}
			System.out.print("\n");
		}
	}

	public static void main(String[] args) throws VertexNotFoundException
	{
		DepthFirstSearch mDepthFirstSearch = new DepthFirstSearch();
		Graph mGraph = mDepthFirstSearch.generateGraph();
		mDepthFirstSearch.printGraph( mGraph );

		System.out.print("\n������ȱ������\n");
		mDepthFirstSearch.dfs( mGraph ); //��Ĭ�϶��㿪ʼ����
		//System.out.print("\n");
		//mDepthFirstSearch.dfs( mGraph, "v1" ); //��ָ�����㿪ʼ����

		System.out.println();
	}
}
