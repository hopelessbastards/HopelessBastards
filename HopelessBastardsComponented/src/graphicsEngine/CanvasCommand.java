package graphicsEngine;

public abstract class CanvasCommand {
	/*Erre az absztrakcióra azért van szükség, hogy a Canvasnak átadhassunk a kamerát is mint kirajzolandó
	 elem, ami majd megkapva a vászon kontextust csinál azzaql valamit.Tehát a RenderableObject
	 absztrakció is ebbõl származik.*/
	private int x;
	private int y;
	
	public CanvasCommand(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public abstract void render(Object graphicContext);
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}	
}