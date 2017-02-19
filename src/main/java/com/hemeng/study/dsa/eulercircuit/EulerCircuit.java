/**
 * 练习欧拉回路的查找
 * @date 2016/4/3
 */
package com.hemeng.study.dsa.eulercircuit;
import java.util.*;

/**
 * 图的顶点类
 * @date 2016/4/3
 */
class Vertex {
	public String name; //名字
	public boolean visited; //是否被访问过
	public int degree; //度
	public int currentDegree; //指向即将访问的边

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

	public AdjVertex( Vertex vertex ) {
		this.vertex = vertex;
	}
}

/**
 * 图类
 * @date 2016/3/26
 */
class Graph
{
	public HashMap< Vertex, AdjVertex[] > adjacencys; //邻接表
	
	public Graph() {
		adjacencys = new HashMap< Vertex, AdjVertex[] >();
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
 * 异常类（该图不存在欧拉回路）
 * 此时，图中仍有可能存在欧拉路径
 * @date 2016/4/3
 */
class EulerCircuitNotFoundException extends Exception 
{
	public EulerCircuitNotFoundException()  
    {  
        super( "EulerCircuitNotFoundException: There is no any EulerCircuit in this graph." );  
    }
}

/**
 * 查找连通的且所有顶点入度均为偶数的图的欧拉回路
 * @date 2016/4/3
 */
public class EulerCircuit 
{
	private LinkedList<Vertex> subPath;

	public EulerCircuit() {
		subPath = new LinkedList<Vertex>();
	}

	/**
	 * 查找欧拉回路(从指定的顶点开始)
	 * @param mGraph 图
	 * @param name 起始顶点的名字
	 * @return 顶点链表表示的欧拉回路
	 * @throws VertexNotFoundException 如果指定的顶点不存在
	 * @throws EulerCircuitNotFoundException 如果图中不存在欧拉回路
	 * @date 2016/4/3
	 */
	public LinkedList<Vertex> findEulerCircuit( Graph mGraph, String name ) throws VertexNotFoundException,EulerCircuitNotFoundException {
		LinkedList<Vertex> eulerCircuit = new LinkedList<Vertex>();

		Set<Vertex> vertexSet = mGraph.adjacencys.keySet();
		Vertex theVertex = null;

		for ( Vertex vertex : vertexSet )  //图中相关信息的初始化
		{
			AdjVertex[] vertexs = mGraph.adjacencys.get( vertex );
			vertex.degree = vertexs.length;
			vertex.currentDegree = 0;
			for ( int i=0; i<vertexs.length; i++ )
			{
				vertexs[ i ].visited = false;
			}
			if ( vertex.name.compareTo( name ) == 0 )
			{
				theVertex = vertex;
			}
		}
		if ( theVertex == null ) //如果找不到指定的起始顶点，就抛出VertexNotFoundException异常
		{
			throw new VertexNotFoundException();
		}

		eulerCircuit.offer( theVertex ); //初始化总的欧拉回路：只包含起始顶点
		int theIndex = 0;
		int tempIndex = theIndex;
		Vertex tempVertex = null;

		while ( theVertex != null ) //分阶段进行欧拉回路查找
		{
			dfs( mGraph, theVertex); //进行当前阶段的欧拉回路查找
			if ( subPath.getFirst() != subPath.getLast() ) //如果这个阶段得到的路径不首尾相接，则说明这个图不存在欧拉回路
			{
				throw new EulerCircuitNotFoundException();
			}
			
			eulerCircuit.remove( theIndex );
			theVertex = null;
			while ( subPath.size() > 0 ) //将当前阶段查找得到的欧拉路径拼接到总回路中
			{
				tempVertex = subPath.poll();
				eulerCircuit.add( theIndex, tempVertex );
				if ( theVertex == null ) //选取下一阶段的遍历起始顶点
				{
					AdjVertex[] vertexs = mGraph.adjacencys.get( tempVertex );
					while ( tempVertex.currentDegree < tempVertex.degree ) //从邻接表中查找未被访问过的边
					{
						if ( !vertexs[ tempVertex.currentDegree ].visited ) //如果能够找到，就把该顶点作为下一阶段遍历的起始顶点
						{
							tempIndex = theIndex;
							theVertex = tempVertex;
							break;
						}	
						else {
							tempVertex.currentDegree++;
						}
					}				
				}
				theIndex++;
			}
			theIndex = tempIndex; //下一阶段起始顶点的下标
		}

		return eulerCircuit;
	}
	
	/**
	 * 图的深度优先搜索的改进版，中间函数
	 *	    递归方法，类似于树的先序遍历法
	 * @param mGraph 图
	 * @param vertex 遍历的起点
	 * @date 2016/4/3
	 */
	private void dfs( Graph mGraph, Vertex theVertex ) {	
		
		//do something
		subPath.offer( theVertex );
		//do something

		AdjVertex[] vertexs = mGraph.adjacencys.get( theVertex );

		while ( theVertex.currentDegree < theVertex.degree ) //从当前顶点的邻接表中查找未被访问过的边
		{
			if ( !vertexs[ theVertex.currentDegree ].visited )
			{
				break;
			}	
			else {
				theVertex.currentDegree++;
			}
		}
		
		if ( theVertex.currentDegree >= theVertex.degree ) //如果该顶点没有未曾访问过的边，就返回
		{
			return;
		}
		else { //如果当前顶点的邻接表中还有未被访问过的边
			vertexs[ theVertex.currentDegree ].visited = true; //将这条边标为已访问

			AdjVertex[] inVertexs = mGraph.adjacencys.get( vertexs[ theVertex.currentDegree ].vertex ); //将这个边“已访问”的信息同步到这条边另一顶点的邻接表中
			for ( int i=0; i<inVertexs.length; i++ )													//这里采用遍历的方式查找“另一顶点”，有待优化
			{
				if ( inVertexs[ i ].vertex == theVertex )
				{
					inVertexs[ i ].visited = true;
					break;
				}
			}
			theVertex.currentDegree++; //更新当前顶点的下一扫描顶点指向
			dfs( mGraph, vertexs[ theVertex.currentDegree-1 ].vertex ); //递归遍历		
		}
	}

	/**
	 * 生成一张图，用于测试
	 * @return 图
	 * @date 2016/4/3
	 */
	public Graph generateGraph() {

		//图四(有向图)
		Graph mGraph = new Graph();
		Vertex v1 = new Vertex( "v1" );
		Vertex v2 = new Vertex( "v2" );
		Vertex v3 = new Vertex( "v3" );
		Vertex v4 = new Vertex( "v4" );
		Vertex v5 = new Vertex( "v5" );
		Vertex v6 = new Vertex( "v6" );
		Vertex v7 = new Vertex( "v7" );
		Vertex v8 = new Vertex( "v8" );
		Vertex v9 = new Vertex( "v9" );
		Vertex v10 = new Vertex( "v10" );
		Vertex v11 = new Vertex( "v11" );
		Vertex v12 = new Vertex( "v12" );

		mGraph.adjacencys.put( v1, new AdjVertex[]{ new AdjVertex( v3 ), new AdjVertex( v4 ) } );
		//mGraph.adjacencys.put( v1, new AdjVertex[]{ new AdjVertex( v3 ), new AdjVertex( v4 ), new AdjVertex( v12 ) } );

		mGraph.adjacencys.put( v2, new AdjVertex[]{ new AdjVertex( v3 ), new AdjVertex( v8 ) } );
		mGraph.adjacencys.put( v3, new AdjVertex[]{ new AdjVertex( v1 ), new AdjVertex( v2 ), new AdjVertex( v4 ), new AdjVertex( v6 ), new AdjVertex( v7 ), new AdjVertex( v9 ) } );
		mGraph.adjacencys.put( v4, new AdjVertex[]{ new AdjVertex( v1 ), new AdjVertex( v3 ), new AdjVertex( v5 ), new AdjVertex( v7 ), new AdjVertex( v10 ), new AdjVertex( v11 ) } );
		mGraph.adjacencys.put( v5, new AdjVertex[]{ new AdjVertex( v4 ), new AdjVertex( v10 ) } );
		mGraph.adjacencys.put( v6, new AdjVertex[]{ new AdjVertex( v3 ), new AdjVertex( v9 ) } );
		mGraph.adjacencys.put( v7, new AdjVertex[]{ new AdjVertex( v3 ), new AdjVertex( v4 ), new AdjVertex( v9 ), new AdjVertex( v10 ) } );
		mGraph.adjacencys.put( v8, new AdjVertex[]{ new AdjVertex( v2 ), new AdjVertex( v9 ) } );
		mGraph.adjacencys.put( v9, new AdjVertex[]{ new AdjVertex( v3 ), new AdjVertex( v6 ), new AdjVertex( v7 ), new AdjVertex( v8 ), new AdjVertex( v10 ), new AdjVertex( v12 ) } );
		mGraph.adjacencys.put( v10, new AdjVertex[]{ new AdjVertex( v4 ), new AdjVertex( v5 ), new AdjVertex( v7 ), new AdjVertex( v9 ), new AdjVertex( v11 ), new AdjVertex( v12 ) } );
		mGraph.adjacencys.put( v11, new AdjVertex[]{ new AdjVertex( v4 ), new AdjVertex( v10 ) } );

		mGraph.adjacencys.put( v12, new AdjVertex[]{ new AdjVertex( v9 ), new AdjVertex( v10 ) } );
		//mGraph.adjacencys.put( v12, new AdjVertex[]{ new AdjVertex( v9 ), new AdjVertex( v10 ), new AdjVertex( v1 ) } );

		return mGraph;
	}

	/**
	 * 将图打印在控制台上，用于测试
	 * @param mGraph 图
	 * @date 2016/3/26
	 */
	public void printGraph( Graph mGraph ) {

		Set<Vertex> vertexSet = mGraph.adjacencys.keySet();
		AdjVertex[] vertexs;


		System.out.print("\n图的邻接表\n");
		for ( Vertex vertex : vertexSet )
		{
			System.out.print( vertex.name );		
			System.out.print( "	" );

			vertexs = mGraph.adjacencys.get( vertex );
			for ( int i=0; i<vertexs.length; i++ )
			{
				System.out.print( vertexs[i].vertex.name );
				if ( i < vertexs.length - 1 )
				{
					System.out.print(",  ");
				}
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * 将路径打印到控制台上，用于测试
	 * @param path 路径链表
	 * @date 2016/4/3
	 */
	public void printPath( LinkedList<Vertex> path ) {
		System.out.print( "length of the path : " + path.size() + "\n" );
		for( int i=0; i<path.size(); i++ )
		{
			System.out.print( path.get( i ).name + " " );
		}
		System.out.print( "\n" );

	}

	public static void main(String[] args) throws VertexNotFoundException,EulerCircuitNotFoundException
	{
		EulerCircuit mEulerCircuit = new EulerCircuit();
        Graph mGraph = mEulerCircuit.generateGraph();
		mEulerCircuit.printGraph( mGraph );

		System.out.println( "\n欧拉回路查找结果：" );
		LinkedList<Vertex> path = mEulerCircuit.findEulerCircuit( mGraph, "v5" );
		mEulerCircuit.printPath( path );
	}
}
