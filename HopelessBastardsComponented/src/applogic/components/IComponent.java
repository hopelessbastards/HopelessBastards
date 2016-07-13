package applogic.components;

import java.util.ArrayList;
import java.util.List;

import controller.events.KeyValue;

public abstract class IComponent {
	/*Ez minden komponens õse(gomboké,beviteli szövegdobozoké,stb).*/
	private int x;
	private int y;
	private int width;
	private int height;
	
	private boolean hovered;/*Tudja magáról minden komponens, hogy most épp felette van-e az egér.*/
	private boolean deletable;/*A GarbageCollector számára nagyon fontos információ.Ha el kell tüntetni
	ezt a komponenst, akkor ennek az adattagnak az értéke true;*/
	
	private List<ActionListener> actionListeners;/*A komponens eseményeire feliratkozó
	Listenerek, amelyek eseményt kezelnek.*/
	
	private IContainer container;
	
	private IFocus focus;/*Ismernie kell a focust.*/
	
	private boolean showNow;/*Megjelenik-e a képernyõn vagy sem.Ezt leginkább az optionPane
	miatt kellett bevezetni.*/
	
	public IComponent(int x, int y,int width,int height,IContainer container) {
		super();
		this.container = container;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.actionListeners = new ArrayList<ActionListener>();
		this.focus = container.getFocus();
	}
	
	public abstract void keyhandle(KeyValue value);
	
	public abstract String toStringTransformation();/*Minden komponens reprezentál valami
	Stringek, vagy tartalmaz feliratot, vagy szöveget jelenít meg.Ezzel a metódussal kérhetjük
	el tõle.Olyan funkciója van mint a toStringnek.*/
	
	/*Minden komponens tud hangot kiadni, és kívülrõl ezen metóduson keresztül kényszeríthetjük
	 hang lejátszására.*/
	public abstract void playSoundEffect();
	
	/*Ezzel a metódussal állít be focusbirtokost a komponens.Itt magát és mást is beállíthat.*/
	public void setFocusOwner(IComponent component){
		focus.setFocusOwnerComponent(component);
	}
	
	public IFocus getFocus() {
		return focus;
	}
	
	public abstract void tick();
	
	
	public boolean isShowNow() {
		return showNow;
	}

	public void setShowNow(boolean showNow) {
		this.showNow = showNow;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public IContainer getContainer() {
		return container;
	}

	public void setContainer(IContainer container) {
		this.container = container;
	}

	public boolean isHovered() {
		return hovered;
	}

	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}

	public void addActionListener(ActionListener listener){
		actionListeners.add(listener);
	}
	
	public void removeActionListener(ActionListener listener){
		actionListeners.remove(listener);
	}
	
	public boolean isDeletable() {
		return deletable;
	}
	
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public List<ActionListener> getActionListeners() {
		return actionListeners;
	}

	public void setActionListeners(List<ActionListener> actionListeners) {
		this.actionListeners = actionListeners;
	}
}