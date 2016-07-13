package applogic.viewbuilder;

import java.awt.Point;
import applogic.IViewBuilderContainer;
import screenconverter.IMonitorScreenManager;
import screenconverter.descriptors.ImageDescriptor;

public class HealthBarViewBuilder extends IImageViewBuilder{

	private IViewBuilderContainer container;
	private IMonitorScreenManager monitorScreenManager;
	private ImageDescriptor[] describers;
	
	private Point barPoint;
	private Point healthPoint;
	private Point manaPoint;
	
	public HealthBarViewBuilder(IMonitorScreenManager monitorScreenManager,IViewBuilderContainer container) {
		this.container = container;
		this.describers = new ImageDescriptor[3];
		
		this.monitorScreenManager = monitorScreenManager;
		
		this.barPoint = new Point();
		this.manaPoint = new Point();
		this.healthPoint = new Point();
		
		this.describers[0] = new ImageDescriptor(0, 0,0,0,0,300,200,"healthbar",0);
		this.describers[1] = new ImageDescriptor(0, 0,0,0,0,151,32,"health",0);
		this.describers[2] = new ImageDescriptor(0, 0,0,0,0,141,26,"mana",0);
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		/*Itt lekérdezi a monitorscreenmanager komponenstõl, hogy hova is kellene raknia a skillbart.*/
		this.barPoint = this.monitorScreenManager.getHealthBarLocation((int)container.getPlayer().getX() + container.getPlayer().getWidth()/2,(int)container.getPlayer().getY() + container.getPlayer().getHeight()/2);
		describers[0].setX(barPoint.x);
		describers[0].setY(barPoint.y);
		
		this.healthPoint = this.monitorScreenManager.getHealtbarHealtLineLocation((int)container.getPlayer().getX() + container.getPlayer().getWidth()/2,(int)container.getPlayer().getY() + container.getPlayer().getHeight()/2);
		describers[1].setX(healthPoint.x);
		describers[1].setY(healthPoint.y);
		describers[1].setWidthscale(container.getPlayer().getHealth()/ container.getPlayer().getMaxhealth());
		
		this.manaPoint = this.monitorScreenManager.getHealtbarManaLineLocation((int)container.getPlayer().getX() + container.getPlayer().getWidth()/2,(int)container.getPlayer().getY() + container.getPlayer().getHeight()/2);
		describers[2].setX(manaPoint.x);
		describers[2].setY(manaPoint.y);
		describers[2].setWidthscale(container.getPlayer().getMana() / container.getPlayer().getMaxMana());
		
		return this.describers;
	}
}