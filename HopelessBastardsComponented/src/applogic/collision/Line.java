package applogic.collision;

public class Line {
	private DoublePoint p1;
	private DoublePoint p2;
	private LineSide side;

	public Line(DoublePoint p1, DoublePoint p2, LineSide side) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.side = side;
	}
	
	@Override
	public String toString() {
		return p1.toString() + "    " + p2.toString();
	}

	public LineSide getSide() {
		return side;
	}

	public Line setSide(LineSide side) {
		this.side = side;
		return this;
	}

	public DoublePoint getP1() {
		return p1;
	}

	public Line setP1(DoublePoint p1) {
		this.p1 = p1;
		return this;
	}

	public DoublePoint getP2() {
		return p2;
	}

	public Line setP2(DoublePoint p2) {
		this.p2 = p2;
		return this;
	}	
}