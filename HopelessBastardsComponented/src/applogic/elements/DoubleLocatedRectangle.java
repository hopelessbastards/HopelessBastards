package applogic.elements;

public class DoubleLocatedRectangle {
	private double x;
	private double y;
	private int width;
	private int height;
	
	public DoubleLocatedRectangle() {
	}
	
	public DoubleLocatedRectangle(double x, double y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public double getX() {
		return x;
	}
	
	public DoubleLocatedRectangle setX(double x) {
		this.x = x;
		return this;
	}
	
	public double getY() {
		return y;
	}
	
	public DoubleLocatedRectangle setY(double y) {
		this.y = y;
		return this;
	}
	
	public int getWidth() {
		return width;
	}
	
	public DoubleLocatedRectangle setWidth(int width) {
		this.width = width;
		return this;
	}
	
	public int getHeight() {
		return height;
	}
	
	public DoubleLocatedRectangle setHeight(int height) {
		this.height = height;
		return this;
	}	
}