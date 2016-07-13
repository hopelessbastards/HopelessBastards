package applogic.elements;

import java.util.List;

import applogic.IViewBuilderContainer;
import applogic.elements.controllers.Commander;
import applogic.elements.controllers.EnemyAndFriendlyEntityProvider;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.AbstractSkill;

public abstract class Entity extends LivingObject{
	
	private boolean selected = false;/*ez az�rt kell, hogy kivan-e v�lasztva ez az entit�s az itteni
	lok�lis player sz�m�ra.*/
	private boolean dead;
	private boolean live;
	
	private double mana;
	private double maxMana;
	
	private double xold;
	private double yold;
	
	private double velocityX = 0;/*velocity- sebess�g*/
    private double velocityY = 0;
    
    private int movementSpeed = 6;
    
    private int turningSpeed = 2;/*milyen m�rt�kben forduljon(360 mennyivel m�dosuljon)*/
    
	private AbstractSkill[] skills;/*hogy h�ny skillje van konfigfileb�l kapja.*/
    
	private boolean moving;
	
	private boolean blocking;/*Jelenleg blokkol-e minden t�mad�st �s �rtalmas effetket.*/
	private boolean stunned;/*Jelenleg le van-e stunnolva ez az entit�s.*/
    
	private boolean[] skillStarted;/*skill0started,skill1started,...*/
    
	/*Minden entit�snak van egy commandere, legyen az felhaszn�l�i beavatkoz�s, vagy mesters�ges intelligencia.*/
    private Commander commander;
    
    private IViewBuilderContainer container;
    
    /*Ebben t�rolja, hogy h�ny darab skillje van ennek az entit�snak.*/
    private int skillCount;
    
    private IEnvironment environment;

    private boolean controlledByPlayer;
    
    private Entity selectedEntity = null;
    
    private List<Entity> enemyEntities;
    private List<Entity> enemyPlayers;
    private List<LivingObject> enemyBuildings;
    
    private List<Entity> friendlyEntities;
    private List<Entity> friendlyPlayers;
    private List<LivingObject> friendlyBuildings;
    
    
	public Entity(int x, int y, int width, int height, double angle, int health,int maxhealth,int mana,int maxMana,
			int skillCount,IViewBuilderContainer container,IEnvironment environment,EnemyAndFriendlyEntityProvider provider) {
		super(x, y, width, height, angle,0,0,health,maxhealth);
		this.live = true;
		
		this.friendlyBuildings = provider.getFriendlyBuildings();
		this.friendlyEntities = provider.getFriendlyEntities();
		this.friendlyPlayers = provider.getEnemyPlayers();
		
		this.enemyBuildings = provider.getEnemyBuildings();
		this.enemyEntities = provider.getEnemyEntities();
		this.enemyPlayers = provider.getEnemyPlayers();
		
		this.dead = false;
		this.live = true;
		this.mana = mana;
		this.maxMana = maxMana;
		this.environment = environment;
		
		this.container = container;
		
		this.skillCount = skillCount;
		skills = new AbstractSkill[this.skillCount];
		this.skillStarted = new boolean[this.skillCount];
		this.xold = getX();
		this.yold = getY();
	}
	
	public abstract void activateSkill(int skillnumber,double appTime);
	public abstract boolean isDead();
	
	public void die(){
		this.dead = true;
		this.live = false;
		setDeletable(true);
	}
	
	public void setHealth(int health){
		if(getHealth()+health > getMaxhealth()){
			setHealth(getMaxhealth());
		}else{
			setHealth(getHealth() + health);
		}
		
		if(getHealth() <= 0){
			die();
		}
	}
	
	/*Az entit�sok tick met�dusa azt csin�lja, hogy megh�vja a cammander�nek a command met�dus�t, hogy
	 majd az tudja, hogy mit kellc sin�lnia, teh�t az visszah�vja az entit�s move , stb met�dusait.*/
	@Override
	public void tick(double appTime) {
		if(environment.getCursorInformationProvider().isClick() && getCollideArea().contains(environment.getCursorInformationProvider().getMouse())){
			environment.getPlayer().setSelectedEntity(this);
		}
		
		commander.command(appTime);
		/*Illetve a skillek tick met�dus�t is tov�bb h�vja.*/
		for(int i=0;i<skills.length;i++){
			if(skills[i] != null){
				skills[i].tick(appTime);
			}
		}
		
		setAppTime(appTime);
	}
	
	public void moveForward(){
		// x += Math.cos(Math.toRadians(angle)) * sx;
		 
		setX(getX() + Math.cos( Math.toRadians(getAngle())) * this.movementSpeed);
		// y += Math.sin(Math.toRadians(angle)) * sy;
				 
		setY(getY() + Math.sin(Math.toRadians(getAngle())) * this.movementSpeed);
	}
	
	public void moveBack() {
		// x += Math.cos(Math.toRadians(angle)) * sx;
		 
		setX(getX() - Math.cos( Math.toRadians(getAngle())) * (this.movementSpeed/2));
		// y += Math.sin(Math.toRadians(angle)) * sy;
				 
		setY(getY() - Math.sin(Math.toRadians(getAngle())) * (this.movementSpeed/2));	
	}

	public void turnLeft() {
		setAngle(getAngle() - this.turningSpeed);
		if (getAngle() > 360) {
		       setAngle(0);
		} else if (getAngle() < 0) {
		       setAngle(360);
		}
	}

	public void trunRight() {
		setAngle(getAngle() + this.turningSpeed);
		if (getAngle() > 360) {
		       setAngle(0);
		} else if (getAngle() < 0) {
		       setAngle(360);
		}		
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		super.setDeletable(deletable);
		/*Itta  skilleknek is be�ll�tom, hogy t�rl�djenek, azok pedig a k�pi vil�gukra az iconra
		 �s a skillvill�ml�s stb effektekre is be�ll�tj�k.*/
		for(int i=0;i<skills.length;i++){
			/*Itt az�rt kell megvizsg�lni, hogy az adott skill nem-e null, mivel lehet,
			 hogy van �resen hagyott skill, mondjuk a zombinek csak a 6. skillje van,
			 a t�bbi �res.*/
			if(skills[i] != null){
				skills[i].setDeletebale(deletable);
			}
		}
	}
		
	public boolean isControlledByPlayer() {
		return controlledByPlayer;
	}

	public void setControlledByPlayer(boolean controlledByPlayer) {
		this.controlledByPlayer = controlledByPlayer;
	}

	public Entity getSelectedEntity() {
		return selectedEntity;
	}


	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public void setSelectedEntity(Entity selectedEntity) {
		this.selectedEntity = selectedEntity;
	}

	public IEnvironment getEnvironment() {
		return environment;
	}

	public void setEnvironment(IEnvironment environment) {
		this.environment = environment;
	}

	public IViewBuilderContainer getContainer() {
		return container;
	}

	public void setContainer(IViewBuilderContainer container) {
		this.container = container;
	}

	public Commander getCommander() {
		return commander;
	}
	
	public void setCommander(Commander commander) {
		this.commander = commander;
	}
	
	public AbstractSkill[] getSkills() {
		return skills;
	}

	public void setSkills(AbstractSkill[] skills) {
		this.skills = skills;
	}

	public int getTurningSpeed() {
		return turningSpeed;
	}
	
	public void setTurningSpeed(int turningSpeed) {
		this.turningSpeed = turningSpeed;
	}
		
	public double getXold() {
		return xold;
	}

	public void setXold(double xold) {
		this.xold = xold;
	}

	public double getYold() {
		return yold;
	}

	public void setYold(double yold) {
		this.yold = yold;
	}
	
	public boolean isLive() {
		return live;
	}
	
	public void setLive(boolean live) {
		this.live = live;
	}
	
	public double getVelocityX() {
		return velocityX;
	}
	
	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}
	
	public double getVelocityY() {
		return velocityY;
	}
	
	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}
	
	public int getMovementSpeed() {
		return movementSpeed;
	}
	
	public void setMovementSpeed(int movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public List<Entity> getEnemyEntities() {
		return enemyEntities;
	}

	public void setEnemyEntities(List<Entity> enemyEntities) {
		this.enemyEntities = enemyEntities;
	}

	public List<Entity> getEnemyPlayers() {
		return enemyPlayers;
	}

	public void setEnemyPlayers(List<Entity> enemyPlayers) {
		this.enemyPlayers = enemyPlayers;
	}

	public List<LivingObject> getEnemyBuildings() {
		return enemyBuildings;
	}

	public void setEnemyBuildings(List<LivingObject> enemyBuildings) {
		this.enemyBuildings = enemyBuildings;
	}

	public List<Entity> getFriendlyEntities() {
		return friendlyEntities;
	}

	public void setFriendlyEntities(List<Entity> friendlyEntities) {
		this.friendlyEntities = friendlyEntities;
	}

	public List<Entity> getFriendlyPlayers() {
		return friendlyPlayers;
	}

	public void setFriendlyPlayers(List<Entity> friendlyPlayers) {
		this.friendlyPlayers = friendlyPlayers;
	}

	public List<LivingObject> getFriendlyBuildings() {
		return friendlyBuildings;
	}

	public void setFriendlyBuildings(List<LivingObject> friendlyBuildings) {
		this.friendlyBuildings = friendlyBuildings;
	}

	public double getMana() {
		return mana;
	}

	public void setMana(double mana) {
		this.mana = mana;
	}

	public double getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(double maxMana) {
		this.maxMana = maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}


	public boolean[] getSkillStarted() {
		return skillStarted;
	}

	public void setSkillStarted(boolean[] skillStarted) {
		this.skillStarted = skillStarted;
	}
	
	/*Ez a met�dus t�lterhel�s az�rt kell, hogy egyes�vel indexek szerint is el�rhess�k a skillstarted 
	 elemeit, vagy egybe az eg�sz t�mb�t is.*/
	public boolean getSkillStarted(int skillNumber){
		return this.skillStarted[skillNumber];
	}
	
	public void setSkillStarted(int skillNumber, boolean skillStarted){
		this.skillStarted[skillNumber] = skillStarted;
	}

	public int getSkillCount() {
		return skillCount;
	}

	public void setSkillCount(int skillCount) {
		this.skillCount = skillCount;
	}

	public boolean isBlocking() {
		return blocking;
	}

	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}

	public boolean isStunned() {
		return stunned;
	}

	public void setStunned(boolean stunned) {
		this.stunned = stunned;
	}	
}