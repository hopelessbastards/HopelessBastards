package applogic.elements.controllers;

import java.util.List;
import applogic.CursorInformationProvider;
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
	public List<LivingObject> getEnemyBuildings();
	public List<LivingObject> getFriendlyBuildings();
	public List<SkillVehicle> getSkillVehicles();
	public EntityCommander getUserAction();
	public Entity getPlayer();
	public void setPlayer(Entity player);
	public CursorInformationProvider getCursorInformationProvider();
}