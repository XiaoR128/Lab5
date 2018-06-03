package vertex;

import java.util.regex.Pattern;

public abstract class Vertex {
	/*
	 * Abstraction function:
	 * 表示一个点
	 * Representation invariant:
	 * 属性是protected的，表示是可继承的
	 * Safety from rep exposure:
	 *  由于属性是mutable的，所以采取防御式编程规范，每次都新建一个变量返回
	 */
	protected String label;
	public Vertex(String label) {
		Pattern p = Pattern.compile("\\w+");
		assert p.matcher(label).matches();
		this.label = label;
	}
	
	/**
	 * 添加点的信息
	 * @param args
	 */
	public abstract void fillVertexInfo (String[] args);
	
	/**
	 * 获取点的名称
	 * @return label
	 */
	public String getLabel() {
		String la;
		la = this.label;
		return la;
	}
	
	/**
	 * 改变点的名称
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
	 * 将点的信息转换成字符串
	 */
	public abstract String toString();
	
	/**
	 * 判断两个点是否相等
	 * @param v
	 * @return
	 */
	public abstract boolean equals(Vertex v);
	
	/**
	 * 获取点的哈希值
	 */
	public abstract int hashCode();
}
