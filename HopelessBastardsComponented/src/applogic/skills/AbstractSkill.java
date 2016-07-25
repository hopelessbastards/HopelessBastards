package applogic.skills;

import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.IEnvironment;
import applogic.viewbuilder.IImageViewBuilder;
import soundapi.ISoundProvider;

public abstract class AbstractSkill {
	/*Ez az osztály arra szolgál,hogy definiáljon közös elemeket, ami minden skillben benne kell,hogy legyen.
	 Ez az osztály ezért is jó, mert a skill referencia változóknak ez lesz a statikus típusa,így a 7 skillt egy 7 elemû
	 AbstractSkill tömbben fogom tárolni.*/

	private Entity skillOwner;/*Minden skill ismeri az entitást,ami használja õt*/
	
	private IImageViewBuilder skillViewBuilder;
	
	private double skillStartedMainTime = 0;/*Ezt nullára állítom,ez az az idõ, ami a skill kezdési ideje lesz*/
	
	private int timeuntilcdend;/*Ebbe az idõt tárolom másodpercben,ami még hátravan a cd lejárásáig,azaz amíg újra
	használhatok egy képességet*/
	
	private int cdtime;/*cd ideje másodpercben*/
	
	private int secWhileActive;/*Egy skill akár hosszabb ideig is aktív lehet pl.buff, de a lövés csak adott pillanatban
	aktív*/
	
	private boolean deletable;/*A skilleket is lehet törölni, ugyebár ha egy entitás meghal,
	akkor a neki létrehozott képességeket is törölni kell.*/
	
	private boolean isactivated = false;/*aktiválva van-e a skill, ez azért fontos , mert a player objektumban
	minden skillre van egy objektum, és ez a változó jelzi,hogy a skill mûködik-e vagy sem.*/
	
	// Nem biztos, hogy szökség lesz rápublic boolean animaionStillRuning = false;/*Ez a változó amíg igaz, addig kell kirajzolni a képességhez tartozó animációt.*/
	
	private int skillnumber;/*Minden skillnek tudnia kell magáról, hogy hanyadik skill.*/
	
	private IEnvironment environment;
	
	private IViewBuilderContainer container;
	
	private boolean networkActivate;
	
	private int width;
	private int height;
	
	private boolean viewBuilderActivate;/*Ez jelzi, hogy aktiválni kell-e a skillhez tartozó képi
	animációt vagy sem.*/
	
	public AbstractSkill(Entity skillOwner,IEnvironment environment,IViewBuilderContainer container,int skillnumber) {
		this.skillOwner = skillOwner;
		this.environment = environment;
		this.skillnumber = skillnumber;
		this.container = container;
	}
	
	public abstract void activateSkill(double appTime);/*Minden skillnek kell lennie egy aktiváló metódusnak*/
	
	public abstract void activateSkillByServer(double appTime);
	
	public abstract void tick(double appTime);
	
	public abstract Polygon getPolygon();
	
	public abstract Rectangle getBounds();/*Minden skill vissza kell tudjon adni egy körülvevõ téglalapot,de pl van aminek nincs
	az egyszerûen nullt ad vissza, pl a gyors futásnak nincs animációja, sem területi sebzése, az csak null returnnel oldja meg.*/

	public void setDeletebale(boolean deletable){
		this.deletable = deletable;
		this.skillViewBuilder.setDeletable(deletable);
	}
	
	public boolean isDeletable(){
		return this.deletable;
		
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

	public double getSkillStartedMainTime() {
		return skillStartedMainTime;
	}

	public void setSkillStartedMainTime(double skillStartedMainTime) {
		this.skillStartedMainTime = skillStartedMainTime;
	}

	public IViewBuilderContainer getContainer() {
		return container;
	}

	public void setContainer(IViewBuilderContainer container) {
		this.container = container;
	}

	public IImageViewBuilder getSkillViewBuilder() {
		return skillViewBuilder;
	}

	public void setSkillViewBuilder(IImageViewBuilder skillViewBuilder) {
		this.skillViewBuilder = skillViewBuilder;
	}

	public void setSkillStartedMainTime(int skillStartedMainTime) {
		this.skillStartedMainTime = skillStartedMainTime;
	}

	public int getTimeuntilcdend() {
		return timeuntilcdend;
	}

	public void setTimeuntilcdend(int timeuntilcdend) {
		this.timeuntilcdend = timeuntilcdend;
	}

	public int getCdtime() {
		return cdtime;
	}

	public void setCdtime(int cdtime) {
		this.cdtime = cdtime;
	}

	public int getSecWhileActive() {
		return secWhileActive;
	}

	public void setSecWhileActive(int secWhileActive) {
		this.secWhileActive = secWhileActive;
	}

	public boolean isIsactivated() {
		return isactivated;
	}

	public void setIsactivated(boolean isactivated) {
		this.isactivated = isactivated;
	}

	public int getSkillnumber() {
		return skillnumber;
	}

	public void setSkillnumber(int skillnumber) {
		this.skillnumber = skillnumber;
	}

	public Entity getSkillOwner() {
		return skillOwner;
	}

	public void setSkillOwner(Entity skillOwner) {
		this.skillOwner = skillOwner;
	}

	

	public IEnvironment getEnvironment() {
		return environment;
	}

	public void setEnvironment(IEnvironment environment) {
		this.environment = environment;
	}

	public boolean isViewBuilderActivate() {
		return viewBuilderActivate;
	}

	public void setViewBuilderActivate(boolean viewBuilderActivate) {
		this.viewBuilderActivate = viewBuilderActivate;
	}

	public boolean isNetworkActivate() {
		return networkActivate;
	}

	public void setNetworkActivate(boolean networkActivate) {
		this.networkActivate = networkActivate;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}
}