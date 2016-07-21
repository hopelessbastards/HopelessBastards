package applogic.collision;

public class HomogeneusOperations {
	
	private HomogeneusCoordinate line;
	
	public HomogeneusOperations() {
		super();	
		this.line = new HomogeneusCoordinate(0, 0, 0);
	}
	
	public HomogeneusCoordinate getHomogeneusMultiplication(HomogeneusCoordinate a, HomogeneusCoordinate b){
		line.setI((a.getJ() * b.getK()) - (b.getJ() * a.getK()));
		line.setJ((b.getI() * a.getK()) - (a.getI() * b.getK()));
		line.setK((a.getI() * b.getJ()) - (b.getI() * a.getJ()));
		
		return line;
	}
	
	public DoublePoint twoLinesCommonPoint(DoublePoint[] points){
		
		HomogeneusCoordinate a = new HomogeneusCoordinate(points[0], points[1]);
		HomogeneusCoordinate b = new HomogeneusCoordinate(points[2], points[3]);
		
		HomogeneusCoordinate end = getHomogeneusMultiplication(a, b);
		DoublePoint p = new DoublePoint((int)(end.getI()/end.getK()), (int)(end.getJ()/end.getK()));
		
		return p;
	}
}