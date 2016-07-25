package applogic.elements.controllers;

import java.util.List;
import applogic.CursorInformationProvider;
import applogic.elements.CharacterType;
import applogic.elements.Entity;
import applogic.elements.LivingObject;
import applogic.elements.SkillVehicle;

public interface IEnvironment {
	public void PlayerMoved(Entity player);
	public void tick(double appTime);
	public List<Entity> getFriendlyEntities();
	public List<Entity> getEnemyEntities();
	public List<Entity> getFriendlyPlayers();
	public List<Entity> getEnemyPlayers();
	public List<Entity> getOwnedEntities();
	public List<LivingObject> getEnemyBuildings();
	public List<LivingObject> getFriendlyBuildings();
	public List<SkillVehicle> getSkillVehicles();
	public EntityCommander getUserAction();
	public Entity getPlayer();
	public PlayerRectangle getPlayerRectangle();
	public void setPlayer(Entity player);
	public CursorInformationProvider getCursorInformationProvider();
	public CharacterType getSelectedCharacterType();
	public void setSelectedCharacterType(CharacterType characterType);
	public void makePlayer(int x, int y, String networkid, String characterType);
	public void makeEnemyPlayer(int x, int y, String networkid, String characterType);
	public void makeEnemyEntity(int x, int y, String networkid, String characterType);
	public void makeFriendlyPlayer(int x, int y, String networkid, String characterType);
	public void makeFriendlyEntity(int x, int y, String networkid, String characterType);
}