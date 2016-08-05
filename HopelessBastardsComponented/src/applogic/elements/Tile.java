package applogic.elements;

import java.awt.Rectangle;

import applogic.elements.controllers.ai.ElementDescriptionToAI;

public class Tile extends BasicElement{

	private boolean hasCollision;/*át lehet-e menni a testen vagy sem,azaz kell-e ütközést vizsgálni vele vagy sem.*/
	
	private TileType tileType;
	
	private ElementDescriptionToAI elementToAI;
	
	
	public Tile(int x, int y, int width, int height, double angle,int angleCenterX,int angleCenterY,boolean solid,TileType tileType) {
		super(x, y, width, height, angle,angleCenterX,angleCenterY);
		this.hasCollision = solid;
		this.tileType = tileType;
		this.elementToAI = new ElementDescriptionToAI();
	}
	
	public TileType getTileType() {
		return tileType;
	}

	public void setTileType(TileType tileType) {
		this.tileType = tileType;
	}

	public boolean isHasCollision() {
		return hasCollision;
	}

	public void setHasCollision(boolean hasCollision) {
		this.hasCollision = hasCollision;
	}

	@Override
	public void tick(double appTime) {
		setAppTime(appTime);
	}

	@Override
	public ElementDescriptionToAI createElementDescriptionToAI(Rectangle fogOfWar, Entity askerEntity) {
		if(hasCollision){
			elementToAI.setCollidedArea(getOperations().fogOfWarLocalLocation(fogOfWar, getCollideArea()));
			if(elementToAI.getCollidedArea() != null){
				elementToAI.setElementType("LivingObject");
				return this.elementToAI;
			}
		}
		
		return null;
	}
}