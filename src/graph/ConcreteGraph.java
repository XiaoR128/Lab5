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
	 * 表示一张图
	 * Representation invariant:
	 * 属性是protected final的，表示是可继承并且不能再被更改了
	 * Safety from rep exposure:
	 *  由于属性是mutable的，所以采取防御式编程规范，每次都新建一个变量返回
	 */
	protected final List<Vertex> vertices = new ArrayList<>();
	protected final List<Edge> edges = new ArrayList<>();
	
	public ConcreteGraph() {
	}
	
	/**
	 * 向图中添加结点
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
	 * 删除图中的结点
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
	 * 获取图中点的集合
	 * @return List<Vertex>点的集合
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
	 * 向图中添加边
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
	 * 删除图中的边
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
	 * 获取图中的所有的边的集合
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
	 * 获取图中的某个点的所有起点以及所对应的每条边的权值
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
					if(eachedge.targetVertices().size()==2) {  //对于无向图而言
						for(Vertex key : eachedge.sourceVertices()) {
							if(!key.equals(target)) {
								List<Double> list = new ArrayList<>();
								list.add(eachedge.getweight());
								sourcemap.put(key, list);
							}
						}
					}else {
						for(Vertex key : eachedge.sourceVertices()) { //对于有自环和有向图
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
	 * 获取图中的某个点的所有的目标点以及所对应的每条边的权值
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
					if(eachedge.sourceVertices().size()==2) {  //对于无向图而言
						for(Vertex key : eachedge.targetVertices()) {
							if(!key.equals(source)) {
								List<Double> list = new ArrayList<>();
								list.add(eachedge.getweight());
								targetmap.put(key, list);
							}
						}
					}else {
						for(Vertex key : eachedge.targetVertices()) { //对于有自环和有向图
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
