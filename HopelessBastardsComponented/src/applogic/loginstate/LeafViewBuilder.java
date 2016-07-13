package applogic.loginstate;

import java.util.Random;

import applogic.elements.BasicElement;
import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.descriptors.ImageDescriptor;

public class LeafViewBuilder extends IImageViewBuilder{
	
	private ImageDescriptor[] describers;
	private BasicElement leaf;
	private Random random;
	private int leafNumber;
	
	public LeafViewBuilder(BasicElement leaf) {
		this.leaf = leaf;
		this.random = new Random();
		this.leafNumber = random.nextInt(2) + 1;
		this.describers = new ImageDescriptor[1];
		this.describers[0] = new ImageDescriptor(0, 0,0,0,0,60,60,"leaf" + Integer.toString(leafNumber),0);
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		this.describers[0].setX((int)leaf.getX());
		this.describers[0].setY((int)leaf.getY());
		this.describers[0].setAngle(leaf.getAngle());
		this.describers[0].setAngleCenterPointX((int)leaf.getX() + leaf.getWidth()/2);
		this.describers[0].setAngleCenterPointY((int)leaf.getY() + leaf.getHeight() / 2);
			
		return this.describers;
	}	
}