package applogic.elements.controllers;

import applogic.elements.Entity;

public interface IEntityCreator {
	public Entity createEntity(int x, int y, String networkid, String characterType);
}
