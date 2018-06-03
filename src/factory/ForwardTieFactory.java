package factory;

import java.util.List;

import edge.Edge;
import edge.ForwardTie;
import vertex.Vertex;

public class ForwardTieFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, String type, double weight, List<Vertex> vertices) {
		Edge newedge = null;
		if(type.equals("ForwardTie")) {
			newedge = new ForwardTie(label, weight);
			newedge.addVertices(vertices);
		}
		return newedge;
	}
	
}
