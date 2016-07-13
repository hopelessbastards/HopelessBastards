package applogic;

import initapplication.IProviderFactory;
import soundapi.ISoundProvider;

public class AppLogicProvider implements IAppLogicProvider{

	/*Ez biztosítja, hogy az APP-Logic komponenshez szükséges minden osztályból példányosítsunk.*/
	private IProviderFactory factory;
	private IGameLoop gameLoop;
	private AbstractGameStateContainer gameStateContainer;
	
	public AppLogicProvider(IProviderFactory factory,ISoundProvider soundProvider) {
		this.factory = factory;
		this.gameStateContainer = new GameStateContainer(this.factory.getConverter(), this.factory.getController(),soundProvider);
		gameLoop = new GameLoop(this.gameStateContainer);	
	}
	
	@Override
	public IGameLoop getGameLoop() {
		return gameLoop;
	}
}