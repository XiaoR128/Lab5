package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edge.Edge;
import vertex.Vertex;

public class SocialNetwork extends ConcreteGraph{
	/*
	 * Abstraction function:
	 * 表示一张图
	 * Representation invariant:
	 * 属性是protected final的，表示是可继承并且不能再被更改了
	 * Safety from rep exposure:
	 *  由于属性是mutable的，所以采取防御式编程规范，每次都新建一个变量返回
	 */
	
	public void checkRep() {
		double sum=0;
		if(!edges.isEmpty()) {
			for(Edge e : edges) {
				sum+=e.getweight();
			}
		}
		assert 1-sum<1e-6;
	}
	
	/**
	 * 删除图中的结点
	 */
	@Override
	public boolean removeVertex(Vertex vertex) {
		assert vertices.size()>0;
		double wa = 0;
		int flag = 1;
		for(Vertex ve:vertices) {
			if(ve.equals(vertex)) {
				flag = 0;
			}
		}
		if(flag==1) {
			return false;
		}
		List<Edge> li = new ArrayList<>();
		for(Edge eachedge : edges) {
			for(Vertex v:eachedge.vertices()) {
				if(v.equals(vertex)) {
					wa+= eachedge.getweight();
//					edges.remove(eachedge);
					li.add(eachedge);
				}
			}
		}
		if(!li.isEmpty()) {
			for(Edge e:li) {
				edges.remove(e);
			}
		}
		for(Edge eachedge : edges) {
			double wb = eachedge.getweight();
			eachedge.setweight(wb/(1-wa));
		}
		vertices.remove(vertex);
//		checkRep();
		return true;
	}
	
	/**
	 * 向图中添加边
	 */
	@Override
	public boolean addEdge(Edge edge) {
		if(edges.isEmpty()) {
			edges.add(edge);
			return true;
		}else {
			for(Edge ed:edges) {
				if(ed.equals(edge)) {
					return false;
				}
			}
			double sum=0;
			for(Edge ed:edges) {
				sum=sum+ed.getweight();
			}
			if(1-sum<1e-6) {
				double wa = edge.getweight();
				for(Edge eachedge : edges) {
					double wb = eachedge.getweight();
					eachedge.setweight(wb*(1-wa));
				}
				edges.add(edge);
			}else {
				edges.add(edge);
			}
		}
//		checkRep();
		return true;
	}
	
	
	/**
	 * 删除图中的边
	 */
	@Override
	public boolean removeEdge(Edge edge) {
//		assert edges.size()>0;
		int flag = 1;
		for(Edge ed:edges) {
			if(ed.equals(edge)) {
				flag = 0;
			}
		}
		if(flag==1) {
			return false;
		}
		Edge p = null;
		for(Edge ed : edges) {
			if(ed.equals(edge)) {
				p=ed;
			}
		}
		edges.remove(p);
		double wa = edge.getweight();
		for(Edge eachedge : edges) {
			double wb = eachedge.getweight();
			eachedge.setweight(wb/(1-wa));
		}
//		checkRep();
		return true;
	}
	
	
	/**
	 * 获取图中的某个点的所有起点以及所对应的每条边的权值
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
	 * 获取图中的某个点的所有的目标点以及所对应的每条边的权值
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
	 * 将图中信息转换为字符串
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
