package controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import controller.events.ActivationNumber;
import controller.events.CursorInformation;
import controller.events.KeyValue;
import controller.events.KeyValueConstant;
import controller.events.MoveWayEnum;
import controller.events.MovedWay;
import graphicsEngine.ICanvas;

public class Controller implements IController,KeyListener,MouseMotionListener,MouseWheelListener,MouseListener{

	private ICanvas canvas;
	
	private Point press;
	private Point release;
	private Point dragg;
	private Point move;
	
	private MenuListener mlistener = null;
	private GameListener glistener = null;
	private ActivationNumber number;
	private CursorInformation screen;
	private KeyValue value;
	private MovedWay way;
	private boolean englishCharacterHelper;
	private Character characterBoxingType;
	private String validCharacters = "0123456789qwertzuiopasdfghjklyxcvbnm,.*QWERTZUIOPASDFGHJKLYXCVBNM@";
	
	public Controller(ICanvas canvas) {
		this.canvas = canvas;
		number = new ActivationNumber(0);
		screen = new CursorInformation(new Point(1,2), 1, 1);
		value = new KeyValue();
		way = new MovedWay(MoveWayEnum.DOWN);
		
		press = new Point();
		release = new Point();
		dragg = new Point();
		move = new Point();
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		press.setLocation(e.getXOnScreen() - canvas.getCanvasRectangleOnScreen().x, 
				e.getYOnScreen() - canvas.getCanvasRectangleOnScreen().y);
		this.screen.setLocationOnScreen(press);
		this.screen.setxOnScreen(e.getXOnScreen() - canvas.getCanvasRectangleOnScreen().x);
		this.screen.setyOnScreen(e.getYOnScreen() - canvas.getCanvasRectangleOnScreen().y);
		
		if(mlistener != null){
			mlistener.cursorClicked(screen);
		}else if(glistener != null){
			glistener.cursorClicked(screen);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(mlistener != null){
			mlistener.cursorRelease();
		}else if(glistener != null){
			glistener.cursorRelease();
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		dragg.setLocation(e.getXOnScreen() - canvas.getCanvasRectangleOnScreen().x, 
				e.getYOnScreen() - canvas.getCanvasRectangleOnScreen().y);
		this.screen.setLocationOnScreen(dragg);
		this.screen.setxOnScreen(e.getXOnScreen() - canvas.getCanvasRectangleOnScreen().x);
		this.screen.setyOnScreen(e.getYOnScreen() - canvas.getCanvasRectangleOnScreen().y);
		
		if(mlistener != null){
			mlistener.cursorMOved(screen);
		}else if(glistener != null){
			glistener.cursorMOved(screen);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		move.setLocation(e.getXOnScreen() - canvas.getCanvasRectangleOnScreen().x, 
				e.getYOnScreen() - canvas.getCanvasRectangleOnScreen().y);
		this.screen.setLocationOnScreen(move);
		this.screen.setxOnScreen(e.getXOnScreen()  - canvas.getCanvasRectangleOnScreen().x);
		this.screen.setyOnScreen(e.getYOnScreen() - canvas.getCanvasRectangleOnScreen().y);
		
		if(mlistener != null){
			mlistener.cursorMOved(screen);
		}else if(glistener != null){
			glistener.cursorMOved(screen);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(mlistener != null){
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				value.setKeyType(KeyValueConstant.ESCAPE);
				value.setValue(null);
			}else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
				value.setKeyType(KeyValueConstant.DELETE);
				value.setValue(null);
			}else if(e.getKeyCode() == KeyEvent.VK_SPACE){
				value.setKeyType(KeyValueConstant.SPACE);
				value.setValue(null);
			}else if(e.getKeyCode() == KeyEvent.VK_ENTER){
				value.setKeyType(KeyValueConstant.ENTER);
				value.setValue(null);
			}else{
				for(int i=0;i<this.validCharacters.length();i++){
					this.characterBoxingType = e.getKeyChar();
					if(characterBoxingType.equals(this.validCharacters.charAt(i))){
						this.englishCharacterHelper = true;
					}
				}
				if(this.englishCharacterHelper){
					value.setKeyType(null);
					value.setValue(e.getKeyChar());
					this.englishCharacterHelper = false;
				}
			}
			
			mlistener.characterTyped(value);
			/*Az új leótésnek alapértelmezésbe állítjuk az értékeket,hogy az bármi lehessen.*/
			value.setKeyType(null);
			value.setValue(null);
			
		}else if(glistener != null){
			if(e.getKeyCode() == KeyEvent.VK_W){
				way.setWay(MoveWayEnum.UP);
				glistener.characterMoved(way);
			}else if(e.getKeyCode() == KeyEvent.VK_A){
				way.setWay(MoveWayEnum.LEFT);
				glistener.characterMoved(way);
			}else if(e.getKeyCode() == KeyEvent.VK_D){
				way.setWay(MoveWayEnum.RIGHT);
				glistener.characterMoved(way);
			}else if(e.getKeyCode() == KeyEvent.VK_S){
				way.setWay(MoveWayEnum.DOWN);
				glistener.characterMoved(way);
			}else if(e.getKeyCode() == KeyEvent.VK_0){
				number.setNumber(0);
				glistener.characterActivation(number);
			}else if(e.getKeyCode() == KeyEvent.VK_1){
				number.setNumber(1);
				glistener.characterActivation(number);
			}else if(e.getKeyCode() == KeyEvent.VK_2){
				number.setNumber(2);
				glistener.characterActivation(number);
			}else if(e.getKeyCode() == KeyEvent.VK_3){
				number.setNumber(3);
				glistener.characterActivation(number);
			}else if(e.getKeyCode() == KeyEvent.VK_4){
				number.setNumber(4);
				glistener.characterActivation(number);
			}else if(e.getKeyCode() == KeyEvent.VK_5){
				number.setNumber(5);
				glistener.characterActivation(number);
			}else if(e.getKeyCode() == KeyEvent.VK_6){
				number.setNumber(6);
				glistener.characterActivation(number);
			}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				value.setKeyType(KeyValueConstant.ESCAPE);
				glistener.characterTyped(value);
				value.setValue(null);
			}else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
				value.setKeyType(KeyValueConstant.DELETE);
				glistener.characterTyped(value);
				value.setValue(null);
			}else if(e.getKeyCode() == KeyEvent.VK_SPACE){
				value.setKeyType(KeyValueConstant.SPACE);
				glistener.characterTyped(value);
				value.setValue(null);
			}else if(e.getKeyCode() == KeyEvent.VK_ENTER){
				value.setKeyType(KeyValueConstant.ENTER);
				glistener.characterTyped(value);
				value.setValue(null);
			}else{
				for(int i=0;i<this.validCharacters.length();i++){
					this.characterBoxingType = e.getKeyChar();
					if(characterBoxingType.equals(this.validCharacters.charAt(i))){
						this.englishCharacterHelper = true;
					}
				}
				if(this.englishCharacterHelper){
					value.setKeyType(null);
					value.setValue(e.getKeyChar());
					glistener.characterTyped(value);
					this.englishCharacterHelper = false;
				}
			}
			
			/*Az új leótésnek alapértelmezésbe állítjuk az értékeket,hogy az bármi lehessen.*/
			value.setKeyType(null);
			value.setValue(null);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(mlistener != null){
			
		}else if(glistener != null){
			if(e.getKeyCode() == KeyEvent.VK_A){
				way.setWay(MoveWayEnum.LEFT);
				glistener.characterStopMoving(way);
			}else if(e.getKeyCode() == KeyEvent.VK_S){
				way.setWay(MoveWayEnum.DOWN);
				glistener.characterStopMoving(way);
			}else if(e.getKeyCode() == KeyEvent.VK_D){
				way.setWay(MoveWayEnum.RIGHT);
				glistener.characterStopMoving(way);
			}else if(e.getKeyCode() == KeyEvent.VK_W){
				way.setWay(MoveWayEnum.UP);
				glistener.characterStopMoving(way);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void addMenuListener(MenuListener listener) {
		this.mlistener = listener;
	}

	@Override
	public void addGameListener(GameListener listener) {
		this.glistener = listener;
	}

	@Override
	public void removeMenuListener() {
		this.mlistener = null;
	}

	@Override
	public void removeGameListener() {
		this.glistener = null;
	}

	@Override
	public void removeAllListeners() {
		this.glistener = null;
		this.mlistener = null;
	}
}