package applogic.elements;

import java.awt.Rectangle;

public class ElementOperations {
	private Rectangle location;
	
	public ElementOperations() {
		location = new Rectangle();
	}
	
	/**
	 * This method returns a Rectangle, which is located in the fogOfWar Local Area.
	 * Local Area means, that if bounds x:100, y:100, than makes it to 0,0, because every
	 * ai creature has an own coordinate system, which is smaller than the global one.
	 */
	public Rectangle fogOfWarLocalLocation(Rectangle fogOfWar, Rectangle element){
		this.location.setBounds(element.x, element.y, element.width, element.height);
		this.location = this.location.intersection(fogOfWar);
		if(location.width > 0 && location.height > 0){
			this.location.x -= fogOfWar.x;
			this.location.y -= fogOfWar.y;
			return this.location;
		}
		return null;
	}
}
