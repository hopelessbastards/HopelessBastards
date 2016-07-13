package applogic.viewbuilder.simpleshapes;

import java.awt.Color;
import applogic.viewbuilder.IRectangleViewBuilder;
import screenconverter.descriptors.RectangleDescriptor;
import applogic.elements.BasicElement;

public class EntityAppointerRectangle extends IRectangleViewBuilder{
	
	private RectangleDescriptor[] describers;
	private BasicElement appointedObject;
	
	public EntityAppointerRectangle(BasicElement appointedObject) {
		this.appointedObject = appointedObject;
		describers = new RectangleDescriptor[1];
		this.describers[0] = new RectangleDescriptor(0,0,0,0,0,10,10,Color.red,true);
	}
	
	@Override
	public RectangleDescriptor[] getRectangleDescriptor() {
		this.describers[0].setX((int)appointedObject.getX());
		this.describers[0].setY((int)appointedObject.getY());
		this.describers[0].setWidth(appointedObject.getWidth());
		this.describers[0].setHeight(appointedObject.getHeight());
		
		return describers;
	}
}