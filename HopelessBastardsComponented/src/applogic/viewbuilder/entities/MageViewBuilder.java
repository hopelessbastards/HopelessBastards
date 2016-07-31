package applogic.viewbuilder.entities;

import java.awt.Point;

import applogic.AnimationHandler;
import applogic.GameLoop;
import applogic.IAnimationHandler;
import applogic.IViewBuilderContainer;
import applogic.elements.BasicElement;
import applogic.elements.characters.Mage;
import applogic.elements.controllers.Environment;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;
import applogic.viewbuilder.simpleshapes.EntityAppointerRectangle;
import applogic.viewbuilder.simpleshapes.HealtBarRectangle;
import controller.Controller;
import math.RotatePoints;
import applogic.viewbuilder.entities.DoublePointtt;
import screenconverter.Converter;
import screenconverter.descriptors.ImageDescriptor;

public class MageViewBuilder extends IImageViewBuilder{

	private Mage mage;
	private static ImageDescriptor[] describers;
	private IAnimationHandler animationHandler;
	private IAnimationHandler attackanimation;
	private IAnimationHandler usingHandler;
	
	
	private IViewBuilderContainer container;
	
	/*Ez a piros négyzet, ami a karakterek felett jelenik meg, ha az egeret feléjük húzzák.*/
	private IRectangleViewBuilder appointer;
	
	private IRectangleViewBuilder healthBar;
	
	
	public double newxx;
	public double newyy;
	public double newangle;
	
	public MageViewBuilder(Mage mage,IViewBuilderContainer container) {
		this.container = container;
		
		setPositionEstimate(mage.getPositionEstimate());
		
		describers = new ImageDescriptor[1];
		describers[0] = new ImageDescriptor(10, 10, 10, 10, 10, mage.getWidth(), 1000, "fire", 0);
		//describers[1] = new ImageDescriptor(10, 10, 10, 10, 10, mage.getWidth(), 1000, "fire", 0);
		//describers[2] = new ImageDescriptor(10, 10, 10, 10, 10, mage.getWidth(), 1000, "fire", 0);
		
		setX(mage.getX());
		setY(mage.getY());
		setAngle(mage.getAngle());
		
		this.mage = mage;
		animationHandler = new AnimationHandler(60, 2,true,"mage");
		this.attackanimation = new AnimationHandler(80, 1, false,"mageattack");
		usingHandler = animationHandler;
		this.healthBar = new HealtBarRectangle(mage);
		container.getRectangleBuilder().add(healthBar);
		
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		super.setDeletable(deletable);
		healthBar.setDeletable(deletable);
	}
	
	/**
	 * Get the image description.
	 * 
	 * The mage animation depends on normal or on attack state.
	 */
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		
		if (appointer == null && mage.getCollideArea().contains(container.getCursorInformationProvider().getMouse())){
			appointer = new EntityAppointerRectangle(mage);
			container.getRectangleBuilder().add(this.appointer);
		} else {
			/*Ha az egérrel nem a karakter felett vagyunk, akkor ha a kijelölõ négyzet kivan
			 rajzolva, akkor tüntessük már el.*/
			if (appointer != null){
				appointer.setDeletable(true);
				appointer = null;
			}
		}
				
		for (int i = 0; i < mage.getSkillCount(); i++) {
			if(mage.getSkills()[i] != null && (mage.getSkills()[i].isViewBuilderActivate())){
				usingHandler = attackanimation;
				attackanimation.setAnimationable(true);
				mage.getSkills()[i].setViewBuilderActivate(false);
				break;
			}
		}
		
		if (attackanimation.getAnimationable() == false) {
			usingHandler = animationHandler;
		}
		
		if(!mage.isThisEntityIsThePlayer()){
			describers[0].setX((int)getX());
			describers[0].setY((int)getY());
			describers[0].setAngle(getAngle());
		}else{
			describers[0].setX((int)mage.getX());
			describers[0].setY((int)mage.getY());
			describers[0].setAngle(mage.getAngle());
		}
		
		
		
		describers[0].setAnimation(usingHandler.animationPiece());
		describers[0].setImageLogicalName(usingHandler.getLogicName());
		describers[0].setHeight(mage.getHeight());
		describers[0].setWidth(mage.getWidth());	
		
		
		/*if(!mage.isThisEntityIsThePlayer() && Converter.doit){
			/*double xx = mage.interspeedx;
	    	double yy = mage.interspeedy;
	    	double aa = mage.interanglespeed;*/
	    	/*double xx = mage.getPositionEstimate().getInterspeedx();
	    	double yy = mage.getPositionEstimate().getInterspeedy();
	    	double aa = mage.getPositionEstimate().getInteranglespeed();
	    	
	    	
	    	double vx;
	    	double vy;
	    	double va;
	    	
	    	va = aa / ((GameLoop.lastTick - GameLoop.lastlastTick) / (GameLoop.renderTime));
	    	aa = va;
	    	/*newangle = aa + mage.interangle;
	    	mage.interangle = newangle;*/
	    	
	    	/*newangle = aa + mage.getPositionEstimate().getInterangle();
	    	mage.getPositionEstimate().setInterangle(newangle);
	    	
	    	describers[0].setAngle(newangle);
	    	
	    	
		    if(((mage.getAngle() == 0 || mage.getAngle() == 180 || mage.getAngle() == 360) && mage.getX() != 0) || ((mage.getAngle() == 270 || mage.getAngle() == 90) && mage.getY() != 0) || (mage.getX() != 0 && mage.getY() != 0)){
		    	
		    	vx = xx / ((GameLoop.lastTick - GameLoop.lastlastTick) / (GameLoop.renderTime));
		    	
		    	vy = yy / ((GameLoop.lastTick - GameLoop.lastlastTick) / (GameLoop.renderTime));
		    	
		    	xx = vx;
		    	yy = vy;
		    	
					
				/*newxx = xx + mage.interx;
				newyy = yy + mage.intery;*/
		    /*	newxx = xx + mage.getPositionEstimate().getInterx();
				newyy = yy + mage.getPositionEstimate().getIntery();
			
				/*mage.interx = newxx;
				mage.intery = newyy;*/
				/*mage.getPositionEstimate().setInterx(newxx);
				mage.getPositionEstimate().setIntery(newyy);
				
		    }else{	
		    	/*newxx = (int)mage.oldx1;
		    	newyy = (int)mage.oldy1;*/
		    /*	newxx = (int)mage.getPositionEstimate().getOldx1();
		    	newyy = (int)mage.getPositionEstimate().getOldy1();
		    }*/
				
		    
			/*describers[0].setX((int)newxx);
			describers[0].setY((int)newyy);*/
			
		/*}else{
			
			describers[0].setX((int)mage.getX());
			describers[0].setY((int)mage.getY());
			describers[0].setAngle(mage.getAngle());
		}*/
		
		
		
		
		
		describers[0].setAngleCenterPointX((int)describers[0].getX() + mage.getWidth()/2);
		describers[0].setAngleCenterPointY((int)describers[0].getY() + mage.getHeight()/2);
		
	/*	describers[1].setAngle(mage.getAngle());
		describers[1].setAngleCenterPointX((int)mage.getX() + mage.getWidth()/2);
		describers[1].setAngleCenterPointY((int)mage.getY() + mage.getHeight()/2);
		
		describers[2].setAngle(mage.getAngle());
		describers[2].setAngleCenterPointX((int)describers[2].getX() + mage.getWidth()/2);
		describers[2].setAngleCenterPointY((int)describers[2].getY() + mage.getHeight()/2);
		*/
		return describers;
	}

	@Override
	public BasicElement getTheRepresentetedElement() {
		return this.mage;
	}
}