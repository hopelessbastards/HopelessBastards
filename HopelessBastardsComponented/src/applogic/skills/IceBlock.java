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
	private int manacost = 100;/*Ennyi man�t vesz le a haszn�lata a t�mad�snak*/

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
			setSkillStartedMainTime(appTime);/*A skillkezd�si id�t be�ll�tom a j�t�k f�idej�re*/
			setIsactivated(true);/*akt�vnak tekintj�k innent�l a skillt*/
			//player.monitorScreenmanager.skill0useable = false;/*A skillbaron elsz�rk�tj�k a k�pet, ami a skillt k�pviseli*/
			
			
			setViewBuilderActivate(true);
		//	boltSong.play();
			
			/*A playern�l leveszem a man�t,amibe a skill ker�lt.*/
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
			
			/*alap�rtelmezett player tulajdons�gok vissza�ll�t�sa.*/
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