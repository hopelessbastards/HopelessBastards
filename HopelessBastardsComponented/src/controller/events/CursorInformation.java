package controller.events;

import java.awt.Point;

public class CursorInformation {
	private Point locationOnScreen;
	private int xOnScreen;
	private int yOnScreen;
	
	public CursorInformation(Point locationOnScreen, int xOnScreen, int yOnScreen) {
		super();
		this.locationOnScreen = locationOnScreen;
		this.xOnScreen = xOnScreen;
		this.yOnScreen = yOnScreen;
	}
	public Point getLocationOnScreen() {
		return locationOnScreen;
	}
	public void setLocationOnScreen(Point locationOnScreen) {
		this.locationOnScreen = locationOnScreen;
	}
	public int getxOnScreen() {
		return xOnScreen;
	}
	public void setxOnScreen(int xOnScreen) {
		this.xOnScreen = xOnScreen;
	}
	public int getyOnScreen() {
		return yOnScreen;
	}
	public void setyOnScreen(int yOnScreen) {
		this.yOnScreen = yOnScreen;
	}
	
}
