package applogic.elements.controllers;

import java.util.List;

import applogic.elements.Entity;
import applogic.elements.LivingObject;

public class EnemyAndFriendlyEntityProvider{
	/*Ennek az osztálynak az a lényege, hogy ez biztosítja az entitások számára, a világon szereplõ
	 más entitások csoportosítását.Friendly és enemy entitásokat elszeparálja.*/
	
	private List<Entity> enemyEntities;
    private List<Entity> enemyPlayers;
    private List<LivingObject> enemyBuildings;
    
    private List<Entity> friendlyEntities;
    private List<Entity> friendlyPlayers;
    private List<LivingObject> friendlyBuildings;
        
	public EnemyAndFriendlyEntityProvider(IEnvironment environment,boolean friendOfThisEnemy) {
		if(friendOfThisEnemy){
			this.enemyEntities = environment.getEnemyEntities();
			this.enemyPlayers = environment.getEnemyPlayers();
			this.enemyBuildings = environment.getEnemyBuildings();
			this.friendlyEntities = environment.getFriendlyEntities();
			this.friendlyPlayers = environment.getFriendlyPlayers();
			this.friendlyBuildings = environment.getFriendlyBuildings();
		}else{
			this.enemyEntities = environment.getFriendlyEntities();
			this.enemyPlayers = environment.getFriendlyPlayers();
			this.enemyBuildings = environment.getFriendlyBuildings();
			this.friendlyEntities = environment.getEnemyEntities();
			this.friendlyPlayers = environment.getEnemyPlayers();
			this.friendlyBuildings = environment.getEnemyBuildings();
		}
	}

	public List<Entity> getEnemyEntities() {
		return enemyEntities;
	}

	public List<Entity> getEnemyPlayers() {
		return enemyPlayers;
	}

	public List<LivingObject> getEnemyBuildings() {
		return enemyBuildings;
	}

	public List<Entity> getFriendlyEntities() {
		return friendlyEntities;
	}

	public List<Entity> getFriendlyPlayers() {
		return friendlyPlayers;
	}

	public List<LivingObject> getFriendlyBuildings() {
		return friendlyBuildings;
	}   
}