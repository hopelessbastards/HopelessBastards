package applogic.skills;

import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.DoNothingCommander;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.vehicles.Bomb;
import applogic.skills.viewbuilder.BombTrapViewBuilder;
import applogic.skills.viewbuilder.ChangePlayerSkillViewBuilder;


public class BombTrap extends AbstractSkill{
	private int manacost = 50;/*Ennyi manát vesz le a használata a támadásnak*/
	
	public BombTrap(Entity skillOwner, IEnvironment environment,IViewBuilderContainer container, int skillnumber) {
		super(skillOwner, environment,container, skillnumber);
		setCdtime(3);
		
		setSkillViewBuilder(new BombTrapViewBuilder(this, container, skillOwner));
		
	}

	@Override
	public void activateSkill(double appTime) {
		if((getSkillStartedMainTime() + this.getCdtime() < appTime || getSkillStartedMainTime() == 0)){
			setSkillStartedMainTime(appTime);/*A skillkezdési idõt beállítom a játék fõidejére*/
			setIsactivated(true);/*aktívnak tekintjük innentõl a skillt*/
			
			getEnvironment().getSkillVehicles().add(new Bomb((int)getSkillOwner().getX(),(int)getSkillOwner().getY(),64,64,0,0,0,getSkillOwner(),getContainer()));
			
			setViewBuilderActivate(true);

			   if(getSkillOwner().getMana() - this.manacost < 0){
			    	getSkillOwner().setMana(0);
			    }else{
			    	getSkillOwner().setMana(getSkillOwner().getMana()-this.manacost);
			    }
			   
			   getSkillOwner().setSkillStarted(getSkillnumber(), true);
		}
	}

	@Override
	public void activateSkillByServer() {
		
	}

	@Override
	public void tick(double appTime) {
	}

	@Override
	public Polygon getPolygon() {
		return null;
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
}