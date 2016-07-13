package bufferedImageImplementation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import graphicsEngine.RenderableObject;

public class StringObject extends RenderableObject{
	private Graphics2D g2d;
	private String string;
	private AffineTransform oldTransformation;
	private Font font;
	private int size;
	
	public StringObject(int x, int y,int size, Color color, double angle, int rotateAngleCenterX, int rotateAngleCenterY,
			String string,Font font) {
		super(x, y, color, angle, rotateAngleCenterX, rotateAngleCenterY);
		this.string = string;
		this.font = font;
		this.size = size;
	}
	
	@Override
	public void render(Object graphicContext) {
		g2d = (Graphics2D)graphicContext;
		
		if(getColor() != null){
			g2d.setColor(getColor());
		}
		g2d.setFont(this.font);
		if(getAngle() != 0){
			oldTransformation = g2d.getTransform();
			g2d.rotate(Math.toRadians(getAngle()),getRotateAngleCenterX(),getRotateAngleCenterY());
			g2d.drawString(string, getX(), getY());
			g2d.setTransform(oldTransformation);
		}else{
			
			g2d.drawString(string, getX(), getY());
		}
		
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}
}