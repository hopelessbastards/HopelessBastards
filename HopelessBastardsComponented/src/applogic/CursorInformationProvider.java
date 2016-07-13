package applogic;

import java.awt.Point;
import applogic.elements.BasicElement;
import screenconverter.IConverter;;;

public class CursorInformationProvider {
	/*Ez egy olyan osztály ami folyamatosan a legfrisebb információkat tárolja a kurzorról, hol van,
	 milyen felhasználói beavatkozás történt(jobbegér kattintás,touchscreen,stb...)*/
	
	private boolean click;
	private boolean release;
	private boolean move;
	private Point locationOnScreen;
	private int xOnScreen;
	private int yOnScreen;
	private Point mouse;/*Az egér pontos hyelének pontja.*/
	
	/*Ez az objektum az , ami a képernyõ közepén van, ehhez igazodik a kamerától kezdve az egér helyzete,
	 és még sok más stb..*/
	private BasicElement centerObject;
	
	private IConverter converter;
	
	public CursorInformationProvider(BasicElement centerObject,IConverter converter) {
		this.centerObject = centerObject;
		this.mouse = new Point(0,0);
		this.locationOnScreen = new Point(0,0);
		this.converter = converter;
	}	

	public BasicElement getCenterObject() {
		return centerObject;
	}

	public void setCenterObject(BasicElement centerObject) {
		this.centerObject = centerObject;
	}

	public Point getMouse() {
		return mouse;
	}

	public void setMouse(Point mouse) {
		this.mouse = mouse;
	}

	public boolean isClick() {
		return click;
	}

	public void setClick(boolean click) {
		this.click = click;
	}

	public boolean isRelease() {
		return release;
	}

	public void setRelease(boolean release) {
		this.release = release;
	}

	public boolean isMove() {
		return move;
	}

	public void setMove(boolean move) {
		this.move = move;
	}

	public Point getLocationOnScreen() {
		return locationOnScreen;
	}

	public void setLocationOnScreen(Point locationOnScreen) {
		this.locationOnScreen = locationOnScreen;
		
			/*this.mouse.setLocation(this.locationOnScreen.getX() + (centerObject.getX() + centerObject.getWidth()/2 - converter.getCanvasSize().getWIDTH()/2) ,
					this.locationOnScreen.getY() + (centerObject.getY() + (centerObject.getHeight() / 2) - converter.getCanvasSize().getHEIGHT()/2 ));*/
	}
	
	public void tick(double appTime){
		if(centerObject == null){
			this.mouse.setLocation(this.locationOnScreen.getX(),this.locationOnScreen.getY());
		}else{
			this.mouse.setLocation(this.locationOnScreen.getX() + (centerObject.getX() + centerObject.getWidth()/2 - converter.getCanvasSize().getWIDTH()/2) ,
					this.locationOnScreen.getY() + (centerObject.getY() + (centerObject.getHeight() / 2) - converter.getCanvasSize().getHEIGHT()/2 ));
		}
	}

	public int getXOnScreen() {
		return xOnScreen;
	}

	public void setXOnScreen(int xOnScreen) {
		this.xOnScreen = xOnScreen;
	}

	public int getYOnScreen() {
		return yOnScreen;
	}

	public void setYOnScreen(int yOnScreen) {
		this.yOnScreen = yOnScreen;
	}
}