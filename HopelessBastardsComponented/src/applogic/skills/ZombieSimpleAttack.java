package applogic.skills;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.viewbuilder.ZombieSimpleAttackViewBuilder;
import math.RotatePoints;

public class ZombieSimpleAttack extends AbstractSkill{

	private int damageValue = 50;/*Ennyi életerõt vesz le arról,akit ér a támadás*/
	private int manacost = 50;/*Ennyi manát vesz le a használata a támadásnak*/
	 
	private double x, y, angle; // x,y and angle(szög)
	private int width, height; //width and height 
	 
	private Point[] collideAreaPoints;
	private Entity helper;
	 
	public ZombieSimpleAttack(Entity skillOwner, IEnvironment environment,IViewBuilderContainer container, int skillnumber) {
		super(skillOwner, environment,container, skillnumber);
		 setCdtime(2);
		 this.collideAreaPoints = new Point[4];
		 for(int i=0;i<collideAreaPoints.length;i++){
			 this.collideAreaPoints[i] = new Point();
		 }
		 
		 setSkillViewBuilder(new ZombieSimpleAttackViewBuilder(this, container, skillOwner));
		 
	}

	@Override
	public void activateSkill(double appTime) {
		if(getSkillStartedMainTime() + this.getCdtime() < appTime || getSkillStartedMainTime() == 0){
			setSkillStartedMainTime(appTime);/*A skillkezdési idõt beállítom a játék fõidejére*/
			setIsactivated(true);/*aktívnak tekintjük innentõl a skillt*/
			
			
			setViewBuilderActivate(true);

			   if(getSkillOwner().getMana() - this.manacost < 0){
			    	getSkillOwner().setMana(0);
			    }else{
			    	getSkillOwner().setMana(getSkillOwner().getMana()-this.manacost);
			    }
			   
			   this.x = getSkillOwner().getX() + getSkillOwner().getWidth();
			   this.y = getSkillOwner().getY() ;
			   this.angle = getSkillOwner().getAngle();
			   this.width = getSkillOwner().getWidth();
			   this.height = 20;
			   
			   getSkillOwner().setSkillStarted(getSkillnumber(), true);
	
		}
	}

	@Override
	public void activateSkillByServer(double appTime) {
		
	}

	@Override
	public void tick(double appTime) {
		if(isIsactivated()){
			   /*for(int i=0;i<getEnvironment().getEnemyEntities().size();i++){
				   helper = getEnvironment().getEnemyEntities().get(i);
				   if(getPolygon().intersects(helper.getCollideArea())){
					   helper.setHealth(-damageValue);
					   //player.handler.damagetext.add(new DamagingText(en.x, en.y,String.valueOf(damageValue),true, player.handler));
				   }
			   }*/
			   
			   for(int i=0;i<getSkillOwner().getEnemyPlayers().size();i++){
				   helper = getSkillOwner().getEnemyPlayers().get(i);
				   if(getPolygon().intersects(helper.getCollideArea())){
					   helper.setHealth(-damageValue);
				   }
			   }
			   
			   for(int i=0;i<getSkillOwner().getEnemyEntities().size();i++){
				   helper = getSkillOwner().getEnemyEntities().get(i);
				   if(getPolygon().intersects(helper.getCollideArea())){
					   helper.setHealth(-damageValue);
				   }
			   }
			   
			setIsactivated(false);
			getSkillOwner().setSkillStarted(getSkillnumber(), false);
		}
	}

	@Override
	public Polygon getPolygon() {

		this.collideAreaPoints[0].setLocation((int)x,(int)y);
		this.collideAreaPoints[1].setLocation((int)x + width,(int)y);
		this.collideAreaPoints[2].setLocation((int)x+width,(int)y+height);
		this.collideAreaPoints[3].setLocation((int)x,(int)y+height);
		
		return RotatePoints.rotate(collideAreaPoints[0],collideAreaPoints[1],collideAreaPoints[2],
				  collideAreaPoints[3],angle,(int)(getSkillOwner().getX() + getSkillOwner().getWidth()/2), (int)(getSkillOwner().getY() + getSkillOwner().getHeight()/2));
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
}