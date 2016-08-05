package applogic.elements;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import applogic.elements.controllers.ai.ElementDescriptionToAI;
import math.RotatePoints;

public abstract class BasicElement {
	/*Minden játékelem õse.*/
	
	private double appTime;
	
	private double x;
	private double y;
	private int width;
	private int height;
	private double angle;
	private int angleCenterX;
	private int angleCenterY;
	private boolean deletable;/*ez a változó jelzi, hogy a saját szemétgyûjtõmnek törölnie kell-e ezt az
	objektumot a memóriából.*/
	
	private Rectangle collideArea;
	private Point[] collideAreaPoints;
	
	private ElementOperations operations;
	
	public BasicElement(int x, int y, int width, int height, double angle,int angleCenterX,int angleCenterY) {
		super();
		this.operations = new ElementOperations();
		this.angleCenterX = angleCenterX;
		this.angleCenterY = angleCenterY;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.angle = angle;
		this.collideArea = new Rectangle();
		this.collideAreaPoints = new Point[4];
		for(int i =0;i<collideAreaPoints.length;i++){
			collideAreaPoints[i] = new Point();
		}
	}
	public abstract void tick(double appTime);
	
	public abstract ElementDescriptionToAI createElementDescriptionToAI(Rectangle fogOfWar, Entity askerEntity);
	
	public double getAppTime() {
		return appTime;
	}
	public void setAppTime(double appTime) {
		this.appTime = appTime;
	}
	public double getX() {
		return x;
	}

	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public int getAngleCenterX() {
		return angleCenterX;
	}
	public void setAngleCenterX(int angleCenterX) {
		this.angleCenterX = angleCenterX;
	}
	public int getAngleCenterY() {
		return angleCenterY;
	}
	public void setAngleCenterY(int angleCenterY) {
		this.angleCenterY = angleCenterY;
	}
	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	public boolean isDeletable() {
		return deletable;
	}
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}
	
	public Rectangle getCollideArea() {
		this.collideArea.setBounds((int)this.x, (int)this.y, this.width, this.height);
		return this.collideArea;
	}
	
	public ElementOperations getOperations() {
		return operations;
	}
	
	public void setOperations(ElementOperations operations) {
		this.operations = operations;
	}
	public Point[] getCollideAreaPoints(){
		this.collideAreaPoints[0].setLocation((int)x - 1 , (int)y - 1);
		this.collideAreaPoints[1].setLocation((int)x + width + 1 , (int)y - 1);
		this.collideAreaPoints[2].setLocation((int)x - 1 , (int)y + height - 1);
		this.collideAreaPoints[3].setLocation((int)x  + width - 1 , (int)y + height - 1);
		return this.collideAreaPoints;
	}
	
	public Polygon getCollideAreaPolygon(){
		this.collideAreaPoints[0].setLocation((int)x,(int)y);
		this.collideAreaPoints[1].setLocation((int)x + width,(int)y);
		this.collideAreaPoints[2].setLocation((int)x+width,(int)y+height);
		this.collideAreaPoints[3].setLocation((int)x,(int)y+height);
		
		return RotatePoints.rotate(this.collideAreaPoints[0], this.collideAreaPoints[1], this.collideAreaPoints[2] ,
				   this.collideAreaPoints[3],angle,(int)(x+width/2), (int)(y+height/2));
	}	
}