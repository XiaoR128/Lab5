package factory;

import vertex.Computer;
import vertex.Vertex;

public class ComputerVertexFactory extends VertexFactory{

	@Override
	public Vertex createVertex(String label, String type, String[] args) {
		Vertex newvertex=null;
		if(type.equals("Computer")) {
			newvertex = new Computer(label);
			newvertex.fillVertexInfo(args);
		}
		return newvertex;
	}

}
