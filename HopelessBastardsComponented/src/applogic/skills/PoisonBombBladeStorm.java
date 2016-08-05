package applogic.skills;

import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.vehicles.PoisonBomb;
import applogic.skills.viewbuilder.PoisonBombBladeStormViewBuilder;

public class PoisonBombBladeStorm extends AbstractSkill{
	private int manacost = 50;/*Ennyi manát vesz le a használata a támadásnak*/
	
	private double actualAngle;
	
	private int turningSpeed = 45;
	
	private double lastStackedTime;
	
	private int stack;
	
	public PoisonBombBladeStorm(Entity skillOwner, IEnvironment environment, IViewBuilderContainer container,
			int skillnumber) {
		super(skillOwner, environment, container, skillnumber);
		setCdtime(1);
		
		setSkillViewBuilder(new PoisonBombBladeStormViewBuilder(this, container, skillOwner));
	}

	@Override
	public void activateSkill(double appTime) {
		if((getSkillStartedMainTime() + this.getCdtime() < appTime || getSkillStartedMainTime() == 0)){
			setSkillStartedMainTime(appTime);/*A skillkezdési idõt beállítom a játék fõidejére*/
			setIsactivated(true);/*aktívnak tekintjük innentõl a skillt*/
			lastStackedTime = appTime;
			setViewBuilderActivate(true);

			if(getSkillOwner().getMana() - this.manacost < 0){
			    getSkillOwner().setMana(0);
			}else{
			    getSkillOwner().setMana(getSkillOwner().getMana()-this.manacost);
			}
			   
			getSkillOwner().setSkillStarted(getSkillnumber(), true);
			   
			actualAngle = getSkillOwner().getAngle();
			   
			stack = 0;
			setNetworkActivate(true);
		}
	}

	@Override
	public void activateSkillByServer(double appTime) {
		setSkillStartedMainTime(appTime);/*A skillkezdési idõt beállítom a játék fõidejére*/
		setIsactivated(true);/*aktívnak tekintjük innentõl a skillt*/
		
		setViewBuilderActivate(true);
		
		/*A playernél leveszem a manát,amibe a skill került.*/
		if(getSkillOwner().getMana() - this.manacost < 0){
	    	getSkillOwner().setMana(0);
	    }else{
	    	getSkillOwner().setMana(getSkillOwner().getMana()-this.manacost);
	    }
		
		lastStackedTime = appTime;
		
		actualAngle = getSkillOwner().getAngle();
		   
		stack = 0;
		
		getSkillOwner().setSkillStarted(getSkillnumber(), true);
	
		   
		getSkillOwner().setSkillStarted(getSkillnumber(), false);
		//player.skill0started = false;
	}

	@Override
	public void tick(double appTime) {
		if(isIsactivated() && stack < 30){
			
			if(lastStackedTime + 0.050 <= appTime){
				getEnvironment().getSkillVehicles().add(new PoisonBomb(getSkillOwner().getX() + getSkillOwner().getWidth() + 80,getSkillOwner().getY() + getSkillOwner().getHeight()/2 + 5,actualAngle,32,20,getSkillOwner(),getContainer()));
				getSkillOwner().setAngle(actualAngle);
				
				if(actualAngle + turningSpeed > 360){
					actualAngle = actualAngle + turningSpeed - 360;
				}else{
					actualAngle += turningSpeed;
				}
				lastStackedTime = appTime;
				stack++;
			}
		}else{
			setIsactivated(false);
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