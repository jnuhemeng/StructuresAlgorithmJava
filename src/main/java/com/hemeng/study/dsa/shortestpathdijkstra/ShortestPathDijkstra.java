/**
 * ��ϰ����Dijkstra�㷨�ĸ�Ȩͼ��Դ���·������
 *
 */
package com.hemeng.study.dsa.shortestpathdijkstra;

import java.util.*;

/**
 * ������
 *	   �ڽӱ���Ϊ��������ݳ�Ա
 * @date 2016/3/22
 */
class Vertex  
{
	public static final int INFINITY = Integer.MAX_VALUE; //·��Ϊ�����ʾĳ�㲻�ɴ�
	public String name;
	public int dist;
	public Vertex path;
	public boolean known;

	//������������������и�ֵȦ��ͼ�����
	public boolean isInQueue; //�Ƿ����ڶ�����
	public int enqueueTimes; //�Ѿ���ӵĴ�������ֹ��ֵȦ���µ���ѭ��

	public ArrayList<VertexAdj> adj;
	
	public Vertex( String name ) {
		this.name = name;
		adj = new ArrayList<VertexAdj>();
	}
}

/**
 * ������ڽӱ��е�Ԫ���࣬����һ������Ͷ�Ӧ�ߵ�Ȩֵ
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
 * ����ֵͼ
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
	 *	������Ӷ���
	 */
	public void addVertexs( Vertex[] vertexs ) {
		for (int i=0; i<vertexs.length; i++ )
		{
			vertexSet.add( vertexs[i] );
		}
	}

	/**
	 * ��ȡָ������
	 *	   ����ʱ�������
	 * @param name ��������
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
 * ����Dijkstra�㷨�ĸ�Ȩͼ��Դ���·������
 * @date 2016/3/22
 */
public class ShortestPathDijkstra
{
	public ShortestPathDijkstra() {
	}

	/**
	 * ���㸳Ȩͼָ�����ĵ�Դ���·��
	 *		������ͨ����������ͼ��dist��path�������ֳ���
	 * @param mGraph ����Ȩͼ
	 * @param startvertex ��ʼ����
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
				if ( !vertex.known ) //�ö���unknown
				{
					if ( minVertex != null ) //unknown�Ķ��㲻ֹһ��
					{
						if ( vertex.dist < minVertex.dist )
						{
							minVertex = vertex;
						}
					} else { //�׸��ҵ���unknown����
						minVertex = vertex;
					}					
				}
			}	
			if ( minVertex == null || minVertex.dist == Vertex.INFINITY ) //����Ѿ�������unknown���㣬������ʣ�ඥ�㶼���ɴ�
			{															//����ֹѭ����ɼ���
				break;
			}

			minVertex.known = true; //����ǰ���·����Ӧ�Ķ�����Ϊknown
			int vertexAdjLength = minVertex.adj.size();
			for ( int i=0; i<vertexAdjLength; i++ ) //���¸ö����ڽӵ��Ķ����·����Ϣ
			{
				if ( minVertex.adj.get(i).vertex.known==false && minVertex.adj.get(i).vertex.dist > minVertex.dist + minVertex.adj.get(i).weight)
				{
					minVertex.adj.get(i).vertex.path = minVertex;
					minVertex.adj.get(i).vertex.dist = minVertex.dist + minVertex.adj.get(i).weight;
				}				
			}

			/*///����Ĵ�������ã��������Ӧ�ñ�ע��
				System.out.print( "\n\n");
				printGraph( mGraph );
			*////
		}
	}

	/**
	 * ������и�ֵ�ߵĸ�Ȩͼָ�����ĵ�Դ���·��
	 *		������ͨ����������ͼ��dist��path�������ֳ������������Ȩͼ�͸�Ȩͼ���ַ���,
	 *		��ʱ�临�Ӷȱ�Dijkstra��
	 * @param mGraph ����Ȩͼ
	 * @param startvertex ��ʼ����
	 * @date 2016/3/24
	 */
	public void  weightedNegative( Graph mGraph, Vertex startVertex) {

		LinkedList<Vertex> queue = new LinkedList<>();
		int enqueueTimes = mGraph.vertexSet.size();
		System.out.println("mGraph.vertexSet.size() = " + mGraph.vertexSet.size() ); //���ڵ���
		for ( Vertex vertex : mGraph.vertexSet )
		{
			vertex.dist = Vertex.INFINITY;
			vertex.path = null;
			vertex.isInQueue = false; //�Ƿ����ڶ�����
			vertex.enqueueTimes = 0; //��ֹ��ֵȦ���µ���ѭ��
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
				if ( vertexAdj.vertex.dist > vertexTemp.dist + vertexAdj.weight ) //�����ڽӶ����·����Ϣ
				{
					vertexAdj.vertex.dist = vertexTemp.dist + vertexAdj.weight;
					vertexAdj.vertex.path = vertexTemp;
					if ( !vertexAdj.vertex.isInQueue && vertexAdj.vertex.enqueueTimes < enqueueTimes )
					{ //������ڶ������Ҳ��ڸ�ֵȦ�л���Ӵ���δ�������������ͽ��ö������
						queue.offer( vertexAdj.vertex );
						vertexAdj.vertex.isInQueue = true;
						vertexAdj.vertex.enqueueTimes++;
					}
				}
			}
		}
	}

	/**
	 * �����������Ȩͼ��ָ���㵽��һָ������·����Ȼ���ӡ����
	 * @param mGraph ����Ȩͼ
	 * @param startVertex ���
	 * @param destVertex �յ�
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
	 * �����������Ȩͼ��ָ���㵽��һָ������·����Ȼ���ӡ����
	 * @param mGraph ����Ȩͼ
	 * @param startVertex ���
	 * @param destVertex �յ�
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
	 * ��ӡ��ʼ�㵽ָ�������·��	   
	 * @param v �յ㣨ֻ�����Ѿ��������Դ���·��������Ȩͼ�ϵĶ���������壩
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
	 * ��ӡ��ʼ�㵽ָ�������·��	   
	 * @param v �յ㣨ֻ�����Ѿ��������Դ���·��������Ȩͼ�ϵĶ���������壩
	 * @param graphSize ����ֵͼ�����ܺ��и�ֵȦ���Ķ�������
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
	 * ����һ������ͼ�����ڲ���
	 * @date 2016/3/22
	 */
	public Graph generateGraph() {
		//����ͼ1
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

		/*//����ͼ2�����и�ֵ�ߣ�ֱ����Dijkstra�޷��ó���ȷ·���� 
		Vertex v1 = new Vertex( "v1" );
		Vertex v2 = new Vertex( "v2" );
		Vertex v3 = new Vertex( "v3" );
		Vertex v4 = new Vertex( "v4" );

		v1.adj.add( new VertexAdj(v2, 1) );
		v1.adj.add( new VertexAdj(v3, 4) );
		v2.adj.add( new VertexAdj(v4, 2) );
		v3.adj.add( new VertexAdj(v4, -2) );
		
		return new Graph( new Vertex[]{ v1, v2, v3, v4 });*/

		/*//����ͼ3�����и�ֵ���Һ��и�ֵȦ��ֱ����Dijkstra�޷��ó���ȷ·���� 
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
	 * ������ĸ�ֵͼ���ڽӱ��ӡ�ڿ���̨�ϣ����ڲ���
	 * @param mGraph ����ֵͼ
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
			//System.out.print( "(" + path + ", " + dist + ", " + vertex.known + ")" ); //���ڵ���
			System.out.print( "(" + path + ", " + dist + ", " + vertex.enqueueTimes + ")" ); //���ڵ���
			
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
		System.out.print("ԭʼ����Ȩͼ��\n");
		mShortestPathDijkstra.printGraph( mGraph );
		
		Vertex startVertex = mGraph.getVertex("v4"); //���
		Vertex destVertex = mGraph.getVertex("v1"); //�յ�

		/*//������ֵ�ߵ�����ֵͼ
		mShortestPathDijkstra.dijkstra( mGraph, startVertex );
		System.out.print("\nDijkstra��Դ(" + startVertex.name + ")���·����������\n");
		mShortestPathDijkstra.printGraph( mGraph );
	
		System.out.print("\n\nDijkstra( " + startVertex.name + " ---> " + destVertex.name + " )���·����������\n");
		mShortestPathDijkstra.printPath( mGraph, startVertex, destVertex );*/


		//���и�ֵ�ߵ�����ֵͼ
		mShortestPathDijkstra.weightedNegative( mGraph, startVertex );
		System.out.print("\n�Ľ�������ֵ�ߣ�Dijkstra��Դ(" + startVertex.name + ")���·����������\n");
		mShortestPathDijkstra.printGraph( mGraph );
	
		System.out.print("\n\n�Ľ�������ֵ�ߣ�Dijkstra( " + startVertex.name + " ---> " + destVertex.name + " )���·����������\n");
		mShortestPathDijkstra.printPathNegative( mGraph, startVertex, destVertex );

		System.out.println();
	}
}
