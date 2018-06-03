package factory;

import vertex.Actor;
import vertex.Vertex;

public class ActorVertexFactory extends VertexFactory{

	@Override
	public Vertex createVertex(String label, String type, String[] args) {
		Vertex newvertex=null;
		if(type.equals("Actor")) {
			newvertex = new Actor(label);
			newvertex.fillVertexInfo(args);
		}
		return newvertex;
	}

}
