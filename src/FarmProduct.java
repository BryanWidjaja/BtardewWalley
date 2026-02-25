
public class FarmProduct extends Item {
	private int freshness;

	public FarmProduct(String name, double price, int freshness) {
		super(name, price);
		this.freshness = freshness;
	}

	public int getFreshness() {
		return freshness;
	}

	public void setFreshness(int freshness) {
		this.freshness = freshness;
	}
	
	
}
