package applogic.elements;

public class Tile extends BasicElement{

	private boolean hasCollision;/*át lehet-e menni a testen vagy sem,azaz kell-e ütközést vizsgálni vele vagy sem.*/
	
	private TileType tileType;
	
	public Tile(int x, int y, int width, int height, double angle,int angleCenterX,int angleCenterY,boolean solid,TileType tileType) {
		super(x, y, width, height, angle,angleCenterX,angleCenterY);
		this.hasCollision = solid;
		this.tileType = tileType;
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
}