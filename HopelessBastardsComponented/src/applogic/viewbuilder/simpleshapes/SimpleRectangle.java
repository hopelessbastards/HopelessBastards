package applogic.viewbuilder.simpleshapes;

import java.awt.Color;

import applogic.viewbuilder.IRectangleViewBuilder;
import screenconverter.descriptors.RectangleDescriptor;

public class SimpleRectangle extends IRectangleViewBuilder{

	private RectangleDescriptor[] describers;
	
	public SimpleRectangle(int x,int y,double angle,int angleCenterPointX,int angleCenterPointY,
			int width,int height,Color color,boolean draw) {
		describers = new RectangleDescriptor[1];
		this.describers[0] = new RectangleDescriptor(x, y, angle, angleCenterPointX, angleCenterPointY, width, height, color, draw);
	}
	
	@Override
	public RectangleDescriptor[] getRectangleDescriptor() {
		
		return describers;
	}
}