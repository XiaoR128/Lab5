package vertex;

import java.util.Objects;

public class Word extends Vertex{
	/*
	 * Abstraction function:
	 * 表示一个点
	 * Representation invariant:
	 * 属性都是private的，无法被其他类访问
	 * Safety from rep exposure:
	 *  由于属性是mutable的，所以采取防御式编程规范，每次都新建一个变量返回
	 */
	public Word(String label) {
		super(label);
	}
	
	/**
	 * 添加点的信息
	 * @param args
	 */
	@Override
	public void fillVertexInfo (String[] args) {
		
	}
	
	/**
	 * 将点的信息转换成字符串
	 */
	@Override
	public String toString() {
		String newstr = "label:"+this.label;
		return newstr;
	}
	
	/**
	 * 判断两个点是否相等
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
	 * 获取点的哈希值
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.label); 
	}
}
