package graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Graph<L, E> {

	// 构建一张空图
	// public static <L,E> Graph<L,E> empty(){
	// return (Graph<L, E>) new ConcreteGraph();
	// }

	// 添加结点
	public boolean addVertex(L vertex);

	// 从图中删除一个点
	public boolean removeVertex(L vertex);

	// 返回一个点的集合
	public List<L> vertices();

	// 返回源点集合
	public Map<L, List<Double>> sources(L target);

	// 返回目标点集合
	public Map<L, List<Double>> targets(L source);

	// 增加一条边（包括超边）
	public boolean addEdge(E edge);

	// 删除一条边（包括超边）
	public boolean removeEdge(E edge);

	// 返回边的集合（包括超边）
	public Set<E> edges();
}
