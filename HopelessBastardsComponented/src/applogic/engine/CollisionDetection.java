package applogic.engine;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import applogic.elements.Entity;
import applogic.elements.Tile;

public class CollisionDetection implements ICollisionDetection{
	/*Ebben az osztályban definiálok ütközésvizsgáló metódusokat, melyek paraméterben kapnak
	 megvizsgálandó elemeket, és azokról eldöntik, hogy ütköznek-e vagy sem.*/
	
	/*Számítás segítõ változók, ezekre azért van szükség, hogy ne mindig new Point()-ot toljak.*/
	private Rectangle countingHelper;
	private Point[] countingHelperPoints;
	
	public CollisionDetection() {
		this.countingHelper = new Rectangle();
		this.countingHelperPoints = new Point[4];
	}
	
	/*Az ütközésvizsgáló metódusok igaz értékkel térnek vissza, ha a vizsgált elemek ütköznek.*/
	@Override
	public boolean entityCollideTileWithPoints(Entity entity, Tile tile) {
		this.countingHelper = tile.getCollideArea();
		this.countingHelperPoints = entity.getCollideAreaPoints();
		
		if(tile.isHasCollision() && (countingHelper.contains(countingHelperPoints[0]) || countingHelper.contains(countingHelperPoints[1]) ||
				countingHelper.contains(countingHelperPoints[2]) || countingHelper.contains(countingHelperPoints[3]) )){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean entityCollideEntityWithPoints(Entity mover, Entity other) {
		this.countingHelper = other.getCollideArea();
		this.countingHelperPoints = mover.getCollideAreaPoints();
		
		if(countingHelper.contains(countingHelperPoints[0]) || countingHelper.contains(countingHelperPoints[1]) ||
				countingHelper.contains(countingHelperPoints[2]) || countingHelper.contains(countingHelperPoints[3])){
			return true;
		}
		return false;
	}
	
	/*Ez a metódus eldönti két polygonról,hogy mettszik-e egymást.*/
	private boolean DoesPolygonIntersectPolygon(Polygon p1, Polygon p2) {
		Point p; 
		for(int i = 0; i < p2.npoints;i++) {
			p = new Point(p2.xpoints[i],p2.ypoints[i]); 
			if(p1.contains(p)) return true; 
		} 
		
		for(int i = 0; i < p1.npoints;i++) { 
			p = new Point(p1.xpoints[i],p1.ypoints[i]); 
			if(p2.contains(p))
				return true; 
			} 
		return false; 
	}

	@Override
	public boolean entityCollideEntityWithRectangle(Entity mover, Entity other) {
		if(mover.getCollideArea().intersects(other.getCollideArea())){
			return true;
		}
		return false;
	}

	@Override
	public boolean entityCollideEntityWithPolygon(Entity mover, Entity other) {
		if(DoesPolygonIntersectPolygon(mover.getCollideAreaPolygon(), other.getCollideAreaPolygon())){
			return true;
		}
		return false;
	}
}