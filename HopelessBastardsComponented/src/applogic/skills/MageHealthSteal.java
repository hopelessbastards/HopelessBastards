package applogic.skills;

import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.viewbuilder.MageHealthStealViewBuilder;


public class MageHealthSteal extends AbstractSkill{
	
	private int damageValue = 100;/*Ennyi életerõt vesz le arról,akit ér a támadás*/
	private int manacost = 50;/*Ennyi manát vesz le a használata a támadásnak*/

	public MageHealthSteal(Entity skillOwner, IEnvironment environment,IViewBuilderContainer container, int skillnumber) {
		super(skillOwner, environment,container, skillnumber);
		setCdtime(3);
		
		setSkillViewBuilder(new MageHealthStealViewBuilder(this, container, skillOwner));
		
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
			getSkillOwner().getSelectedEntity().setHealth(-damageValue);
			getSkillOwner().setHealth(damageValue);
			
			
			//player.handler.damagetext.add(new DamagingText(en.x, en.y,String.valueOf(damageValue),true, player.handler));
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