package applogic.collision;

public class HomogeneusCoordinate {
	private double i;
	private double j;
	private double k;
	
	public HomogeneusCoordinate(double i, double j, double k) {
		this.i = i;
		this.j = j;
		this.k = k;
	}
	
	public HomogeneusCoordinate(DoublePoint a) {
		this.i = a.getX();
		this.j = a.getY();
		this.k = 1;
	}
	
	public HomogeneusCoordinate(DoublePoint a, DoublePoint b) {
		this.i = a.getY() - b.getY();
		this.j = b.getX() - a.getX();
		this.k = a.getX() * b.getY() - b.getX()* a.getY();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(i);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(j);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(k);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HomogeneusCoordinate other = (HomogeneusCoordinate) obj;
		if (Double.doubleToLongBits(i) != Double.doubleToLongBits(other.i))
			return false;
		if (Double.doubleToLongBits(j) != Double.doubleToLongBits(other.j))
			return false;
		if (Double.doubleToLongBits(k) != Double.doubleToLongBits(other.k))
			return false;
		return true;
	}

	public double getI() {
		return i;
	}

	public void setI(double i) {
		this.i = i;
	}

	public double getJ() {
		return j;
	}

	public void setJ(double j) {
		this.j = j;
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}	
	
	@Override
	public String toString() {
		return " i: " + this.i + " j:" + this.j + " k:" + this.k; 
	}
}