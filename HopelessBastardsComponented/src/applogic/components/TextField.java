package applogic.components;

import java.awt.Rectangle;
import java.util.Random;

import applogic.components.viewbuilders.CharSparkViewBuilder;
import applogic.components.viewbuilders.TextBoxViewBuilder;
import applogic.components.viewbuilders.TextFieldLabelViewBuilder;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;
import applogic.viewbuilder.IStringViewBuilder;
import controller.events.KeyValue;
import controller.events.KeyValueConstant;
import soundapi.ISound;
import soundapi.ISoundProvider;
import soundapi.MP3;

public class TextField extends IComponent{

	/*Szövegbeviteli mezõ komponens.*/
	
	private StringBuilder text;
	
	/*segédváltozó*/
	private Rectangle helperRectangle;
	private IRectangleViewBuilder textBox;/*A doboz megjelenítése*/
	private IStringViewBuilder label;/*A szöveg megjelenítése*/
	private IImageViewBuilder spark;/*A szikra megjelenítése*/
	
	private String stringHelper;
	private Random random;
	private ISound typeSong;

	public TextField(int x, int y, int width, int height, IContainer container,ISoundProvider soundProvider,boolean sparkAble) {
		super(x, y, width, height, container);
		text = new StringBuilder();
		container.addComponent(this);
		this.random = new Random();
		typeSong = new MP3("hit" + Integer.toString(random.nextInt(3) + 1),soundProvider);
		
		/*Ugye a szövegmezõ lehet sima is szikra nélkül, ezt létrehozáskor a konstruktorban
		 adhatjuk meg.*/
		if(sparkAble){
			this.spark = new CharSparkViewBuilder(this);
			container.getImages().add(spark);
		}
		
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
		/*Elõször megvizsgáljuk, hogy van e élõ madális komponens, mert ha van, akkor ez semmire
		 sem reagál.*/
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
		return this.text.toString();
	}

	@Override
	public void playSoundEffect() {
		
	}
}