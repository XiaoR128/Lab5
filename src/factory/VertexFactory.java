package factory;

import vertex.Vertex;

public abstract class VertexFactory {
	public abstract Vertex createVertex(String label, String type, String[] args);
}
