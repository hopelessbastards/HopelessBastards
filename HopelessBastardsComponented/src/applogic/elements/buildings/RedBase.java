package applogic.elements.buildings;

import applogic.IViewBuilderContainer;
import applogic.elements.LivingObject;
import applogic.viewbuilder.buildings.RedBaseViewBuilder;

public class RedBase extends LivingObject{

	public RedBase(int x, int y, int width, int height, double angle,IViewBuilderContainer container) {
		super(x, y, width, height, angle,x + width/2,y + height / 2,100,5000);
		container.getViewBuilder().add(new RedBaseViewBuilder(this, container));
	}

	@Override
	public void tick(double appTime) {
		
	}
}