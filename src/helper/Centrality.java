package helper;

import edge.Edge;
import graph.Graph;
import vertex.Vertex;

public class Centrality {
	public static double calCentrality(GraphMetricsStrategy gs,Graph<Vertex, Edge> g,Vertex v) {
		double a=gs.Centrality(g, v);
		return a;
	}
}
