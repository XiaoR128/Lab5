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
	 * 表示一张图
	 * Representation invariant:
	 * 属性是protected final的，表示是可继承并且不能再被更改了
	 * Safety from rep exposure:
	 *  由于属性是mutable的，所以采取防御式编程规范，每次都新建一个变量返回
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
	 * 获取图中的某个点的所有起点以及所对应的每条边的权值
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
	 * 获取图中的某个点的所有的目标点以及所对应的每条边的权值
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
	 * 向图中添加边
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
