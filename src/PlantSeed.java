
public class PlantSeed extends Item {
	
	private char symbol;
	private int growthTime;
	
	public PlantSeed(String name, double price, char symbol, int growthTime) {
		super(name, price);
		this.symbol = symbol;
		this.growthTime = growthTime;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public int getGrowthTime() {
		return growthTime;
	}

	public void setGrowthTime(int growthTime) {
		this.growthTime = growthTime;
	}

	

	
	
}
