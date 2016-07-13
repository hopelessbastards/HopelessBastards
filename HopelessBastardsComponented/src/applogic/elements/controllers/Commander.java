package applogic.elements.controllers;

import applogic.elements.Entity;

public interface Commander {
	/*Minden Commander implementálja, aki Commander annak tudnia kell irányítani a rábízott entitást
	 azaz a command() metódus, illetve be kell tudnia állíttatni az irányítandó entitást.*/
	public void command(double appTime);
	public void setControlledEntity(Entity controlledEntity);
	
}
