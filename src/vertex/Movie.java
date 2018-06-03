package vertex;

import java.util.Objects;

public class Movie extends Vertex{
	/*
	 * Abstraction function:
	 * 表示一个点
	 * Representation invariant:
	 * 属性都是private的，无法被其他类访问
	 * Safety from rep exposure:
	 *  由于属性是mutable的，所以采取防御式编程规范，每次都新建一个变量返回
	 */
	public Movie(String label) {
		super(label);
	}
	private int year;
	private String country;
	private double score;
	
	/**
	 * 添加点的信息
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
	 * 将点的信息转换成字符串
	 */
	@Override
	public String toString() {
		String newstr = "label:"+this.label+",year:"+year
				+",country:"+country+",score:"+score;
		return newstr;
	}
	
	/**
	 * 判断两个点是否相等
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
	 * 获取点的哈希值
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.label); 
	}
	
	/**
	 * 获取年份属性
	 * @return year
	 */
	public int getyear() {
		int l = this.year;
		return l;
	}
	
	/**
	 * 获取国家属性
	 * @return country
	 */
	public String getcountry() {
		String a = this.country;
		return a;
	}
	
	/**
	 * 获取分数属性
	 * @return score
	 */
	public double getscore() {
		double s = this.score;
		return s;
	}
}
