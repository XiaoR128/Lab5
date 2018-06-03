package vertex;

import java.util.regex.Pattern;

public abstract class Vertex {
	/*
	 * Abstraction function:
	 * ��ʾһ����
	 * Representation invariant:
	 * ������protected�ģ���ʾ�ǿɼ̳е�
	 * Safety from rep exposure:
	 *  ����������mutable�ģ����Բ�ȡ����ʽ��̹淶��ÿ�ζ��½�һ����������
	 */
	protected String label;
	public Vertex(String label) {
		Pattern p = Pattern.compile("\\w+");
		assert p.matcher(label).matches();
		this.label = label;
	}
	
	/**
	 * ��ӵ����Ϣ
	 * @param args
	 */
	public abstract void fillVertexInfo (String[] args);
	
	/**
	 * ��ȡ�������
	 * @return label
	 */
	public String getLabel() {
		String la;
		la = this.label;
		return la;
	}
	
	/**
	 * �ı�������
	 * @param label
	 * @return true or false
	 */
	public boolean changelabel(String label) {
		Pattern p = Pattern.compile("\\w+");
		assert p.matcher(label).matches();
		this.label = label;
		return true;
	}
	
	/**
	 * �������Ϣת�����ַ���
	 */
	public abstract String toString();
	
	/**
	 * �ж��������Ƿ����
	 * @param v
	 * @return
	 */
	public abstract boolean equals(Vertex v);
	
	/**
	 * ��ȡ��Ĺ�ϣֵ
	 */
	public abstract int hashCode();
}
