package applogic.components.viewbuilders;

import applogic.components.IComponent;
import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.descriptors.ImageDescriptor;

public class OptionPaneBoxViewBuilder extends IImageViewBuilder{
	
	private ImageDescriptor[] describers;
	private IComponent optionPane;
	
	public OptionPaneBoxViewBuilder(IComponent optionPane) {
		this.optionPane = optionPane;
		this.describers = new ImageDescriptor[1];
		this.describers[0] = new ImageDescriptor(optionPane.getX(),optionPane.getY(),0,0,0,optionPane.getWidth(),optionPane.getHeight(),"warning",0);
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		if(optionPane.isShowNow()){
			this.describers[0].setX(200);
			this.describers[0].setY(200);
		}else{
			this.describers[0].setX(20000);
			this.describers[0].setY(20000);
		}
		return this.describers;
	}
}