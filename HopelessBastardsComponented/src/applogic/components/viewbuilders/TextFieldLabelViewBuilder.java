package applogic.components.viewbuilders;

import java.awt.Color;

import applogic.components.IComponent;
import applogic.viewbuilder.IStringViewBuilder;
import screenconverter.descriptors.StringDescriptor;

public class TextFieldLabelViewBuilder extends IStringViewBuilder{
	
	private StringDescriptor[] describers;
	private IComponent textField;
	
	public TextFieldLabelViewBuilder(IComponent textField) {
		this.textField = textField;
		this.describers = new StringDescriptor[1];
		describers[0] = new StringDescriptor(300,300,10, 0,0,0,textField.toStringTransformation(),null,"Impact");
	}
	
	@Override
	public StringDescriptor[] getStringDescriptor() {
		if(textField.getContainer().getModalComponent() == null){
			this.describers[0].setString(textField.toStringTransformation());
			this.describers[0].setX(textField.getX());
			this.describers[0].setY(textField.getY() + textField.getHeight());
			if(textField.getFocus().getFocusOwnerComponent() == textField){
				this.describers[0].setColor(Color.white);
			}else{
				this.describers[0].setColor(Color.black);
			}
			this.describers[0].setSize(textField.getHeight());
		}else{
			this.describers[0].setX(20000);
		}
		
		
		
		return describers;
	}	
}