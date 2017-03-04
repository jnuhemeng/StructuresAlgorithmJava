/**
 *	练习无圈图的单源最短路径计算在关键路径分析中的应用
 *		在这里，图用两张HashMap来表示，分别对应两种邻接表
 *		（与顶点邻接且在前和在后的邻接表） 
 *	@date 2016/3/25
 */
package com.hemeng.study.dsa.crtpathanalysis;
import java.util.*;

/**
 * 图的顶点类
 * @date 2016/3/25
 */
class Vertex {
	public String name; //名字
	public int indegree; //入度
	public int outdegree; //出度
	public int tempdegree; //用于临时保存入度或出度的拷贝
	public int topNum; //拓扑顺序排序后的位置
	public int bottomNum; //拓扑逆序排序后的位置
	public int earliestTime; //最早完成时间
	public int latestTime; //最晚完成时间
	public Vertex beforeVertex; //最早完成时间对应的前一跳节点	
	public Vertex afterVertex; //最晚完成时间对应的下一跳节点


	public Vertex( String name ) {
		this( name, 0, 0, 0, -1, -1, -1, -1, null, null );
	}

	public Vertex( String name, int indegree, int outdegree, int tempdegree, int topNum, 
		int bottomNum, int earliestTime, int latestTime, Vertex beforeVertex, Vertex afterVertex ) {

		this.name = name;
		this.indegree = indegree;
		this.outdegree = outdegree;
		this.tempdegree = tempdegree;
		this.topNum = topNum;
		this.bottomNum = bottomNum;
		this.earliestTime = earliestTime;
		this.latestTime = latestTime;
		this.beforeVertex = beforeVertex;		
		this.afterVertex = afterVertex;
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
 * 有向图类
 * @date 2016/3/25
 */
class Graph
{
	public HashMap< Vertex, VertexAdj[] > adjacencysBef; //前邻接表
	public HashMap< Vertex, VertexAdj[] > adjacencysAft; //后邻接表
	
	public Graph() {
		adjacencysBef = new HashMap< Vertex, VertexAdj[] >();
		adjacencysAft = new HashMap< Vertex, VertexAdj[] >();
	}

	/**
	 * 往前邻接表中添加一条记录
	 * @param vertex 顶点
	 * @param vertexAdjBefs 后邻接表
	 * @date 2016/3/25
	 */
	public void addAdjacencyBef( Vertex vertex, VertexAdj[] vertexAdjBefs ) {
		for ( int i=0; i<vertexAdjBefs.length; i++ )
		{
			vertexAdjBefs[i].vertex.outdegree++;
		}
		adjacencysBef.put( vertex, vertexAdjBefs );
	}

	/**
	 * 往后邻接表中添加一条记录
	 * @param vertex 顶点
	 * @param vertexAdjAfts 后邻接表
	 * @date 2016/3/25
	 */
	public void addAdjacencyAft( Vertex vertex, VertexAdj[] vertexAdjAfts ) {
		for ( int i=0; i<vertexAdjAfts.length; i++ )
		{
			vertexAdjAfts[i].vertex.indegree++;
		}
		adjacencysAft.put( vertex, vertexAdjAfts );
	}
}

/**
 * 异常类（有向图中有圈）
 */
class CycleFoundException extends Exception  
{  
    public CycleFoundException()  
    {  
        super( "Topsort abortion: The graph is not a DAG." );  
    }  
}

/**
 * 关键路径分析。计算无圈赋权有向图中每个节点的最早完成时间、
 *		最晚完成时间和每条边的松弛时间（slack time）
 * @date 2016/3/25
 */
public class CrtPathAnalysis
{
	public CrtPathAnalysis() {
	}

	/**
	 * 计算最早完成时间
	 * @param mGraph 无圈有向赋权图
	 * @throws CycleFoundException 如果传入的有向图中有圈
	 * @date 2016/3/25
	 */
	public void earliestTime( Graph mGraph ) throws CycleFoundException {
		Set<Vertex> vertexSet = mGraph.adjacencysAft.keySet();
		LinkedList<Vertex> queue = new LinkedList<>();
		for ( Vertex vertex : vertexSet )
		{
			vertex.tempdegree = vertex.indegree; //备份入度
			if ( vertex.tempdegree == 0 )
			{
				queue.offer( vertex );
			}			
		}	
		
		int counter = 0;
		VertexAdj[] vertexAdjsBef;
		VertexAdj[] vertexAdjsAft;

		Vertex vertex = queue.poll();
		vertex.earliestTime = 0; //设置起始节点的最早完成时间为0
		queue.offer( vertex );

		while( queue.size() > 0 ) {
			//取出队首的节点
			vertex = queue.poll();
			vertex.topNum = ++counter;
			
			//计算当前节点的最早完成时间（利用前邻接表）——最早完成时间
			vertexAdjsBef = mGraph.adjacencysBef.get( vertex );

			int maxTime = -1;
			for ( int i=0; i<vertexAdjsBef.length; i++ )
			{
				if ( vertexAdjsBef[ i ].vertex.earliestTime + vertexAdjsBef[ i ].weight > maxTime )
				{
					maxTime = vertexAdjsBef[ i ].vertex.earliestTime + vertexAdjsBef[ i ].weight;
					vertex.beforeVertex = vertexAdjsBef[ i ].vertex;
					vertex.earliestTime = maxTime; 
				}
			}
				
			//更新后邻接节点的入度，并把入度为0的节点存入队列（利用后邻接表）——拓扑排序
			vertexAdjsAft = mGraph.adjacencysAft.get( vertex );
			for (int i=0; i<vertexAdjsAft.length; i++ )
			{
				if ( --vertexAdjsAft[i].vertex.tempdegree == 0 ) 
				{
					queue.offer( vertexAdjsAft[i].vertex );
				}
			}
		}

		if ( counter < vertexSet.size() ) //如果最终成功排序的顶点数比顶点总个数少
		{								  //	，则说明该图中有圈
			throw new CycleFoundException();
		}
	}

	/**
	 * 计算最晚完成时间
	 * @param mGraph 无圈有向赋权图
	 * @param EC 最后一个事件完成的最晚时间
	 * @date 2016/3/25
	 */
	public void latestTime( Graph mGraph, int EC ) throws CycleFoundException {

		Set<Vertex> vertexSet = mGraph.adjacencysBef.keySet();
		LinkedList<Vertex> queue = new LinkedList<>();
		for ( Vertex vertex : vertexSet )
		{
			vertex.tempdegree = vertex.outdegree; //备份出度
			if ( vertex.tempdegree == 0 )
			{
				queue.offer( vertex );
			}	
			vertex.latestTime = EC + 1;
		}


		int counter = 0;
		VertexAdj[] vertexAdjsBef;
		VertexAdj[] vertexAdjsAft;

		Vertex vertex = queue.poll();
		vertex.latestTime = EC; //设置结束节点的最晚完成时间为LASTESTTIME
		queue.offer( vertex );

		while( queue.size() > 0 ) {
			//取出队首的节点
			vertex = queue.poll();
			vertex.bottomNum = ++counter;
			
			//计算当前节点的最晚完成时间（利用后邻接表）——最晚完成时间
			vertexAdjsAft = mGraph.adjacencysAft.get( vertex );

			int minTime = EC + 1;
			for ( int i=0; i<vertexAdjsAft.length; i++ )
			{
				if ( vertexAdjsAft[ i ].vertex.latestTime - vertexAdjsAft[ i ].weight < minTime )
				{
					minTime = vertexAdjsAft[ i ].vertex.latestTime - vertexAdjsAft[ i ].weight;
					vertex.afterVertex = vertexAdjsAft[ i ].vertex;
					vertex.latestTime = minTime; 
				}
			}
				
			//更新前邻接节点的出度，并把入度为0的节点存入队列（利用前邻接表）——拓扑排序
			vertexAdjsBef = mGraph.adjacencysBef.get( vertex );
			for (int i=0; i<vertexAdjsBef.length; i++ )
			{
				if ( --vertexAdjsBef[i].vertex.tempdegree == 0 ) 
				{
					queue.offer( vertexAdjsBef[i].vertex );
				}
			}
		}

		if ( counter < vertexSet.size() ) //如果最终成功排序的顶点数比顶点总个数少
		{								  //	，则说明该图中有圈
			throw new CycleFoundException();
		}
	}

	/**
	 * 查找关键路径(Critical path)
	 * @param mGraph 已经计算好所有节点最早和最晚完成时间的无圈有向赋权图
	 * @return 顶点队列表示的关键路径
	 * @throws CycleFoundException 如果传入的有向图中有圈 
	 * @date 2016/3/25
	 */
	public LinkedList<Vertex> findCrtPath( Graph mGraph ) throws CycleFoundException {

		LinkedList<Vertex> queue = new LinkedList<>();
		Vertex currentVertex = findStartVertex( mGraph );

		VertexAdj[] vertexAdjsAft;
		while ( currentVertex.outdegree > 0 )
		{
			queue.offer( currentVertex );
			vertexAdjsAft = mGraph.adjacencysAft.get( currentVertex );

			for ( int i=0; i<vertexAdjsAft.length ; i++ )
			{
				if ( vertexAdjsAft[i].vertex.latestTime - currentVertex.earliestTime - vertexAdjsAft[i].weight == 0 )
				{
					currentVertex = vertexAdjsAft[i].vertex;
				}
			}
		}

		queue.offer( currentVertex );

		return queue;
	}

	/**
	 * 返回最终完成时间
	 * @param mGraph 已经计算好所有节点最早完成时间的无圈有向赋权图
	 * @date 2016/3/25
	 */
	public int findEC( Graph mGraph) {
		Set<Vertex> vertexSet = mGraph.adjacencysAft.keySet();
		int EC = -1;
		for ( Vertex vertex : vertexSet )
		{
			if ( vertex.earliestTime > EC )
			{
				EC = vertex.earliestTime;
			}
		}
		return EC;
	}

	/**
	 * 返回起始节点（入度为0）
	 *		如果有多个满足条件的节点，则只会返回第一个
	 * @param 无圈有向赋权图
	 * @return 起始顶点
	 * @throws CycleFoundException 如果传入的有向图中有圈 
	 * @date 2016/3/25 
	 */
	public Vertex findStartVertex( Graph mGraph ) throws CycleFoundException {
		Set<Vertex> vertexSet = mGraph.adjacencysAft.keySet();
		LinkedList<Vertex> queue = new LinkedList<>();
		for ( Vertex vertex : vertexSet )
		{
			if ( vertex.indegree == 0 )
			{
				queue.offer( vertex );
			}			
		}
		if ( queue.size() < 1 )
		{
			throw new CycleFoundException();
		}
		return queue.poll();
	}


	/**
	 * 生成一张有向图，用于测试
	 * @return 无圈赋权有向图
	 * @date 2016/3/25
	 */
	public Graph generateGraph() {
		Graph mGraph = new Graph();

		Vertex v1 = new Vertex( "v1" );
		Vertex v2 = new Vertex( "v2" );
		Vertex v3 = new Vertex( "v3" );
		Vertex v4 = new Vertex( "v4" );
		Vertex v5 = new Vertex( "v5" );
		Vertex v6 = new Vertex( "v6" );
		Vertex v6_1 = new Vertex( "v6_1" );
		Vertex v7 = new Vertex( "v7" );
		Vertex v7_1 = new Vertex( "v7_1" );
		Vertex v8 = new Vertex( "v8" );
		Vertex v8_1 = new Vertex( "v8_1" );
		Vertex v9 = new Vertex( "v9" );
		Vertex v10 = new Vertex( "v10" );
		Vertex v10_1 = new Vertex( "v10_1" );

		//前邻接表
		mGraph.addAdjacencyBef( v1, new VertexAdj[]{} );
		mGraph.addAdjacencyBef( v2, new VertexAdj[]{ new VertexAdj(v1, 3) } );
		mGraph.addAdjacencyBef( v3, new VertexAdj[]{ new VertexAdj(v1, 2) } );
		mGraph.addAdjacencyBef( v4, new VertexAdj[]{ new VertexAdj(v2, 3) } );
		mGraph.addAdjacencyBef( v5, new VertexAdj[]{ new VertexAdj(v3, 1) } );
		mGraph.addAdjacencyBef( v6, new VertexAdj[]{ new VertexAdj(v6_1, 2) } );
		mGraph.addAdjacencyBef( v6_1, new VertexAdj[]{ new VertexAdj(v2, 0),new VertexAdj(v3, 0) } );
		mGraph.addAdjacencyBef( v7, new VertexAdj[]{ new VertexAdj(v7_1, 3) } );
		mGraph.addAdjacencyBef( v7_1, new VertexAdj[]{ new VertexAdj(v4, 0),new VertexAdj(v6, 0) } );
		mGraph.addAdjacencyBef( v8, new VertexAdj[]{ new VertexAdj(v8_1, 2) } );
		mGraph.addAdjacencyBef( v8_1, new VertexAdj[]{ new VertexAdj(v5, 0),new VertexAdj(v6, 0) } );
		mGraph.addAdjacencyBef( v9, new VertexAdj[]{ new VertexAdj(v5, 4) } );
		mGraph.addAdjacencyBef( v10, new VertexAdj[]{ new VertexAdj(v10_1, 1) } );
		mGraph.addAdjacencyBef( v10_1, new VertexAdj[]{ new VertexAdj(v7, 0),new VertexAdj(v8, 0), new VertexAdj(v9, 0) } );

		//后邻接表
		mGraph.addAdjacencyAft( v1, new VertexAdj[]{ new VertexAdj(v2, 3),new VertexAdj(v3, 2) } );
		mGraph.addAdjacencyAft( v2, new VertexAdj[]{ new VertexAdj(v4, 3),new VertexAdj(v6_1, 0) } );
		mGraph.addAdjacencyAft( v3, new VertexAdj[]{ new VertexAdj(v5, 1),new VertexAdj(v6_1, 0) } );
		mGraph.addAdjacencyAft( v4, new VertexAdj[]{ new VertexAdj(v7_1, 0) } );
		mGraph.addAdjacencyAft( v5, new VertexAdj[]{ new VertexAdj(v8_1, 0),new VertexAdj(v9, 4) } );
		mGraph.addAdjacencyAft( v6, new VertexAdj[]{ new VertexAdj(v7_1, 0),new VertexAdj(v8_1, 0) } );
		mGraph.addAdjacencyAft( v6_1, new VertexAdj[]{ new VertexAdj(v6, 2) } );
		mGraph.addAdjacencyAft( v7, new VertexAdj[]{ new VertexAdj(v10_1, 0) } );
		mGraph.addAdjacencyAft( v7_1, new VertexAdj[]{ new VertexAdj(v7, 3) } );
		mGraph.addAdjacencyAft( v8, new VertexAdj[]{ new VertexAdj(v10_1, 0) } );
		mGraph.addAdjacencyAft( v8_1, new VertexAdj[]{ new VertexAdj(v8, 2) } );
		mGraph.addAdjacencyAft( v9, new VertexAdj[]{ new VertexAdj(v10_1, 0) } );
		mGraph.addAdjacencyAft( v10, new VertexAdj[]{} );
		mGraph.addAdjacencyAft( v10_1, new VertexAdj[]{ new VertexAdj(v10, 1) } );

		return mGraph;
	}

	/**
	 * 将传入的赋值图的邻接表打印在控制台上，用于测试
	 * @param mGraph 有向赋值图
	 * @date 2016/3/22
	 */
	public void printGraph( Graph mGraph ) {

		Set<Vertex> vertexSet = mGraph.adjacencysBef.keySet();
		VertexAdj[] vertexAdjs;

		System.out.print("\n无圈有向赋权图(包括前邻接表和后邻接表)\n");

		System.out.print("\n前邻接表\n");
		for ( Vertex vertex : vertexSet )
		{
			System.out.print( vertex.name );		
			System.out.print( "	" );

			vertexAdjs = mGraph.adjacencysBef.get( vertex );
			for ( int i=0; i<vertexAdjs.length; i++ )
			{
				System.out.print( "(" + vertexAdjs[i].vertex.name + ", " + vertexAdjs[i].weight + ")" );
				if ( i < vertexAdjs.length - 1 )
				{
					System.out.print(",  ");
				}
			}
			System.out.print("\n");
		}

		System.out.print("\n后邻接表\n");
		for ( Vertex vertex : vertexSet )
		{
			System.out.print( vertex.name );			
			System.out.print( "	" );

			vertexAdjs = mGraph.adjacencysAft.get( vertex );
			for ( int i=0; i<vertexAdjs.length; i++ )
			{
				System.out.print( "(" + vertexAdjs[i].vertex.name + ", " + vertexAdjs[i].weight + ")" );
				if ( i < vertexAdjs.length - 1 )
				{
					System.out.print(",  ");
				}
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * 打印路径，用于测试
	 * @param path 顶点队列
	 * @date 2016/3/22
	 */
	public void printPath( LinkedList<Vertex> path ) {
		while ( path.size() > 0 )
		{
			System.out.print( path.poll().name );
			if ( path.size() >= 1)
			{
				System.out.print( " -> " );
			}
		}
	}

	/**
	 * 打印关键路径分析结果，包括最早完成时间、最晚完成时间和关键路径，用于测试
	 * @param mGraph 已经计算好所有节点最早完成时间的无圈有向赋权图
	 * @throws CycleFoundException 如果传入的有向图中有圈 
	 */
	public void printCpaResult( Graph mGraph ) throws CycleFoundException {

		Set<Vertex> vertexSet = mGraph.adjacencysBef.keySet();
		VertexAdj[] vertexAdjs;

		System.out.println( "关键路径分析结果 : " );
		System.out.println( "\n顶点	(EC,beforeVertex) (LC,afterVertex)" );
		for ( Vertex vertex : vertexSet )
		{
			System.out.print( vertex.name );
			System.out.print( "	" );
			
			//输出最早完成时间和对应前邻接节点
			String beforeVertex = "null";
			if ( vertex.beforeVertex != null )
			{
				beforeVertex = vertex.beforeVertex.name;
			}
			System.out.print( "(" + vertex.earliestTime + ", " + beforeVertex + ")" );
			System.out.print( " " );
			
			//输出最晚完成时间和对应后邻接节点
			String afterVertex = "null";
			if ( vertex.afterVertex != null )
			{
				afterVertex = vertex.afterVertex.name;
			}
			System.out.print( "(" + vertex.latestTime + ", " + afterVertex + ")" );
			
			System.out.print("\n");
		}

		System.out.println( "\n关键路径(Critical Path) " );
		printPath( findCrtPath( mGraph ) );
		System.out.print("\n");
	}

	public static void main( String args[] )  throws CycleFoundException  {
		CrtPathAnalysis mCrtPathAnalysis = new CrtPathAnalysis();
		
		Graph mGraph = mCrtPathAnalysis.generateGraph();	
		mCrtPathAnalysis.printGraph( mGraph );  //打印原图

		System.out.println("\n");
		mCrtPathAnalysis.earliestTime( mGraph );
		mCrtPathAnalysis.latestTime( mGraph, mCrtPathAnalysis.findEC( mGraph) );
		mCrtPathAnalysis.printCpaResult( mGraph );	 //打印分析结果
	}
} 
