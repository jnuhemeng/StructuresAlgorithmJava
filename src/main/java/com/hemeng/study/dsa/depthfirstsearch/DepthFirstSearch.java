/**
 * 练习图的深度优先遍历（depth-first search）
 * @date 2016/3/26
 */
package com.hemeng.study.dsa.depthfirstsearch;
import java.util.*;

/**
 * 图的顶点类
 * @date 2016/3/26
 */
class Vertex {
	public String name; //名字
	public boolean visited; //是否被访问过

	public Vertex( String name ) {
		this.name = name;
	}
			
	@Override  
    public boolean equals(Object obj) {  //由于图的表示用到了HashMap，所以需要合适的equal方法
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
 * 邻接表中的元素的类
 * @date 2016/4/3
 */
class AdjVertex
{
	public Vertex vertex; //邻接顶点
	public boolean visited; //是否已被访问过

	public AdjVertex( Vertex vertex, boolean visited ) {
		this.vertex = vertex;
		this.visited = visited;
	}
}

/**
 * 图类
 * @date 2016/3/26
 */
class Graph
{
	//public HashMap< Vertex, AdjVertex[] > adjacencys; //邻接表
	public HashMap< Vertex, Vertex[] > adjacencys; //邻接表
	
	public Graph() {
		//adjacencys = new HashMap< Vertex, AdjVertex[] >();
		adjacencys = new HashMap< Vertex, Vertex[] >();
	}

	/**
	 * 获取指定名字的顶点
	 *		如果该顶点不存在，就抛出异常。采用遍历法查找，时间复杂度为O(N)。
	 * @param name 顶点的名字
	 * @throws VertexNotFoundException 如果图中未找到指定顶点
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
	 * 返回图中的第一个顶点，用于启动遍历过程
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
 * 异常类（该顶点不存在）
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
 * 图的深度优先搜索
 * @date 2016/3/26
 */
public class  DepthFirstSearch
{
	public DepthFirstSearch() {
	}

	/**
	 * 图深度优先遍历的驱动例程(从默认的顶点开始)
	 * @param mGraph 图
	 * @date 2016/3/26
	 */
	public void dfs( Graph mGraph ) {
		Set<Vertex> vertexSet = mGraph.adjacencys.keySet();
		Vertex vertexTemp = null;
		LinkedList<Vertex> leftVertexs = new LinkedList<>();
		for ( Vertex vertex : vertexSet ) //初始化图中的visited属性为false(即未访问)
		{
			vertex.visited = false;
			leftVertexs.offer( vertex );
		}

		while ( leftVertexs.size() > 0 ) //遍历所有节点，保证即使图中有不连通的节点也能被访问到
		{
			if ( !(vertexTemp = leftVertexs.poll()).visited  )
			{
				dfs( mGraph, vertexTemp );
			}
		}
		//dfs( mGraph, vertexTemp );
	}

	/**
	 * 图深度优先遍历的驱动例程(从指定的顶点开始)
	 * @param mGraph 图
	 * @param name 起始顶点的名字
	 * @throws VertexNotFoundException 如果指定的顶点不存在
	 * @date 2016/3/26
	 */
	public void dfs( Graph mGraph, String name ) throws VertexNotFoundException {
		Set<Vertex> vertexSet = mGraph.adjacencys.keySet();
		Vertex vertexTemp = null;
		LinkedList<Vertex> leftVertexs = new LinkedList<>(); 
		for ( Vertex vertex : vertexSet ) //初始化图中的visited属性为false(即未访问)
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
		while ( leftVertexs.size() > 0 ) //遍历所有节点，保证即使图中有不连通的节点也能被访问到
		{
			if ( !(vertexTemp = leftVertexs.poll()).visited  )
			{
				dfs( mGraph, vertexTemp );
			}
		}
	}

	/**
	 * 图的深度优先搜索，中间函数
	 *	    递归方法，类似于树的先序遍历法
	 * @param mGraph 图
	 * @param vertex 遍历的起点
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
	 * 生成一张图，用于测试
	 * @return 图
	 * @date 2016/3/26
	 */
	public Graph generateGraph() {

		/*//图一
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

		/*//图二
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

		//图三(图中的两部分互不连通)
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

		//图四(有向图)
		/*×Graph mGraph = new Graph();
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
	 * 将图打印在控制台上，用于测试
	 * @param mGraph 图
	 * @date 2016/3/26
	 */
	public void printGraph( Graph mGraph ) {

		Set<Vertex> vertexSet = mGraph.adjacencys.keySet();
		Vertex[] vertexs;

		System.out.print("\n图的邻接表\n");
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

		System.out.print("\n深度优先遍历结果\n");
		mDepthFirstSearch.dfs( mGraph ); //从默认顶点开始遍历
		//System.out.print("\n");
		//mDepthFirstSearch.dfs( mGraph, "v1" ); //从指定顶点开始遍历

		System.out.println();
	}
}
