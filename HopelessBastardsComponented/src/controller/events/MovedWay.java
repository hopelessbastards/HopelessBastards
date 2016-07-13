package controller.events;

public class MovedWay {
	private MoveWayEnum way;

	public MovedWay(MoveWayEnum way) {
		super();
		this.way = way;
	}

	public MoveWayEnum getWay() {
		return way;
	}

	public void setWay(MoveWayEnum way) {
		this.way = way;
	}
	
}
