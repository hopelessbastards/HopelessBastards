package graphicsEngine;

import java.util.Map;

/*A képbetöltõ interface, ezt implementálja a képbetöltõ komponensük, ezen az interfacen keresztül tudjuk
 elérni.*/
public interface IimageLoader {
	public Map<String, Animation> imageLoad();
}
