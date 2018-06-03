package factory;

import vertex.Vertex;
import vertex.WirelessRouter;

public class WirelessRouterVertexFactory extends VertexFactory{
	@Override
	public Vertex createVertex(String label, String type, String[] args) {
		Vertex newvertex=null;
		if(type.equals("WirelessRouter")) {
			newvertex = new WirelessRouter(label);
			newvertex.fillVertexInfo(args);
		}
		return newvertex;
	}
}
