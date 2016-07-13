package bufferedImageImplementation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import graphicsEngine.RenderableObject;

public class LineObject extends RenderableObject{
	private Graphics2D g2d;
	private int x2;
	private int y2;
	private AffineTransform oldTransformation;
	
	public LineObject(int x, int y, Color color, double angle, int rotateAngleCenterX, int rotateAngleCenterY
			,int x2,int y2) {
		super(x, y, color, angle, rotateAngleCenterX, rotateAngleCenterY);
		this.x2 = x2;
		this.y2 = y2;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
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
			g2d.drawLine(getX(), getY(), this.x2, this.y2);
			g2d.setTransform(oldTransformation);
		}else{
			g2d.drawLine(getX(), getY(), this.x2, this.y2);
		}
	}
}