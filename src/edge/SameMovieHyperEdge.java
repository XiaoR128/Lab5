package edge;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public class SameMovieHyperEdge extends Edge{
	public SameMovieHyperEdge(String label, double weight) {
		super(label, weight);
	}

	public void checkRep(){
		assert vertices.size()>=2;
	}
	/**
	 * 向边中添加点的集合
	 * @param 点的集合
	 */
	public boolean addVertices(List<Vertex> vertice) {
		for(int i=0;i<vertice.size();i++) {
			this.vertices.add(vertice.get(i));
		}
		checkRep();
		return true;
	}

	public List<Vertex> vertices(){
//		Set<Vertex> vertex = new HashSet<>();
		List<Vertex> vertex = new ArrayList<>();
		for(Vertex v : vertices) {
			vertex.add(v);
		}
		checkRep();
		return vertex;
	}
	
	/**
	 * tostring方法
	 */
	public String toString() {
		String newstr ="";
		for(int i=0;i<this.vertices.size();i++) {
			newstr=newstr+vertices.get(i).toString();
		}
		String str = "label:"+this.label+"|"+newstr;
		return str;
	}

	@Override
	public Set<Vertex> sourceVertices() {
		return null;
	}

	@Override
	public Set<Vertex> targetVertices() {
		return null;
	}
}
