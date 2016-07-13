package graphicsEngine;

import java.util.ArrayList;
import java.util.List;

/*Ez az osztály tárolja az animációkat.Minden kép animációba sorolható, ha egy kép van, ami csak kép,
 akkor az egy egy elemû Animation lesz.Image objektumokat tartalmaz, ami konkrétan tartalmazza, hogy
 milyen típusú kép(BufferedImage, stb...).*/

public class Animation {
	/*Egy animation az lényegében egy lista, és abban tárolja az animáció képkockáit.*/
	private List<Object> animation;
	
	public Animation() {
		animation = new ArrayList<Object>();
	}
	/*Egy kép hozzáadása az animációhoz.*/
	public void addAnimationSlice(Object image){
		animation.add(image);
	}
	/*Egy kép hozzáadása az animációhoz index alapján.Az index a lejátszási sorrendben számít, és a leíró
	 xml-ben azt is megadják a képhez, hogy hanyadik animációs képkocka, ezért tudnunk kell úgy beszúrni.*/
	public void removeAnimationSliceByIndex(int index){
		animation.remove(index);
	}
	
	public void addAnimationSliceByIndex(int index,Object image){
		animation.add(index, image);
	}
	
	public Object getAnimationSlice(int index){
		return animation.get(index);
	}
	
	public List<Object> getAnimation() {
		return animation;
	}
	
	public void setAnimation(List<Object> animation) {
		this.animation = animation;
	}
}