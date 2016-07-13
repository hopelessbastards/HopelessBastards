package applogic;

import java.util.List;
import applogic.elements.BasicElement;
import applogic.elements.Entity;
import applogic.components.IComponent;
import applogic.elements.Player;
import applogic.elements.SkillVehicle;
import applogic.viewbuilder.IViewBuilder;

public interface IGarbageCollector {
	public void cleanPlayers(List<Player> ...list);
	public void cleanEntities(List<Entity> ...list);
	public void cleanViewBuilders(List<? extends IViewBuilder> ...list);
	public void cleanGuiComponents(List<IComponent> ...list);
	public void cleanBasicElements(List<BasicElement> ...list);
	public void cleanSkillVehicles(List<SkillVehicle> ...list);
}