package factory;

import java.util.List;

import edge.CommentTie;
import edge.Edge;
import vertex.Vertex;

public class CommentTieFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, String type, double weight, List<Vertex> vertices) {
		Edge newedge = null;
		if(type.equals("CommentTie")) {
			newedge = new CommentTie(label, weight);
			newedge.addVertices(vertices);
		}
		return newedge;
	}
	
}
