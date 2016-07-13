package applogic.elements;

import java.util.List;

import applogic.IViewBuilderContainer;

public abstract class SkillVehicle extends BasicElement{
	/*Ez az osztály azoknak az õse, amik a skillekhez elemek, és van tick metódusuk,
	 azaz figyelnek a vilkágra,(bomba,bullet,stb..)*/
	
	private Entity owner;
	
	private List<Entity> enemyEntities;
	private List<Entity> enemyPlayers;
	private List<LivingObject> enemyBuildings;
	
	public SkillVehicle(int x, int y, int width, int height, double angle, int angleCenterX, int angleCenterY,Entity owner,IViewBuilderContainer container) {
		super(x, y, width, height, angle, angleCenterX, angleCenterY);
		this.owner = owner;
	}
	
	public Entity getOwner() {
		return owner;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}

	public List<Entity> getEnemyEntities() {
		return enemyEntities;
	}

	public void setEnemyEntities(List<Entity> enemyEntities) {
		this.enemyEntities = enemyEntities;
	}

	public List<Entity> getEnemyPlayers() {
		return enemyPlayers;
	}

	public void setEnemyPlayers(List<Entity> enemyPlayers) {
		this.enemyPlayers = enemyPlayers;
	}

	public List<LivingObject> getEnemyBuildings() {
		return enemyBuildings;
	}

	public void setEnemyBuildings(List<LivingObject> enemyBuildings) {
		this.enemyBuildings = enemyBuildings;
	}
}