package applogic.skills.viewbuilder;

import applogic.IViewBuilderContainer;
import applogic.elements.BasicElement;
import applogic.elements.Entity;
import applogic.skills.AbstractSkill;
import applogic.skills.imageicons.ImageIcons;
import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.descriptors.ImageDescriptor;

public class ChangePlayerSkillViewBuilder extends IImageViewBuilder{
	/*Minden skillhez tartozik egy animáció leíró, még ha nincs is látványos animáció hozá, azaz például
	 életet lop, akkor nem kell robbanó és semmi animáció, ekkor a Descriptoként nullt ad vissza, viszont
	 a kis iconképet tudja magáról , és beállítja azt.*/
	
	private ImageIcons icon;
	
	public ChangePlayerSkillViewBuilder(AbstractSkill skill,IViewBuilderContainer container,Entity player) {
		this.icon = new ImageIcons(skill, container.getMonitorScreenManager(), player,"bodycontroll");
		
		
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