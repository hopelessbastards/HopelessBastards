package applogic.viewbuilder;

import applogic.elements.Tile;
import screenconverter.descriptors.ImageDescriptor;

public class TileViewBuilder extends IImageViewBuilder{
	private ImageDescriptor[] describers;
	
	public TileViewBuilder(Tile tile) {
		describers = new ImageDescriptor[1];
		describers[0] = new ImageDescriptor((int)tile.getX(), (int)tile.getY(),tile.getAngle(),tile.getAngleCenterX(),tile.getAngleCenterY(),tile.getWidth(),tile.getHeight(),tile.getTileType().toString(), 0);
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		return describers;
	}
}