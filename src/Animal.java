
public class Animal {
	private char symbol;
	private String name;
	private String type;
	private String animalProduct;
	private int harvestRate;
	private int animalX;
	private int animalY;
	private double price;
	private boolean harvestable;
	
	public Animal(char symbol, String name, String type, String animalProduct, int harvestRate, int animalX,
			int animalY, double price, boolean harvestable) {
		super();
		this.symbol = symbol;
		this.name = name;
		this.type = type;
		this.animalProduct = animalProduct;
		this.harvestRate = harvestRate;
		this.animalX = animalX;
		this.animalY = animalY;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAnimalProduct() {
		return animalProduct;
	}

	public void setAnimalProduct(String animalProduct) {
		this.animalProduct = animalProduct;
	}

	public int getHarvestRate() {
		return harvestRate;
	}

	public void setHarvestRate(int harvestRate) {
		this.harvestRate = harvestRate;
	}

	public int getAnimalX() {
		return animalX;
	}

	public void setAnimalX(int animalX) {
		this.animalX = animalX;
	}

	public int getAnimalY() {
		return animalY;
	}

	public void setAnimalY(int animalY) {
		this.animalY = animalY;
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
