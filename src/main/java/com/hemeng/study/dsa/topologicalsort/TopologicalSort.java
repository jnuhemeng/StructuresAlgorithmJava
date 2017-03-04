/**
 *	练习图的表示，有向图的拓扑排序
 *		在这里，图用HashMap来表示 
 *	@date 2016/3/21
 */
package com.hemeng.study.dsa.topologicalsort;
import java.util.*;

/**
 *	图的顶点类
 */
class Vertex {
	private String name; //名字
	private int indegree; //入度
	//private int outdegree; //出度
	private int topNum; //拓扑排序后的位置

	public Vertex( String name ) {
		this( name, 0, -1 ); //顶点的默认值（入度：0，排序位置（表示未排序）：-1）
	}

	public Vertex( String name, int indegree, int topNum ) {
		this.name = name;
		this.indegree = indegree;
		this.topNum = topNum;
	}

	public String getName() {
		return this.name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public int getIndegree() {
		return this.indegree;
	}

	public void setIndegree( int indegree ){
		this.indegree = indegree;
	}

	public int getTopNum() {
		return this.topNum;
	}

	public void setTopNum( int topNum ) {
		this.topNum = topNum;
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
 *	有向图类
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
		for ( int i=0; i<vertexs.length; i++ )
		{
			vertexs[i].setIndegree( vertexs[i].getIndegree() + 1 );
		}
		adjacencys.put( vertex, vertexs );
	}
}

/**
 *	异常类（有向图中有圈）
 */
class CycleFoundException extends Exception  
{  
    public CycleFoundException()  
    {  
        super( "Topsort abortion: The graph is not a DAG." );  
    }  
}

/**
 *	有向图的拓扑排序
 */
public class TopologicalSort
{
	public TopologicalSort() {
	}
	
	/**
	 *	对传入的有向图进行拓扑排序
	 *		排序结果通过设置有向图的topNum属性体现出来，排
	 *		序完成后有向图的indegree属性将不能代表真实的入度。
	 *	@param mGraph 有向图
	 *	@throws CycleFoundException 如果传入的有向图中有圈
	 *	@date 2016/3/21
	 *		
	 */
	public void topsort( Graph mGraph ) throws CycleFoundException {

		LinkedList<Vertex> queue = new LinkedList<>(); //用于存储入度为0的顶点
		Set<Vertex> vertexSet = mGraph.getVertexSet();

		for ( Vertex vertex : vertexSet ) //初始化队列
		{
			if ( vertex.getIndegree() == 0 ) //找出排序前所有入度为0的顶点
			{
				queue.offer( vertex );
			}
		}

		int counter = 0;
		Vertex vertex;
		Vertex[] vertexs;
		while ( queue.size() > 0 )
		{
			vertex = queue.poll(); //取出队列中任意一个入度为0的顶点
			vertex.setTopNum( ++counter );
			vertexs = mGraph.getVertexArray( vertex );
			
			int vertexsLenght = vertexs.length;
			for (int i=0; i<vertexsLenght; i++ )
			{
				vertexs[i].setIndegree( vertexs[i].getIndegree() -1 );
				if ( vertexs[i].getIndegree() == 0 ) //更新存储入度为0的队列的值
				{
					queue.offer( vertexs[i] );
				}
			}
		}

		if ( counter < vertexSet.size() ) //如果最终成功排序的顶点数比顶点总个数少
		{								  //	，则说明该图中有圈
			throw new CycleFoundException();
		}
	}

	/**
	 *	生成一张有向图，用于测试
	 *	@date 2016/3/21
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
		
		mGraph.addAdjacency( v1, new Vertex[]{ v2, v4, v3 } );
		mGraph.addAdjacency( v2, new Vertex[]{ v4, v5 } );
		mGraph.addAdjacency( v3, new Vertex[]{ v6 } );
		mGraph.addAdjacency( v4, new Vertex[]{ v6, v7, v3 } );
		mGraph.addAdjacency( v5, new Vertex[]{ v4, v7 } );
		mGraph.addAdjacency( v6, new Vertex[]{} );
		mGraph.addAdjacency( v7, new Vertex[]{ v6 } );		

		return mGraph;
	}

	/**
	 *	将传入的图的邻接表打印在控制台上，用于测试
	 *	@param mGraph DAG图
	 *	@date 2016/3/21
	 */
	public void printGraph( Graph mGraph ) {

		Set<Vertex> vertexSet = mGraph.getVertexSet();
		for ( Vertex vertex : vertexSet )
		{
			Vertex[] vertexs = mGraph.getVertexArray( vertex );
			System.out.print( vertex.getName() );
			System.out.print( "(" + vertex.getIndegree() + "," + vertex.getTopNum() + ")" );
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
	
	/**
	 *	将传入的经过拓扑排序的DAG按照顺序打印在控制台上，用于测试
	 *		如果传入一个未经拓扑排序的图，就无法得到正确的结果。
	 *	@param mGraph 经过拓扑排序的DAG图
	 *	@date 2016/3/21
	 */
	public void printTopsort( Graph mGraph ) {

		Set<Vertex> vertexSet = mGraph.getVertexSet();
		Vertex[] vertexs = new Vertex[ vertexSet.size() ];

		for ( Vertex vertex : vertexSet )
		{
			vertexs[ vertex.getTopNum() -1 ] = vertex;
		}

		for ( int i=0; i<vertexs.length; i++ )
		{
			System.out.print( vertexs[i].getName() );
			if ( i < vertexs.length - 1 )
			{
				System.out.print(", ");
			}
		}
	}

	public static void main( String args[] ) {
		TopologicalSort mTopologicalSort = new TopologicalSort();
		Graph mGraph = mTopologicalSort.generateGraph(); //生成DAG图
		System.out.print("原DAG图：\n");
		mTopologicalSort.printGraph( mGraph );
		
		try
		{
			mTopologicalSort.topsort( mGraph ); //DAG图拓扑排序
			System.out.print("\n拓扑排序的其中一种结果：\n");
			mTopologicalSort.printTopsort( mGraph );
		}
		catch ( CycleFoundException mCycleFoundException )
		{
			System.out.println( "\n" + mCycleFoundException.getMessage() );
		}

		System.out.println();
	}
}


