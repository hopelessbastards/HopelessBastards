package applogic.loginstate;

import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.IConverter;
import screenconverter.descriptors.ImageDescriptor;

public class BackgroundImageViewBuilder extends IImageViewBuilder{
	
	private ImageDescriptor[] describers;
	
	private IConverter converter;
	
	public BackgroundImageViewBuilder(IConverter converter) {
		this.converter = converter;
		this.describers = new ImageDescriptor[1];
		this.describers[0] = new ImageDescriptor(0, 0,0,0,0,100,26,"darkBackground",0);
	}
	

	@Override
	public ImageDescriptor[] getImageDescriptor() {
		describers[0].setWidth(this.converter.getCanvasSize().getWIDTH());
		describers[0].setHeight(this.converter.getCanvasSize().getHEIGHT());
		
		return this.describers;
	}
}