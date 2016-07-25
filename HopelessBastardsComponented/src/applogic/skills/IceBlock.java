package applogic.skills;

import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.viewbuilder.IceBlockViewBuilder;
import soundapi.ISound;
import soundapi.ISoundProvider;
import soundapi.MP3;

public class IceBlock extends AbstractSkill{
	private int manacost = 100;/*Ennyi manát vesz le a használata a támadásnak*/

	private ISound iceblocksound;
	
	public IceBlock(Entity skillOwner, IEnvironment environment, IViewBuilderContainer container, int skillnumber,ISoundProvider soundProvider) {
		super(skillOwner, environment, container, skillnumber);
		setSecWhileActive(5);
		
		this.iceblocksound = new MP3("iceblock", soundProvider);
		
		setCdtime(2);
		 
		setSkillViewBuilder(new IceBlockViewBuilder(this,container, skillOwner));
	}

	@Override
	public void activateSkill(double appTime) {
		if(getSkillStartedMainTime() + this.getCdtime() < appTime || getSkillStartedMainTime() == 0){
			setSkillStartedMainTime(appTime);/*A skillkezdési idõt beállítom a játék fõidejére*/
			setIsactivated(true);/*aktívnak tekintjük innentõl a skillt*/
			//player.monitorScreenmanager.skill0useable = false;/*A skillbaron elszürkítjük a képet, ami a skillt képviseli*/
			
			
			setViewBuilderActivate(true);
		//	boltSong.play();
			
			/*A playernél leveszem a manát,amibe a skill került.*/
			   if(getSkillOwner().getMana() - this.manacost < 0){
			    	getSkillOwner().setMana(0);
			    }else{
			    	getSkillOwner().setMana(getSkillOwner().getMana()-this.manacost);
			    }
			  
			   getSkillOwner().setBlocking(true);
			   getSkillOwner().setStunned(true);
			   
			   getSkillOwner().setSkillStarted(getSkillnumber(), true);
			   this.iceblocksound.play();
			   
		}
	}

	@Override
	public void activateSkillByServer(double appTime) {

	}

	@Override
	public void tick(double appTime) {
		if(appTime - getSecWhileActive() - getSkillStartedMainTime() > 0){
			setIsactivated(false);
			
			/*alapértelmezett player tulajdonságok visszaállítása.*/
			getSkillOwner().setBlocking(false);
			getSkillOwner().setStunned(false);
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