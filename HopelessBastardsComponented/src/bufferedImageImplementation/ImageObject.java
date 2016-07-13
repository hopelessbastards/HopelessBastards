package bufferedImageImplementation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import graphicsEngine.RenderableObject;

public class ImageObject extends RenderableObject{
	private Graphics2D g2d;
	private BufferedImage image;
	private AffineTransform oldTransformation;
	
	/*Eza ascale, hogy a kép alapértelmezett értékét hányszorosára növeljük vagy csökkentsük, ez
	 mobil platformra való átvitelnél lehet hasznos.*/
	private double widthscale;
	private double heightscale;
	
	private int width;
	private int height;
	
	public ImageObject(int x, int y, Color color, double angle, int rotateAngleCenterX,
			int rotateAngleCenterY,int width,int height,double widthscale,double heightscale,Object image) {
		super(x, y, color, angle, rotateAngleCenterX, rotateAngleCenterY);
		this.image = (BufferedImage)image;
		this.widthscale = widthscale;
		this.heightscale = heightscale;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void render(Object graphicContext) {
		g2d = (Graphics2D)graphicContext;

		if(getColor() != null){
			g2d.setColor(getColor());
		}

		if(getAngle() != 0){
			oldTransformation = g2d.getTransform();
			g2d.rotate(Math.toRadians(getAngle()),getRotateAngleCenterX(),getRotateAngleCenterY());
			//g2d.drawImage(this.image, getX(), getY(),null);
			g2d.drawImage(this.image, getX(), getY(), (int)(this.width*widthscale), (int)(this.height*heightscale), null);
			
			g2d.setTransform(oldTransformation);
		}else{
			//g2d.drawImage(this.image, getX(), getY(),null);
			g2d.drawImage(this.image, getX(), getY(), (int)(this.width*widthscale), (int)(this.height*heightscale), null);
		}
	}
	
	
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getWidthscale() {
		return widthscale;
	}

	public void setWidthscale(double widthscale) {
		this.widthscale = widthscale;
	}

	public double getHeightscale() {
		return heightscale;
	}

	public void setHeightscale(double heightscale) {
		this.heightscale = heightscale;
	}

	public Object getImage() {
		return image;
	}

	public void setImage(Object image) {
		this.image = (BufferedImage)image;
	}

}