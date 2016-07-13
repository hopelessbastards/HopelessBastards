package applogic.skills.vehicles;

import java.awt.Rectangle;

import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.LivingObject;
import applogic.elements.SkillVehicle;
import applogic.skills.vehicles.viewBuilder.BombViewBuilder;
import applogic.viewbuilder.IImageViewBuilder;

public class Bomb extends SkillVehicle{

	private Rectangle damagingArea;
	private Entity damagedEntity;
	private Entity bombOwner;
	
	/*Az alábbi két változó segédváltozó*/
	private LivingObject building;
	private Entity entity;
	
	private int damagingValue;
	
	private IImageViewBuilder bombViewBuilder;
	
	public Bomb(int x, int y, int width, int height, double angle, int angleCenterX, int angleCenterY, Entity owner,IViewBuilderContainer container) {
		super(x, y, width, height, angle, angleCenterX, angleCenterY, owner,container);
		
		this.damagingArea = new Rectangle(x,y, width, height);
		this.bombOwner = owner;
		setEnemyBuildings(bombOwner.getEnemyBuildings());
		setEnemyEntities(bombOwner.getEnemyEntities());
		setEnemyPlayers(bombOwner.getEnemyPlayers());
		
		this.damagingValue = 250;
		this.bombViewBuilder = new BombViewBuilder(x, y, width, height);
		
		container.getViewBuilder().add(this.bombViewBuilder);
	}

	@Override
	public void setDeletable(boolean deletable) {
		super.setDeletable(deletable);
		this.bombViewBuilder.setDeletable(deletable);
	}
	
	@Override
	public void tick(double appTime) {
		for(int i=0;i<getEnemyBuildings().size();i++){
			building = getEnemyBuildings().get(i);
			if(building.getCollideArea().intersects(this.damagingArea)){
				building.setHealth(building.getHealth() - this.damagingValue);
				setDeletable(true);
				break;
			}
		}
		
		if(!isDeletable()){
			for(int i=0;i<getEnemyEntities().size();i++){
				entity = getEnemyEntities().get(i);
				if(entity.getCollideArea().intersects(this.damagingArea)){
					entity.setHealth(entity.getHealth() - this.damagingValue);
					setDeletable(true);
					break;
				}
			}
		}
		
		if(!isDeletable()){
			for(int i=0;i<getEnemyPlayers().size();i++){
				entity = getEnemyPlayers().get(i);
				if(entity.getCollideArea().intersects(this.damagingArea)){
					entity.setHealth(entity.getHealth() - this.damagingValue);
					setDeletable(true);
					break;
				}
			}
		}
	}

}