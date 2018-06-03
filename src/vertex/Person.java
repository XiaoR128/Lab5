package vertex;

import java.util.Objects;

public class Person extends Vertex{
	/*
	 * Abstraction function:
	 * ��ʾһ����
	 * Representation invariant:
	 * ���Զ���private�ģ��޷������������
	 * Safety from rep exposure:
	 *  ����������mutable�ģ����Բ�ȡ����ʽ��̹淶��ÿ�ζ��½�һ����������
	 */
	public Person(String label) {
		super(label);
	}
	private String gender;
	private int age;
//	private double degree;
	
	/**
	 * ��ӵ����Ϣ
	 * @param args
	 */
	@Override
	public void fillVertexInfo (String[] args) {
		assert args.length==2;
		this.gender = args[0];
		this.age = Integer.parseInt(args[1]);
	}
	
	/**
	 * �������Ϣת�����ַ���
	 */
	@Override
	public String toString() {
		String newstr = "label:"+this.label+",Gender:"+gender+",age:"+age;
		return newstr;
	}
	
	
	/**
	 * �ж��������Ƿ����
	 * @param v
	 * @return
	 */
	@Override
	public boolean equals(Vertex v) {
//		Person a = (Person) v;
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
	
	public String getgender() {
		String a = this.gender;
		return a;
	}
	
	public int getage() {
		return age;
	}
}
