package bufferedImageImplementation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import graphicsEngine.RenderableObject;

public class OvalObject extends RenderableObject{
	private Graphics2D g2d;
	private AffineTransform oldTransformation;
	private int width;
	private int height;
	private boolean draw;
	
	public OvalObject(int x, int y, Color color, double angle, int rotateAngleCenterX, int rotateAngleCenterY,
			int width,int height,boolean draw) {
		super(x, y, color, angle, rotateAngleCenterX, rotateAngleCenterY);
		this.draw = draw;
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
			if(draw){
				g2d.drawOval(getX(), getY(), this.width, this.height);
			}else{
				g2d.fillOval(getX(), getY(), this.width, this.height);
			}
			g2d.setTransform(oldTransformation);
		}else{
			if(draw){
				g2d.drawOval(getX(), getY(), this.width, this.height);
			}else{
				g2d.fillOval(getX(), getY(), this.width, this.height);
			}
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

	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

}