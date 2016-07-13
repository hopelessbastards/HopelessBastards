package applogic.components.viewbuilders;

import java.awt.Color;

import applogic.components.IComponent;
import applogic.viewbuilder.IStringViewBuilder;
import screenconverter.descriptors.StringDescriptor;

public class OptionPaneTextViewBuilder extends IStringViewBuilder{

	private StringDescriptor[] describers;
	private IComponent optionPane;
	
	public OptionPaneTextViewBuilder(IComponent optionPane) {
		this.optionPane = optionPane;
		this.describers = new StringDescriptor[1];
		describers[0] = new StringDescriptor(0,0,40, 0,0,0,optionPane.toStringTransformation(),Color.red,"Chiller");
	}
	
	@Override
	public StringDescriptor[] getStringDescriptor() {
		if(this.optionPane.isShowNow()){
			this.describers[0].setX(optionPane.getX() + 140);
			this.describers[0].setY(optionPane.getY() + 100);
			this.describers[0].setString(optionPane.toStringTransformation());
		}else{
			this.describers[0].setX(20000);
		}
		
		
		return this.describers;
	}
}