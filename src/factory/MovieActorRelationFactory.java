package factory;

import java.util.List;

import edge.Edge;
import edge.MovieActorRelation;
import vertex.Vertex;

public class MovieActorRelationFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, String type, double weight, List<Vertex> vertices) {
		Edge newedge = null;
		if(type.equals("MovieActorRelation")) {
			newedge = new MovieActorRelation(label, weight);
			newedge.addVertices(vertices);
		}
		return newedge;
	}

}
