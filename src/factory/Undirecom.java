package factory;

public class Undirecom {
	private String src;
	private String dest;
	
	public Undirecom(String src,String dest) {
		this.src = src;
		this.dest = dest;
	}
	
	@Override
	public int hashCode() {
		int result = 17+31*(dest.hashCode()+src.hashCode());
		return result;
	}
}
