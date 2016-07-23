package applogic.collision;

public class LineCounter implements ILineCounter{
	private HomogeneusOperations op;

	public boolean twoLinesCollided(DoublePoint[] twoLines){
		op = new HomogeneusOperations();
		
		DoublePoint doublePoints = op.twoLinesCommonPoint(twoLines);
		
		int minx = 0;
		int maxx = 0;
		int miny = 0;
		int maxy = 0;
		
		for(int i=0;i<twoLines.length - 2;i++){
			if(twoLines[i].getX() < twoLines[minx].getX()){
				minx = i;
			}
			
			if(twoLines[i].getX() > twoLines[maxx].getX()){
				maxx = i;
			}
			
			if(twoLines[i].getY() < twoLines[miny].getY()){
				miny = i;
			}
			
			if(twoLines[i].getY() > twoLines[maxy].getY()){
				maxy = i;
			}
		}
		
		int minx2 = 2;
		int maxx2 = 2;
		int miny2 = 2;
		int maxy2 = 2;
		
		for(int i=2;i<twoLines.length;i++){
			if(twoLines[i].getX() < twoLines[minx2].getX()){
				minx2 = i;
			}
			
			if(twoLines[i].getX() > twoLines[maxx2].getX()){
				maxx2 = i;
			}
			
			if(twoLines[i].getY() < twoLines[miny2].getY()){
				miny2 = i;
			}
			
			if(twoLines[i].getY() > twoLines[maxy2].getY()){
				maxy2 = i;
			}
		}
		
		if(doublePoints.getX() >= twoLines[minx].getX() && doublePoints.getX() <= twoLines[maxx].getX() && 
				doublePoints.getY() >= twoLines[miny].getY() && doublePoints.getY() <= twoLines[maxy].getY() &&
				doublePoints.getX() >= twoLines[minx2].getX() && doublePoints.getX() <= twoLines[maxx2].getX() && 
				doublePoints.getY() >= twoLines[miny2].getY() && doublePoints.getY() <= twoLines[maxy2].getY()
				){
			
			return true;
		}else{
			
			return false;
		}
	}
}