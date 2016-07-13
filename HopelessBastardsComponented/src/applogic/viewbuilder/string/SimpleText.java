package applogic.viewbuilder.string;

import java.awt.Color;
import applogic.viewbuilder.IStringViewBuilder;
import screenconverter.descriptors.StringDescriptor;

public class SimpleText extends IStringViewBuilder{

	private StringDescriptor[] describers;
	
	public SimpleText(int x,int y,int size,double angle,int angleCenterPointX,int angleCenterPointY,
			String string,Color color,String fontName) {
		this.describers = new StringDescriptor[1];
		describers[0] = new StringDescriptor(x, y,size, angle, angleCenterPointX, angleCenterPointY, string, color,fontName);
	}
	
	@Override
	public StringDescriptor[] getStringDescriptor() {
		
		return describers;
	}	
}