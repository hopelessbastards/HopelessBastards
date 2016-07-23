package applogic;

import java.lang.Object;
import java.util.List;

import applogic.elements.Tile;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;

public interface IMapLoader {
	/*Nagyon rugalmasra lett elkészítve ez a funkció.A loadMap() metódus lényegében betölti egy bitmapbõl, a
	 statikus pályaelemeket.Objectként van megadva, de ezt kényelmesen lehet kasztolni BufferedImagere
	 vagy amiben tárolva van az információ.A tiles lista tartalmazza a nem mozgó pályaelemeket
	 ezért azt a listát is átadjuk neki, hogy pakoljon bele, illetve a viewBuilder leírót is, 
	 amiben a képleírók vannak, azt is adja hozzá, tehát hogy melyik csempe , ház,stb hol
	 helyeszkedik el.*/
	public void loadMap(Object bitMap,Object bitTileMap,List<Tile> tiles,List<Tile>nonBlockingTile,List<IImageViewBuilder> viewBuilder);
}
