package applogic.loginstate;

import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.descriptors.ImageDescriptor;

public class LoginDragonViewBuilder extends IImageViewBuilder{

	private ImageDescriptor[] describers;
	
	public LoginDragonViewBuilder() {
		this.describers = new ImageDescriptor[1];
		this.describers[0] = new ImageDescriptor(875, 35,0,0,0,370,210,"loginDragon",0);
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		return this.describers;
	}	
}