package applogic.elements.controllers.ai;

import java.awt.Rectangle;

public class ElementDescriptionToAI {
	/*Ilyen objektumokat kap az ágens inputként a világról.Minden elem elõ tud állítani magából
	 egy ilyet.*/
	
	private Rectangle collidedArea;
	private String elementType;
	private double health;
	private double maxHealth;
	
	/*Itt azért power, mert csak az számít, hogy mennyi van az erõbõl, nem az, hogy mana, vagy rage.
	 ez egy átlagszámításhoz kell majd.*/
	private double power;
	private double maxPower;
	
	private boolean isEnemy;/*Ez ellenség-e a kérõ entitás számára.Tehát ha ellenséges zombie kérdi
	a maget, hogy ellenséf-e neki, akkor ezt true-ra állítja.*/
	
	public ElementDescriptionToAI() {
		
	}
	
	public ElementDescriptionToAI(Rectangle collidedArea, String elementType, int health, int maxHealth, int power,
			int maxPower) {
		super();
		this.collidedArea = collidedArea;
		this.elementType = elementType;
		this.health = health;
		this.maxHealth = maxHealth;
		this.power = power;
		this.maxPower = maxPower;
	}

	public Rectangle getCollidedArea() {
		return collidedArea;
	}

	public void setCollidedArea(Rectangle collidedArea) {
		this.collidedArea = collidedArea;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public double getMaxPower() {
		return maxPower;
	}

	public void setMaxPower(double maxPower) {
		this.maxPower = maxPower;
	}

	public boolean isEnemy() {
		return isEnemy;
	}

	public void setEnemy(boolean isEnemy) {
		this.isEnemy = isEnemy;
	}

	
	
}