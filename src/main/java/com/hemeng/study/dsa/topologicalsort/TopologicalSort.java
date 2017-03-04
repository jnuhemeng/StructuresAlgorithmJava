/**
 *	��ϰͼ�ı�ʾ������ͼ����������
 *		�����ͼ��HashMap����ʾ 
 *	@date 2016/3/21
 */
package com.hemeng.study.dsa.topologicalsort;
import java.util.*;

/**
 *	ͼ�Ķ�����
 */
class Vertex {
	private String name; //����
	private int indegree; //���
	//private int outdegree; //����
	private int topNum; //����������λ��

	public Vertex( String name ) {
		this( name, 0, -1 ); //�����Ĭ��ֵ����ȣ�0������λ�ã���ʾδ���򣩣�-1��
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
 *	����ͼ��
 */
class Graph
{
	private HashMap< Vertex, Vertex[] > adjacencys;
	
	public Graph() {
		adjacencys = new HashMap< Vertex, Vertex[] >();
	}

	/**
	 *	��ȡ���ж���
	 */
	public Set<Vertex> getVertexSet() {
		return adjacencys.keySet();
	}
	
	/**
	 *	��ȡһ��ָ�������Ӧ���ڽӱ��¼
	 */
	public Vertex[] getVertexArray( Vertex vertex ) {
		return adjacencys.get( vertex );
	}

	/**
	 *	��ȡ�����ڽӱ�
	 */
	public Map< Vertex, Vertex[] > getAdjacencys() {
		return adjacencys;
	}

	/**
	 *	���ڽӱ������һ����¼
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
 *	�쳣�ࣨ����ͼ����Ȧ��
 */
class CycleFoundException extends Exception  
{  
    public CycleFoundException()  
    {  
        super( "Topsort abortion: The graph is not a DAG." );  
    }  
}

/**
 *	����ͼ����������
 */
public class TopologicalSort
{
	public TopologicalSort() {
	}
	
	/**
	 *	�Դ��������ͼ������������
	 *		������ͨ����������ͼ��topNum�������ֳ�������
	 *		����ɺ�����ͼ��indegree���Խ����ܴ�����ʵ����ȡ�
	 *	@param mGraph ����ͼ
	 *	@throws CycleFoundException ������������ͼ����Ȧ
	 *	@date 2016/3/21
	 *		
	 */
	public void topsort( Graph mGraph ) throws CycleFoundException {

		LinkedList<Vertex> queue = new LinkedList<>(); //���ڴ洢���Ϊ0�Ķ���
		Set<Vertex> vertexSet = mGraph.getVertexSet();

		for ( Vertex vertex : vertexSet ) //��ʼ������
		{
			if ( vertex.getIndegree() == 0 ) //�ҳ�����ǰ�������Ϊ0�Ķ���
			{
				queue.offer( vertex );
			}
		}

		int counter = 0;
		Vertex vertex;
		Vertex[] vertexs;
		while ( queue.size() > 0 )
		{
			vertex = queue.poll(); //ȡ������������һ�����Ϊ0�Ķ���
			vertex.setTopNum( ++counter );
			vertexs = mGraph.getVertexArray( vertex );
			
			int vertexsLenght = vertexs.length;
			for (int i=0; i<vertexsLenght; i++ )
			{
				vertexs[i].setIndegree( vertexs[i].getIndegree() -1 );
				if ( vertexs[i].getIndegree() == 0 ) //���´洢���Ϊ0�Ķ��е�ֵ
				{
					queue.offer( vertexs[i] );
				}
			}
		}

		if ( counter < vertexSet.size() ) //������ճɹ�����Ķ������ȶ����ܸ�����
		{								  //	����˵����ͼ����Ȧ
			throw new CycleFoundException();
		}
	}

	/**
	 *	����һ������ͼ�����ڲ���
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
	 *	�������ͼ���ڽӱ��ӡ�ڿ���̨�ϣ����ڲ���
	 *	@param mGraph DAGͼ
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
	 *	������ľ������������DAG����˳���ӡ�ڿ���̨�ϣ����ڲ���
	 *		�������һ��δ�����������ͼ�����޷��õ���ȷ�Ľ����
	 *	@param mGraph �������������DAGͼ
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
		Graph mGraph = mTopologicalSort.generateGraph(); //����DAGͼ
		System.out.print("ԭDAGͼ��\n");
		mTopologicalSort.printGraph( mGraph );
		
		try
		{
			mTopologicalSort.topsort( mGraph ); //DAGͼ��������
			System.out.print("\n�������������һ�ֽ����\n");
			mTopologicalSort.printTopsort( mGraph );
		}
		catch ( CycleFoundException mCycleFoundException )
		{
			System.out.println( "\n" + mCycleFoundException.getMessage() );
		}

		System.out.println();
	}
}


