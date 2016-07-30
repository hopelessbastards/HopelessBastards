package applogic.viewbuilder.entities;

import java.awt.Point;

import applogic.collision.DoublePoint;

public class Futosgal {
	public static void main(String[] args) {
		LineEquation line = new LineEquation();
		double[] tomb = line.lineMaker(new DoublePointtt(10,10), new DoublePointtt(100,100));
		double x = line.xUnknown(tomb, 60);
		System.out.println(x);
	}
}
