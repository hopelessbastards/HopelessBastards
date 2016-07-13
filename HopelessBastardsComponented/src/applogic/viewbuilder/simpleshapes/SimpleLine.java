package applogic.viewbuilder.simpleshapes;

import java.awt.Color;

import applogic.viewbuilder.ILineViewBuilder;
import screenconverter.descriptors.LineDescriptor;

public class SimpleLine extends ILineViewBuilder{

	private LineDescriptor[] describers;
	
	public SimpleLine(int x,int y,double angle,int angleCenterPointX,int angleCenterPointY,
			int x2,int y2,Color color) {
		describers = new LineDescriptor[1];
		describers[0] = new LineDescriptor(x, y, angle, angleCenterPointX, angleCenterPointY, x2, y2, color);
	}
	
	@Override
	public LineDescriptor[] getLineDescriptor() {

		return this.describers;
	}
}