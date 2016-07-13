package controller;


public class ControllerProvider implements IControllerProvider{
	private IController controller;
	private IEventHandlerer eventHandler;
	
	public ControllerProvider(IEventHandlerer eventHanlder) {
		this.eventHandler = eventHanlder;
		this.controller = new Controller();
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
