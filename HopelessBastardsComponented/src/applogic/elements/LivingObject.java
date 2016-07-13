package applogic.elements;

public abstract class LivingObject extends BasicElement{
	/*Erre az absztrakcióra az életerõvel rendelkezõ épületek miatt van szükség.*/

	private double health;
	private double maxhealth;
	
	public LivingObject(int x, int y, int width, int height, double angle,int angleCenterX,int angleCenterY,double health,double maxhealth) {
		super(x, y, width, height, angle,angleCenterX,angleCenterY);
		this.health = health;
		this.maxhealth = maxhealth;
	}
	
	public void setHealth(double health) {
		this.health = health;
	}
	
	public double getHealth() {
		return health;
	}
	
	public void setMaxhealth(double maxhealth) {
		this.maxhealth = maxhealth;
	}
	
	public double getMaxhealth() {
		return maxhealth;
	}
}