package initapplication;

import applogic.IGameLoop;
import controller.IController;
import graphicsEngine.IRenderer;
import screenconverter.IConverter;
import soundapi.ISoundProvider;

public interface IProviderFactory {
	public void buildApplication();
	public IRenderer getRenderer();
	public IController getController();
	public IConverter getConverter();
	public IGameLoop getGameLoop();
	public ISoundProvider getSoundProvider();
}