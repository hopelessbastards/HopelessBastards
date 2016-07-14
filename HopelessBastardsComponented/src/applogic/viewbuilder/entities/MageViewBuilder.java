package applogic.viewbuilder.entities;

import applogic.AnimationHandler;
import applogic.IAnimationHandler;
import applogic.IViewBuilderContainer;
import applogic.elements.characters.Mage;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;
import applogic.viewbuilder.simpleshapes.EntityAppointerRectangle;
import applogic.viewbuilder.simpleshapes.HealtBarRectangle;
import screenconverter.descriptors.ImageDescriptor;

public class MageViewBuilder extends IImageViewBuilder{

	private Mage mage;
	private ImageDescriptor[] describers;
	private IAnimationHandler animationHandler;
	private IAnimationHandler attackanimation;
	private IAnimationHandler usingHandler;
	
	private IViewBuilderContainer container;
	
	/*Ez a piros négyzet, ami a karakterek felett jelenik meg, ha az egeret feléjük húzzák.*/
	private IRectangleViewBuilder appointer;
	
	public MageViewBuilder(Mage mage,IViewBuilderContainer container) {
		this.container = container;
		
		describers = new ImageDescriptor[1];
		describers[0] = new ImageDescriptor(10, 10, 10, 10, 10, mage.getWidth(), 1000, "fire", 0);
		this.mage = mage;
		animationHandler = new AnimationHandler(60, 2,true,"mage");
		this.attackanimation = new AnimationHandler(80, 1, false,"mageattack");
		usingHandler = animationHandler;
		container.getRectangleBuilder().add(new HealtBarRectangle(mage));
		
	}
	
	/**
	 * Get the image description.
	 * 
	 * The mage animation depends on normal or on attack state.
	 */
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		
		if (appointer == null && mage.getCollideArea().contains(container.getCursorInformationProvider().getMouse())){
			appointer = new EntityAppointerRectangle(mage);
			container.getRectangleBuilder().add(this.appointer);
		} else {
			/*Ha az egérrel nem a karakter felett vagyunk, akkor ha a kijelölõ négyzet kivan
			 rajzolva, akkor tüntessük már el.*/
			if (appointer != null){
				appointer.setDeletable(true);
				appointer = null;
			}
		}
				
		for (int i = 0; i < mage.getSkillCount(); i++) {
			if(mage.getSkills()[i] != null && (mage.getSkills()[i].isViewBuilderActivate())){
				usingHandler = attackanimation;
				attackanimation.setAnimationable(true);
				mage.getSkills()[i].setViewBuilderActivate(false);
				break;
			}
		}
		
		if (attackanimation.getAnimationable() == false) {
			usingHandler = animationHandler;
		}
		
		describers[0].setAnimation(usingHandler.animationPiece());
		describers[0].setImageLogicalName(usingHandler.getLogicName());
		
		describers[0].setAngle(mage.getAngle());
		describers[0].setAngleCenterPointX((int)mage.getX()+mage.getWidth()/2);
		describers[0].setAngleCenterPointY((int)mage.getY() + mage.getHeight()/2);
		
		describers[0].setHeight(mage.getHeight());
		describers[0].setWidth(mage.getWidth());
		describers[0].setX((int)mage.getX());
		describers[0].setY((int)mage.getY());
		
		return describers;
	}
}