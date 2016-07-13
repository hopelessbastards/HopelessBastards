package applogic.viewbuilder.simpleshapes;

import java.awt.Color;

import applogic.viewbuilder.IOvalViewBuilder;
import screenconverter.descriptors.OvalDescriptor;

public class SimpleOval extends IOvalViewBuilder{

	private OvalDescriptor[] describers;
	
	public SimpleOval(int x,int y,double angle, int angleCenterPointX,int angleCenterPointY,
			int width,int height,Color color,boolean draw) {
		describers = new OvalDescriptor[1];
		describers[0] = new OvalDescriptor(x, y, angle, angleCenterPointX, angleCenterPointY, width, height, color, draw);
	}
	
	@Override
	public OvalDescriptor[] getOvalDescriptor() {
		return describers;
	}
}