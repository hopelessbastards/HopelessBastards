package applogic.collision;

public class DoublePoint {
	private int x;
	private int y;
	
	public DoublePoint(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public DoublePoint() {
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
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
		DoublePoint other = (DoublePoint) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x) && ((Math.abs(x) != 0 && Math.abs(other.x) != 0)))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y) && ((Math.abs(x) != 0 && Math.abs(other.x) != 0)))
			return false;
		return true;
	}

	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "x:" + this.x + " y:" + this.y;
	}
}