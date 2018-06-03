package helper;

import edge.Edge;
import graph.Graph;
import vertex.Vertex;

public class degreeStrategy extends GraphMetricsStrategy{

	@Override
	public double Centrality(Graph<Vertex, Edge> g, Vertex v) {
		double vertexnum = g.vertices().size()-1;
		double count = g.sources(v).size();
		return count/vertexnum;
	}
	
}
