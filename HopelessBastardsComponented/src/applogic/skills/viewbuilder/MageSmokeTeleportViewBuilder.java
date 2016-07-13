package applogic.skills.viewbuilder;

import applogic.AnimationHandler;
import applogic.IAnimationHandler;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.skills.MageSmokeTeleport;
import applogic.skills.imageicons.ImageIcons;
import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.descriptors.ImageDescriptor;

public class MageSmokeTeleportViewBuilder extends IImageViewBuilder{
	private MageSmokeTeleport skill;
	private ImageDescriptor[] describers;
	/*Ettõl kéri el az animáció következõ képdarabkáját.*/
	private IAnimationHandler animationHandler;

	private Entity player;
	
	private IViewBuilderContainer container;
	
	private ImageIcons icon;
	
	private int animationHelper;
	
	public MageSmokeTeleportViewBuilder(MageSmokeTeleport skill,IViewBuilderContainer container,Entity player) {
		
		this.icon = new ImageIcons(skill, container.getMonitorScreenManager(), player,"smoke");
		this.container = container;
		
		this.skill = skill;
		describers = new ImageDescriptor[2];
		
		describers[0] = new ImageDescriptor(10, 10,0,0,0,skill.getWidth(),skill.getHeight(),"smoketeleport", 0);
		describers[1] = new ImageDescriptor(10, 10,0,0,0,skill.getWidth(),skill.getHeight(),"smoketeleport", 0);
		animationHandler = new AnimationHandler(30, 10,false);
		
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
		if(skill.isViewBuilderActivate()){
			animationHandler.setAnimationable(true);
			skill.setViewBuilderActivate(false);
		}
		
		if(animationHandler.getAnimationable()){
			this.describers[0].setX((int)player.getX() -64);
			this.describers[0].setY((int)player.getY() - 64);
			this.describers[1].setX(skill.getOldPlayerX() - 64);
			this.describers[1].setY(skill.getOldPlayerY() - 64);
			this.animationHelper = animationHandler.animationPiece();
			describers[0].setAnimation(this.animationHelper);
			describers[1].setAnimation(this.animationHelper);
		
		}else{
			
			describers[0].setX(200000);
			describers[0].setY(-20000);
			describers[1].setX(200000);
			describers[1].setY(-20000);
		}	
		
		return this.describers;
	}
}
