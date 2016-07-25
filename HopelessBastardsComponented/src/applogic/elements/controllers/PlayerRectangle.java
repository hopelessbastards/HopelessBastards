package applogic.elements.controllers;

import applogic.elements.BasicElement;
import applogic.elements.Entity;

public class PlayerRectangle {
	private double x;
	private double y;
	private int width;
	private int height;
	BasicElement centerObject;
	
	public PlayerRectangle() {
		
	}

	public PlayerRectangle(double x, double y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public PlayerRectangle(double x, double y, int width, int height, BasicElement centerObject) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.centerObject = centerObject;
	}

	public double getX() {
		if(centerObject != null){
			return centerObject.getX();
		}else{
			return 0;
		}
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		if(centerObject != null){
			return centerObject.getY();
		}else{
			return 0;
		}
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		if(centerObject != null){
			return centerObject.getWidth();
		}else{
			return 10;
		}
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		if(centerObject != null){
			return centerObject.getHeight();
		}else{
			return 10;
		}
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BasicElement getCenterObject() {
		return centerObject;
	}

	public void setCenterObject(BasicElement centerObject) {
		this.centerObject = centerObject;
	}

		
}