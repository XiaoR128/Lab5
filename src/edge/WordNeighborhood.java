package edge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public class WordNeighborhood extends Edge{
	/*
	 * Abstraction function:
	 * ��ʾһ���ߣ���������Ȩֵ�������������ĵ�
	 * Representation invariant:
	 * ������protected�ģ���ʾ�ǿɼ̳е�
	 * Safety from rep exposure:
	 *  ����������mutable�ģ����Բ�ȡ����ʽ��̹淶��ÿ�ζ��½�һ����������
	 */
	public WordNeighborhood(String label, double weight) {
		super(label, weight);
	}

	public void checkRep() {
		assert weight>0;
	}
	/**
	 * �������ӵ�ļ���
	 * @param ��ļ���
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
	 * ��ȡ�ߵ����ļ���
	 * @param ���ļ���Set<Vertex>
	 */
	@Override
	public Set<Vertex> sourceVertices(){
		Set<Vertex> setsource = new HashSet<>();
		setsource.add(this.vertices.get(0));
		checkRep();
		return setsource;
	}
	
	/**
	 * ��ȡ�ߵ��յ�ļ���
	 * @param �յ�ļ���Set<Vertex>
	 */
	@Override
	public Set<Vertex> targetVertices(){
		Set<Vertex> settarget = new HashSet<>();
		settarget.add(this.vertices.get(1));
		checkRep();
		return settarget;
	}

	/**
	 * tostring����
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
