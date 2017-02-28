/**
 * 练习基于Dijkstra算法的赋权图单源最短路径计算
 *
 */
package com.hemeng.study.dsa.shortestpathdijkstra;

import java.util.*;

/**
 * 顶点类
 *	   邻接表作为顶点的数据成员
 * @date 2016/3/22
 */
class Vertex  
{
	public static final int INFINITY = Integer.MAX_VALUE; //路径为无穷，表示某点不可达
	public String name;
	public int dist;
	public Vertex path;
	public boolean known;

	//下面两个数据域针对有负值圈的图而添加
	public boolean isInQueue; //是否已在队列中
	public int enqueueTimes; //已经入队的次数，防止负值圈导致的死循环

	public ArrayList<VertexAdj> adj;
	
	public Vertex( String name ) {
		this.name = name;
		adj = new ArrayList<VertexAdj>();
	}
}

/**
 * 顶点的邻接表中的元素类，包括一个顶点和对应边的权值
 * @date 2016/3/22
 */
class VertexAdj
{
	public Vertex vertex;
	public int weight;

	public VertexAdj( Vertex vertex, int weight ) {
		this.vertex = vertex;
		this.weight = weight;
	}
}

/**
 * 有向赋值图
 * @date 2016/3/22
 */
class Graph
{
	public Set<Vertex> vertexSet;
	public Graph() {
		vertexSet = new HashSet<Vertex>();
	}

	public Graph( Vertex[] vertexs ) {
		this();
		addVertexs( vertexs );
	}

	/**
	 *	批量添加顶点
	 */
	public void addVertexs( Vertex[] vertexs ) {
		for (int i=0; i<vertexs.length; i++ )
		{
			vertexSet.add( vertexs[i] );
		}
	}

	/**
	 * 获取指定顶点
	 *	   线性时间最坏情形
	 * @param name 顶点名字
	 * @date 2016/3/22
	 */
	public Vertex getVertex( String name ) {
		for ( Vertex vertex : vertexSet )
		{
			if ( vertex.name == name )
			{
				return vertex;
			}
		}
		return null;
	}
}

/**
 * 基于Dijkstra算法的赋权图单源最短路径计算
 * @date 2016/3/22
 */
public class ShortestPathDijkstra
{
	public ShortestPathDijkstra() {
	}

	/**
	 * 计算赋权图指定起点的单源最短路径
	 *		排序结果通过设置有向图的dist和path属性体现出来
	 * @param mGraph 有向赋权图
	 * @param startvertex 起始顶点
	 * @date 2016/3/22
	 */
	public void  dijkstra( Graph mGraph, Vertex startVertex ) {
		for ( Vertex vertex : mGraph.vertexSet )
		{
			vertex.dist = Vertex.INFINITY;
			vertex.path = null;
			vertex.known = false;
		}

		startVertex.dist = 0;
		startVertex.path = startVertex;
		Vertex minVertex;
		while( true ) {
			minVertex = null;
			for ( Vertex vertex : mGraph.vertexSet )
			{
				if ( !vertex.known ) //该顶点unknown
				{
					if ( minVertex != null ) //unknown的顶点不止一个
					{
						if ( vertex.dist < minVertex.dist )
						{
							minVertex = vertex;
						}
					} else { //首个找到的unknown顶点
						minVertex = vertex;
					}					
				}
			}	
			if ( minVertex == null || minVertex.dist == Vertex.INFINITY ) //如果已经不存在unknown顶点，或所有剩余顶点都不可达
			{															//就终止循环完成计算
				break;
			}

			minVertex.known = true; //将当前最短路径对应的顶点标记为known
			int vertexAdjLength = minVertex.adj.size();
			for ( int i=0; i<vertexAdjLength; i++ ) //更新该顶点邻接到的顶点的路径信息
			{
				if ( minVertex.adj.get(i).vertex.known==false && minVertex.adj.get(i).vertex.dist > minVertex.dist + minVertex.adj.get(i).weight)
				{
					minVertex.adj.get(i).vertex.path = minVertex;
					minVertex.adj.get(i).vertex.dist = minVertex.dist + minVertex.adj.get(i).weight;
				}				
			}

			/*///下面的代码调试用，调试完毕应该被注释
				System.out.print( "\n\n");
				printGraph( mGraph );
			*////
		}
	}

	/**
	 * 计算具有负值边的赋权图指定起点的单源最短路径
	 *		排序结果通过设置有向图的dist和path属性体现出来。结合了无权图和赋权图两种方法,
	 *		但时间复杂度比Dijkstra大
	 * @param mGraph 有向赋权图
	 * @param startvertex 起始顶点
	 * @date 2016/3/24
	 */
	public void  weightedNegative( Graph mGraph, Vertex startVertex) {

		LinkedList<Vertex> queue = new LinkedList<>();
		int enqueueTimes = mGraph.vertexSet.size();
		System.out.println("mGraph.vertexSet.size() = " + mGraph.vertexSet.size() ); //用于调试
		for ( Vertex vertex : mGraph.vertexSet )
		{
			vertex.dist = Vertex.INFINITY;
			vertex.path = null;
			vertex.isInQueue = false; //是否已在队列中
			vertex.enqueueTimes = 0; //防止负值圈导致的死循环
		}
		
		startVertex.dist = 0;
		startVertex.path = startVertex;
		queue.offer( startVertex );
		startVertex.isInQueue = true;
		startVertex.enqueueTimes++;

		Vertex vertexTemp;
		while( queue.size() > 0 ) {
			vertexTemp = queue.poll();
			vertexTemp.isInQueue = false;
			for ( VertexAdj vertexAdj : vertexTemp.adj )
			{
				if ( vertexAdj.vertex.dist > vertexTemp.dist + vertexAdj.weight ) //更新邻接顶点的路径信息
				{
					vertexAdj.vertex.dist = vertexTemp.dist + vertexAdj.weight;
					vertexAdj.vertex.path = vertexTemp;
					if ( !vertexAdj.vertex.isInQueue && vertexAdj.vertex.enqueueTimes < enqueueTimes )
					{ //如果不在队列中且不在负值圈中或入队次数未超出最大次数，就将该顶点入队
						queue.offer( vertexAdj.vertex );
						vertexAdj.vertex.isInQueue = true;
						vertexAdj.vertex.enqueueTimes++;
					}
				}
			}
		}
	}

	/**
	 * 计算给定有向赋权图中指定点到另一指点的最短路径，然后打印出来
	 * @param mGraph 有向赋权图
	 * @param startVertex 起点
	 * @param destVertex 终点
	 * @date 2016/3/22
	 */
	public void printPath( Graph mGraph, Vertex startVertex, Vertex destVertex) {
		dijkstra( mGraph, startVertex);
		System.out.print( "path: " );
		printPath( destVertex );

		String dist = "INFINITY";
		if ( destVertex.dist != Vertex.INFINITY )
		{
			dist = String.valueOf( destVertex.dist ); 
		}
		System.out.print( "    cost: " + dist );
	}

	/**
	 * 计算给定有向赋权图中指定点到另一指点的最短路径，然后打印出来
	 * @param mGraph 有向赋权图
	 * @param startVertex 起点
	 * @param destVertex 终点
	 * @date 2016/3/22
	 */
	public void printPathNegative( Graph mGraph, Vertex startVertex, Vertex destVertex) {
		weightedNegative( mGraph, startVertex);
		System.out.print( "path: " );
		printPath( destVertex, mGraph.vertexSet.size() );

		String dist = "INFINITY";
		if ( destVertex.dist != Vertex.INFINITY )
		{
			dist = String.valueOf( destVertex.dist ); 
		}
		System.out.print( "    cost: " + dist );
	}

	/**
	 * 打印起始点到指定顶点的路径	   
	 * @param v 终点（只对在已经计算过单源最短路径的有向赋权图上的顶点才有意义）
	 * @date 2016/3/22
	 */
	private void printPath( Vertex v ) {
		if ( v.path == null )
		{
			System.out.print( "The vertex cann't be reached." );
			return;
		}
		if ( v.path != v )
		{
			printPath( v.path );
			System.out.print( "->" );
		}
		System.out.print( v.name );
	}

	/**
	 * 打印起始点到指定顶点的路径	   
	 * @param v 终点（只对在已经计算过单源最短路径的有向赋权图上的顶点才有意义）
	 * @param graphSize 有向负值图（可能含有负值圈）的顶点总数
	 * @date 2016/3/22
	 */
	private void printPath( Vertex v, int graphSize ) {
		if ( v.enqueueTimes >= graphSize )
		{
			System.out.print( "There is a negative cycle for this vertex." );
			return;
		}
		if ( v.path == null )
		{
			System.out.print( "The vertex cann't be reached." );
			return;
		}
		if ( v.path != v )
		{
			printPath( v.path, graphSize );
			System.out.print( "->" );
		}
		System.out.print( v.name );
	}


	/**
	 * 生成一张有向图，用于测试
	 * @date 2016/3/22
	 */
	public Graph generateGraph() {
		//测试图1
		Vertex v1 = new Vertex( "v1" );
		Vertex v2 = new Vertex( "v2" );
		Vertex v3 = new Vertex( "v3" );
		Vertex v4 = new Vertex( "v4" );
		Vertex v5 = new Vertex( "v5" );
		Vertex v6 = new Vertex( "v6" );
		Vertex v7 = new Vertex( "v7" );

		v1.adj.add( new VertexAdj(v2, 2) );
		v1.adj.add( new VertexAdj(v4, 1) );
		v2.adj.add( new VertexAdj(v4, 3) );
		v2.adj.add( new VertexAdj(v5, 10) );
		v3.adj.add( new VertexAdj(v1, 4) );
		v3.adj.add( new VertexAdj(v6, 5) );
		v4.adj.add( new VertexAdj(v3, 2) );
		v4.adj.add( new VertexAdj(v5, 2) );
		v4.adj.add( new VertexAdj(v6, 8) );
		v4.adj.add( new VertexAdj(v7, 4) );
		v5.adj.add( new VertexAdj(v7, 6) );
		v7.adj.add( new VertexAdj(v6, 1) );

		return new Graph( new Vertex[]{ v1, v2, v3, v4, v5, v6, v7 });

		/*//测试图2（含有负值边，直接用Dijkstra无法得出正确路径） 
		Vertex v1 = new Vertex( "v1" );
		Vertex v2 = new Vertex( "v2" );
		Vertex v3 = new Vertex( "v3" );
		Vertex v4 = new Vertex( "v4" );

		v1.adj.add( new VertexAdj(v2, 1) );
		v1.adj.add( new VertexAdj(v3, 4) );
		v2.adj.add( new VertexAdj(v4, 2) );
		v3.adj.add( new VertexAdj(v4, -2) );
		
		return new Graph( new Vertex[]{ v1, v2, v3, v4 });*/

		/*//测试图3（含有负值边且含有负值圈，直接用Dijkstra无法得出正确路径） 
		Vertex v1 = new Vertex( "v1" );
		Vertex v2 = new Vertex( "v2" );
		Vertex v3 = new Vertex( "v3" );
		Vertex v4 = new Vertex( "v4" );
		Vertex v5 = new Vertex( "v5" );

		v1.adj.add( new VertexAdj(v2, 1) );
		v1.adj.add( new VertexAdj(v3, 4) );
		v2.adj.add( new VertexAdj(v4, 2) );
		v3.adj.add( new VertexAdj(v4, -3) );
		v4.adj.add( new VertexAdj(v5, 1) );
		v5.adj.add( new VertexAdj(v3, 1) );
		
		return new Graph( new Vertex[]{ v1, v2, v3, v4, v5 });*/
	}

	/**
	 * 将传入的赋值图的邻接表打印在控制台上，用于测试
	 * @param mGraph 有向赋值图
	 * @date 2016/3/22
	 */
	public void printGraph( Graph mGraph ) {

		for ( Vertex vertex : mGraph.vertexSet )
		{
			System.out.print( vertex.name );
			
			String path = "null";
			String dist = "INFINITY";
			if ( vertex.path != null )
			{
				path = vertex.path.name;
			}
			if ( vertex.dist != Vertex.INFINITY )
			{
				dist = String.valueOf( vertex.dist ); 
			}
			//System.out.print( "(" + path + ", " + dist + ")" );
			//System.out.print( "(" + path + ", " + dist + ", " + vertex.known + ")" ); //用于调试
			System.out.print( "(" + path + ", " + dist + ", " + vertex.enqueueTimes + ")" ); //用于调试
			
			System.out.print( "   " );
			int vertexAdjLength = vertex.adj.size();
			for ( int i=0; i<vertexAdjLength; i++ )
			{
				System.out.print( "(" + vertex.adj.get(i).vertex.name + ", " + vertex.adj.get(i).weight + ")" );
				if ( i < vertexAdjLength - 1 )
				{
					System.out.print(", ");
				}
			}
			System.out.print("\n");
		}
	}

	public static void main(String[] args) 
	{
		ShortestPathDijkstra mShortestPathDijkstra = new ShortestPathDijkstra();
		Graph mGraph = mShortestPathDijkstra.generateGraph();
		System.out.print("原始有向赋权图；\n");
		mShortestPathDijkstra.printGraph( mGraph );
		
		Vertex startVertex = mGraph.getVertex("v4"); //起点
		Vertex destVertex = mGraph.getVertex("v1"); //终点

		/*//不含负值边的有向负值图
		mShortestPathDijkstra.dijkstra( mGraph, startVertex );
		System.out.print("\nDijkstra单源(" + startVertex.name + ")最短路径计算结果：\n");
		mShortestPathDijkstra.printGraph( mGraph );
	
		System.out.print("\n\nDijkstra( " + startVertex.name + " ---> " + destVertex.name + " )最短路径计算结果：\n");
		mShortestPathDijkstra.printPath( mGraph, startVertex, destVertex );*/


		//具有负值边的有向负值图
		mShortestPathDijkstra.weightedNegative( mGraph, startVertex );
		System.out.print("\n改进（含负值边）Dijkstra单源(" + startVertex.name + ")最短路径计算结果：\n");
		mShortestPathDijkstra.printGraph( mGraph );
	
		System.out.print("\n\n改进（含负值边）Dijkstra( " + startVertex.name + " ---> " + destVertex.name + " )最短路径计算结果：\n");
		mShortestPathDijkstra.printPathNegative( mGraph, startVertex, destVertex );

		System.out.println();
	}
}
