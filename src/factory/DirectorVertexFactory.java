package factory;

import vertex.Director;
import vertex.Vertex;

public class DirectorVertexFactory extends VertexFactory{

	@Override
	public Vertex createVertex(String label, String type, String[] args) {
		Vertex newvertex=null;
		if(type.equals("Director")) {
			newvertex = new Director(label);
			newvertex.fillVertexInfo(args);
		}
		return newvertex;
	}

}
