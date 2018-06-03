package factory;

/**
 * memory data
 * @author rui
 *
 */

public class Companny {
	private String src;
	private String dest;
	
	public Companny(String src,String dest) {
		this.src = src;
		this.dest = dest;
	}
	
	@Override
	public int hashCode() {
		final int p = 31;
		int result = 1;
		result = result*p + dest.hashCode();
		result = result*p + src.hashCode();
		return result;
	}
}
