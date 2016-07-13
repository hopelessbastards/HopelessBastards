package applogic.skills.vehicles.viewBuilder;

import applogic.AnimationHandler;
import applogic.IAnimationHandler;
import applogic.skills.vehicles.Bullet;
import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.descriptors.ImageDescriptor;

public class BulletViewBuilder extends IImageViewBuilder{
	private ImageDescriptor[] describers;
	private Bullet bullet;
	
	public BulletViewBuilder(int x, int y, int width, int height,Bullet bullet) {
		this.describers = new ImageDescriptor[1];
		this.describers[0] = new ImageDescriptor((int)bullet.getX(),(int)bullet.getY(),0,0,0,bullet.getWidth(),bullet.getHeight(),"bullet",0);
		this.bullet = bullet;
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		
		this.describers[0].setX((int)bullet.getX());
		this.describers[0].setY((int)bullet.getY());
		this.describers[0].setAngle(bullet.getAngle());
		/*this.describers[0].setWidth(10000);
		this.describers[0].setHeight(10000);*/
		this.describers[0].setAngleCenterPointX((int)bullet.getX() + bullet.getWidth()/2);
		this.describers[0].setAngleCenterPointY((int)bullet.getY() + bullet.getHeight()/2);
		return this.describers;
	}
}