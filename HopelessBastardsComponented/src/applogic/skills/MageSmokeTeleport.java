package applogic.skills;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.viewbuilder.MageSmokeTeleportViewBuilder;
import math.RotatePoints;

public class MageSmokeTeleport extends AbstractSkill{

	private int oldPlayerX;/*a player eredeti koordinátája,ahonnan elteleportál lényegében*/
	private int oldPlayerY;
	private Point behing500px;
	
	public MageSmokeTeleport(Entity skillOwner, IEnvironment environment, IViewBuilderContainer container,
			int skillnumber) {
		super(skillOwner, environment, container, skillnumber);
		
		setCdtime(3);
		
		setWidth(128);
		setHeight(128);
		
		setSkillViewBuilder(new MageSmokeTeleportViewBuilder(this, container, skillOwner));
	}

	@Override
	public void activateSkill(double appTime) {
		if((getSkillStartedMainTime() + this.getCdtime() < appTime || getSkillStartedMainTime() == 0)){
			setSkillStartedMainTime(appTime);/*A skillkezdési idõt beállítom a játék fõidejére*/
			setIsactivated(true);/*aktívnak tekintjük innentõl a skillt*/
			
			
			setViewBuilderActivate(true);

			this.oldPlayerX = (int)getSkillOwner().getX();/*player alap koordinátájának beállítása,ahonnan elteleportál*/
			this.oldPlayerY = (int)getSkillOwner().getY();
			

			/*Itt kiszámoljuk azt a pontot,ami a player közepétõl 500 pixellel mögötte van.
			 Az a lényeg,hogy alaphelyzetben a player jobboldalra néz,tehát a mögötte lévõ pontot úgy kapom meg,
			 hogy az x koordinátájából kivonok 500-at.Majd ezt a pontot annyival forgatom a player középpontja körül,
			 ahány fokkal el van fordulva a player, és kész, megvan a pontosan mögötte lévõ pont.*/
			behing500px = RotatePoints.rotatePoint(new Point((int)getSkillOwner().getX() + getSkillOwner().getWidth()/2-300, (int)getSkillOwner().getY() + getSkillOwner().getHeight()/2), getSkillOwner().getAngle(), (int)getSkillOwner().getX() + getSkillOwner().getWidth()/2,(int)getSkillOwner().getY() + getSkillOwner().getHeight()/2);
			
			/*Az új hely koordinátáinak meghatározása.*/
			getSkillOwner().setX(behing500px.x - getSkillOwner().getWidth()/2);
			getSkillOwner().setY(behing500px.y - getSkillOwner().getHeight()/2);
			/*player.x = behing500px.x - player.width/2;
			player.y = behing500px.y - player.height/2;*/
	
			getSkillOwner().setSkillStarted(getSkillnumber(), true);
		}
	}

	@Override
	public void activateSkillByServer(double appTime) {
		setSkillStartedMainTime(appTime);/*A skillkezdési idõt beállítom a játék fõidejére*/
		setIsactivated(true);/*aktívnak tekintjük innentõl a skillt*/
		
		setViewBuilderActivate(true);
		
		
		this.oldPlayerX = (int)getSkillOwner().getX();/*player alap koordinátájának beállítása,ahonnan elteleportál*/
		this.oldPlayerY = (int)getSkillOwner().getY();
		

		/*Itt kiszámoljuk azt a pontot,ami a player közepétõl 500 pixellel mögötte van.
		 Az a lényeg,hogy alaphelyzetben a player jobboldalra néz,tehát a mögötte lévõ pontot úgy kapom meg,
		 hogy az x koordinátájából kivonok 500-at.Majd ezt a pontot annyival forgatom a player középpontja körül,
		 ahány fokkal el van fordulva a player, és kész, megvan a pontosan mögötte lévõ pont.*/
		behing500px = RotatePoints.rotatePoint(new Point((int)getSkillOwner().getX() + getSkillOwner().getWidth()/2-300, (int)getSkillOwner().getY() + getSkillOwner().getHeight()/2), getSkillOwner().getAngle(), (int)getSkillOwner().getX() + getSkillOwner().getWidth()/2,(int)getSkillOwner().getY() + getSkillOwner().getHeight()/2);
		
		/*Az új hely koordinátáinak meghatározása.*/
		getSkillOwner().setX(behing500px.x - getSkillOwner().getWidth()/2);
		getSkillOwner().setY(behing500px.y - getSkillOwner().getHeight()/2);
		
		
		
		getSkillOwner().setSkillStarted(getSkillnumber(), true);
	
		   
		getSkillOwner().setSkillStarted(getSkillnumber(), false);
		//player.skill0started = false;
	}

	@Override
	public void tick(double appTime) {
		if(isIsactivated()){   
			
			
			setIsactivated(false);
			getSkillOwner().setSkillStarted(getSkillnumber(), false);
		}
	}

	@Override
	public Polygon getPolygon() {
		return null;
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

	public int getOldPlayerX() {
		return oldPlayerX;
	}

	public int getOldPlayerY() {
		return oldPlayerY;
	}
	
}