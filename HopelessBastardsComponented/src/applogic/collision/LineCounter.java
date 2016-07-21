package applogic.collision;


public class LineCounter {
	private HomogeneusOperations op;

	public boolean twoLinesCollided(DoublePoint[] p){
		op = new HomogeneusOperations();
		
		DoublePoint doublePoints = op.twoLinesCommonPoint(p);
		
		int minx = 0;
		int maxx = 0;
		int miny = 0;
		int maxy = 0;
		
		for(int i=0;i<p.length - 2;i++){
			if(p[i].getX() < p[minx].getX()){
				minx = i;
			}
			
			if(p[i].getX() > p[maxx].getX()){
				maxx = i;
			}
			
			if(p[i].getY() < p[miny].getY()){
				miny = i;
			}
			
			if(p[i].getY() > p[maxy].getY()){
				maxy = i;
			}
		}
		
		int minx2 = 2;
		int maxx2 = 2;
		int miny2 = 2;
		int maxy2 = 2;
		
		for(int i=2;i<p.length;i++){
			if(p[i].getX() < p[minx2].getX()){
				minx2 = i;
			}
			
			if(p[i].getX() > p[maxx2].getX()){
				maxx2 = i;
			}
			
			if(p[i].getY() < p[miny2].getY()){
				miny2 = i;
			}
			
			if(p[i].getY() > p[maxy2].getY()){
				maxy2 = i;
			}
		}
		
		if(doublePoints.getX() >= p[minx].getX() && doublePoints.getX() <= p[maxx].getX() && 
				doublePoints.getY() >= p[miny].getY() && doublePoints.getY() <= p[maxy].getY() &&
				doublePoints.getX() >= p[minx2].getX() && doublePoints.getX() <= p[maxx2].getX() && 
				doublePoints.getY() >= p[miny2].getY() && doublePoints.getY() <= p[maxy2].getY()
				){
			
			return true;
		}else{
			
			return false;
		}
	}
}