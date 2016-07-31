package applogic.components.viewbuilders;

import applogic.components.IComponent;
import applogic.elements.BasicElement;
import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.descriptors.ImageDescriptor;

public class ButtonViewBuilder extends IImageViewBuilder{

	private ImageDescriptor[] describers;
	private IComponent button;
	
	public ButtonViewBuilder(IComponent button) {
		this.button = button;
		this.describers = new ImageDescriptor[1];
		this.describers[0] = new ImageDescriptor(0,0,0,0,0,100,100,"nonhoverbutton",0);
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		this.describers[0].setX(button.getX());
		this.describers[0].setY(button.getY());
		this.describers[0].setWidth(button.getWidth());
		this.describers[0].setHeight(button.getHeight());
		
		if(button.isHovered()){
			this.describers[0].setImageLogicalName("hoveredbutton");
		}else{
			this.describers[0].setImageLogicalName("nonhoveredbutton");
		}
		return this.describers;
	}

	@Override
	public BasicElement getTheRepresentetedElement() {
		return null;
	}
}