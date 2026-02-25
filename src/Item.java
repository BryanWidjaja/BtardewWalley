
public abstract class Item {
	private String name;
	private double price;
	
	public Item(String name, double price) {
		super();
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice(String name) {
		if (name.equals("Bucket")) 
			price = 1000;
		else if (name.equals("Scissors")) 
			price = 1500;
		else if (name.equals("Egg")) 
			price = 100;
		else if (name.equals("Milk")) 
			price = 300;
		else if (name.equals("Wool")) 
			price = 800;
		
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
