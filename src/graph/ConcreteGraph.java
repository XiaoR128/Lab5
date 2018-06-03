package graph;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import edge.Edge;
import edge.SameMovieHyperEdge;
import vertex.Vertex;
public class ConcreteGraph implements Graph<Vertex,Edge>{
	/*
	 * Abstraction function:
	 * ��ʾһ��ͼ
	 * Representation invariant:
	 * ������protected final�ģ���ʾ�ǿɼ̳в��Ҳ����ٱ�������
	 * Safety from rep exposure:
	 *  ����������mutable�ģ����Բ�ȡ����ʽ��̹淶��ÿ�ζ��½�һ����������
	 */
	protected final List<Vertex> vertices = new ArrayList<>();
	protected final List<Edge> edges = new ArrayList<>();
	
	public ConcreteGraph() {
	}
	
	/**
	 * ��ͼ����ӽ��
	 */
	@Override
	public boolean addVertex(Vertex vertex) {
		for(Vertex ve:vertices) {
			if(ve.equals(vertex)) {
				return false;
			}
		}
		vertices.add(vertex);
		return true;
	}
	
	/**
	 * ɾ��ͼ�еĽ��
	 */
	@Override
	public boolean removeVertex(Vertex vertex) {
		assert vertices.size()>0;
		int flag = 1;
		for(Vertex ve:vertices) {
			if(ve.equals(vertex)) {
				flag=0;
			}
		}
		if(flag==1) {
			return false;
		}
		List<Edge> li = new ArrayList<>();
		for(int i=0;i<edges.size();i++) {
			Edge eachedge=edges.get(i);
			for(int j=0;j<eachedge.vertices().size();j++) {
				Vertex c = eachedge.vertices().get(j);
				if(c.equals(vertex)) {
					li.add(eachedge);
				}
			}
		}
		if(!li.isEmpty()) {
			for(Edge e:li) {
				edges.remove(e);
			}
		}
		for(int i=0;i<vertices.size();i++) {
			Vertex ve = vertices.get(i);
			if(ve.equals(vertex)) {
				vertices.remove(ve);
				break;
			}
		}
		return true;
	}
	
	/**
	 * ��ȡͼ�е�ļ���
	 * @return List<Vertex>��ļ���
	 */
	@Override
	public List<Vertex> vertices(){
//		Set<Vertex> vertexs = new HashSet<>();
		
		List<Vertex> vertexs = new ArrayList<>();
		for(Vertex v:vertices) {
			vertexs.add(v);
		}
		return vertexs;
	}
	
	/**
	 * ��ͼ����ӱ�
	 */
	@Override
	public boolean addEdge(Edge edge) {
//		for(Edge edg : edges) { 
//			if (edg.equals(edge)) {
//				return false;
//			}
//		}
		edges.add(edge);
		return true;
	}
	
	/**
	 * ɾ��ͼ�еı�
	 */
	@Override
	public boolean removeEdge(Edge edge) {
//		assert edges.size()>0;
		for(Edge edg : edges) { 
			if (edg.equals(edge)) {
				edges.remove(edg);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ��ȡͼ�е����еıߵļ���
	 */
	@Override
	public List<Edge> edges(){
		List<Edge> edge = new ArrayList<>();
//		for(Edge eachedge : edges) {
//			edge.add(eachedge);
//		}
		edge = edges;
		return edge;
	}

	/**
	 * ��ȡͼ�е�ĳ�������������Լ�����Ӧ��ÿ���ߵ�Ȩֵ
	 * @return Map<Vertex, List<Double>>
	 */
	@Override
	public Map<Vertex, List<Double>> sources(Vertex target) {
		Map<Vertex, List<Double>> sourcemap = new HashMap<>();
		for(Edge eachedge : this.edges) {
			if(eachedge instanceof SameMovieHyperEdge) {
				continue;
			}
			for(Vertex ve:eachedge.targetVertices()) {
				if(ve.equals(target)) {
					if(eachedge.targetVertices().size()==2) {  //��������ͼ����
						for(Vertex key : eachedge.sourceVertices()) {
							if(!key.equals(target)) {
								List<Double> list = new ArrayList<>();
								list.add(eachedge.getweight());
								sourcemap.put(key, list);
							}
						}
					}else {
						for(Vertex key : eachedge.sourceVertices()) { //�������Ի�������ͼ
							List<Double> list = new ArrayList<>();
							list.add(eachedge.getweight());
							sourcemap.put(key, list);
						}
					}
				}
			}
		}
		return sourcemap;
	}
	
	/**
	 * ��ȡͼ�е�ĳ��������е�Ŀ����Լ�����Ӧ��ÿ���ߵ�Ȩֵ
	 * @return Map<Vertex, List<Double>> targets
	 */
	@Override
	public Map<Vertex, List<Double>> targets(Vertex source) {
		Map<Vertex, List<Double>> targetmap = new HashMap<>();
		for(Edge eachedge : this.edges) {
			if(eachedge instanceof SameMovieHyperEdge) {
				continue;
			}
			for(Vertex ve:eachedge.sourceVertices()) {
				if(ve.equals(source)) {
					if(eachedge.sourceVertices().size()==2) {  //��������ͼ����
						for(Vertex key : eachedge.targetVertices()) {
							if(!key.equals(source)) {
								List<Double> list = new ArrayList<>();
								list.add(eachedge.getweight());
								targetmap.put(key, list);
							}
						}
					}else {
						for(Vertex key : eachedge.targetVertices()) { //�������Ի�������ͼ
							List<Double> list = new ArrayList<>();
							list.add(eachedge.getweight());
							targetmap.put(key, list);
						}
					}
				}
			}
		}
		return targetmap;
	}
	
	/**
	 * ��ͼ����Ϣת��Ϊ�ַ���
	 */
	@Override
	public String toString() {
		String str = new String();
		for(Vertex ve:vertices) {
			str = str+ve.toString()+"\n";
		}
		for(Edge edge:edges) {
			str = str+edge.toString()+"\n";
		}
		return str;
	}
}
