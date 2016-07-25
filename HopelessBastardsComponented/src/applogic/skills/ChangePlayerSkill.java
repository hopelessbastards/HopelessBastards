package applogic.skills;

import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.DoNothingCommander;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.viewbuilder.ChangePlayerSkillViewBuilder;


public class ChangePlayerSkill extends AbstractSkill{

	private int manacost = 50;/*Ennyi manát vesz le a használata a támadásnak*/
	
	
	public ChangePlayerSkill(Entity skillOwner, IEnvironment environment,IViewBuilderContainer container, int skillnumber) {
		super(skillOwner, environment,container, skillnumber);
		setCdtime(3);
		
		setSkillViewBuilder(new ChangePlayerSkillViewBuilder(this, container, skillOwner));
		
	}

	@Override
	public void activateSkill(double appTime) {
		if((getSkillStartedMainTime() + this.getCdtime() < appTime || getSkillStartedMainTime() == 0) && getSkillOwner().getSelectedEntity() != null){
			setSkillStartedMainTime(appTime);/*A skillkezdési idõt beállítom a játék fõidejére*/
			setIsactivated(true);/*aktívnak tekintjük innentõl a skillt*/
			
			
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
	public void activateSkillByServer(double appTime) {
		
	}

	@Override
	public void tick(double appTime) {
		if(isIsactivated()){   
			getEnvironment().setPlayer(getSkillOwner().getSelectedEntity());
			getEnvironment().getPlayer().setCommander(getEnvironment().getUserAction());
			getSkillOwner().setCommander(new DoNothingCommander());
			getSkillOwner().setControlledByPlayer(false);
			getEnvironment().getUserAction().setControlledEntity(getEnvironment().getPlayer());
			
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
}