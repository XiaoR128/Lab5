package factory;

import java.util.List;

import edge.Edge;
import edge.SameMovieHyperEdge;
import vertex.Vertex;

public class SameMovieHyperEdgeFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, String type, double weight, List<Vertex> vertices) {
		Edge newedge = null;
		if(type.equals("SameMovieHyperEdge")) {
			newedge = new SameMovieHyperEdge(label, weight);
			newedge.addVertices(vertices);
		}
		return newedge;
	}

}
