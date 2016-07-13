package applogic.engine;

import java.util.ArrayList;
import java.util.List;

import applogic.CursorInformationProvider;
import applogic.GameState;
import applogic.GameStateEnum;
import applogic.GarbageCollector;
import applogic.IGarbageCollector;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.Player;
import applogic.elements.Tile;
import applogic.elements.controllers.Environment;
import applogic.elements.controllers.IEnvironment;
import controller.events.ActivationNumber;
import controller.events.CursorInformation;
import controller.events.KeyValue;
import controller.events.KeyValueConstant;
import controller.events.MoveWayEnum;
import controller.events.MovedWay;
import screenconverter.IConverter;
import soundapi.ISoundProvider;

public class Engine extends GameState{
	
	private IConverter converter;
	private IViewBuilderContainer viewBuilderContainer;
	
	private List<Tile> tiles = new ArrayList<Tile>();
	private List<Tile> nonBlockingTile = new ArrayList<Tile>();/*Ezeket azért vesszük külön, hogy 
	ütközésvizsgálatkor véletlenül se ellenõrizze senki, hogy ezekkel ütközik-e
	mert ezeken át lehet gyalogolni, ilyen a talajkõzet és stb...*/
	
	private Player player;
	
	private IGarbageCollector garbageCollector;
	private IEnvironment environment;
	private CursorInformationProvider cursorProvider;
	
	public Engine(IConverter converter,ISoundProvider soundProvider) {
		super(soundProvider);
		setNextGameState(GameStateEnum.GAME);
		this.converter  = converter;
		this.garbageCollector = new GarbageCollector();
		viewBuilderContainer = new ViewBuilderContainer(tiles, nonBlockingTile, this.converter, player,garbageCollector);
		this.environment = new Environment(tiles,viewBuilderContainer,this.converter,soundProvider);
		
		this.cursorProvider = this.environment.getCursorInformationProvider();
		
		/*Beállítja a saját gamestate típusát, ez egy enum.*/
		setOwnGameStateType(GameStateEnum.GAME);
	}
	
	/*A GameStateContainer ezt a metódust hívja a saját render metódusából.Itt lényegében a 
	 ViewBuilderContainer komponens viewPrepare() metódusát hívjuk tovább, ami ismeri az összes 
	 Buildert(View,Line , stb..), és oda tudja adni a következõ nagy komponensnek, a ScreenConverternek.*/
	@Override
	public void viewPrepare() {
		viewBuilderContainer.viewPrepare();
		
	}
	
	/*Ez az osztály megvalósítja a listenereket, így a különbözõ felhasználói iterakciókra ez tud 
	 választ generálni.*/
	
	
	/*Az EntityCommander interfacet megvalósítü UserActionCommand osztályból készített objektum neve 
	 userAction.*/
	@Override
	public void characterMoved(MovedWay way) {
		/*Mivel van egy komponensem, ami irányítja a Playert, legyen az mesterséges intelligencia,
		 vagy a felhsaználói iterációk.Itt a felhasználói iterációs irányítónak továbbítom, hogy
		 milyen semény keletkezett, tehát ezek függvényében parancsolgasson a Playernek.*/
		if(way.getWay() == MoveWayEnum.DOWN){
			
			environment.getUserAction().setDown(true);
			
		}else if(way.getWay() == MoveWayEnum.LEFT){
			environment.getUserAction().setLeft(true);
		}else if(way.getWay() == MoveWayEnum.RIGHT){
			environment.getUserAction().setRight(true);
		}else if(way.getWay() == MoveWayEnum.UP){
			environment.getUserAction().setUp(true);
		}
	}

	@Override
	public void characterStopMoving(MovedWay way) {
		if(way.getWay() == MoveWayEnum.DOWN){
			environment.getUserAction().setDown(false);
		}else if(way.getWay() == MoveWayEnum.LEFT){
			environment.getUserAction().setLeft(false);
		}else if(way.getWay() == MoveWayEnum.RIGHT){
			environment.getUserAction().setRight(false);
		}else if(way.getWay() == MoveWayEnum.UP){
			environment.getUserAction().setUp(false);
		
		}
	}
	
	@Override
	public void characterActivation(ActivationNumber number) {
		environment.getUserAction().setSkillActivated(number.getNumber(),true);
	}

	@Override
	public void cursorClicked(CursorInformation screen) {
		this.cursorProvider = environment.getCursorInformationProvider();
		
		this.cursorProvider.setClick(true);
		this.cursorProvider.setMove(false);
	
		this.cursorProvider.setLocationOnScreen(screen.getLocationOnScreen());
		this.cursorProvider.setXOnScreen(screen.getxOnScreen());
		this.cursorProvider.setYOnScreen(screen.getyOnScreen());
	}

	@Override
	public void cursorMOved(CursorInformation screen) {
		this.cursorProvider = environment.getCursorInformationProvider();
		
		this.cursorProvider.setClick(false);
		this.cursorProvider.setMove(true);
	
		this.cursorProvider.setLocationOnScreen(screen.getLocationOnScreen());
		this.cursorProvider.setXOnScreen(screen.getxOnScreen());
		this.cursorProvider.setYOnScreen(screen.getyOnScreen());
	}

	@Override
	public void cursorRelease() {
		this.cursorProvider = environment.getCursorInformationProvider();
		
		this.cursorProvider.setClick(false);
		this.cursorProvider.setMove(false);
	}

	@Override
	public void characterTyped(KeyValue value) {
		if(value.getKeyType() != null && value.getKeyType() == KeyValueConstant.ESCAPE){
			setNextGameState(GameStateEnum.LOGIN);;
		}
	}
	
	/*A GameStatek tick metódusa úgy van megcsinálva, hogy tovább hívja az összes objectének meghívja
	 a tick metódusát, majd visszaadja, hogy kell -e új gamestatera váltani.Ha nem kell, például
	 nem kattintottak rá semelyik gombra, akkor nullt ad vissza.*/
	@Override
	public GameStateEnum tick(double appTime) {
		this.garbageCollector.cleanEntities(this.environment.getEnemyEntities(),this.environment.getFriendlyEntities());
		this.garbageCollector.cleanEntities(this.environment.getEnemyPlayers(),this.environment.getFriendlyPlayers());
		
		if(getNextGameState() == GameStateEnum.GAME){
			/*for(int i=0;i<environment.getPlayer();i++){
				players.get(i).tick();
			}*/
			environment.tick(appTime);

			return null;
		}else{
			return getNextGameState();
		}
	}

	@Override
	public void init() {
	}

	public Entity getPlayer() {
		return environment.getPlayer();
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}