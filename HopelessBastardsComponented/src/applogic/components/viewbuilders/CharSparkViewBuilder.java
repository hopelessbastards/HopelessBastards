package applogic.components.viewbuilders;

import applogic.AnimationHandler;
import applogic.IAnimationHandler;
import applogic.components.IComponent;
import applogic.elements.BasicElement;
import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.descriptors.ImageDescriptor;

public class CharSparkViewBuilder extends IImageViewBuilder{
	/*Ez az soztály írja le a bejelentekzõ betûk szikraanimációját.*/
	private IComponent textField;
	private ImageDescriptor[] describers;
	private IAnimationHandler animationHandler;
	private int oldTextFieldLenght;
	
	public CharSparkViewBuilder(IComponent textField) {
		this.textField = textField;
		this.describers = new ImageDescriptor[1];
		this.describers[0] = new ImageDescriptor(0,0,0,0,0,50,50,"spark",0);
		animationHandler = new AnimationHandler(60, 4,false,"spark");
		animationHandler.setAnimationable(false);
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		/*Ha új karaktert ütünk be, akkor az animáció lejátszható, azaz állítsuk be
		 az animationHandler-t animatonablere.*/
		if(oldTextFieldLenght < textField.toStringTransformation().length()){
			animationHandler.setAnimationable(true);
		}
		
		if(animationHandler.getAnimationable()){
			this.oldTextFieldLenght = textField.toStringTransformation().length();
			this.describers[0].setX(textField.getX() + textField.toStringTransformation().length() * (int)(textField.getHeight()/1.5));
			this.describers[0].setY(textField.getY() - textField.getHeight()/2);
			this.describers[0].setAnimation(animationHandler.animationPiece());
		}else{
			this.oldTextFieldLenght = textField.toStringTransformation().length();
			this.describers[0].setX(20000);
		}
		
		return this.describers;
	}

	@Override
	public BasicElement getTheRepresentetedElement() {
		return null;
	}
}
