package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edge.Edge;
import edge.SameMovieHyperEdge;
import vertex.Vertex;

public class MovieGraph extends ConcreteGraph{
	/*
	 * Abstraction function:
	 * ��ʾһ��ͼ
	 * Representation invariant:
	 * ������protected final�ģ���ʾ�ǿɼ̳в��Ҳ����ٱ�������
	 * Safety from rep exposure:
	 *  ����������mutable�ģ����Բ�ȡ����ʽ��̹淶��ÿ�ζ��½�һ����������
	 */
	
	public void checkRep() {
		if(!edges.isEmpty()) {
			for(Edge edge:edges) {
				if(edge instanceof SameMovieHyperEdge) {
					assert edge.vertices().size()>=2;
				}
			}
		}
	}
	
	/**
	 * ��ȡͼ�е�ĳ�������������Լ�����Ӧ��ÿ���ߵ�Ȩֵ
	 * @return Map<Vertex, List<Double>>
	 */
	@Override
	public Map<Vertex, List<Double>> sources(Vertex target) {
		Map<Vertex, List<Double>> sourcemap = new HashMap<>();
		List<Double> list = new ArrayList<>();
		for(Edge eachedge : this.edges) {
			if(eachedge instanceof SameMovieHyperEdge) {
				continue;
			}
			for(Vertex v:eachedge.vertices()) {
				if(v.equals(target)) {
					if(eachedge.sourceVertices().size()>0) {
						for(Vertex key : eachedge.sourceVertices()) {
							if(!key.equals(target)) {
								list.add(eachedge.getweight());
								sourcemap.put(key, list);
							}
						}
					}
				}
			}
		}
		checkRep();
		return sourcemap;
	}
	
	/**
	 * ��ȡͼ�е�ĳ��������е�Ŀ����Լ�����Ӧ��ÿ���ߵ�Ȩֵ
	 * @return targets
	 */
	@Override
	public Map<Vertex, List<Double>> targets(Vertex source) {
		Map<Vertex, List<Double>> targetmap = new HashMap<>();
		List<Double> list = new ArrayList<>();
		for(Edge eachedge : this.edges) {
			if(eachedge instanceof SameMovieHyperEdge) {
				continue;
			}
			for(Vertex v:eachedge.vertices()) {
				if(v.equals(source)) {
					if(eachedge.targetVertices().size()>0) {
						for (Vertex key : eachedge.targetVertices()) {
							if (!key.equals(source)) {
								list.add(eachedge.getweight());
								targetmap.put(key, list);
							}
						}
					}
				}
			}
		}
		checkRep();
		return targetmap;
	}
	
	/**
	 * ��ͼ����ӱ�
	 */
	@Override
	public boolean addEdge(Edge edge) {
		for(Edge edg : edges) { 
			if (edg.equals(edge)) {
				return false;
			}
		}
		if(edge instanceof SameMovieHyperEdge) {
			if(edge.vertices().size()<=1) {
				return false;
			}
		}
		edges.add(edge);
		checkRep();
		return true;
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
