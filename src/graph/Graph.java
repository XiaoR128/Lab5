package graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Graph<L, E> {

	// ����һ�ſ�ͼ
	// public static <L,E> Graph<L,E> empty(){
	// return (Graph<L, E>) new ConcreteGraph();
	// }

	// ��ӽ��
	public boolean addVertex(L vertex);

	// ��ͼ��ɾ��һ����
	public boolean removeVertex(L vertex);

	// ����һ����ļ���
	public List<L> vertices();

	// ����Դ�㼯��
	public Map<L, List<Double>> sources(L target);

	// ����Ŀ��㼯��
	public Map<L, List<Double>> targets(L source);

	// ����һ���ߣ��������ߣ�
	public boolean addEdge(E edge);

	// ɾ��һ���ߣ��������ߣ�
	public boolean removeEdge(E edge);

	// ���رߵļ��ϣ��������ߣ�
	public Set<E> edges();
}
