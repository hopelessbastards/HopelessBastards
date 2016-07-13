package applogic;

import java.awt.Point;

import applogic.components.Container;
import applogic.components.IContainer;
import controller.GameListener;
import controller.MenuListener;
import soundapi.ISoundProvider;

public abstract class GameState implements GameListener,MenuListener{
	/*Játékállás(játék,fõmenü,opció menü stb..)
	 Ebból az osztályból származik az összes,tehát itt definiálom a közös dolgokat.
	 Ez az osztály implementálja a Listenereket,hisz a játkban a katintásra máshogy kell reagálni,mint a fõmenün, ezért
	 minden GameState magának külön meghatározza az eseménykezelõket, és A Canvasnak magát a GameStatetet át lehet adni mint Listenert*/
	
	private int mouseMovedX;/*az egér x mozdulása*/
	private int mouseMovedY;/*az egér y mozdulása*/
	
	private Point mouse = new Point(0,0);/*Az egér melyik pontra mutat*/
	
	/*Minden gamestatenak van egy warning joptionpanehez hasonló eszköze.A boolean azt mutatja, hogy
	 kikell-e rajzolni a figyelmeztetést vagy sem.Ha warning van, akkor semmi más eseményre nem 
	 reagálunk, csak a warning elfogadás gombjára.*/
	//public Warning warning;
	private boolean isWarningSituation = false;
	
	private ISoundProvider soundProvider;
	
	private GameStateEnum ownGameStateType;
	private GameStateEnum nextGameState;
	
	public GameState(ISoundProvider soundProvider) {
		this.soundProvider = soundProvider;
	}
	
	public abstract GameStateEnum tick(double appTime);
	public abstract void init();
	public abstract void viewPrepare();

	public ISoundProvider getSoundProvider() {
		return soundProvider;
	}

	public void setSoundProvider(ISoundProvider soundProvider) {
		this.soundProvider = soundProvider;
	}

	public GameStateEnum getNextGameState() {
		return nextGameState;
	}

	public void setNextGameState(GameStateEnum nextGameState) {
		this.nextGameState = nextGameState;
	}

	public GameStateEnum getOwnGameStateType() {
		return ownGameStateType;
	}

	public void setOwnGameStateType(GameStateEnum ownGameStateType) {
		this.ownGameStateType = ownGameStateType;
	}

	public int getMouseMovedX() {
		return mouseMovedX;
	}
	public void setMouseMovedX(int mouseMovedX) {
		this.mouseMovedX = mouseMovedX;
	}
	public int getMouseMovedY() {
		return mouseMovedY;
	}
	public void setMouseMovedY(int mouseMovedY) {
		this.mouseMovedY = mouseMovedY;
	}
	public Point getMouse() {
		return mouse;
	}
	public void setMouse(Point mouse) {
		this.mouse = mouse;
	}
	public boolean isWarningSituation() {
		return isWarningSituation;
	}
	public void setWarningSituation(boolean isWarningSituation) {
		this.isWarningSituation = isWarningSituation;
	}	
}