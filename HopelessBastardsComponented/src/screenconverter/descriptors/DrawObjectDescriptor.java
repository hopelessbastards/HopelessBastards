package screenconverter.descriptors;

import java.awt.Color;

public abstract class DrawObjectDescriptor {
	/*A Converter komponenshez tartozó osztályok egyike.Lényege, hogy a leíró objektumok õse legyen.*/
	
	private int x;
	private int y;
	private double angle;
	private Color color;
	private boolean draw;
	private int angleCenterPointX;/*melyik pont körül forgassuk el az elemet.Alatta az y koordinátája*/
	private int angleCenterPointY;
	
	public DrawObjectDescriptor(int x, int y, double angle, int angleCenterPointX, int angleCenterPointY,Color color,
			boolean draw) {
		super();
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.angleCenterPointX = angleCenterPointX;
		this.angleCenterPointY = angleCenterPointY;
		this.color = color;
		this.draw = draw;
	}
	
	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public int getAngleCenterPointX() {
		return angleCenterPointX;
	}
	public void setAngleCenterPointX(int angleCenterPointX) {
		this.angleCenterPointX = angleCenterPointX;
	}
	public int getAngleCenterPointY() {
		return angleCenterPointY;
	}
	public void setAngleCenterPointY(int angleCenterPointY) {
		this.angleCenterPointY = angleCenterPointY;
	}
}