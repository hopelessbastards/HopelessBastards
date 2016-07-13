package applogic.components.viewbuilders;

import java.awt.Color;

import applogic.components.IComponent;
import applogic.viewbuilder.IRectangleViewBuilder;
import screenconverter.descriptors.ImageDescriptor;
import screenconverter.descriptors.RectangleDescriptor;

public class TextBoxViewBuilder extends IRectangleViewBuilder{
	
	private RectangleDescriptor[] describers;
	private IComponent textField;
	
	public TextBoxViewBuilder(IComponent textField) {
		this.textField = textField;
		this.describers = new RectangleDescriptor[2];
		this.describers[0] = new RectangleDescriptor(100,100,0,0,0,200,100,Color.GREEN,true);
		this.describers[1] = new RectangleDescriptor(99,99,0,0,0,198,98,Color.red,false);
	}
	
	@Override
	public RectangleDescriptor[] getRectangleDescriptor() {
		this.describers[0].setX(textField.getX());
		this.describers[0].setY(textField.getY());
		this.describers[0].setWidth(textField.getWidth());
		this.describers[0].setHeight(textField.getHeight());
		
		this.describers[1].setX(textField.getX() + 1);
		this.describers[1].setY(textField.getY() + 1);
		this.describers[1].setWidth(textField.getWidth() - 1);
		this.describers[1].setHeight(textField.getHeight() - 1);
		
		if(/*textField.getFocus().getFocusOwnerComponent() != null && */textField.getFocus().getFocusOwnerComponent() == textField){
			this.describers[0].setColor(Color.white);
			this.describers[1].setColor(Color.black);
		}else{
			this.describers[0].setColor(Color.black);
			this.describers[1].setColor(Color.white);
		}
		return this.describers;
	}
}