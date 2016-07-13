package applogic;

import applogic.components.IComponent;
import applogic.elements.BasicElement;
import applogic.elements.Entity;
import applogic.elements.Player;
import applogic.elements.SkillVehicle;
import applogic.viewbuilder.IViewBuilder;

import java.util.List;

public class GarbageCollector implements IGarbageCollector{
	/*Ez a szolgáltatás azért kell, hogy a ViewBuildereket felszabadítsa a memóriából.Pontosan
	 az a feladata, hogy végigmegy listákon, és amely listaelem deletable adattagjának értéke true,
	 azt kitörli a listából, ezzel felszabadítva azt a memóriaterületet amit foglalt, hisz máshonnan
	 nem lesz arra az objektumra referencia.*/

	@Override
	public void cleanPlayers(List<Player>... list) {
		for (List<Player> item : list){
			for(int i=0;i<item.size();i++){
				if(item.get(i).isDeletable()){
					item.remove(i);
				}
			}
		}
	}

	@Override
	public void cleanEntities(List<Entity>... list) {
		for (List<Entity> item : list){
			for(int i=0;i<item.size();i++){
				if(item.get(i).isDeletable()){
					item.remove(i);
				}
			}
		}	
	}

	@Override
	public void cleanViewBuilders(List<? extends IViewBuilder> ...list) {
		for (List<? extends IViewBuilder> item : list){
			for(int i=0;i<item.size();i++){
				if(item.get(i).isDeletable()){
					item.remove(i);
				}
			}
		}
	}

	@Override
	public void cleanGuiComponents(List<IComponent>... list) {
		for(List<IComponent> item : list){
			for(int i=0;i<item.size();i++){
				if(item.get(i).isDeletable()){
					item.remove(i);
				}
			}
		}
	}

	@Override
	public void cleanBasicElements(List<BasicElement>... list) {
		for(List<BasicElement> item : list){
			for(int i=0;i<item.size();i++){
				if(item.get(i).isDeletable()){
					item.remove(i);
				}
			}
		}
	}

	@Override
	public void cleanSkillVehicles(List<SkillVehicle>... list) {
		for(List<SkillVehicle> item : list){
			for(int i=0;i<item.size();i++){
				if(item.get(i).isDeletable()){
					item.remove(i);
				}
			}
		}
	}
}