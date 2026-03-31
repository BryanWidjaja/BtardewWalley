
public class Plant {
	private char symbol;
	private String name;
	private int plantX;
	private int plantY;
	private int growthTime;
	private double price;
	private boolean harvestable;
	
	public Plant(char symbol, String name, int plantX, int plantY, int growthTime, double price, boolean harvestable) {
		super();
		this.symbol = symbol;
		this.name = name;
		this.plantX = plantX;
		this.plantY = plantY;
		this.growthTime = growthTime;
		this.price = price;
		this.harvestable = harvestable;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPlantX() {
		return plantX;
	}

	public void setPlantX(int plantX) {
		this.plantX = plantX;
	}

	public int getPlantY() {
		return plantY;
	}

	public void setPlantY(int plantY) {
		this.plantY = plantY;
	}

	public int getGrowthTime() {
		return growthTime;
	}

	public void setGrowthTime(int growthTime) {
		this.growthTime = growthTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isHarvestable() {
		return harvestable;
	}

	public void setHarvestable(boolean harvestable) {
		this.harvestable = harvestable;
	}
	
	
}