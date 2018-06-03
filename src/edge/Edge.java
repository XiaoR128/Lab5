package edge;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import vertex.Vertex;
/*
 * 这是一个边的抽象表示
 */
public abstract class Edge {
	protected String label;
	protected double weight;
	protected List<Vertex> vertices = new ArrayList<>();

	/*
	 * Abstraction function: 表示一条边，它包括了权值和这条边所含的点 Representation invariant:
	 * 属性是protected的，表示是可继承的 Safety from rep exposure:
	 * 由于属性是mutable的，所以采取防御式编程规范，每次都新建一个变量返回
	 */
	public Edge(String label, double weight) {
		Pattern p = Pattern.compile("\\w+");
		assert p.matcher(label).matches();
		this.label = label;
		this.weight = weight;
	}

	public abstract boolean addVertices(List<Vertex> vertices);

	/**
	 * 设置边的名称
	 * 
	 * @param 边的名称
	 * @return 边的名称
	 */
	public boolean setlabel(String la) {
		Pattern p = Pattern.compile("\\w+");
		assert p.matcher(la).matches();
		this.label = la;
		return true;
	}

	/**
	 * 获取边的名称
	 * 
	 * @return 边的名称
	 */
	public String getlabel() {
		String la = this.label;
		return la;
	}

	/**
	 * 转换边的方向
	 */
	public boolean changefor() {
		List<Vertex> li = new ArrayList<>();
		li.add(vertices.get(1));
		li.add(vertices.get(0));
		vertices = li;
		return true;
	}

	/**
	 * 判断边中是否包含点v
	 * 
	 * @param 点v
	 * @return true 或则 false
	 */
	public boolean containVertex(Vertex v) {
		for (Vertex ver : vertices) {
			if (ver.equals(v)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取点的集合
	 * 
	 * @return 点的集合List<Vertex>
	 */
	public List<Vertex> vertices() {
		List<Vertex> vertex = new ArrayList<>();
//		for (Vertex v : vertices) {
//			vertex.add(v);
//		}
		vertex = vertices;
		return vertex;
	}

	/**
	 * 设置边的权值
	 * 
	 * @param wei
	 */
	public void setweight(double wei) {
		this.weight = wei;
	}

	/**
	 * 获取边的权值
	 * 
	 * @return weight
	 */
	public double getweight() {
		double newweight = this.weight;
		return newweight;
	}

	public abstract Set<Vertex> sourceVertices();

	public abstract Set<Vertex> targetVertices();

	public abstract String toString();

	/**
	 * 判断两条边是否相等
	 * 
	 * @param e
	 * @return true or false
	 */
	public boolean equals(Edge e) {
		if (this.label.equals(e.label) && this.weight == e.weight) {
			return true;
		}
		return false;
	}

	/**
	 * 获取边的哈希值
	 */
	public int hashCode() {
		return Objects.hash(this.label, weight);
	}
}
