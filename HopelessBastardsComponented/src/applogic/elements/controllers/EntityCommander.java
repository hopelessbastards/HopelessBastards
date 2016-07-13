package applogic.elements.controllers;

import applogic.elements.Entity;

public abstract class EntityCommander implements Commander{
	/*Ennek az interfacenak az a lényege, hogy aki megvalósítja az az soztály tudja irányítani a pályán
	 mozgó karaktereket, persze a felhasználói beavatkozások hatására.*/
	
	/*Booleanok, hogy jelenleg merre mozogjon a Player.*/
	private boolean up,left,down,right;
	
	/*Booleanok, hogy jelenleg mely skilleket kell eltolnia a Playernek.*/
	private boolean[] skillActivated;
	
	/*Ez az soztály ezt az entitást irányítja.*/
	private Entity controlledEntity;
	
	private IEnvironment environment;
	
	/*Ez a metódus foglya a lényegi munkát végezni.ami az interfaceban van definiálva command()*/
	
	
	public EntityCommander(IEnvironment environment) {
		this.environment = environment;
	}
	
	public void setControlledEntity(Entity controlledEntity){
		this.controlledEntity = controlledEntity;
		this.skillActivated = new boolean[this.controlledEntity.getSkillCount()];
		
	}
	
	public void setSkillActivated(int skillnumber,boolean value){
		this.skillActivated[skillnumber] = value;
	}
	
	public boolean getSkillActivated(int skillnumber){
		return this.skillActivated[skillnumber];
	}
	
	public boolean[] getSkillActivated() {
		return skillActivated;
	}

	public void setSkilleActivated(boolean[] skillActivated) {
		this.skillActivated = skillActivated;
	}

	public Entity getControlledEntity() {
		return controlledEntity;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public IEnvironment getEnvironment() {
		return environment;
	}

	public void setEnvironment(IEnvironment environment) {
		this.environment = environment;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
}