package helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edge.Edge;
import graph.Graph;
import vertex.Vertex;
public class GraphMetrics {
	/**
	 * ��ȡͼ��ĳ����v��degree���Ķ�
	 * @param һ��ͼg
	 * @param һ����v
	 * @return ���Ķȵ�ֵ
	 */
	public static double degreeCentrality(Graph<Vertex,Edge> g, Vertex v) {
		double vertexnum = g.vertices().size()-1;
		double count = g.sources(v).size();
		return count/vertexnum;
	}
	
	
	/**
	 * ��ȡ����ͼ��degree���Ķ�
	 * @param g
	 * @return ���Ķȵ�ֵ
	 */
	public static double degreeCentrality(Graph<Vertex,Edge> g) {
		double result = 0;
		double max = 0;
		int n = g.vertices().size();
		for(Vertex eachvertex:g.vertices()) {
			if(degreeCentrality(g, eachvertex)>max) {
				max = degreeCentrality(g, eachvertex);
			}
		}
		for(Vertex eachvertex:g.vertices()) {
			result+= max-degreeCentrality(g, eachvertex);
		}
		return result/(n*n-3*n+2);
	}
	
	/*
	 * ��������������ж����ߣ��ҵ���С����һ����
	 */
	public static double leastedgew(Map<Vertex, List<Double>> map,Vertex key) {
		double min = Double.MAX_VALUE/8;
		for(Double k : map.get(key)) {
			if(k<min) {
				min = k;
			}
		}
		return min;
	}
	
	/**
	 * ��ͼ��ĳ�����closeness���Ķ�
	 * @param g
	 * @param v
	 * @return ���Ķȵ�ֵ
	 */
	public static double closenessCentrality(Graph<Vertex,Edge> g, Vertex v) {
		int n = g.vertices().size();
		Map<String,Integer> vertindex = new HashMap<>();
		Map<Integer, String> indexvert = new HashMap<>();
		
		double Max = Double.MAX_VALUE/8;
		double[][] d = new double[n][n];
		int count=0;
		for(int j=0;j<n;j++) {
			for(int m=0;m<n;m++) {
				d[j][m] = Max;
			}
		}
		for(Vertex key : g.vertices()) {
			vertindex.put(key.getLabel(),count);
			indexvert.put(count, key.getLabel());
			count++;
		}
		for(int i=0;i<n;i++) {
			String str = indexvert.get(i);
			for(Vertex key : g.vertices()) {
				if(key.getLabel().equals(str)) {
					Map<Vertex, List<Double>> map = g.targets(key);
					if (g.targets(key)!=null) {
						for (Vertex k : map.keySet()) {
							int j = vertindex.get(k.getLabel());
							double dis = leastedgew(map, k);
							d[i][j] = dis;
						}
					}
				}
			}
		}
		for(int i=0;i<n;i++) {
			d[i][i] = 0;
		}
		for(int k=0;k<n;k++) {
			for(int i=0;i<n;i++) {
				for(int j=0;j<n;j++) {
					if(d[i][k]+d[k][j]<d[i][j]) {
						d[i][j] = d[i][k]+d[k][j];
					}
				}
			}
		}
		int x = vertindex.get(v.getLabel());
		double result = 0;
		for(int i=0;i<n;i++) {
			if(d[x][i]!=Max) {
			result = result+d[x][i];
			}
		}
//		for(int i=0;i<n;i++) {
//			result = result+d[x][i];
//		}
		return 1/(result*(n-1));
	}
	
	
	/**
	 * ��ͼ��ĳ�����betweenness���Ķ�
	 * @param g
	 * @param v
	 * @return betweenness���Ķȵ�ֵ
	 */
	public static double betweennessCentrality(Graph<Vertex,Edge> g, Vertex v) {
		int n = g.vertices().size();
		Map<String,Integer> vertindex = new HashMap<>();
		Map<Integer, String> indexvert = new HashMap<>();
		
		double Max = Double.MAX_VALUE/8;
		double[][] d = new double[n][n];
		int count=0;
		for(int j=0;j<n;j++) {
			for(int m=0;m<n;m++) {
				d[j][m] = Max;
			}
		}
		for(Vertex key : g.vertices()) {
			vertindex.put(key.getLabel(),count);
			indexvert.put(count, key.getLabel());
			count++;
		}
		for(int i=0;i<n;i++) {
			String str = indexvert.get(i);
			for(Vertex key : g.vertices()) {
				if(key.getLabel().equals(str)) {
					Map<Vertex, List<Double>> map = g.targets(key);
					if (g.targets(key)!=null) {
						for (Vertex k : map.keySet()) {
							int j = vertindex.get(k.getLabel());
							double dis = leastedgew(map, k);
							d[i][j] = dis;
						}
					}
				}
			}
		}
		for(int i=0;i<n;i++) {
			d[i][i] = 0;
		}
		int[][] ci =new int[n][n];
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if(d[i][j]!=0&&d[i][j]!=Max) {
					ci[i][j]=1;
				}
			}
		}
		for (int k = 0; k < n; k++) {// kΪ�м䶥��
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (d[i][k] + d[k][j] < d[i][j])// ����kΪ�м䶥���·�����Ƚ�С
					{
						d[i][j] = d[i][k] + d[k][j];
						ci[i][j] = ci[i][k] * ci[k][j];
					} else if (Math.abs((d[i][k]+d[k][j])-d[i][j])<1e-7) {// ���еȳ�·��
						ci[i][j] = ci[i][k] * ci[k][j] + ci[i][j];
					}
				}
			}
		}
		int numb=0;
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if(i!=j) {
					numb=numb+ci[i][j];
				}
			}
		}
		int x = vertindex.get(v.getLabel());
		int nu=0;
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if(i!=x&&j!=x) {
					if(ci[i][j]!=0) {
						int p = ci[i][x];
						int q = ci[x][j];
						int l = p*q;
						nu=nu+l;
					}
				}
			}
		}
		return (double)nu/numb;
	}
	
	/**
	 * ������ͼ�е�ĳ�����inDegree��
	 * @param g
	 * @param v
	 * @return inDegree��
	 */
	public static double inDegreeCentrality(Graph<Vertex,Edge> g, Vertex v) {
		double count = g.sources(v).size();
		double n = g.vertices().size();
		return count/(n-1);
	}
	
	/**
	 * ������ͼ��ĳ�����outDegree��
	 * @param g
	 * @param v
	 * @return outDegree�ȵ�ֵ
	 */
	public static double outDegreeCentrality(Graph<Vertex,Edge> g, Vertex v) {
		double count = g.targets(v).size();
		double n = g.vertices().size();
		return count/(n-1);
	}
	
	/**
	 * ��ͼ�����������̾���
	 * @param g
	 * @param start
	 * @param end
	 * @return �����ֵ
	 */
	public static double distance(Graph<Vertex, Edge> g, Vertex start, Vertex end) {
		int n = g.vertices().size();
		Map<String,Integer> vertindex = new HashMap<>();
		Map<Integer, String> indexvert = new HashMap<>();
		
		double Max = Double.MAX_VALUE/8;
		double[][] d = new double[n][n];
		int count=0;
		for(int j=0;j<n;j++) {
			for(int m=0;m<n;m++) {
				d[j][m] = Max;
			}
		}
		for(Vertex key : g.vertices()) {
			vertindex.put(key.getLabel(),count);
			indexvert.put(count, key.getLabel());
			count++;
		}
		for(int i=0;i<n;i++) {
			String str = indexvert.get(i);
			for(Vertex key : g.vertices()) {
				if(key.getLabel().equals(str)) {
					Map<Vertex, List<Double>> map = g.targets(key);
					if (g.targets(key)!=null) {
						for (Vertex k : map.keySet()) {
							int j = vertindex.get(k.getLabel());
							double dis = leastedgew(map, k);
							d[i][j] = dis;
						}
					}
				}
			}
		}
		for(int i=0;i<n;i++) {
			d[i][i] = 0;
		}
		for(int k=0;k<n;k++) {
			for(int i=0;i<n;i++) {
				for(int j=0;j<n;j++) {
					if(d[i][k]+d[k][j]<d[i][j]) {
						d[i][j] = d[i][k]+d[k][j];
					}
				}
			}
		}
		int i = vertindex.get(start.getLabel());
		int j = vertindex.get(end.getLabel());
		return d[i][j];
	}
	
	/**
	 * ��ͼ��ĳ�����������
	 * @param g
	 * @param v
	 * @return ������
	 */
	public static double eccentricity(Graph<Vertex,Edge> g, Vertex v) {
		int n = g.vertices().size();
		Map<String,Integer> vertindex = new HashMap<>();
		Map<Integer, String> indexvert = new HashMap<>();
		
		double Max = Double.MAX_VALUE/8;
		double[][] d = new double[n][n];
		int count=0;
		for(int j=0;j<n;j++) {
			for(int m=0;m<n;m++) {
				d[j][m] = Max;
			}
		}
		for(Vertex key : g.vertices()) {
			vertindex.put(key.getLabel(),count);
			indexvert.put(count, key.getLabel());
			count++;
		}
		for(int i=0;i<n;i++) {
			String str = indexvert.get(i);
			for(Vertex key : g.vertices()) {
				if(key.getLabel().equals(str)) {
					Map<Vertex, List<Double>> map = g.targets(key);
					if (g.targets(key)!=null) {
						for (Vertex k : map.keySet()) {
							int j = vertindex.get(k.getLabel());
							double dis = leastedgew(map, k);
							d[i][j] = dis;
						}
					}
				}
			}
		}
		for(int i=0;i<n;i++) {
			d[i][i] = 0;
		}
		for(int k=0;k<n;k++) {
			for(int i=0;i<n;i++) {
				for(int j=0;j<n;j++) {
					if(d[i][k]+d[k][j]<d[i][j]) {
						d[i][j] = d[i][k]+d[k][j];
					}
				}
			}
		}
		int i=vertindex.get(v.getLabel());
		double max = 0;
		for(int j=0;j<n;j++) {
			if(d[i][j]>max) {
				max = d[i][j];
			}
		}
		return max;
	}
	
	/**
	 * ��ͼ�еİ뾶
	 * @param g
	 * @return �뾶��ֵ
	 */
	public static double radius(Graph<Vertex,Edge> g) {
		double min = Double.MAX_VALUE/8;
		List<Vertex> set = new ArrayList<>();
		set = g.vertices();
		for(Vertex vertex : set) {
			if(eccentricity(g,vertex)<min) {
				min = eccentricity(g,vertex);
			}
		}
		return min;
	}
	
	/**
	 * ��ͼ�е�ֱ��
	 * @param g
	 * @return ֱ����ֵ
	 */
	public static double diameter(Graph<Vertex,Edge> g) {
		double max = 0;
//		Set<Vertex> set = new HashSet<>();
		List<Vertex> set = new ArrayList<>();
		set = g.vertices();
		for(Vertex vertex : set) {
			if(eccentricity(g,vertex)>max) {
				max = eccentricity(g,vertex);
			}
		}
		return max;
	}
}
