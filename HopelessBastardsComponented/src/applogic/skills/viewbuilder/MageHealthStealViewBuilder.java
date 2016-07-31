package applogic.skills.viewbuilder;

import applogic.IViewBuilderContainer;
import applogic.elements.BasicElement;
import applogic.elements.Entity;
import applogic.skills.AbstractSkill;
import applogic.skills.imageicons.ImageIcons;
import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.descriptors.ImageDescriptor;

public class MageHealthStealViewBuilder extends IImageViewBuilder{
	private ImageIcons icon;
	
	public MageHealthStealViewBuilder(AbstractSkill skill,IViewBuilderContainer container,Entity player) {
		this.icon = new ImageIcons(skill, container.getMonitorScreenManager(), player,"healthsteal");
		
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
		return null;
	}
}