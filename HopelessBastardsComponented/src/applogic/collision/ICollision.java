package applogic.collision;

import java.awt.Rectangle;

public interface ICollision {
	public DoublePoint newLocation(Rectangle mover,Rectangle staticElement, DoublePoint moverDestination);
	
	public Line[] mustCheckLines();
}
