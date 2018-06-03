package factory;

import java.util.List;

import edge.Edge;
import edge.NetworkConnection;
import vertex.Vertex;

public class NetworkConnectionFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, String type, double weight, List<Vertex> vertices) {
		Edge newedge = null;
		if(type.equals("NetworkConnection")) {
			newedge = new NetworkConnection(label, weight);
			newedge.addVertices(vertices);
		}
		return newedge;
	}

}
