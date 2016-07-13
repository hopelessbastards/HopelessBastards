package applogic.skills.viewbuilder;

import applogic.AnimationHandler;
import applogic.IAnimationHandler;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.skills.AbstractSkill;
import applogic.skills.imageicons.ImageIcons;
import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.descriptors.ImageDescriptor;

public class IceBlockViewBuilder extends IImageViewBuilder{

	private AbstractSkill skill;
	private ImageDescriptor[] describers;
	
	private IAnimationHandler animationHandler;

	private Entity player;
	
	private IViewBuilderContainer container;
	
	private ImageIcons icon;
	private boolean lastIsActivated;
	
	public IceBlockViewBuilder(AbstractSkill skill,IViewBuilderContainer container,Entity player) {
		this.icon = new ImageIcons(skill, container.getMonitorScreenManager(), player,"iceblockicon");
		this.container = container;
		
		this.skill = skill;
		describers = new ImageDescriptor[1];
		
		describers[0] = new ImageDescriptor(10, 10,0,0,0,128,128,"iceblock", 0);
	
		animationHandler = new AnimationHandler(10, 44,false);
		
		this.player = player;
		
		this.container.getViewBuilder().add(this);
		this.container.getStaticviewBuilder().add(this.icon);
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		super.setDeletable(deletable);
		this.icon.setDeletable(deletable);
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		if(skill.isIsactivated() == false && lastIsActivated == true){
			animationHandler.setAnimationable(true);
			skill.setViewBuilderActivate(false);
		}
		lastIsActivated = skill.isIsactivated();
		
		/*Erre a blokkra csak akkor fut rá, ha az animationHandler animatoanable boleanja true,
		 ekkor ad következõ képkockát az animation handler*/
		if(animationHandler.getAnimationable()){
			
			describers[0].setX((int)player.getX() - player.getWidth()/2);
			describers[0].setY((int)player.getY() - player.getHeight()/2);
			describers[0].setAngle(player.getAngle());
			describers[0].setAngleCenterPointX((int)player.getX() + player.getWidth()/2);
			describers[0].setAngleCenterPointY((int)player.getY() + player.getHeight()/2);
			describers[0].setAnimation(animationHandler.animationPiece());
		}else if(skill.isIsactivated()){
			describers[0].setX((int)player.getX() - player.getWidth()/2);
			describers[0].setY((int)player.getY() - player.getHeight()/2);
			describers[0].setAngle(player.getAngle());
			describers[0].setAngleCenterPointX((int)player.getX() + player.getWidth()/2);
			describers[0].setAngleCenterPointY((int)player.getY() + player.getHeight()/2);
		}else{
			/*Egyébként ,hogy ne a képernyõre rajzolja a skill egyik animációs képkockáját,
			 ezért a skill kordiniátáit, ha épp nincs szükség a kirajzolására, akkor beállítjuk 
			 irrális értékekre, kirajzolni így úgysem foglya semmi, hisz sosem lesz benne a képernyõben,
			 erre a Converter komponens a garancia.*/
			describers[0].setX(200000);
			describers[0].setY(-20000);
		}
		
		return this.describers;
	}
}