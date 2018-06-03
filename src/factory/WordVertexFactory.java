package factory;

import vertex.Word;
import vertex.Vertex;

public class WordVertexFactory extends VertexFactory{
	public Vertex createVertex(String label, String type, String[] args) {
		Vertex newvertex=null;
		if(type.equals("Word")) {
			newvertex = new Word(label);
			newvertex.fillVertexInfo(args);
		}
		return newvertex;
	}
}
