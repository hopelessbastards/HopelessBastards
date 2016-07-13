package applogic.components;

import java.awt.Rectangle;

import applogic.components.viewbuilders.OptionPaneBoxViewBuilder;
import applogic.components.viewbuilders.OptionPaneTextViewBuilder;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.IStringViewBuilder;
import controller.events.KeyValue;
import soundapi.ISound;
import soundapi.ISoundProvider;
import soundapi.MP3;

public class OptionPane extends IComponent{

	/*Felugró ablak osztálya.*/
	
	private String warningLabel;
	private IImageViewBuilder boxBuilder;
	private IStringViewBuilder warningLabelBuilder;
	
	private Rectangle helperRectangle;
	private ISound warningSong;
	
	public OptionPane(int x, int y, int width, int height,String warningLabel, IContainer container,ISoundProvider soundProvider) {
		super(x, y, width, height, container);
		this.warningLabel = warningLabel;
		boxBuilder = new OptionPaneBoxViewBuilder(this);
		warningLabelBuilder = new OptionPaneTextViewBuilder(this);
		this.warningSong = new MP3("error", soundProvider);
		
		container.addComponent(this);
		container.getImages().add(boxBuilder);
		container.getLabels().add(warningLabelBuilder);
		this.helperRectangle = new Rectangle();
		
	}

	@Override
	public void keyhandle(KeyValue value) {
		
	}

	@Override
	public String toStringTransformation() {
		return this.warningLabel;
	}
	
	private Rectangle getArea(){
		this.helperRectangle.setBounds(getX(), getY(), getWidth(), getHeight());
		return this.helperRectangle;
	}

	@Override
	public void tick() {
		/*A felugró ablak tick metódusa, ami a megjelenítésért, felugrásért felelõs.*/
		if(isShowNow()){
			getContainer().setModalComponent(this);
		}else{
			getContainer().setModalComponent(null);
		}
		
		if(getArea().contains(getContainer().getCursor().getMouse())){
			setHovered(true);
		}else{
			setHovered(false);
		}
		
		if(getArea().contains(getContainer().getCursor().getMouse()) && getContainer().getCursor().isClick()){
			setShowNow(false);
			getContainer().setModalComponent(null);
		}
	}

	@Override
	public void playSoundEffect() {
		this.warningSong.play();
	}
}