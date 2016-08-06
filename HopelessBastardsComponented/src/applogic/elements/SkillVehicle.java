package applogic.elements;

import java.awt.Rectangle;
import java.util.List;

import applogic.IViewBuilderContainer;
import applogic.elements.controllers.ai.ElementDescriptionToAI;

public abstract class SkillVehicle extends BasicElement{
	/*Ez az osztály azoknak az õse, amik a skillekhez elemek, és van tick metódusuk,
	 azaz figyelnek a vilkágra,(bomba,bullet,stb..)*/
	
	private Entity owner;
	
	private List<Entity> enemyEntities;
	private List<Entity> enemyPlayers;
	private List<LivingObject> enemyBuildings;
	
	private ElementDescriptionToAI elementToAI;
	
	public SkillVehicle(int x, int y, int width, int height, double angle, int angleCenterX, int angleCenterY,Entity owner,IViewBuilderContainer container) {
		super(x, y, width, height, angle, angleCenterX, angleCenterY);
		this.owner = owner;
		this.elementToAI = new ElementDescriptionToAI();
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
	
	@Override
	public ElementDescriptionToAI createElementDescriptionToAI(Entity askerEntity) {
	
		elementToAI.setCollidedArea(getOperations().fogOfWarLocalLocation(askerEntity.getFogOfWar(), getCollideArea()));
		if(elementToAI.getCollidedArea() != null){
			elementToAI.setElementType("SkillVehicle");
			
			boolean isEnemy = false;
			
			for(int i=0;i<askerEntity.getEnemyEntities().size();i++){
				if(askerEntity.getEnemyEntities().get(i).getId().equals(getOwner().getId())){
					isEnemy = true;
					break;
				}
			}
			
			for(int i=0;i<askerEntity.getEnemyPlayers().size();i++){
				if(askerEntity.getEnemyPlayers().get(i).getId().equals(getOwner().getId())){
					isEnemy = true;
					break;
				}
			}
			elementToAI.setEnemy(isEnemy);
			
			
			return this.elementToAI;
		}
		
		return null;
	}
}