/**
 *	��ϰ��������ͼ����Ȩ���·��
 *		�����ͼ��HashMap����ʾ 
 *	@date 2016/3/22
 */
package com.hemeng;

import java.util.*;

/**
 *	ͼ�Ķ�����
 */
class Vertex {

	public static final int INFINITY = Integer.MAX_VALUE; //·��Ϊ�����ʾĳ�㲻�ɴ�
	private String name; //����
	private int dist; //������ʼ�����·������
	private Vertex path; //ͨ����ʼ�������һ��·��

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
 * ����ͼ�࣬��HashMap��ʵ��
 * @data 2016/3/22
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
	 * ��ȡָ������
	 *	   ����ʱ�������
	 * @param name ��������
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
		adjacencys.put( vertex, vertexs );
	}
}

/**
 * ��������ͼ����Ȩ���·��
 * 
 */
public class ShortestPathUnweighted
{
	public ShortestPathUnweighted() {
	}

	/**
	 * ���㴫�������ͼ��ָ�����㵽���ж�������·��
	 *		������ͨ����������ͼ��dist��path�������ֳ���
	 * @param mGraph ����ͼ
	 * @param startVertex ·������ʼ����
	 * @date 2016/3/22		
	 */
	public void unweighted( Graph mGraph, Vertex startVertex ) 
	{	
		LinkedList<Vertex> queue = new LinkedList<>();
		Set<Vertex> vertexSet = mGraph.getVertexSet();
		for ( Vertex vertex : vertexSet ) //��ʼ����һ������;���ֵ
		{ 
			vertex.setDist( Vertex.INFINITY );
			vertex.setPath( null );
		}
		startVertex.setDist( 0 );
		startVertex.setPath( startVertex );
		queue.offer( startVertex ); //�������ӣ�������ѭ��
		
		Vertex currentVertex;
		int currentDist;
		Vertex[] vertexs;

		while( queue.size() > 0 ) { //�������������ƣ�����Ϊ������ֹѭ��
			currentVertex = queue.poll();
			currentDist = currentVertex.getDist();
			vertexs = mGraph.getVertexArray( currentVertex );
			
			for (int i=0; i<vertexs.length; i++ )
			{
				if ( vertexs[i].getDist() == Vertex.INFINITY ) //����ö������ֵΪ���˵��δ�����������
				{												//�ʹ���ö���
					vertexs[i].setDist( currentDist + 1 );
					vertexs[i].setPath( currentVertex );
					queue.offer( vertexs[i] );
				}
			}
		}
	}

	/**
	 *	����һ������ͼ�����ڲ���
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
	 *	�������ͼ���ڽӱ��ӡ�ڿ���̨�ϣ����ڲ���
	 *	@param mGraph ����ͼ
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
		System.out.print("ԭͼ��\n");
		mShortestPathUnweighted.printGraph( mGraph );

		mShortestPathUnweighted.unweighted( mGraph, mGraph.getVertex("v3") );
		System.out.print("\n��Ȩ���·���������������Ϊv3��\n");
		mShortestPathUnweighted.printGraph( mGraph );
	}
}


