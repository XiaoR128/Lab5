package vertex;

import java.util.Objects;

public class Router extends Vertex{
	/*
	 * Abstraction function:
	 * ��ʾһ����
	 * Representation invariant:
	 * ���Զ���private�ģ��޷������������
	 * Safety from rep exposure:
	 *  ����������mutable�ģ����Բ�ȡ����ʽ��̹淶��ÿ�ζ��½�һ����������
	 */
    public Router(String label) {
		super(label);
	}
	private String ip;
	
	
	/**
	 * ��ӵ����Ϣ
	 * @param args
	 */
	@Override
	public void fillVertexInfo (String[] args) {
		assert args.length==1;
		this.ip = args[0];
	}
	
	/**
	 * �������Ϣת�����ַ���
	 */
	@Override
	public String toString() {
		String newstr = "label:"+this.label+",ip:"+ip;
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
	
	/**
	 * ����ipֵ
	 * @return ip
	 */
	public String getip() {
		String a = this.ip;
		return a;
	}
}
