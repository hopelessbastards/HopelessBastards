package applogic.elements.controllers;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import applogic.CursorInformationProvider;
import applogic.GarbageCollector;
import applogic.IGarbageCollector;
import applogic.IViewBuilderContainer;
import applogic.collision.Collision;
import applogic.collision.DoublePoint;
import applogic.elements.CharacterType;
import applogic.elements.Entity;
import applogic.elements.LivingObject;
import applogic.elements.Player;
import applogic.elements.SkillVehicle;
import applogic.elements.Tile;
import applogic.elements.Zombie;
import applogic.elements.buildings.BlueBase;
import applogic.elements.buildings.GreenBase;
import applogic.elements.buildings.RedBase;
import applogic.elements.characters.Mage;
import applogic.elements.characters.SteveShooter;
import applogic.engine.CollisionDetection;
import applogic.engine.ICollisionDetection;
import screenconverter.IConverter;
import soundapi.ISoundProvider;

public class Environment implements IEnvironment{

	private List<Player> players;
	
	private List<Entity> enemyEntities;
	private List<Entity> friendlyEntities;
	private List<Entity> friendlyPlayers;
	private List<Entity> enemyPlayers;
	private List<LivingObject> enemyBuildings;
	private List<LivingObject> friendlyBuildings;
	
	private List<SkillVehicle> skillVehicles;
	
	private List<Tile> tiles;
	
	private List<LivingObject> buildings;
	
	private Entity player;
	private DoublePoint playerLocation;
	private EntityCommander userAction;
	
	private CursorInformationProvider cursorProvider;
	
	private ICollisionDetection collision;
	private boolean collided;
	private StupidZombieAI stupidZombieCommander;
	
	private IViewBuilderContainer container;
	
	private IConverter converter;
	
	private IGarbageCollector garbageCollector;
	
	public Environment(List<Tile> tiles,IViewBuilderContainer container,IConverter converter,ISoundProvider soundProvider) {
		this.garbageCollector = new GarbageCollector();
		
		this.playerLocation = new DoublePoint();
		
		this.enemyEntities = new ArrayList<Entity>();
		this.friendlyEntities = new ArrayList<Entity>();
		this.friendlyPlayers = new ArrayList<Entity>();
		this.enemyPlayers = new ArrayList<Entity>();
		this.friendlyBuildings = new ArrayList<LivingObject>();
		this.enemyBuildings = new ArrayList<LivingObject>();
		
		this.skillVehicles = new ArrayList<SkillVehicle>();
		
		this.converter = converter;
		
		buildings = new ArrayList<LivingObject>();
		
		players = new ArrayList<Player>();
		
		players.add(new Mage(2500, 2500, 63, 63, 0, 500, 1000, 500, 1000,"networirkid", CharacterType.MAGE,7,container,this,new EnemyAndFriendlyEntityProvider(this,true),soundProvider));
		//players.add(new SteveShooter(2500, 2500, 100, 100, 0, 500, 1000, 500, 1000,"networirkid", CharacterType.MAGE,7,container,this,new EnemyAndFriendlyEntityProvider(this,true)));
		friendlyEntities.add(players.get(0));
		
		userAction = new UserActionCommander(this);
		
		players.get(0).setCommander(userAction);
		player = players.get(0);
		player.setControlledByPlayer(true);
		
		this.container = container;
	
		this.tiles = tiles;
		
		this.stupidZombieCommander = new StupidZombieAI(this);
		
		enemyEntities.add(new Zombie(3000, 3000,64,64,0, 500,1000,500,1000,7,container,this,new EnemyAndFriendlyEntityProvider(this,false),soundProvider));
		
		this.stupidZombieCommander.setControlledEntity(enemyEntities.get(enemyEntities.size()-1));
		enemyEntities.get(enemyEntities.size()-1).setCommander(stupidZombieCommander);
		enemyEntities.get(enemyEntities.size()-1).setSelectedEntity(player);
		
		/*Kit kövessen a kamera*/
		//this.container.setPlayer(enemyEntities.get(enemyEntities.size() - 1));
		this.container.setPlayer(player);
		
		this.cursorProvider = new CursorInformationProvider(player,this.converter);
		
		this.container.setCursor(cursorProvider);
		
		userAction.setControlledEntity(players.get(0));
		
		this.collision = new CollisionDetection();
		
		enemyPlayers.add(new SteveShooter(3000, 3000, 100, 100, 0, 500, 1000, 500, 1000,"networirkid", CharacterType.MAGE,7,container,this,new EnemyAndFriendlyEntityProvider(this,false),soundProvider));
		//enemyPlayers.add(new Mage(2500, 2500, 63, 63, 0, 500, 1000, 500, 1000,"networirkid", CharacterType.MAGE,7,container,this));
		enemyPlayers.get(enemyPlayers.size() - 1).setCommander(new DoNothingCommander());
		
		buildings.add(new BlueBase(2000, 2000, 297, 297, 0, container));
		buildings.add(new RedBase(3500, 3000, 297, 234, 0, container));
		buildings.add(new GreenBase(4000, 4000, 200, 206, 0, container));
	}

	@Override
	public void PlayerMoved(Entity player) {
		/*for(int i=0;i<tiles.size();i++){
			if(collision.entityCollideTileWithPoints(player, tiles.get(i))){
				this.collided = true;
				player.setX(player.getXold());
				player.setY(player.getYold());
			}
		}	
		if(collided == false){
			player.setXold(player.getX());
			player.setYold(player.getY());
		}
		this.collided = false;*/
	
		Collision collision = new Collision();
		DoublePoint db = new DoublePoint((int)player.getX(), (int)player.getY());
		Rectangle rectes = new Rectangle((int)player.getXold(),(int)player.getYold(),player.getWidth(),player.getHeight());
		
		/*Kell egy olyan gyorsítás, hogyha megvan az ütközés, akkor ne vizsgáljuk tovább.*/
		for(int i = 0;i < tiles.size();i++){
			playerLocation = collision.newLocation(rectes, tiles.get(i).getCollideArea(), db);
			
			if(playerLocation != null){
				
				player.setX(playerLocation.getX());
				player.setY(playerLocation.getY());
				
				player.setXold(playerLocation.getX());
				player.setYold(playerLocation.getY());
				break;
			}
		}
	}

	@Override
	public void tick(double appTime) {
		cursorProvider.tick(appTime);
		
		player.tick(appTime);
		for(int i=0;i<enemyEntities.size();i++){
			enemyEntities.get(i).tick(appTime);
		}
		
		for(int i=0;i<enemyPlayers.size();i++){
			enemyPlayers.get(i).tick(appTime);
		}
		
		for(int i=0;i<friendlyEntities.size();i++){
			friendlyEntities.get(i).tick(appTime);
		}
		
		for(int i=0;i<friendlyPlayers.size();i++){
			friendlyPlayers.get(i).tick(appTime);
		}
		
		this.garbageCollector.cleanSkillVehicles(skillVehicles);
		for(int i=0;i<skillVehicles.size();i++){
			skillVehicles.get(i).tick(appTime);
		}
	}

	@Override
	public CursorInformationProvider getCursorInformationProvider() {
		return this.cursorProvider;
	}

	public void setCursorProvider(CursorInformationProvider cursorProvider) {
		this.cursorProvider = cursorProvider;
	}

	@Override
	public List<Entity> getFriendlyEntities() {
		return this.friendlyEntities;
	}

	@Override
	public List<Entity> getEnemyEntities() {
		return this.enemyEntities;
	}

	@Override
	public List<Entity> getFriendlyPlayers() {
		return this.friendlyPlayers;
	}

	@Override
	public List<Entity> getEnemyPlayers() {
		return this.enemyPlayers;
	}

	@Override
	public EntityCommander getUserAction() {
		return this.userAction;
	}

	@Override
	public Entity getPlayer() {
		return this.player;
	}

	@Override
	public void setPlayer(Entity player) {
		this.player = player;
		container.setPlayer(player);
		cursorProvider.setCenterObject(player);
		this.player.setControlledByPlayer(true);
		
	}

	@Override
	public List<LivingObject> getEnemyBuildings() {
		return this.enemyBuildings;
	}

	@Override
	public List<LivingObject> getFriendlyBuildings() {
		return this.friendlyBuildings;
	}

	@Override
	public List<SkillVehicle> getSkillVehicles() {
		return this.skillVehicles;
	}	
}