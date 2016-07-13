package applogic.components;

import java.awt.Rectangle;
import java.util.Random;

import applogic.components.viewbuilders.TextBoxViewBuilder;
import applogic.components.viewbuilders.TextFieldLabelViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;
import applogic.viewbuilder.IStringViewBuilder;
import controller.events.KeyValue;
import controller.events.KeyValueConstant;
import soundapi.ISound;
import soundapi.ISoundProvider;
import soundapi.MP3;

public class PasswordTextField extends IComponent{
	/*Ugyan az mint a sima textfiled, ugyan azokat tudja, csak annyi különbséggel, hogy itt
	 a megjelenített szöveg az csak csillag karakter.*/
	
	private StringBuilder text;
	private StringBuilder stars;
	private Rectangle helperRectangle;
	private IRectangleViewBuilder textBox;
	private IStringViewBuilder label;
	private Random random;
	private ISound typeSong;
	
	private String stringHelper;
	
	public PasswordTextField(int x, int y, int width, int height, IContainer container,ISoundProvider soundProvider) {
		super(x, y, width, height, container);
		text = new StringBuilder();
		stars = new StringBuilder();
		container.addComponent(this);
		
		this.random = new Random();
		typeSong = new MP3("hit" + Integer.toString(random.nextInt(3) + 1),soundProvider);
		
		this.helperRectangle = new Rectangle();
		this.textBox = new TextBoxViewBuilder(this);
		this.label = new TextFieldLabelViewBuilder(this);
		container.getRectangles().add(textBox);
		container.getLabels().add(label);
	}
	
	private Rectangle getRectangleArea(){
		this.helperRectangle.setBounds(getX(), getY(), getWidth(), getHeight());
		return this.helperRectangle;
	}

	@Override
	public void tick() {
		if(getContainer().getModalComponent() == null){
			if(getRectangleArea().contains(getContainer().getCursor().getMouse())){
				setHovered(true);
			}else{
				setHovered(false);
			}
			
			if(getRectangleArea().contains(getContainer().getCursor().getMouse()) && getContainer().getCursor().isClick()){
				setFocusOwner(this);	
			}
		}
	}

	public StringBuilder getText() {
		return text;
	}

	public void setText(StringBuilder text) {
		this.text = text;
	}

	@Override
	public void keyhandle(KeyValue value) {
		if(getContainer().getModalComponent() == null){
			if((int)(getWidth()/(getHeight()/1.5)) > text.length() || (value.getKeyType() != null && value.getKeyType() == KeyValueConstant.DELETE)){
				typeSong.setLogicName("hit" + Integer.toString(random.nextInt(3) + 1));
				typeSong.play();
				if(value.getValue() != null){
					this.text.append(value.getValue());
				}else{
					if(value.getKeyType() == KeyValueConstant.SPACE){
						this.text.append(" ");
					}else if(value.getKeyType() == KeyValueConstant.DELETE){
						if(this.text.length() > 0){
							stringHelper = text.substring(0,text.length() - 1); 
							this.text.delete(0,text.length());
							this.text.append(stringHelper);
						}
					}
				}
			}
		}
	}

	@Override
	public String toStringTransformation() {
		this.stars.delete(0, stars.length());
		
		for(int i=0;i<this.text.toString().length();i++){
			stars.append("*");
		}
		return this.stars.toString();
	}

	@Override
	public void playSoundEffect() {
		
	}
}
