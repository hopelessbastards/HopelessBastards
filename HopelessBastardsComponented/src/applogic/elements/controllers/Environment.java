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
import applogic.collision.ICollision;
import applogic.elements.CharacterType;
import applogic.elements.Entity;
import applogic.elements.LivingObject;
import applogic.elements.SkillVehicle;
import applogic.elements.Tile;
import applogic.elements.Zombie;
import applogic.elements.buildings.BlueBase;
import applogic.elements.buildings.GreenBase;
import applogic.elements.buildings.RedBase;
import applogic.elements.characters.Mage;
import applogic.elements.characters.SteveShooter;
import screenconverter.IConverter;
import soundapi.ISoundProvider;

public class Environment implements IEnvironment{
	
	private CharacterType selectedCharacterType;
	
	private List<Entity> enemyEntities;
	private List<Entity> friendlyEntities;
	private List<Entity> friendlyPlayers;
	private List<Entity> enemyPlayers;
	private List<LivingObject> enemyBuildings;
	private List<LivingObject> friendlyBuildings;
	
	private List<Entity> ownedEntities;
	
	private List<SkillVehicle> skillVehicles;
	private List<Tile> tiles;
	private List<LivingObject> buildings;
	
	private Entity player;
	private PlayerRectangle playerRectangle;
	
	private DoublePoint playerLocation;
	private EntityCommander userAction;
	
	private CursorInformationProvider cursorProvider;
	
	private ICollision collision;
	private StupidZombieAI stupidZombieCommander;
	
	private IViewBuilderContainer container;
	
	private IConverter converter;
	
	private IGarbageCollector garbageCollector;
	
	private ISoundProvider soundProvider;
	
	private IEntityCreator entityCreator;
	
	public Environment(List<Tile> tiles,IViewBuilderContainer container,IConverter converter,ISoundProvider soundProvider, PlayerRectangle playerRectangle) {
		this.playerRectangle = playerRectangle;
		
		this.selectedCharacterType = CharacterType.MAGE;
		this.garbageCollector = new GarbageCollector();
		
		this.soundProvider = soundProvider;
		
		this.ownedEntities = new ArrayList<Entity>();
		
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
		
		this.entityCreator = new EntityCreator(this, soundProvider, container);
		
		/*player = new Mage(3500, 3048, 0, 500, 1000, 500, 1000,"networirkid", CharacterType.MAGE,7,container,this,new EnemyAndFriendlyEntityProvider(this,true),soundProvider);
		
		friendlyEntities.add(player);*/
		
		userAction = new UserActionCommander(this);
		
		/*player.setCommander(userAction);
		
		player.setControlledByPlayer(true);*/
		
		this.container = container;
	
		this.tiles = tiles;
		
		this.stupidZombieCommander = new StupidZombieAI(this);
		
		/*enemyEntities.add(new Zombie(3000, 3000, 0, 500,1000,500,1000,7,container,this,new EnemyAndFriendlyEntityProvider(this,false),soundProvider));
		
		this.stupidZombieCommander.setControlledEntity(enemyEntities.get(enemyEntities.size()-1));
		enemyEntities.get(enemyEntities.size()-1).setCommander(stupidZombieCommander);
		enemyEntities.get(enemyEntities.size()-1).setSelectedEntity(player);*/
		
		/*Kit kövessen a kamera*/
		//this.container.setPlayer(enemyEntities.get(enemyEntities.size() - 1));
		
		
		
		this.cursorProvider = new CursorInformationProvider(playerRectangle,this.converter);
		
		this.container.setCursor(cursorProvider);
		
		
		
		this.collision = new Collision();
		
		//enemyPlayers.add(new SteveShooter(3000, 3000, 0, 500, 1000, 500, 1000,"networirkid", CharacterType.MAGE,7,container,this,new EnemyAndFriendlyEntityProvider(this,false),soundProvider));
		//enemyPlayers.add(new Mage(2500, 2500, 63, 63, 0, 500, 1000, 500, 1000,"networirkid", CharacterType.MAGE,7,container,this));
		//enemyPlayers.get(enemyPlayers.size() - 1).setCommander(new DoNothingCommander());
		
		buildings.add(new BlueBase(2000, 2000, 297, 297, 0, container));
		buildings.add(new RedBase(3500, 3000, 297, 234, 0, container));
		buildings.add(new GreenBase(4000, 4000, 200, 206, 0, container));
	}

	@Override
	public void PlayerMoved(Entity player) {
	
		
		DoublePoint db = new DoublePoint((int)player.getX(), (int)player.getY());
		Rectangle rectes = new Rectangle((int)player.getXold(),(int)player.getYold(),player.getWidth(),player.getHeight());
		List<DoublePoint> maybePoint = new ArrayList<DoublePoint>();
		
		maybePoint.add(db);
		int maybeIndex = 0;
		boolean finded = false;
		boolean findFree = true;
		
		while(!finded){
			findFree = true;
			for(int i=0;i<tiles.size();i++){
				playerLocation = collision.newLocation(rectes, tiles.get(i).getCollideArea(), maybePoint.get(maybeIndex));
				
				if(playerLocation != null){
					maybePoint.add(playerLocation);
					
					findFree = false;
				}
			}
			
			if(findFree){
				player.setX(maybePoint.get(maybeIndex).getX());
				player.setY(maybePoint.get(maybeIndex).getY());	
				player.setXold(maybePoint.get(maybeIndex).getX());
				player.setYold(maybePoint.get(maybeIndex).getY());
				
				finded = true;
			}
			maybeIndex++;
		}	
	}

	@Override
	public void tick(double appTime) {
		cursorProvider.tick(appTime);
		
		if(player != null){
			player.tick(appTime);
		}
		
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
		playerRectangle.setCenterObject(player);
		cursorProvider.setPlayerRectangle(playerRectangle);
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

	@Override
	public List<Entity> getOwnedEntities() {
		return this.ownedEntities;
	}

	@Override
	public void makePlayer(int x, int y, String networkid, String characterType) {
		this.player = entityCreator.createEntity(x, y, networkid, characterType, true);
		
		player.setCommander(userAction);
		
		player.setControlledByPlayer(true);
		
		userAction.setControlledEntity(player);
		
		this.container.setPlayer(player);
		
		userAction.setControlledEntity(player);
		
		playerRectangle.setCenterObject(player);
	}

	@Override
	public void makeEnemyPlayer(int x, int y, String networkid, String characterType) {
		enemyPlayers.add(entityCreator.createEntity(x, y, networkid, characterType, false));
		enemyPlayers.get(enemyPlayers.size() - 1).setCommander(new DoNothingCommander());
	}

	@Override
	public void makeEnemyEntity(int x, int y, String networkid, String characterType) {
		enemyEntities.add(entityCreator.createEntity(x, y, networkid, characterType, false));
		enemyEntities.get(enemyEntities.size() - 1).setCommander(new DoNothingCommander());
	}

	@Override
	public void makeFriendlyPlayer(int x, int y, String networkid, String characterType) {
		friendlyPlayers.add(entityCreator.createEntity(x, y, networkid, characterType, true));
		friendlyPlayers.get(friendlyPlayers.size() - 1).setCommander(new DoNothingCommander());
	}

	@Override
	public void makeFriendlyEntity(int x, int y, String networkid, String characterType) {
		friendlyEntities.add(entityCreator.createEntity(x, y, networkid, characterType, true));
		friendlyEntities.get(friendlyEntities.size() - 1).setCommander(new DoNothingCommander());
	}

	@Override
	public CharacterType getSelectedCharacterType() {
		return this.selectedCharacterType;
	}

	@Override
	public void setSelectedCharacterType(CharacterType characterType) {
		this.selectedCharacterType = characterType;
	}

	@Override
	public PlayerRectangle getPlayerRectangle() {
		return this.playerRectangle;
	}	
}