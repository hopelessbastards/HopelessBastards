package applogic.skills.vehicles.viewBuilder;

import applogic.skills.vehicles.PoisonBomb;
import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.descriptors.ImageDescriptor;

public class PoisonBombViewBuilder extends IImageViewBuilder{
	private ImageDescriptor[] describers;
	private PoisonBomb bomb;
	
	public PoisonBombViewBuilder(int x, int y, int width, int height,PoisonBomb bomb) {
		this.describers = new ImageDescriptor[1];
		this.describers[0] = new ImageDescriptor((int)bomb.getX(),(int)bomb.getY(),0,0,0,bomb.getWidth(),bomb.getHeight(),"poisonBomb",0);
		this.bomb = bomb;
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		
		this.describers[0].setX((int)bomb.getX());
		this.describers[0].setY((int)bomb.getY());
		this.describers[0].setAngle(bomb.getAngle());
		/*this.describers[0].setWidth(10000);
		this.describers[0].setHeight(10000);*/
		this.describers[0].setAngleCenterPointX((int)bomb.getX() + bomb.getWidth()/2);
		this.describers[0].setAngleCenterPointY((int)bomb.getY() + bomb.getHeight()/2);
		return this.describers;
	}
}