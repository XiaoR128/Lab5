package factory;

import vertex.Movie;
import vertex.Vertex;

public class MovieVertexFactory extends VertexFactory{

	@Override
	public Vertex createVertex(String label, String type, String[] args) {
		Vertex newvertex=null;
		if(type.equals("Movie")) {
			newvertex = new Movie(label);
			newvertex.fillVertexInfo(args);
		}
		return newvertex;
	}
	
}
