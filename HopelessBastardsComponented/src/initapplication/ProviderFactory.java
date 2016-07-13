package initapplication;

import applogic.AppLogicProvider;
import applogic.IAppLogicProvider;
import applogic.IGameLoop;
import controller.ControllerProvider;
import controller.IController;
import controller.IControllerProvider;
import controller.IEventHandlerer;
import graphicsEngine.IRenderer;
import graphicsEngine.IRendererProvider;
import graphicsEngine.RendererProvider;
import screenconverter.ConverterProvider;
import screenconverter.IConverter;
import screenconverter.IConverterProvider;
import soundapi.ISoundProvider;
import soundapi.SoundProvider;

public class ProviderFactory implements IProviderFactory{
	/*A main metódus nem csinál semmi mást, csak létrehoz egy példányt ebbõl az osztályból,persze ezt egy
	 interfacen keresztül teszi.Ez a komponens hozza létre az összes többi egység elõállító komponensét,
	 és azok pedig már saját komponenscsomagukat elõállítják.Itt lényegében minden nagy komponensre 
	 van adattag(Renderer,Controller,Converter,Applogic-gameLoop).*/
	
	
	private IRenderer renderer;
	private IController controller;
	private IConverter converter;
	private IGameLoop gameLoop;
	
	private IRendererProvider rendererProvider;
	private IConverterProvider converterProvider;
	private IControllerProvider controllerProvider;
	private IAppLogicProvider appLogicProvider;
	private ISoundProvider soundProvider;
	
	
	public ProviderFactory() {
		/*Itt példányosítom az összes Providert, azok pedig elõállítják a szükséges objektumokat saját
		 csomagukon belül.*/
		soundProvider = new SoundProvider();
		
		rendererProvider = new RendererProvider();
		renderer = rendererProvider.getRenderer();
		converterProvider = new ConverterProvider(renderer);
		converter = converterProvider.getConverter();
		controllerProvider = new ControllerProvider((IEventHandlerer)renderer.getCanvas());
		controller = controllerProvider.getController();
		appLogicProvider = new AppLogicProvider(this,soundProvider);
		gameLoop = appLogicProvider.getGameLoop();
		
		
	}
	
	@Override
	public void buildApplication() {
	}

	@Override
	public IRenderer getRenderer() {
	
		return this.renderer;
	}
	
	@Override
	public IGameLoop getGameLoop() {
		return this.gameLoop;
	}

	@Override
	public IController getController() {

		return this.controller;
	}

	@Override
	public IConverter getConverter() {

		return this.converter;
	}

	@Override
	public ISoundProvider getSoundProvider() {
		return this.soundProvider;
	}
}