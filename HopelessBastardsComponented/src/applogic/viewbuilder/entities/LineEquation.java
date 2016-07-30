package applogic.viewbuilder.entities;

import java.awt.Point;

import applogic.collision.DoublePoint;

public class LineEquation {
	
	public double[] lineMaker(DoublePointtt p1, DoublePointtt p2){
		double i1 = p2.getX() - p1.getX();
		double i2 = p2.getY() - p1.getY();
		
		double n1 = -i2;
		double n2 = i1;
		
		double constant = n1 * p1.getX() + n2 * p1.getY();
		
		double[] array = new double[3];
		array[0] = n1;
		array[1] = n2;
		array[2] = constant;
		return array;
		
	}
	
	public double xUnknown(double[] array, double y){
		return (array[2] - array[1] * y) / array[0];
	}
	
	public double yUnknown(double[] array, double x){
		return (array[2] - array[0] * x) / array[1];
	}
}
