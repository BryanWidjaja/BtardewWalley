
public class AnimalProduct extends Item {
	private int grade;
	
    public AnimalProduct(String name, int price, int grade) {
    	super(name, price);
    	this.grade = grade;
    }

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}
