package vertex;

import java.util.Objects;

public class Movie extends Vertex{
	/*
	 * Abstraction function:
	 * ��ʾһ����
	 * Representation invariant:
	 * ���Զ���private�ģ��޷������������
	 * Safety from rep exposure:
	 *  ����������mutable�ģ����Բ�ȡ����ʽ��̹淶��ÿ�ζ��½�һ����������
	 */
	public Movie(String label) {
		super(label);
	}
	private int year;
	private String country;
	private double score;
	
	/**
	 * ��ӵ����Ϣ
	 * @param args
	 */
	@Override
	public void fillVertexInfo (String[] args) {
		assert args.length==3;
		this.year = Integer.parseInt(args[0]);
		this.country = args[1];
		this.score = Double.parseDouble(args[2]);
	}
	
	/**
	 * �������Ϣת�����ַ���
	 */
	@Override
	public String toString() {
		String newstr = "label:"+this.label+",year:"+year
				+",country:"+country+",score:"+score;
		return newstr;
	}
	
	/**
	 * �ж��������Ƿ����
	 * @param v
	 * @return
	 */
	@Override
	public boolean equals(Vertex v) {
		if(this.label.equals(v.getLabel())) {
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
	 * ��ȡ�������
	 * @return year
	 */
	public int getyear() {
		int l = this.year;
		return l;
	}
	
	/**
	 * ��ȡ��������
	 * @return country
	 */
	public String getcountry() {
		String a = this.country;
		return a;
	}
	
	/**
	 * ��ȡ��������
	 * @return score
	 */
	public double getscore() {
		double s = this.score;
		return s;
	}
}
