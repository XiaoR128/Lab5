package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edge.Edge;
import vertex.Vertex;

public class NetworkTopology extends ConcreteGraph{
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
			for(Edge ed:edges) {
				assert !ed.vertices().get(0).getLabel().equals(ed.vertices().get(1).getLabel());
			}
		}
	}
	
	
	/**
	 * ��ȡͼ�е�ĳ�������������Լ�����Ӧ��ÿ���ߵ�Ȩֵ
	 * @return Map<Vertex, List<Double>>
	 */
	@Override
	public Map<Vertex, List<Double>> sources(Vertex target){
		Map<Vertex, List<Double>> sourcemap = new HashMap<>();
//		List<Double> listdou = new ArrayList<>();
		List<Edge> listedge = new ArrayList<>();
		List<Vertex> setvertex = new ArrayList<>();
		if(edges.isEmpty()) {
			return null;
		}
		for(Edge eachedge : edges) {
			if (eachedge.targetVertices().size() != 0) {
				for (Vertex ve : eachedge.targetVertices()) {
                      if(ve.equals(target)) {
                    	  listedge.add(eachedge);
                      }
				}
			}
		}
		if(listedge.isEmpty()) {
			return null;
		}
//		int flag = 0;
		for(Edge eachedge : listedge) {
			if (eachedge.sourceVertices().size() != 0) {
				for (Vertex vertex : eachedge.sourceVertices()) {
					int flag = 0;
					if (!vertex.equals(target)) {
						if(setvertex.isEmpty()) {
							setvertex.add(vertex);
						}else {
							for(Vertex ve:setvertex) {
								if(ve.equals(vertex)) {
									flag=1;
								}
							}
							if(flag==0) {
								setvertex.add(vertex);
							}
						}
					}
				}
			}
		}
		for(int i=0;i<setvertex.size();i++) {
			Vertex onevertex = setvertex.get(i);
			List<Double> listdou = new ArrayList<>();
			for(Edge eachedge : listedge) {
				for(Vertex ve:eachedge.sourceVertices()) {
					if (!ve.equals(target)) {
						if (ve.equals(onevertex)) {
							listdou.add(eachedge.getweight());
						}
					}
				}
			}
			sourcemap.put(onevertex, listdou);
		}
		checkRep();
		return sourcemap;
	}
	
	/**
	 * ��ȡͼ�е�ĳ��������е�Ŀ����Լ�����Ӧ��ÿ���ߵ�Ȩֵ
	 * @return Map<Vertex, List<Double>> targets
	 */
	@Override
	public Map<Vertex, List<Double>> targets(Vertex source){
		Map<Vertex, List<Double>> targetmap = new HashMap<>();
//		List<Double> listdou = new ArrayList<>();
		List<Edge> listedge = new ArrayList<>();
		List<Vertex> setvertex = new ArrayList<>();
		if(edges.isEmpty()) {
			return null;
		}
		for(Edge eachedge : edges) {
			if (eachedge.sourceVertices().size() != 0) {
				for (Vertex ve : eachedge.sourceVertices()) {
					if (ve.equals(source)) {
						listedge.add(eachedge);
					}
				}
			}
		}
		if(listedge.isEmpty()) {
			return null;
		}
//		int flag = 0;
		for(Edge eachedge : listedge) {
			if (eachedge.targetVertices().size() != 0) {
				for (Vertex vertex : eachedge.targetVertices()) {
					int flag = 0;
					if (!vertex.equals(source)) {
						if(setvertex.isEmpty()) {
							setvertex.add(vertex);
						}else {
							for(Vertex ve:setvertex) {
								if(ve.equals(vertex)) {
									flag=1;
								}
							}
							if(flag==0) {
								setvertex.add(vertex);
							}
						}
					}
				}
			}
		}
		for(int i=0;i<setvertex.size();i++) {
			Vertex onevertex = setvertex.get(i);
			List<Double> listdou = new ArrayList<>();
			for(Edge eachedge : listedge) {
				for(Vertex ve:eachedge.targetVertices()) {
					if(ve.equals(onevertex)) {
						listdou.add(eachedge.getweight());
					}
				}
			}
			targetmap.put(onevertex, listdou);
		}
		checkRep();
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
