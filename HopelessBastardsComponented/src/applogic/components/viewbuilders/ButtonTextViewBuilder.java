package applogic.components.viewbuilders;

import java.awt.Color;

import applogic.components.IComponent;
import applogic.viewbuilder.IStringViewBuilder;
import screenconverter.descriptors.StringDescriptor;

public class ButtonTextViewBuilder extends IStringViewBuilder{
	private StringDescriptor[] describers;
	private IComponent button;
	
	public ButtonTextViewBuilder(IComponent button) {
		this.button = button;
		this.describers = new StringDescriptor[1];
		describers[0] = new StringDescriptor(0,0,10, 0,0,0,button.toStringTransformation(),Color.black,"Arial");
	}
	
	@Override
	public StringDescriptor[] getStringDescriptor() {
		this.describers[0].setX(button.getX());
		this.describers[0].setY(button.getY() + button.getHeight());
		if(button.isHovered()){
			this.describers[0].setColor(Color.black);
		}else{
			this.describers[0].setColor(Color.white);
		}
		this.describers[0].setSize(button.getHeight());
		
		
		
		return describers;
	}	
}
