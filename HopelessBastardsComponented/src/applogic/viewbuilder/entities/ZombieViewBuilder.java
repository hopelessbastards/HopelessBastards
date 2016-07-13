package applogic.viewbuilder.entities;

import java.awt.Color;
import applogic.AnimationHandler;
import applogic.IAnimationHandler;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;
import applogic.viewbuilder.IStringViewBuilder;
import applogic.viewbuilder.simpleshapes.EntityAppointerRectangle;
import applogic.viewbuilder.simpleshapes.HealtBarRectangle;
import applogic.viewbuilder.string.SimpleText;
import screenconverter.descriptors.ImageDescriptor;

public class ZombieViewBuilder extends IImageViewBuilder{

	private Entity zombie;
	private ImageDescriptor[] describers;
	private IAnimationHandler animationHandler;
	private IAnimationHandler attackanimation;
	private IAnimationHandler usingHandler;
	
	private IViewBuilderContainer container;
	
	private HealtBarRectangle bar;
	
	private IRectangleViewBuilder appointer;
	
	private IStringViewBuilder string;
	
	public ZombieViewBuilder(Entity zombie,IViewBuilderContainer container) {
		this.container = container;
		
		this.zombie = zombie;
		describers = new ImageDescriptor[1];
		describers[0] = new ImageDescriptor(10, 10,10,10,10,100,100,"fire", 0);
		animationHandler = new AnimationHandler(10, 4,true,"zombie");
		this.attackanimation = new AnimationHandler(80, 2, false,"zombieattack");
		usingHandler = animationHandler;
		
		
		string = new SimpleText(1000, 1000,10,0,0,0,"asd",Color.red,"Arial");
		bar = new HealtBarRectangle(zombie);
		
		this.container.getStringBuilder().add(this.string);
		this.container.getRectangleBuilder().add(this.bar);
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		super.setDeletable(deletable);
		this.bar.setDeletable(deletable);
		this.string.setDeletable(deletable);
		if(this.appointer != null){
			this.appointer.setDeletable(deletable);
		}
		
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		if(appointer == null && zombie.getCollideArea().contains(container.getCursorInformationProvider().getMouse())){
			appointer = new EntityAppointerRectangle(zombie);
			container.getRectangleBuilder().add(this.appointer);
		}else{
			if(appointer != null){
				appointer.setDeletable(true);
				appointer = null;
			}
		}
		
		if(zombie.isDeletable()){
			setDeletable(true);
			return describers;
		}else{
			for(int i=0;i<zombie.getSkillCount();i++){
				if(zombie.getSkills()[i] != null && (zombie.getSkills()[i].isViewBuilderActivate())){
					usingHandler = attackanimation;
					attackanimation.setAnimationable(true);
					zombie.getSkills()[i].setViewBuilderActivate(false);
					break;
				}
			}
			if(attackanimation.getAnimationable() == false){
				usingHandler = animationHandler;
			}
			
			
			describers[0].setAnimation(usingHandler.animationPiece());
			describers[0].setImageLogicalName(usingHandler.getLogicName());
			
			describers[0].setAngle(zombie.getAngle());
			describers[0].setAngleCenterPointX((int)zombie.getX()+zombie.getWidth()/2);
			describers[0].setAngleCenterPointY((int)zombie.getY() + zombie.getHeight()/2);
			
			describers[0].setHeight(zombie.getHeight());
			describers[0].setWidth(zombie.getWidth());
			describers[0].setX((int)zombie.getX());
			describers[0].setY((int)zombie.getY());
			
			return describers;
		}	
	}
}