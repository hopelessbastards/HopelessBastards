package screenconverter;

import applogic.elements.EntityPositionEstimate;
import applogic.viewbuilder.IImageViewBuilder;

public class Estimator implements IEstimator{

	private double newxx;
	private double newyy;
	private double newangle;
	
	@Override
	public void estimateNewPosition(IImageViewBuilder viewBuilder, EntityPositionEstimate positionEstimate, double renderTime) {
		/*double xx = mage.interspeedx;
    	double yy = mage.interspeedy;
    	double aa = mage.interanglespeed;*/
    	double xx = positionEstimate.getInterspeedx();
    	double yy = positionEstimate.getInterspeedy();
    	double aa = positionEstimate.getInteranglespeed();
    	
    	
    	double vx;
    	double vy;
    	double va;
    	
    	va = aa / ((positionEstimate.getLastTick() - positionEstimate.getLastlastTick()) / (renderTime));
    	aa = va;
    	/*newangle = aa + mage.interangle;
    	mage.interangle = newangle;*/
    	
    	
    	
    	newangle = aa + positionEstimate.getInterangle();
    	
    	
    	
    	if(newangle > 360){
    		newangle = newangle - 360;
    	}
    	
    	if(newangle < 0){
    		newangle = newangle + 360;
    	}
    	
    	positionEstimate.setInterangle(newangle);
    	
    	viewBuilder.setAngle(newangle);
    	
	    if(((viewBuilder.getTheRepresentetedElement().getAngle() == 0 || 
	    		viewBuilder.getTheRepresentetedElement().getAngle() == 180 || 
	    		viewBuilder.getTheRepresentetedElement().getAngle() == 360) && 
	    		viewBuilder.getTheRepresentetedElement().getX() != 0) || 
	    		((viewBuilder.getTheRepresentetedElement().getAngle() == 270 || 
	    		viewBuilder.getTheRepresentetedElement().getAngle() == 90) && 
	    				viewBuilder.getTheRepresentetedElement().getY() != 0) || 
	    		(viewBuilder.getTheRepresentetedElement().getX() != 0 && 
	    		viewBuilder.getTheRepresentetedElement().getY() != 0) &&
	    		(((positionEstimate.getLastTick() - positionEstimate.getLastlastTick()) > 0.08))){
	    	
	    	
	    	vx = xx / ((positionEstimate.getLastTick() - positionEstimate.getLastlastTick()) / (renderTime));
	    //	System.out.println("xx: " + xx);
	    	//System.out.println("lasttick-lastlast" + (positionEstimate.getLastTick() - positionEstimate.getLastlastTick()));
	    	//System.out.println("renderTme: " + renderTime);
	    	vy = yy / ((positionEstimate.getLastTick() - positionEstimate.getLastlastTick()) / (renderTime));
	    	
	    	xx = vx;
	    	yy = vy;
	    	
				
			/*newxx = xx + mage.interx;
			newyy = yy + mage.intery;*/
	    	newxx = xx + positionEstimate.getInterx();
			newyy = yy + positionEstimate.getIntery();
		
			/*mage.interx = newxx;
			mage.intery = newyy;*/
			positionEstimate.setInterx(newxx);
			positionEstimate.setIntery(newyy);
			
			//System.out.println("newxx:" + newxx);
			
		
	    }else{
	    	/*newxx = (int)mage.oldx1;
	    	newyy = (int)mage.oldy1;*/
	    	newxx = positionEstimate.getOldx1();
	    	newyy = positionEstimate.getOldy1();
	    }
	    
	    viewBuilder.setX(newxx);
	    viewBuilder.setY(newyy);
	    
	}
	

	public void estimateNewPositionOld(IImageViewBuilder viewBuilder, EntityPositionEstimate positionEstimate, double renderTime) {
		/*double xx = mage.interspeedx;
    	double yy = mage.interspeedy;
    	double aa = mage.interanglespeed;*/
    	double xx = positionEstimate.getInterspeedx();
    	double yy = positionEstimate.getInterspeedy();
    	double aa = positionEstimate.getInteranglespeed();
    	
    	
    	double vx;
    	double vy;
    	double va;
    	
    	va = aa / ((positionEstimate.getTickTime()) / (renderTime));
    	aa = va;
    	/*newangle = aa + mage.interangle;
    	mage.interangle = newangle;*/
    	
    	newangle = aa + positionEstimate.getInterangle();
    	positionEstimate.setInterangle(newangle);
    	
    	
    	
	
	    	
	    	
	    	vx = xx / ((positionEstimate.getTickTime()) / (renderTime));
	    //	System.out.println("xx: " + xx);
	    	//System.out.println("lasttick-lastlast" + (positionEstimate.getLastTick() - positionEstimate.getLastlastTick()));
	    	//System.out.println("renderTme: " + renderTime);
	    	vy = yy / ((positionEstimate.getTickTime()) / (renderTime));
	    	
	    	xx = vx;
	    	yy = vy;
	    	
				
			/*newxx = xx + mage.interx;
			newyy = yy + mage.intery;*/
	    	newxx = xx + positionEstimate.getInterx();
			newyy = yy + positionEstimate.getIntery();
		
			/*mage.interx = newxx;
			mage.intery = newyy;*/
			positionEstimate.setInterx(newxx);
			positionEstimate.setIntery(newyy);
			
			System.out.println("newxx:" + newxx);
			
		 
	    viewBuilder.setX(newxx);
	    viewBuilder.setY(newyy);
	    viewBuilder.setAngle(newangle);
	}
	
}