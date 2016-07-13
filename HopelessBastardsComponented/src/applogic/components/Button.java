package applogic.components;

import java.awt.Rectangle;
import applogic.components.viewbuilders.ButtonTextViewBuilder;
import applogic.components.viewbuilders.ButtonViewBuilder;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.IStringViewBuilder;
import controller.events.KeyValue;

public class Button extends IComponent{

	private String label;
	private IImageViewBuilder buttonImage;
	private IStringViewBuilder labelImage;
	private Rectangle helperRectangle;
	
	public Button(int x, int y,int width,int height,String label,IContainer container) {
		super(x, y,width,height,container);
		this.label = label;
		buttonImage = new ButtonViewBuilder(this);
		labelImage = new ButtonTextViewBuilder(this);
		container.addComponent(this);
		container.getImages().add(buttonImage);
		container.getLabels().add(labelImage);
		
		this.helperRectangle = new Rectangle();
	}
	
	public Button(int x, int y,int width,int height,IContainer container) {
		super(x, y,width,height,container);
		container.addComponent(this);
		this.helperRectangle = new Rectangle();
	}
	
	public IImageViewBuilder getButtonImage() {
		return buttonImage;
	}

	public void setButtonImage(IImageViewBuilder buttonImage) {
		this.buttonImage = buttonImage;
	}

	public IStringViewBuilder getLabelImage() {
		return labelImage;
	}

	public void setLabelImage(IStringViewBuilder labelImage) {
		this.labelImage = labelImage;
	}

	public Rectangle getHelperRectangle() {
		return helperRectangle;
	}

	public void setHelperRectangle(Rectangle helperRectangle) {
		this.helperRectangle = helperRectangle;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	private Rectangle getArea(){
		this.helperRectangle.setBounds(getX(), getY(), getWidth(), getHeight());
		return this.helperRectangle;
	}
	
	@Override
	public void tick() {
		/*Elõször is megvizsgáljuk van-e modális elem, mert ha igen, akkor a buttonnak semmire
		 nem szabad reagálnia.*/
		if(getContainer().getModalComponent() == null){
			if(getArea().contains(getContainer().getCursor().getMouse())){
				setHovered(true);
			}else{
				setHovered(false);
			}
			
			if(getArea().contains(getContainer().getCursor().getMouse()) && getContainer().getCursor().isClick()){
				for(int i=0;i<getActionListeners().size();i++){
					getActionListeners().get(i).actionCommand();
				}
			}
		}
		
	}

	@Override
	public void keyhandle(KeyValue value) {
	}

	@Override
	public String toStringTransformation() {
		return this.label;
	}

	@Override
	public void playSoundEffect() {
		// TODO Auto-generated method stub
		
	}
}