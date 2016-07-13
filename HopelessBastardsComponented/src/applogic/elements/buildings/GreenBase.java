package applogic.elements.buildings;

import applogic.IViewBuilderContainer;
import applogic.elements.LivingObject;
import applogic.viewbuilder.buildings.GreenBaseViewBuilder;


public class GreenBase extends LivingObject{

	public GreenBase(int x, int y, int width, int height, double angle,IViewBuilderContainer container) {
		super(x, y, width, height, angle,x + width/2,y + height / 2,1000,5000);
		container.getViewBuilder().add(new GreenBaseViewBuilder(this, container));
	}

	@Override
	public void tick(double appTime) {
		
	}
}