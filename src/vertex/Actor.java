package vertex;

import java.util.Objects;

public class Actor extends Vertex {
	/*
	 * Abstraction function: ��ʾһ���� Representation invariant: ���Զ���private�ģ��޷������������
	 * Safety from rep exposure: ����������mutable�ģ����Բ�ȡ����ʽ��̹淶��ÿ�ζ��½�һ����������
	 */
	private int age;
	private String mgender;

	public Actor(String label) {
		super(label);
	}

	/**
	 * ��ӵ����Ϣ
	 * 
	 * @param args
	 */
	@Override
	public void fillVertexInfo(String[] args) {
		assert args.length == 2;
		this.age = Integer.parseInt(args[1]);
		this.mgender = args[0];
	}

	/**
	 * �������Ϣת�����ַ���
	 */
	@Override
	public String toString() {
		String newstr = "label:" + this.label + ",age:" + age + ",Gender:" + mgender;
		return newstr;
	}

	/**
	 * �ж��������Ƿ����
	 * 
	 * @param v
	 * @return
	 */
	@Override
	public boolean equals(Vertex v) {
		if (this.label.equals(v.label)) {
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
	 * ��ȡ��������
	 * 
	 * @return age
	 */
	public int getage() {
		return age;
	}

	/**
	 * ��ȡ�Ա�����
	 *
	 * @return gender
	 */
	public String getgender() {
		return mgender;
	}
}
