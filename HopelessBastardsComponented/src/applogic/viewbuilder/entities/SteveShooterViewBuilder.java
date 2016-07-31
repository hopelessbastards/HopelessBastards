package applogic.viewbuilder.entities;

import applogic.AnimationHandler;
import applogic.IAnimationHandler;
import applogic.IViewBuilderContainer;
import applogic.elements.BasicElement;
import applogic.elements.Entity;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;
import applogic.viewbuilder.simpleshapes.EntityAppointerRectangle;
import applogic.viewbuilder.simpleshapes.HealtBarRectangle;
import screenconverter.descriptors.ImageDescriptor;

public class SteveShooterViewBuilder extends IImageViewBuilder{
	
	private Entity steve;
	private ImageDescriptor[] describers;
	private IAnimationHandler animationHandler;
	private IAnimationHandler usingHandler;
	
	private IViewBuilderContainer container;
	
	private IRectangleViewBuilder appointer;
	
	private IRectangleViewBuilder healthBar;
	
	public SteveShooterViewBuilder(Entity steve,IViewBuilderContainer container) {
		this.container = container;
		
		describers = new ImageDescriptor[2];
		describers[1] = new ImageDescriptor(10, 10,0,10,10,steve.getWidth(),steve.getHeight(),"steve", 0);
		describers[0] = new ImageDescriptor(10, 10,0,10,10,140,140,"stevegun2", 0);
		
		this.steve = steve;
		animationHandler = new AnimationHandler(60, 2,true,"steve");

		usingHandler = animationHandler;
		this.healthBar = new HealtBarRectangle(steve);
		container.getRectangleBuilder().add(healthBar);
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		super.setDeletable(deletable);
		healthBar.setDeletable(deletable);
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		
		if(appointer == null && steve.getCollideArea().contains(container.getCursorInformationProvider().getMouse())){
			appointer = new EntityAppointerRectangle(steve);
			container.getRectangleBuilder().add(this.appointer);
		}else{
			if(appointer != null){
				appointer.setDeletable(true);
				appointer = null;
			}
		}
		
		
		
		if(steve.isMoving()){
			describers[1].setAnimation(usingHandler.animationPiece());
			describers[1].setImageLogicalName(usingHandler.getLogicName());
			
			describers[1].setAngle(steve.getAngle());
			describers[1].setAngleCenterPointX((int)steve.getX()+steve.getWidth()/2);
			describers[1].setAngleCenterPointY((int)steve.getY() + steve.getHeight()/2);
			
			describers[1].setX((int)steve.getX());
			describers[1].setY((int)steve.getY());
		}else{
			describers[1].setAnimation(0);
			describers[1].setImageLogicalName(usingHandler.getLogicName());
			describers[1].setX((int)steve.getX());
			describers[1].setY((int)steve.getY());
			describers[1].setAngle(steve.getAngle());
		}
		
		describers[0].setAngle(steve.getAngle());
		describers[0].setAngleCenterPointX((int)steve.getX()+steve.getWidth()/2);
		describers[0].setAngleCenterPointY((int)steve.getY() + steve.getHeight()/2);
		describers[0].setX((int)steve.getX() + 40);
		describers[0].setY((int)steve.getY() - 10);
		
		return describers;
	}

	@Override
	public BasicElement getTheRepresentetedElement() {
		return this.steve;
	}
}