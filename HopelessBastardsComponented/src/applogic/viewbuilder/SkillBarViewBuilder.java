package applogic.viewbuilder;

import java.awt.Point;

import applogic.IViewBuilderContainer;
import screenconverter.IMonitorScreenManager;
import screenconverter.descriptors.ImageDescriptor;

public class SkillBarViewBuilder extends IImageViewBuilder{

	private ImageDescriptor[] describers;
	private IMonitorScreenManager monitorScreenManager;
	
	private Point barPoint;
	private IViewBuilderContainer container;
	
	public SkillBarViewBuilder(IMonitorScreenManager monitorScreenManager,IViewBuilderContainer container) {
		describers = new ImageDescriptor[1];
		describers[0] = new ImageDescriptor(10, 10,0,0,0,1140,160,"skillbar",0);
		
		this.container = container;
		
		this.monitorScreenManager = monitorScreenManager;
		
		this.barPoint = new Point();
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		
			/*Itt lekérdezi a monitorscreenmanager komponenstõl, hogy hova is kellene raknia a skillbart.*/
			this.barPoint = this.monitorScreenManager.getSkillBarLocation((int)container.getPlayer().getX() + container.getPlayer().getWidth()/2,(int)container.getPlayer().getY() + container.getPlayer().getHeight()/2);
			describers[0].setX(barPoint.x);
			describers[0].setY(barPoint.y);
						
		return this.describers;
	}
}