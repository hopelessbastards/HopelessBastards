package applogic.elements.controllers;

import java.awt.Point;

public class StupidZombieAI extends EntityCommander{
	private double newX;
	private double newY;
	private double distX;
	private double distY;
	private double length;
	private double xold;
	private double yold;
	
	
	public StupidZombieAI(IEnvironment environment) {
		super(environment);
	}

	@Override
	public void command(double appTime) {
		
	    		Point a = new Point((int)getControlledEntity().getWidth(),(int)getControlledEntity().getHeight()/2);
				Point b = new Point((int)(getControlledEntity().getSelectedEntity().getX() + getControlledEntity().getSelectedEntity().getWidth()/2 - (getControlledEntity().getX() + getControlledEntity().getWidth()/2)),(int)(getControlledEntity().getSelectedEntity().getY() + getControlledEntity().getSelectedEntity().getHeight()/2- (getControlledEntity().getY() + getControlledEntity().getWidth()/2)));
				double scalarszorzat = a.x * b.x + a.y * b.y;
				double alength = Math.sqrt(a.x*a.x + a.y*a.y);
				double blength = Math.sqrt(b.x*b.x + b.y*b.y);
				
				
				if( b.y < 0){
					getControlledEntity().setAngle(180 + 180 -  Math.toDegrees(Math.acos(scalarszorzat/(alength*blength))));
					//angle =180 + 180 -  Math.toDegrees(Math.acos(scalarszorzat/(alength*blength)));
				}else{
					getControlledEntity().setAngle( Math.toDegrees(Math.acos(scalarszorzat/(alength*blength))));
					//angle = Math.toDegrees(Math.acos(scalarszorzat/(alength*blength)));
				}
				

				if (getControlledEntity().getAngle() > 360) {
				       getControlledEntity().setAngle(0);
				} else if (getControlledEntity().getAngle() < 0) {
				       getControlledEntity().setAngle(360);
				}
	    	
		
		boolean menjtovabb = false;
		
		
		newX = getControlledEntity().getSelectedEntity().getX()+getControlledEntity().getSelectedEntity().getWidth()/2;
       newY = getControlledEntity().getSelectedEntity().getY() + getControlledEntity().getSelectedEntity().getHeight()/2;
  
       distX = newX - getControlledEntity().getX() + getControlledEntity().getWidth()/2;
       distY = newY - getControlledEntity().getY() + getControlledEntity().getHeight()/2;
       length = Math.sqrt(((distX * distX) + (distY * distY)));
       if(length >= 1){
            getControlledEntity().setVelocityX(getControlledEntity().getMovementSpeed()/3*distX/length);
            getControlledEntity().setVelocityY(getControlledEntity().getMovementSpeed()/3*distY/length);
       }else{
    	   getControlledEntity().setVelocityX(0);
    	   getControlledEntity().setVelocityY(0);
       }
       
       xold = getControlledEntity().getX();
		yold = getControlledEntity().getY();
       
		getControlledEntity().setX(getControlledEntity().getX() + getControlledEntity().getVelocityX());
		getControlledEntity().setY(getControlledEntity().getY() + getControlledEntity().getVelocityY());
	    
	}
}