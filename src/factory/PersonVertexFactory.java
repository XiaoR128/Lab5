package factory;

import vertex.Person;
import vertex.Vertex;

public class PersonVertexFactory {
	public Vertex createVertex(String label, String type, String[] args) {
		Vertex newvertex=null;
		if(type.equals("Person")) {
			newvertex = new Person(label);
			newvertex.fillVertexInfo(args);
		}
		return newvertex;
	}
}
