package applogic.viewbuilder.entities;

import java.awt.Point;

import applogic.AnimationHandler;
import applogic.GameLoop;
import applogic.IAnimationHandler;
import applogic.IViewBuilderContainer;
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
	
	public MageViewBuilder(Mage mage,IViewBuilderContainer container) {
		this.container = container;
		
		describers = new ImageDescriptor[1];
		describers[0] = new ImageDescriptor(10, 10, 10, 10, 10, mage.getWidth(), 1000, "fire", 0);
		//describers[1] = new ImageDescriptor(10, 10, 10, 10, 10, mage.getWidth(), 1000, "fire", 0);
		//describers[2] = new ImageDescriptor(10, 10, 10, 10, 10, mage.getWidth(), 1000, "fire", 0);
		
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
		
		describers[0].setAnimation(usingHandler.animationPiece());
		describers[0].setImageLogicalName(usingHandler.getLogicName());
		describers[0].setHeight(mage.getHeight());
		describers[0].setWidth(mage.getWidth());
		
		
		/*describers[1].setAnimation(usingHandler.animationPiece());
		describers[1].setImageLogicalName(usingHandler.getLogicName());
		describers[1].setHeight(mage.getHeight());
		describers[1].setWidth(mage.getWidth());
		describers[1].setX((int)mage.getX());
		describers[1].setY((int)mage.getY());
		
		describers[2].setAnimation(usingHandler.animationPiece());
		describers[2].setImageLogicalName(usingHandler.getLogicName());
		describers[2].setHeight(mage.getHeight());
		describers[2].setWidth(mage.getWidth());
*/		
		
		
		/*if(mage.isThisEntityIsThePlayer()){
			  
			   double t =  GameLoop.currentTick - GameLoop.lastTick;
			   double oszto = 0.1 / t;
			   
			   double newSpeed = mage.getMovementSpeed() / oszto;
			   double newAngleSpeed = mage.getTurningSpeed() / oszto;
			   
			   
			   if(Controller.fel){
				   mage.moveForwardNew(newSpeed);
				   mage.setMoving(true);
				   System.out.println("fel");
			   }
			   
			   if(Controller.le){
				   mage.moveBackNew(newSpeed);
				   mage.setMoving(true);
				   System.out.println("le");
			   }
			   
			   if(Controller.balra){
				   mage.turnLeftNew(newAngleSpeed);
				   System.out.println("bal");
			   }
			   
			   if(Controller.jobbra){
				   mage.trunRightNew(newAngleSpeed);
				   System.out.println("jobb");
			   }
			   
			   describers[0].setX((int)mage.newx);
				describers[0].setY((int)mage.newy);
			
				
				describers[0].setAngle(mage.getAngle());
				describers[0].setAngleCenterPointX((int)mage.newx + mage.getWidth()/2);
				describers[0].setAngleCenterPointY((int)mage.newy + mage.getHeight()/2);
		}*/
		
		if(!mage.isThisEntityIsThePlayer() && Converter.doit){
			double xx = mage.interspeedx;
	    	double yy = mage.interspeedy;
	    	//System.out.println("interspeed: " + mage.interspeedx);
	    	double vx;
	    	double vy;
	    	LineEquation line = new LineEquation();
	    	//System.out.println("oldx1: " + mage.oldx1 + "oldx2: " + mage.oldx2);
	    	//System.out.println("oldy: " + mage.oldy1 + "oldy2: " + mage.oldy2);
	    	
	    	//System.out.println(xx);
		    if(((mage.getAngle() == 0 || mage.getAngle() == 180 || mage.getAngle() == 360) && mage.getX() != 0) || ((mage.getAngle() == 270 || mage.getAngle() == 90) && mage.getY() != 0) || (mage.getX() != 0 && mage.getY() != 0)){
		    	//System.out.println(GameLoop.renderTime);
		    	vx = xx / ((GameLoop.lastTick - GameLoop.lastlastTick) / (GameLoop.renderTime));
		    	//System.out.println((GameLoop.lastTick - GameLoop.lastlastTick) / (GameLoop.currentTick - GameLoop.lastRenderTime));
		    	//System.out.println("vx: " + vx);
		    	vy = yy / ((GameLoop.lastTick - GameLoop.lastlastTick) / (GameLoop.renderTime));
		    	
		    	xx = vx;
		    	yy = vy;
		    	//System.out.println(xx);
		    	
		    	Point newp = new Point((int)xx + (int)mage.interx, (int)yy + (int)mage.oldy1);
		    	
		    	//System.out.println("xx :" + xx);
		    	//System.out.println("interspeed: " + mage.interspeedx);
	//	    	describers[2].setX(newp.x);
		//		describers[2].setY(newp.y);
		    	
		    	//System.out.println("newp: " + newp.x);
				/*double[] arrayX = line.lineMaker(new DoublePointtt(mage.oldx1, GameLoop.lastTick),new DoublePointtt(newp.getX(), GameLoop.nextTick));
				double[] arrayY = line.lineMaker(new DoublePointtt(GameLoop.lastTick, mage.oldy1),new DoublePointtt(GameLoop.nextTick, newp.getY()));
					
				double xxx = line.xUnknown(arrayX, GameLoop.currentTick);
				double yyy = line.yUnknown(arrayY, GameLoop.currentTick);*/
					
				newxx = xx + mage.interx;
				newyy = yy + mage.intery;
				
				//System.out.println("newx: " + newxx);
				mage.interx = newxx;
				//System.out.println("xx: " + newxx);
				
				//System.out.println("renderelt");
				mage.intery = newyy;
				
				//System.out.println("speedx: " + xx);
				//System.out.println("interspeedx: " + mage.interspeedx);
				//System.out.println("x: " + newxx);
			
				//System.out.println("oldx: " + mage.oldx1 + " oldy: " + mage.oldy1 + newp + " newxx: " + newxx + " newyy: " + newyy);
					
		    }else{	
		    	newxx = (int)mage.oldx1;
		    	newyy = (int)mage.oldy1;
		    }
				
			describers[0].setX((int)newxx);
			describers[0].setY((int)newyy);
			
			//System.out.println("newp: " + describers[0].getX());
		}else{
			
			describers[0].setX((int)mage.getX());
			describers[0].setY((int)mage.getY());
		}
		
		
		
		
		describers[0].setAngle(mage.getAngle());
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
}