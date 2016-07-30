package applogic.viewbuilder.simpleshapes;

import java.awt.Color;
import applogic.elements.LivingObject;
import applogic.viewbuilder.IRectangleViewBuilder;
import screenconverter.descriptors.RectangleDescriptor;

public class HealtBarRectangle extends IRectangleViewBuilder{
	
	private LivingObject entity;
	private RectangleDescriptor[] describers;
	
	public HealtBarRectangle(LivingObject entity) {
		this.entity = entity;
		
		describers = new RectangleDescriptor[2];
		this.describers[0] = new RectangleDescriptor(0,0,0,0,0,0,0,Color.white,false);
		this.describers[1] = new RectangleDescriptor(0,0,0,0,0,0,0,Color.green,false);
	}

	@Override
	public RectangleDescriptor[] getRectangleDescriptor() {
	
		
		describers[0].setX((int)entity.getX());
		describers[0].setY((int)entity.getY()-15);
		describers[0].setWidth(entity.getWidth());
		describers[0].setHeight(10);
		
		describers[1].setX((int)entity.getX() + 1);
		describers[1].setY((int)entity.getY() - 14);
		describers[1].setWidth((int)((entity.getWidth() - 2) * (entity.getHealth() / entity.getMaxhealth())));
		describers[1].setHeight(8);
		
		return this.describers;
	}
}