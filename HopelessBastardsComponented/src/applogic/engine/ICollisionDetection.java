package applogic.engine;

import applogic.elements.Entity;
import applogic.elements.Tile;

public interface ICollisionDetection {
	public boolean entityCollideTileWithPoints(Entity entity,Tile tile);
	public boolean entityCollideEntityWithPoints(Entity mover, Entity other);
	public boolean entityCollideEntityWithRectangle(Entity mover,Entity other);
	public boolean entityCollideEntityWithPolygon(Entity mover,Entity other);
}
