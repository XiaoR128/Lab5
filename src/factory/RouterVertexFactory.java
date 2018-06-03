package factory;

import vertex.Router;
import vertex.Vertex;

public class RouterVertexFactory extends VertexFactory{

	@Override
	public Vertex createVertex(String label, String type, String[] args) {
		Vertex newvertex=null;
		if(type.equals("Router")) {
			newvertex = new Router(label);
			newvertex.fillVertexInfo(args);
		}
		return newvertex;
	}
	
}
