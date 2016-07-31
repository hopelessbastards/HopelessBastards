package applogic.viewbuilder.buildings;

import applogic.IViewBuilderContainer;
import applogic.elements.BasicElement;
import applogic.elements.LivingObject;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.simpleshapes.HealtBarRectangle;
import screenconverter.descriptors.ImageDescriptor;

public class BlueBaseViewBuilder extends IImageViewBuilder{

private ImageDescriptor[] describers;
	
	private IViewBuilderContainer container;
	

	private LivingObject tile;
	
	public BlueBaseViewBuilder(LivingObject tile,IViewBuilderContainer container) {
		this.tile = tile;
		this.container = container;
		describers = new ImageDescriptor[1];
		describers[0] = new ImageDescriptor((int)tile.getX(), (int)tile.getY(),tile.getAngle(),tile.getAngleCenterX(),tile.getAngleCenterY(),tile.getWidth(),tile.getHeight(),"fire", 0);
		
		container.getRectangleBuilder().add(new HealtBarRectangle(tile));
	}

	@Override
	public ImageDescriptor[] getImageDescriptor() {
		if(tile.getCollideArea().contains(container.getCursorInformationProvider().getMouse())){
			
			describers[0].setImageLogicalName("bluebaseappoint");
		}else{
			describers[0].setImageLogicalName("bluebase");
		}
		
		return this.describers;
	}

	@Override
	public BasicElement getTheRepresentetedElement() {
		// TODO Auto-generated method stub
		return null;
	}
}