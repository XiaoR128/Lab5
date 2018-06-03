package edge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public class MovieActorRelation extends Edge{
	/*
	 * Abstraction function:
	 * ��ʾһ���ߣ���������Ȩֵ�������������ĵ�
	 * Representation invariant:
	 * ������protected�ģ���ʾ�ǿɼ̳е�
	 * Safety from rep exposure:
	 *  ����������mutable�ģ����Բ�ȡ����ʽ��̹淶��ÿ�ζ��½�һ����������
	 */
	public MovieActorRelation(String label, double weight) {
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
	public boolean addVertices(List<Vertex> vertice) {
		assert vertice.size() == 2;
		for(int i=0;i<vertice.size();i++) {
			this.vertices.add(vertice.get(i));
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
		Set<Vertex> source = new HashSet<>();
		for(int i=0;i<this.vertices.size();i++) {
			source.add(this.vertices.get(i));
		}
		checkRep();
		return source;
	}
	
	/**
	 * ��ȡ�ߵ��յ�ļ���
	 * @param �յ�ļ���Set<Vertex>
	 */
	@Override
	public Set<Vertex> targetVertices(){
		Set<Vertex> target = new HashSet<>();
		for(int i=0;i<this.vertices.size();i++) {
			target.add(this.vertices.get(i));
		}
		checkRep();
		return target;
	}

	/**
	 * tostring����
	 */
	@Override
	public String toString() {
		String str = "label:"+this.label+"|"+this.vertices.get(0).toString()
				+"-------" + this.vertices.get(1).toString()+"|weight:"+weight;
		return str;
	}
}
