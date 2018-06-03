package factory;

import java.util.List;

import edge.Edge;
import edge.FriendTie;
import vertex.Vertex;

public class FriendTieFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, String type, double weight, List<Vertex> vertices) {
		Edge newedge =null;
		if(type.equals("FriendTie")) {
			newedge = new FriendTie(label, weight);
			newedge.addVertices(vertices);
		}
		return newedge; 
	}

}
