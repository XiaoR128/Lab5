package vertex;

import java.util.Objects;

public class Word extends Vertex{
	/*
	 * Abstraction function:
	 * ��ʾһ����
	 * Representation invariant:
	 * ���Զ���private�ģ��޷������������
	 * Safety from rep exposure:
	 *  ����������mutable�ģ����Բ�ȡ����ʽ��̹淶��ÿ�ζ��½�һ����������
	 */
	public Word(String label) {
		super(label);
	}
	
	/**
	 * ��ӵ����Ϣ
	 * @param args
	 */
	@Override
	public void fillVertexInfo (String[] args) {
		
	}
	
	/**
	 * �������Ϣת�����ַ���
	 */
	@Override
	public String toString() {
		String newstr = "label:"+this.label;
		return newstr;
	}
	
	/**
	 * �ж��������Ƿ����
	 * @param v
	 * @return
	 */
	@Override
	public boolean equals(Vertex v) {
		if(this.label.equals(v.label)) {
			return true;
		}
		return false;
	}
	
	/**
	 * ��ȡ��Ĺ�ϣֵ
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.label); 
	}
}
