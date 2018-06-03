package edge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public class WordNeighborhood extends Edge{
	/*
	 * Abstraction function:
	 * 表示一条边，它包括了权值和这条边所含的点
	 * Representation invariant:
	 * 属性是protected的，表示是可继承的
	 * Safety from rep exposure:
	 *  由于属性是mutable的，所以采取防御式编程规范，每次都新建一个变量返回
	 */
	public WordNeighborhood(String label, double weight) {
		super(label, weight);
	}

	public void checkRep() {
		assert weight>0;
	}
	/**
	 * 向边中添加点的集合
	 * @param 点的集合
	 */
	@Override
	public boolean addVertices(List<Vertex> vertices) {
		assert vertices.size()==2;
		for(int i=0;i<vertices.size();i++) {
			this.vertices.add(vertices.get(i));
		}
		checkRep();
		return true;
	}
	
	/**
	 * 获取边的起点的集合
	 * @param 起点的集合Set<Vertex>
	 */
	@Override
	public Set<Vertex> sourceVertices(){
		Set<Vertex> setsource = new HashSet<>();
		setsource.add(this.vertices.get(0));
		checkRep();
		return setsource;
	}
	
	/**
	 * 获取边的终点的集合
	 * @param 终点的集合Set<Vertex>
	 */
	@Override
	public Set<Vertex> targetVertices(){
		Set<Vertex> settarget = new HashSet<>();
		settarget.add(this.vertices.get(1));
		checkRep();
		return settarget;
	}

	/**
	 * tostring方法
	 */
	@Override
	public String toString() {
		String str = "label:"+this.label+"|"+this.vertices.get(0).toString()
				+"---->" + this.vertices.get(1).toString()+"|weight:"+this.weight;
		return str;
	}
	
	@Override
	public boolean equals(Edge ed) {
		if(this.label.equals(ed.label) && this.weight==ed.weight) {
			return true;
		}
		return false;
	}
}
