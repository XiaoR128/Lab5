package factory;

import vertex.Server;
import vertex.Vertex;

public class ServerVertexFactory extends VertexFactory{

	@Override
	public Vertex createVertex(String label, String type, String[] args) {
		Vertex newvertex=null;
		if(type.equals("Server")) {
			newvertex = new Server(label);
			newvertex.fillVertexInfo(args);
		}
		return newvertex;
	}
	
}
