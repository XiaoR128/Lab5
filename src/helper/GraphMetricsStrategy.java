package helper;

import edge.Edge;
import graph.Graph;
import vertex.Vertex;

public abstract class GraphMetricsStrategy {
	public abstract double Centrality(Graph<Vertex, Edge> g,Vertex v);
}
