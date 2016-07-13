package bufferedImageImplementation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import graphicsEngine.RenderableObject;

public class PolygonObject extends RenderableObject{
	private Graphics2D g2d;
	private AffineTransform oldTransformation;
	private Polygon polygon;
	private boolean draw;
	
	public PolygonObject(int x, int y, Color color, double angle, int rotateAngleCenterX, int rotateAngleCenterY,
			Polygon polygon,boolean draw) {
		super(x, y, color, angle, rotateAngleCenterX, rotateAngleCenterY);
		this.draw = draw;
		this.polygon = polygon;
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
				g2d.drawPolygon(this.polygon);
			}else{
				g2d.fillPolygon(this.polygon);
			}
			g2d.setTransform(oldTransformation);
		}else{
			if(draw){
				g2d.drawPolygon(this.polygon);
			}else{
				g2d.fillPolygon(this.polygon);
			}
		}	
	}
	
	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public Polygon getPolygon() {
		return polygon;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}
}