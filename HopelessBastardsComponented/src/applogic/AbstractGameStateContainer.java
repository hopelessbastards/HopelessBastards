package applogic;

import controller.IController;
import screenconverter.IConverter;
import soundapi.ISoundProvider;

public abstract class AbstractGameStateContainer {
	/*Ez az interfacéja a GameStateContainer osztálynak.Ez azért kell, hogy a GameLoop ezt hivogatva
	 hívhassa az aktuális gamestate tick és render metódusait.*/
	
	private GameState actualGameState;
	private GameStateEnum nextGameState;
	private IConverter converter;
	private IController controller;
	
	private ISoundProvider soundProvider;
	
	public AbstractGameStateContainer(IConverter converter,IController controller,ISoundProvider soundPrivider) {
		this.converter = converter;
		this.controller = controller;
		this.soundProvider = soundPrivider;
	}
	
	public abstract void tick(double appTime);
	public abstract void render(double lastTickTime, double nextTickTime);

	public IController getController() {
		return this.controller;
	}

	public void setController(IController controller) {
		this.controller = controller;
	}

	public GameState getActualGameState() {
		return actualGameState;
	}

	public void setActualGameState(GameState actualGameState) {
		this.actualGameState = actualGameState;
	}

	public GameStateEnum getNextGameState() {
		return nextGameState;
	}

	public void setNextGameState(GameStateEnum nextGameState) {
		this.nextGameState = nextGameState;
	}

	public IConverter getConverter() {
		return converter;
	}

	public void setConverter(IConverter converter) {
		this.converter = converter;
	}

	public ISoundProvider getSoundProvider() {
		return soundProvider;
	}

	public void setSoundProvider(ISoundProvider soundProvider) {
		this.soundProvider = soundProvider;
	}	
}