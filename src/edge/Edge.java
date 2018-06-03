package edge;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import vertex.Vertex;
/*
 * ����һ���ߵĳ����ʾ
 */
public abstract class Edge {
	protected String label;
	protected double weight;
	protected List<Vertex> vertices = new ArrayList<>();

	/*
	 * Abstraction function: ��ʾһ���ߣ���������Ȩֵ�������������ĵ� Representation invariant:
	 * ������protected�ģ���ʾ�ǿɼ̳е� Safety from rep exposure:
	 * ����������mutable�ģ����Բ�ȡ����ʽ��̹淶��ÿ�ζ��½�һ����������
	 */
	public Edge(String label, double weight) {
		Pattern p = Pattern.compile("\\w+");
		assert p.matcher(label).matches();
		this.label = label;
		this.weight = weight;
	}

	public abstract boolean addVertices(List<Vertex> vertices);

	/**
	 * ���ñߵ�����
	 * 
	 * @param �ߵ�����
	 * @return �ߵ�����
	 */
	public boolean setlabel(String la) {
		Pattern p = Pattern.compile("\\w+");
		assert p.matcher(la).matches();
		this.label = la;
		return true;
	}

	/**
	 * ��ȡ�ߵ�����
	 * 
	 * @return �ߵ�����
	 */
	public String getlabel() {
		String la = this.label;
		return la;
	}

	/**
	 * ת���ߵķ���
	 */
	public boolean changefor() {
		List<Vertex> li = new ArrayList<>();
		li.add(vertices.get(1));
		li.add(vertices.get(0));
		vertices = li;
		return true;
	}

	/**
	 * �жϱ����Ƿ������v
	 * 
	 * @param ��v
	 * @return true ���� false
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
	 * ��ȡ��ļ���
	 * 
	 * @return ��ļ���List<Vertex>
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
	 * ���ñߵ�Ȩֵ
	 * 
	 * @param wei
	 */
	public void setweight(double wei) {
		this.weight = wei;
	}

	/**
	 * ��ȡ�ߵ�Ȩֵ
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
	 * �ж��������Ƿ����
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
	 * ��ȡ�ߵĹ�ϣֵ
	 */
	public int hashCode() {
		return Objects.hash(this.label, weight);
	}
}
