package applogic.skills.vehicles.viewBuilder;

import applogic.AnimationHandler;
import applogic.IAnimationHandler;
import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.descriptors.ImageDescriptor;

public class BombViewBuilder extends IImageViewBuilder{

	private ImageDescriptor[] describers;
	private IAnimationHandler animationHandler;
	
	public BombViewBuilder(int x, int y, int width, int height) {
		this.describers = new ImageDescriptor[1];
		this.describers[0] = new ImageDescriptor(x, y,0,0,0,width, height,"bomb",0);
		animationHandler = new AnimationHandler(30, 9,false);
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		/*Itt az a lényeg, hogy amikor eltüntetjük a bombát, szól a Bomb komponens, hogy 
		 belefutottak és szünjön meg a kép, akkor még egy robbanás animációt is letolunk,
		 tehát itt felüldefiniáljuk a metódust, tehát nem állítjuk be a deletablet truera,
		 hanem csak beállítjuk, hogy a robbanás animációt pörgesse le, majd mikor annak vége
		 , akkor hívjuk meg az õsosztályban definiált deletable-t truera állítót,
		 és akkor már tényleg megszüntethetõ ez a képleíró.*/
		this.describers[0].setImageLogicalName("bombtrapexplosion");
		animationHandler.setAnimationable(true);/*Beállítjuk, hogy az animáció elkezdõdhet.*/
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		if(this.describers[0].getImageLogicalName().equals("bombtrapexplosion")){
			this.describers[0].setAnimation(animationHandler.animationPiece());
			if(!animationHandler.getAnimationable()){/*Ha vége van az animációnak,
			akkor a deletablet beállíthatjuk végre truera, és a garbageCollector kisöpörheti
			a memóriából.*/
				super.setDeletable(true);
			}
		}
		return this.describers;
	}
}