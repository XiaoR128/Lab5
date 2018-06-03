package factory;

import java.util.List;

import edge.Edge;
import edge.WordNeighborhood;
import vertex.Vertex;

public class WordNeighborhoodFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, String type, double weight , List<Vertex> vertices) {
		Edge newedge = null;
		if(type.equals("WordNeighborhood")) {
			newedge = new WordNeighborhood(label, weight);
			newedge.addVertices(vertices);
		}
		return newedge;
	}
	
}
