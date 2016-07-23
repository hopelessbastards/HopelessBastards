package controller;

import graphicsEngine.ICanvas;

public class ControllerProvider implements IControllerProvider{
	private IController controller;
	private IEventHandlerer eventHandler;
	
	public ControllerProvider(IEventHandlerer eventHanlder,ICanvas canvas) {
		this.eventHandler = eventHanlder;
		this.controller = new Controller(canvas);
		loadController();
	}
	
	public void loadController() {
		this.eventHandler.addListener(controller);
	}

	@Override
	public IController getController() {
		return controller;
	}
	
}
