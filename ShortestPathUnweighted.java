/**
 *	练习计算有向图的无权最短路径
 *		在这里，图用HashMap来表示 
 *	@date 2016/3/22
 */
package com.hemeng;

import java.util.*;

/**
 *	图的顶点类
 */
class Vertex {

	public static final int INFINITY = Integer.MAX_VALUE; //路径为无穷，表示某点不可达
	private String name; //名字
	private int dist; //距离起始顶点的路径长度
	private Vertex path; //通往起始顶点的下一跳路径

	public Vertex( String name ) {
		this( name, INFINITY, null ); 
	}

	public Vertex( String name, int dist, Vertex path ) {
		this.name = name;
		this.dist = dist;
		this.path = path;
	}

	public String getName() {
		return this.name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public int getDist() {
		return this.dist;
	}

	public void setDist( int dist ){
		this.dist = dist;
	}

	public Vertex getPath() {
		return this.path;
	}

	public void setPath( Vertex path ) {
		this.path = path;
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
 * 有向图类，用HashMap来实现
 * @data 2016/3/22
 */
class Graph
{
	private HashMap< Vertex, Vertex[] > adjacencys;
	
	public Graph() {
		adjacencys = new HashMap< Vertex, Vertex[] >();
	}

	/**
	 *	获取所有顶点
	 */
	public Set<Vertex> getVertexSet() {
		return adjacencys.keySet();
	}

	/**
	 * 获取指定顶点
	 *	   线性时间最坏情形
	 * @param name 顶点名字
	 * @date 2016/3/22
	 */
	public Vertex getVertex( String name ) {
		Set<Vertex> vertexSet = getVertexSet();
		for ( Vertex vertex : vertexSet )
		{
			if ( vertex.getName() == name )
			{
				return vertex;
			}
		}
		return null;
	}
	
	/**
	 *	获取一条指定顶点对应的邻接表记录
	 */
	public Vertex[] getVertexArray( Vertex vertex ) {
		return adjacencys.get( vertex );
	}

	/**
	 *	获取整个邻接表
	 */
	public Map< Vertex, Vertex[] > getAdjacencys() {
		return adjacencys;
	}

	/**
	 *	往邻接表中添加一条记录
	 */
	public void addAdjacency( Vertex vertex, Vertex[] vertexs ) {
		adjacencys.put( vertex, vertexs );
	}
}

/**
 * 计算有向图的无权最短路径
 * 
 */
public class ShortestPathUnweighted
{
	public ShortestPathUnweighted() {
	}

	/**
	 * 计算传入的有向图中指定顶点到所有顶点的最短路径
	 *		计算结果通过设置有向图的dist和path属性体现出来
	 * @param mGraph 有向图
	 * @param startVertex 路径的起始顶点
	 * @date 2016/3/22		
	 */
	public void unweighted( Graph mGraph, Vertex startVertex ) 
	{	
		LinkedList<Vertex> queue = new LinkedList<>();
		Set<Vertex> vertexSet = mGraph.getVertexSet();
		for ( Vertex vertex : vertexSet ) //初始化下一跳顶点和距离值
		{ 
			vertex.setDist( Vertex.INFINITY );
			vertex.setPath( null );
		}
		startVertex.setDist( 0 );
		startVertex.setPath( startVertex );
		queue.offer( startVertex ); //将起点入队，以启动循环
		
		Vertex currentVertex;
		int currentDist;
		Vertex[] vertexs;

		while( queue.size() > 0 ) { //与拓扑排序类似，队列为空则终止循环
			currentVertex = queue.poll();
			currentDist = currentVertex.getDist();
			vertexs = mGraph.getVertexArray( currentVertex );
			
			for (int i=0; i<vertexs.length; i++ )
			{
				if ( vertexs[i].getDist() == Vertex.INFINITY ) //如果该顶点距离值为无穷（说明未曾处理过），
				{												//就处理该顶点
					vertexs[i].setDist( currentDist + 1 );
					vertexs[i].setPath( currentVertex );
					queue.offer( vertexs[i] );
				}
			}
		}
	}

	/**
	 *	生成一张有向图，用于测试
	 *	@date 2016/3/22
	 */
	public Graph generateGraph() {

		Graph mGraph = new Graph();

		Vertex v1 = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		Vertex v3 = new Vertex("v3");
		Vertex v4 = new Vertex("v4");
		Vertex v5 = new Vertex("v5");
		Vertex v6 = new Vertex("v6");
		Vertex v7 = new Vertex("v7");
		
		mGraph.addAdjacency( v1, new Vertex[]{ v2, v4 } );
		mGraph.addAdjacency( v2, new Vertex[]{ v4, v5 } );
		mGraph.addAdjacency( v3, new Vertex[]{ v1, v6 } );
		mGraph.addAdjacency( v4, new Vertex[]{ v3, v5, v6, v7 } );
		mGraph.addAdjacency( v5, new Vertex[]{ v7 } );
		mGraph.addAdjacency( v6, new Vertex[]{} );
		mGraph.addAdjacency( v7, new Vertex[]{ v6 } );		

		return mGraph;
	}

	/**
	 *	将传入的图的邻接表打印在控制台上，用于测试
	 *	@param mGraph 有向图
	 *	@date 2016/3/22
	 */
	public void printGraph( Graph mGraph ) {

		Set<Vertex> vertexSet = mGraph.getVertexSet();
		for ( Vertex vertex : vertexSet )
		{
			Vertex[] vertexs = mGraph.getVertexArray( vertex );
			System.out.print( vertex.getName() );
			
			String path = "null";
			String dist = "INFINITY";
			if ( vertex.getPath() != null )
			{
				path = vertex.getPath
					
				
				().getName();
			}
			if ( vertex.getDist() != Vertex.INFINITY )
			{
				dist = String.valueOf( vertex.getDist() ); 
			}
			System.out.print( "(" + path + "," + dist + ")" );
			
			System.out.print( "   " );
			int vertexsLenght = vertexs.length;
			for (int j=0; j<vertexsLenght; j++ )
			{
				System.out.print( vertexs[j].getName() );
				if ( j < vertexsLenght - 1 )
				{
					System.out.print(", ");
				}
			}
			System.out.print("\n");
		}
	}

	public static void main( String args[] ) {
		
		ShortestPathUnweighted mShortestPathUnweighted = new ShortestPathUnweighted();
		Graph mGraph = mShortestPathUnweighted.generateGraph();
		System.out.print("原图：\n");
		mShortestPathUnweighted.printGraph( mGraph );

		mShortestPathUnweighted.unweighted( mGraph, mGraph.getVertex("v3") );
		System.out.print("\n无权最短路径计算结果：（起点为v3）\n");
		mShortestPathUnweighted.printGraph( mGraph );
	}
}


