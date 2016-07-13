package applogic.skills;

import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.vehicles.Bomb;
import applogic.skills.vehicles.Bullet;
import applogic.skills.viewbuilder.SimpleGunShootViewBuilder;
import soundapi.ISound;
import soundapi.ISoundProvider;
import soundapi.MP3;

public class SimpleGunShoot extends AbstractSkill{
	private int manacost = 50;/*Ennyi manát vesz le a használata a támadásnak*/
	
	private ISound shot;
	public SimpleGunShoot(Entity skillOwner, IEnvironment environment, IViewBuilderContainer container,
			int skillnumber,ISoundProvider soundProvider) {
		super(skillOwner, environment, container, skillnumber);
		setCdtime(1);
		
		this.shot = new MP3("shot", soundProvider);
		setSkillViewBuilder(new SimpleGunShootViewBuilder(this, container, skillOwner));
	}

	@Override
	public void activateSkill(double appTime) {
		if((getSkillStartedMainTime() + this.getCdtime() < appTime || getSkillStartedMainTime() == 0)){
			setSkillStartedMainTime(appTime);/*A skillkezdési idõt beállítom a játék fõidejére*/
			setIsactivated(true);/*aktívnak tekintjük innentõl a skillt*/
			
			getEnvironment().getSkillVehicles().add(new Bullet(getSkillOwner().getX() + getSkillOwner().getWidth() + 80,getSkillOwner().getY() + getSkillOwner().getHeight()/2 + 5,getSkillOwner().getAngle(),65,6,getSkillOwner(),getContainer()));
			this.shot.play();
			
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(double appTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Polygon getPolygon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
}