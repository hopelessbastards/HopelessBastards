package applogic.components.viewbuilders;

import applogic.components.IComponent;
import applogic.elements.BasicElement;
import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.descriptors.ImageDescriptor;

public class LoginButtonViewBuilder extends IImageViewBuilder{
	private ImageDescriptor[] describers;
	private IComponent button;
	
	public LoginButtonViewBuilder(IComponent button) {
		this.button = button;
		this.describers = new ImageDescriptor[1];
		this.describers[0] = new ImageDescriptor(0,0,0,0,0,100,100,"loginButton1",0);
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		this.describers[0].setX(button.getX());
		this.describers[0].setY(button.getY());
		this.describers[0].setWidth(button.getWidth());
		this.describers[0].setHeight(button.getHeight());
		
		if(button.isHovered()){
			this.describers[0].setImageLogicalName("loginButton2");
		}else{
			this.describers[0].setImageLogicalName("loginButton1");
		}
		
		return this.describers;
	}

	@Override
	public BasicElement getTheRepresentetedElement() {
		return null;
	}
}
