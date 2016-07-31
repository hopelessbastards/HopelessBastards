package applogic.skills.viewbuilder;

import applogic.IViewBuilderContainer;
import applogic.elements.BasicElement;
import applogic.elements.Entity;
import applogic.skills.AbstractSkill;
import applogic.skills.imageicons.ImageIcons;
import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.descriptors.ImageDescriptor;

public class ZombieSimpleAttackViewBuilder extends IImageViewBuilder{
	
	private ImageIcons icon;
		
	public ZombieSimpleAttackViewBuilder(AbstractSkill skill,IViewBuilderContainer container,Entity player) {
		this.icon = new ImageIcons(skill, container.getMonitorScreenManager(), player,"zombieattackimage");
		
		container.getViewBuilder().add(this);
		container.getStaticviewBuilder().add(this.icon);
	}

	@Override
	public void setDeletable(boolean deletable) {
		super.setDeletable(deletable);
		this.icon.setDeletable(deletable);
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		return null;
	}

	@Override
	public BasicElement getTheRepresentetedElement() {
		// TODO Auto-generated method stub
		return null;
	}
}