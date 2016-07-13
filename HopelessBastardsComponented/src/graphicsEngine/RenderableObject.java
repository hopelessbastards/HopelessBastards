package graphicsEngine;

import java.awt.Color;

public abstract class RenderableObject extends CanvasCommand{
	private Color color;
	private double angle;
	private int rotateAngleCenterX;
	private int rotateAngleCenterY;
	
	public RenderableObject(int x,int y,Color color,double angle,int rotateAngleCenterX,
			int rotateAngleCenterY) {
		super(x,y);
		this.color = color;
		this.angle = angle;
		this.rotateAngleCenterX = rotateAngleCenterX;
		this.rotateAngleCenterY = rotateAngleCenterY;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public int getRotateAngleCenterX() {
		return rotateAngleCenterX;
	}
	
	public void setRotateAngleCenterX(int rotateAngleCenterX) {
		this.rotateAngleCenterX = rotateAngleCenterX;
	}
	
	public int getRotateAngleCenterY() {
		return rotateAngleCenterY;
	}
	
	public void setRotateAngleCenterY(int rotateAngleCenterY) {
		this.rotateAngleCenterY = rotateAngleCenterY;
	}
}