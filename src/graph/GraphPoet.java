package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edge.Edge;
import vertex.Vertex;

public class GraphPoet extends ConcreteGraph{
	/*
	 * Abstraction function:
	 * ��ʾһ��ͼ
	 * Representation invariant:
	 * ������protected final�ģ���ʾ�ǿɼ̳в��Ҳ����ٱ�������
	 * Safety from rep exposure:
	 *  ����������mutable�ģ����Բ�ȡ����ʽ��̹淶��ÿ�ζ��½�һ����������
	 */
	
	public void checkRep() {
		List<Double> li = new ArrayList<>();
		if(!edges.isEmpty()) {
			for(Edge ed:edges) {
				li.add(ed.getweight());
			}
		}
		for(Double d:li) {
			assert d>0;
		}
	}
	
	/**
	 * ȷ��ȨֵС��n�ı߲��ܳ�����ͼ��
	 */
	public boolean removeedgen(int n) {
		assert edges.size()>0;
		List<Edge> li = new ArrayList<>();
		for(Edge ed : edges) {
			if(ed.getweight()<n) {
				li.add(ed);
			}
		}
		if(!li.isEmpty()) {
			for(Edge e:li) {
				edges.remove(e);
			}
		}
//		checkRep();
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
//		checkRep();
		return true;
	}
	
	/**
	 * ��ȡͼ�е�ĳ�������������Լ�����Ӧ��ÿ���ߵ�Ȩֵ
	 * @return Map<Vertex, List<Double>>
	 */
	@Override
	public Map<Vertex, List<Double>> sources(Vertex target) {
		Map<Vertex, List<Double>> sourcemap = new HashMap<>();
//		List<Double> list = new ArrayList<>();
		for(Edge eachedge : this.edges) {
			for(Vertex ve:eachedge.targetVertices()) {
				if(ve.equals(target)) {
					if(eachedge.targetVertices().size()!=2) {
					for(Vertex key : eachedge.sourceVertices()) { //�������Ի�������ͼ
						List<Double> list = new ArrayList<>();
						list.add(eachedge.getweight());
						sourcemap.put(key, list);
					}
					}
				}
			}
		}
//		checkRep();
		return sourcemap;
	}
	
	/**
	 * ��ȡͼ�е�ĳ��������е�Ŀ����Լ�����Ӧ��ÿ���ߵ�Ȩֵ
	 * @return Map<Vertex, List<Double>> targets
	 */
	@Override
	public Map<Vertex, List<Double>> targets(Vertex source) {
		Map<Vertex, List<Double>> targetmap = new HashMap<>();
//		List<Double> list = new ArrayList<>();
		for(Edge eachedge : this.edges) {
			for(Vertex ve:eachedge.sourceVertices()) {
				if(ve.equals(source)) {
					if(eachedge.sourceVertices().size()!=2) {
					for(Vertex key : eachedge.targetVertices()) { //�������Ի�������ͼ
						List<Double> list = new ArrayList<>();
						list.add(eachedge.getweight());
						targetmap.put(key, list);
					}
					}
				}
			}
		}
//		checkRep();
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
