import java.util.ArrayList;

public class Player {
	private String name;
	private String gender;
	private double money;
	private ArrayList<PlayerItem> inventory;
	private ArrayList<Animal> animals;
	private ArrayList<Plant> plants;
	private Coordinate x;
	private Coordinate y;
	private int day;
	
	public Player(String name, String gender) {
		super();
		this.name = name;
		this.gender = gender;
		this.money = 1000.0;
		this.inventory = new ArrayList<>();
		this.animals = new ArrayList<>();
		this.plants = new ArrayList<>();
		this.x = new Coordinate(10);
		this.y = new Coordinate(21);
		this.day = 1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public ArrayList<PlayerItem> getInventory() {
		return inventory;
	}

	public void setInventory(ArrayList<PlayerItem> inventory) {
		this.inventory = inventory;
	}
	
	public ArrayList<Animal> getAnimals() {
		return animals;
	}

	public void setAnimals(ArrayList<Animal> animals) {
		this.animals = animals;
	}
	
	public ArrayList<Plant> getPlants() {
		return plants;
	}

	public void setPlants(ArrayList<Plant> plants) {
		this.plants = plants;
	}

	public Coordinate getX() {
		return x;
	}

	public void setX(Coordinate x) {
		this.x = x;
	}

	public Coordinate getY() {
		return y;
	}

	public void setY(Coordinate y) {
		this.y = y;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	
}
