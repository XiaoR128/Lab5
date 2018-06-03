package factory;

import java.util.List;

import edge.Edge;
import edge.MovieDirectorRelation;
import vertex.Vertex;

public class MovieDirectorRelationFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, String type, double weight, List<Vertex> vertices) {
		Edge newedge = null;
		if(type.equals("MovieDirectorRelation")) {
			newedge = new MovieDirectorRelation(label, weight);
			newedge.addVertices(vertices);
		}
		return newedge;
	}

}
