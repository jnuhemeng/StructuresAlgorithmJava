/**
 *	��ϰ��Ȧͼ�ĵ�Դ���·�������ڹؼ�·�������е�Ӧ��
 *		�����ͼ������HashMap����ʾ���ֱ��Ӧ�����ڽӱ�
 *		���붥���ڽ�����ǰ���ں���ڽӱ� 
 *	@date 2016/3/25
 */
package com.hemeng.study.dsa.crtpathanalysis;
import java.util.*;

/**
 * ͼ�Ķ�����
 * @date 2016/3/25
 */
class Vertex {
	public String name; //����
	public int indegree; //���
	public int outdegree; //����
	public int tempdegree; //������ʱ������Ȼ���ȵĿ���
	public int topNum; //����˳��������λ��
	public int bottomNum; //��������������λ��
	public int earliestTime; //�������ʱ��
	public int latestTime; //�������ʱ��
	public Vertex beforeVertex; //�������ʱ���Ӧ��ǰһ���ڵ�	
	public Vertex afterVertex; //�������ʱ���Ӧ����һ���ڵ�


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
 * ����ͼ��
 * @date 2016/3/25
 */
class Graph
{
	public HashMap< Vertex, VertexAdj[] > adjacencysBef; //ǰ�ڽӱ�
	public HashMap< Vertex, VertexAdj[] > adjacencysAft; //���ڽӱ�
	
	public Graph() {
		adjacencysBef = new HashMap< Vertex, VertexAdj[] >();
		adjacencysAft = new HashMap< Vertex, VertexAdj[] >();
	}

	/**
	 * ��ǰ�ڽӱ������һ����¼
	 * @param vertex ����
	 * @param vertexAdjBefs ���ڽӱ�
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
	 * �����ڽӱ������һ����¼
	 * @param vertex ����
	 * @param vertexAdjAfts ���ڽӱ�
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
 * �쳣�ࣨ����ͼ����Ȧ��
 */
class CycleFoundException extends Exception  
{  
    public CycleFoundException()  
    {  
        super( "Topsort abortion: The graph is not a DAG." );  
    }  
}

/**
 * �ؼ�·��������������Ȧ��Ȩ����ͼ��ÿ���ڵ���������ʱ�䡢
 *		�������ʱ���ÿ���ߵ��ɳ�ʱ�䣨slack time��
 * @date 2016/3/25
 */
public class CrtPathAnalysis
{
	public CrtPathAnalysis() {
	}

	/**
	 * �����������ʱ��
	 * @param mGraph ��Ȧ����Ȩͼ
	 * @throws CycleFoundException ������������ͼ����Ȧ
	 * @date 2016/3/25
	 */
	public void earliestTime( Graph mGraph ) throws CycleFoundException {
		Set<Vertex> vertexSet = mGraph.adjacencysAft.keySet();
		LinkedList<Vertex> queue = new LinkedList<>();
		for ( Vertex vertex : vertexSet )
		{
			vertex.tempdegree = vertex.indegree; //�������
			if ( vertex.tempdegree == 0 )
			{
				queue.offer( vertex );
			}			
		}	
		
		int counter = 0;
		VertexAdj[] vertexAdjsBef;
		VertexAdj[] vertexAdjsAft;

		Vertex vertex = queue.poll();
		vertex.earliestTime = 0; //������ʼ�ڵ���������ʱ��Ϊ0
		queue.offer( vertex );

		while( queue.size() > 0 ) {
			//ȡ�����׵Ľڵ�
			vertex = queue.poll();
			vertex.topNum = ++counter;
			
			//���㵱ǰ�ڵ���������ʱ�䣨����ǰ�ڽӱ������������ʱ��
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
				
			//���º��ڽӽڵ����ȣ��������Ϊ0�Ľڵ������У����ú��ڽӱ�������������
			vertexAdjsAft = mGraph.adjacencysAft.get( vertex );
			for (int i=0; i<vertexAdjsAft.length; i++ )
			{
				if ( --vertexAdjsAft[i].vertex.tempdegree == 0 ) 
				{
					queue.offer( vertexAdjsAft[i].vertex );
				}
			}
		}

		if ( counter < vertexSet.size() ) //������ճɹ�����Ķ������ȶ����ܸ�����
		{								  //	����˵����ͼ����Ȧ
			throw new CycleFoundException();
		}
	}

	/**
	 * �����������ʱ��
	 * @param mGraph ��Ȧ����Ȩͼ
	 * @param EC ���һ���¼���ɵ�����ʱ��
	 * @date 2016/3/25
	 */
	public void latestTime( Graph mGraph, int EC ) throws CycleFoundException {

		Set<Vertex> vertexSet = mGraph.adjacencysBef.keySet();
		LinkedList<Vertex> queue = new LinkedList<>();
		for ( Vertex vertex : vertexSet )
		{
			vertex.tempdegree = vertex.outdegree; //���ݳ���
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
		vertex.latestTime = EC; //���ý����ڵ���������ʱ��ΪLASTESTTIME
		queue.offer( vertex );

		while( queue.size() > 0 ) {
			//ȡ�����׵Ľڵ�
			vertex = queue.poll();
			vertex.bottomNum = ++counter;
			
			//���㵱ǰ�ڵ���������ʱ�䣨���ú��ڽӱ������������ʱ��
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
				
			//����ǰ�ڽӽڵ�ĳ��ȣ��������Ϊ0�Ľڵ������У�����ǰ�ڽӱ�������������
			vertexAdjsBef = mGraph.adjacencysBef.get( vertex );
			for (int i=0; i<vertexAdjsBef.length; i++ )
			{
				if ( --vertexAdjsBef[i].vertex.tempdegree == 0 ) 
				{
					queue.offer( vertexAdjsBef[i].vertex );
				}
			}
		}

		if ( counter < vertexSet.size() ) //������ճɹ�����Ķ������ȶ����ܸ�����
		{								  //	����˵����ͼ����Ȧ
			throw new CycleFoundException();
		}
	}

	/**
	 * ���ҹؼ�·��(Critical path)
	 * @param mGraph �Ѿ���������нڵ�������������ʱ�����Ȧ����Ȩͼ
	 * @return ������б�ʾ�Ĺؼ�·��
	 * @throws CycleFoundException ������������ͼ����Ȧ 
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
	 * �����������ʱ��
	 * @param mGraph �Ѿ���������нڵ��������ʱ�����Ȧ����Ȩͼ
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
	 * ������ʼ�ڵ㣨���Ϊ0��
	 *		����ж�����������Ľڵ㣬��ֻ�᷵�ص�һ��
	 * @param ��Ȧ����Ȩͼ
	 * @return ��ʼ����
	 * @throws CycleFoundException ������������ͼ����Ȧ 
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
	 * ����һ������ͼ�����ڲ���
	 * @return ��Ȧ��Ȩ����ͼ
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

		//ǰ�ڽӱ�
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

		//���ڽӱ�
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
	 * ������ĸ�ֵͼ���ڽӱ��ӡ�ڿ���̨�ϣ����ڲ���
	 * @param mGraph ����ֵͼ
	 * @date 2016/3/22
	 */
	public void printGraph( Graph mGraph ) {

		Set<Vertex> vertexSet = mGraph.adjacencysBef.keySet();
		VertexAdj[] vertexAdjs;

		System.out.print("\n��Ȧ����Ȩͼ(����ǰ�ڽӱ�ͺ��ڽӱ�)\n");

		System.out.print("\nǰ�ڽӱ�\n");
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

		System.out.print("\n���ڽӱ�\n");
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
	 * ��ӡ·�������ڲ���
	 * @param path �������
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
	 * ��ӡ�ؼ�·����������������������ʱ�䡢�������ʱ��͹ؼ�·�������ڲ���
	 * @param mGraph �Ѿ���������нڵ��������ʱ�����Ȧ����Ȩͼ
	 * @throws CycleFoundException ������������ͼ����Ȧ 
	 */
	public void printCpaResult( Graph mGraph ) throws CycleFoundException {

		Set<Vertex> vertexSet = mGraph.adjacencysBef.keySet();
		VertexAdj[] vertexAdjs;

		System.out.println( "�ؼ�·��������� : " );
		System.out.println( "\n����	(EC,beforeVertex) (LC,afterVertex)" );
		for ( Vertex vertex : vertexSet )
		{
			System.out.print( vertex.name );
			System.out.print( "	" );
			
			//����������ʱ��Ͷ�Ӧǰ�ڽӽڵ�
			String beforeVertex = "null";
			if ( vertex.beforeVertex != null )
			{
				beforeVertex = vertex.beforeVertex.name;
			}
			System.out.print( "(" + vertex.earliestTime + ", " + beforeVertex + ")" );
			System.out.print( " " );
			
			//����������ʱ��Ͷ�Ӧ���ڽӽڵ�
			String afterVertex = "null";
			if ( vertex.afterVertex != null )
			{
				afterVertex = vertex.afterVertex.name;
			}
			System.out.print( "(" + vertex.latestTime + ", " + afterVertex + ")" );
			
			System.out.print("\n");
		}

		System.out.println( "\n�ؼ�·��(Critical Path) " );
		printPath( findCrtPath( mGraph ) );
		System.out.print("\n");
	}

	public static void main( String args[] )  throws CycleFoundException  {
		CrtPathAnalysis mCrtPathAnalysis = new CrtPathAnalysis();
		
		Graph mGraph = mCrtPathAnalysis.generateGraph();	
		mCrtPathAnalysis.printGraph( mGraph );  //��ӡԭͼ

		System.out.println("\n");
		mCrtPathAnalysis.earliestTime( mGraph );
		mCrtPathAnalysis.latestTime( mGraph, mCrtPathAnalysis.findEC( mGraph) );
		mCrtPathAnalysis.printCpaResult( mGraph );	 //��ӡ�������
	}
} 
