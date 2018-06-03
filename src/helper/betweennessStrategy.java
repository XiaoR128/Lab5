package helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edge.Edge;
import graph.Graph;
import vertex.Vertex;

public class betweennessStrategy extends GraphMetricsStrategy{

	@Override
	public double Centrality(Graph<Vertex, Edge> g, Vertex v) {
		int n = g.vertices().size();
		Map<String, Integer> vertindex = new HashMap<>();
		Map<Integer, String> indexvert = new HashMap<>();

		double Max = Double.MAX_VALUE / 8;
		double[][] d = new double[n][n];
		int count = 0;
		for (int j = 0; j < n; j++) {
			for (int m = 0; m < n; m++) {
				d[j][m] = Max;
			}
		}
		for (Vertex key : g.vertices()) {
			vertindex.put(key.getLabel(), count);
			indexvert.put(count, key.getLabel());
			count++;
		}
		for (int i = 0; i < n; i++) {
			String str = indexvert.get(i);
			for (Vertex key : g.vertices()) {
				if (key.getLabel().equals(str)) {
					Map<Vertex, List<Double>> map = g.targets(key);
					if (g.targets(key) != null) {
						for (Vertex k : map.keySet()) {
							int j = vertindex.get(k.getLabel());
							double dis = leastedgew(map, k);
							d[i][j] = dis;
						}
					}
				}
			}
		}
		for (int i = 0; i < n; i++) {
			d[i][i] = 0;
		}
		int[][] ci = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (d[i][j] != 0 && d[i][j] != Max) {
					ci[i][j] = 1;
				}
			}
		}
		for (int k = 0; k < n; k++) {// k为中间顶点
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (d[i][k] + d[k][j] < d[i][j])// 若以k为中间顶点的路径长度较小
					{
						d[i][j] = d[i][k] + d[k][j];
						ci[i][j] = ci[i][k] * ci[k][j];
					} else if (Math.abs((d[i][k] + d[k][j]) - d[i][j]) < 1e-7) {// 若有等长路径
						ci[i][j] = ci[i][k] * ci[k][j] + ci[i][j];
					}
				}
			}
		}
		int numb = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i != j) {
					numb = numb + ci[i][j];
				}
			}
		}
		int x = vertindex.get(v.getLabel());
		int nu = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i != x && j != x) {
					if (ci[i][j] != 0) {
						int p = ci[i][x];
						int q = ci[x][j];
						int l = p * q;
						nu = nu + l;
					}
				}
			}
		}
		return (double) nu / numb;
	}

	public static double leastedgew(Map<Vertex, List<Double>> map, Vertex key) {
		double min = Double.MAX_VALUE / 8;
		for (Double k : map.get(key)) {
			if (k < min) {
				min = k;
			}
		}
		return min;
	}
}
