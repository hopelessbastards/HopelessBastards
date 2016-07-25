package applogic.skills;

import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.IEnvironment;

public class DoNothingSkill extends AbstractSkill{

	public DoNothingSkill(Entity skillOwner, IEnvironment environment, IViewBuilderContainer container,
			int skillnumber) {
		super(skillOwner, environment, container, skillnumber);
		
	}

	@Override
	public void activateSkill(double appTime) {
		/*Csak akkor aktiválódik a  skill, ha a jelenlegi játékidõ nagyobb,mint a skillkezdés és a skillcd idõ
		 összeadva, azaz ha lejárt a cd,csak akkor aktiválhatom újra a skillt.Illetve hs a skillstartedtime == 0, az azt jelenti
		 hogy mióta megy a játék,azaz inicializálva lett a skill, azóta még nem volt használva,tehát cd sem lehet rajta.*/
		if(getSkillStartedMainTime() + this.getCdtime() < appTime || getSkillStartedMainTime() == 0){
			setSkillStartedMainTime(appTime);/*A skillkezdési idõt beállítom a játék fõidejére*/
			setIsactivated(true);/*aktívnak tekintjük innentõl a skillt*/
			//player.monitorScreenmanager.skill0useable = false;/*A skillbaron elszürkítjük a képet, ami a skillt képviseli*/
			
			
			setViewBuilderActivate(true);
		//	boltSong.play();
			
			   
			  
			   
			   
			
			   //animaionStillRuning = true;/*most már kirajzolhatjuk az animációt.*/
			
			   /*Ezzel állítjuk be a playernél, hogy ez a sorszámú képessége el lett tolva és volt rá
			    elegendõ manája is.*/
			   getSkillOwner().setSkillStarted(getSkillnumber(), true);
			   //player.skill0started = true;
			   
		}
		
	}

	@Override
	public void activateSkillByServer(double appTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(double appTime) {
		 if(isIsactivated()){

			   getSkillOwner().setX(getSkillOwner().getX() + 100);
			   
			
			   
			setIsactivated(false);
			getSkillOwner().setSkillStarted(getSkillnumber(), false);
		   }
		
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
