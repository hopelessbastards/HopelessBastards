package screenconverter.descriptors;

import java.awt.Color;

public class OvalDescriptor extends DrawObjectDescriptor{
	private int width;
	private int height;
	
	public OvalDescriptor(int x, int y, double angle, int angleCenterPointX, int angleCenterPointY, int width,
			int height,Color color,boolean draw) {
		super(x, y, angle, angleCenterPointX, angleCenterPointY,color,draw);
		this.width = width;
		this.height = height;

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
}