package screenconverter.descriptors;

import java.awt.Color;
import java.awt.Polygon;

public class PolygonDescriptor extends DrawObjectDescriptor{
	private Polygon polygon;/*itt lehet más térolási eszköz kellene a rugalmasság végett.*/
	/*Ez még nincs kész*/
	
	public PolygonDescriptor(int x, int y, double angle, int angleCenterPointX, int angleCenterPointY,
			Color color,boolean draw,Polygon polygon) {
		super(x, y, angle, angleCenterPointX, angleCenterPointY,color,draw);
		this.polygon = polygon;
	}

	public Polygon getPolygon() {
		return polygon;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}
	
	
}
