/**
 * ��ϰŷ����·�Ĳ���
 * @date 2016/4/3
 */
package com.hemeng.study.dsa.eulercircuit;
import java.util.*;

/**
 * ͼ�Ķ�����
 * @date 2016/4/3
 */
class Vertex {
	public String name; //����
	public boolean visited; //�Ƿ񱻷��ʹ�
	public int degree; //��
	public int currentDegree; //ָ�򼴽����ʵı�

	public Vertex( String name ) {
		this.name = name;
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
 * �ڽӱ��е�Ԫ�ص���
 * @date 2016/4/3
 */
class AdjVertex
{
	public Vertex vertex; //�ڽӶ���
	public boolean visited; //�Ƿ��ѱ����ʹ�

	public AdjVertex( Vertex vertex ) {
		this.vertex = vertex;
	}
}

/**
 * ͼ��
 * @date 2016/3/26
 */
class Graph
{
	public HashMap< Vertex, AdjVertex[] > adjacencys; //�ڽӱ�
	
	public Graph() {
		adjacencys = new HashMap< Vertex, AdjVertex[] >();
	}

	/**
	 * ��ȡָ�����ֵĶ���
	 *		����ö��㲻���ڣ����׳��쳣�����ñ��������ң�ʱ�临�Ӷ�ΪO(N)��
	 * @param name ���������
	 * @throws VertexNotFoundException ���ͼ��δ�ҵ�ָ������
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
	 * ����ͼ�еĵ�һ�����㣬����������������
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
 * �쳣�ࣨ�ö��㲻���ڣ�
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
 * �쳣�ࣨ��ͼ������ŷ����·��
 * ��ʱ��ͼ�����п��ܴ���ŷ��·��
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
 * ������ͨ�������ж�����Ⱦ�Ϊż����ͼ��ŷ����·
 * @date 2016/4/3
 */
public class EulerCircuit 
{
	private LinkedList<Vertex> subPath;

	public EulerCircuit() {
		subPath = new LinkedList<Vertex>();
	}

	/**
	 * ����ŷ����·(��ָ���Ķ��㿪ʼ)
	 * @param mGraph ͼ
	 * @param name ��ʼ���������
	 * @return ���������ʾ��ŷ����·
	 * @throws VertexNotFoundException ���ָ���Ķ��㲻����
	 * @throws EulerCircuitNotFoundException ���ͼ�в�����ŷ����·
	 * @date 2016/4/3
	 */
	public LinkedList<Vertex> findEulerCircuit( Graph mGraph, String name ) throws VertexNotFoundException,EulerCircuitNotFoundException {
		LinkedList<Vertex> eulerCircuit = new LinkedList<Vertex>();

		Set<Vertex> vertexSet = mGraph.adjacencys.keySet();
		Vertex theVertex = null;

		for ( Vertex vertex : vertexSet )  //ͼ�������Ϣ�ĳ�ʼ��
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
		if ( theVertex == null ) //����Ҳ���ָ������ʼ���㣬���׳�VertexNotFoundException�쳣
		{
			throw new VertexNotFoundException();
		}

		eulerCircuit.offer( theVertex ); //��ʼ���ܵ�ŷ����·��ֻ������ʼ����
		int theIndex = 0;
		int tempIndex = theIndex;
		Vertex tempVertex = null;

		while ( theVertex != null ) //�ֽ׶ν���ŷ����·����
		{
			dfs( mGraph, theVertex); //���е�ǰ�׶ε�ŷ����·����
			if ( subPath.getFirst() != subPath.getLast() ) //�������׶εõ���·������β��ӣ���˵�����ͼ������ŷ����·
			{
				throw new EulerCircuitNotFoundException();
			}
			
			eulerCircuit.remove( theIndex );
			theVertex = null;
			while ( subPath.size() > 0 ) //����ǰ�׶β��ҵõ���ŷ��·��ƴ�ӵ��ܻ�·��
			{
				tempVertex = subPath.poll();
				eulerCircuit.add( theIndex, tempVertex );
				if ( theVertex == null ) //ѡȡ��һ�׶εı�����ʼ����
				{
					AdjVertex[] vertexs = mGraph.adjacencys.get( tempVertex );
					while ( tempVertex.currentDegree < tempVertex.degree ) //���ڽӱ��в���δ�����ʹ��ı�
					{
						if ( !vertexs[ tempVertex.currentDegree ].visited ) //����ܹ��ҵ����ͰѸö�����Ϊ��һ�׶α�������ʼ����
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
			theIndex = tempIndex; //��һ�׶���ʼ������±�
		}

		return eulerCircuit;
	}
	
	/**
	 * ͼ��������������ĸĽ��棬�м亯��
	 *	    �ݹ鷽�����������������������
	 * @param mGraph ͼ
	 * @param vertex ���������
	 * @date 2016/4/3
	 */
	private void dfs( Graph mGraph, Vertex theVertex ) {	
		
		//do something
		subPath.offer( theVertex );
		//do something

		AdjVertex[] vertexs = mGraph.adjacencys.get( theVertex );

		while ( theVertex.currentDegree < theVertex.degree ) //�ӵ�ǰ������ڽӱ��в���δ�����ʹ��ı�
		{
			if ( !vertexs[ theVertex.currentDegree ].visited )
			{
				break;
			}	
			else {
				theVertex.currentDegree++;
			}
		}
		
		if ( theVertex.currentDegree >= theVertex.degree ) //����ö���û��δ�����ʹ��ıߣ��ͷ���
		{
			return;
		}
		else { //�����ǰ������ڽӱ��л���δ�����ʹ��ı�
			vertexs[ theVertex.currentDegree ].visited = true; //�������߱�Ϊ�ѷ���

			AdjVertex[] inVertexs = mGraph.adjacencys.get( vertexs[ theVertex.currentDegree ].vertex ); //������ߡ��ѷ��ʡ�����Ϣͬ������������һ������ڽӱ���
			for ( int i=0; i<inVertexs.length; i++ )													//������ñ����ķ�ʽ���ҡ���һ���㡱���д��Ż�
			{
				if ( inVertexs[ i ].vertex == theVertex )
				{
					inVertexs[ i ].visited = true;
					break;
				}
			}
			theVertex.currentDegree++; //���µ�ǰ�������һɨ�趥��ָ��
			dfs( mGraph, vertexs[ theVertex.currentDegree-1 ].vertex ); //�ݹ����		
		}
	}

	/**
	 * ����һ��ͼ�����ڲ���
	 * @return ͼ
	 * @date 2016/4/3
	 */
	public Graph generateGraph() {

		//ͼ��(����ͼ)
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
	 * ��ͼ��ӡ�ڿ���̨�ϣ����ڲ���
	 * @param mGraph ͼ
	 * @date 2016/3/26
	 */
	public void printGraph( Graph mGraph ) {

		Set<Vertex> vertexSet = mGraph.adjacencys.keySet();
		AdjVertex[] vertexs;


		System.out.print("\nͼ���ڽӱ�\n");
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
	 * ��·����ӡ������̨�ϣ����ڲ���
	 * @param path ·������
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

		System.out.println( "\nŷ����·���ҽ����" );
		LinkedList<Vertex> path = mEulerCircuit.findEulerCircuit( mGraph, "v5" );
		mEulerCircuit.printPath( path );
	}
}
