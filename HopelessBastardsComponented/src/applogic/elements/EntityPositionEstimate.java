package applogic.elements;

public class EntityPositionEstimate {
	private double oldx1;
	private double oldy1;
	private double oldx2;
	private double oldy2;
	
	private double oldangle1;
	private double oldangle2;
	
	private double interspeedx;
	private double interspeedy;
	private double interanglespeed;
	
	private double interx;
	private double intery;
	private double interangle;
	
	private Entity entity;
	private double lastlastTick;
	private double lastTick;
	
	public EntityPositionEstimate(Entity entity) {
		this.oldx1 = entity.getX();
		this.oldx2 = entity.getX();
		this.oldy1 = entity.getY();
		this.oldy2 = entity.getY();
		
	
		this.interx = entity.getX();
		this.intery = entity.getY();
		
		this.entity = entity;
		lastlastTick = (double)System.nanoTime() / 1000000000.0;
		lastTick = (double)System.nanoTime() / 1000000000.0;
	}

	public double getOldx1() {
		return oldx1;
	}

	public void setOldx1(double oldx1) {
		this.oldx1 = oldx1;
	}

	public double getOldy1() {
		return oldy1;
	}

	public void setOldy1(double oldy1) {
		this.oldy1 = oldy1;
	}

	public double getOldx2() {
		return oldx2;
	}

	public void setOldx2(double oldx2) {
		this.oldx2 = oldx2;
	}

	public double getOldy2() {
		return oldy2;
	}

	public void setOldy2(double oldy2) {
		this.oldy2 = oldy2;
	}

	public double getOldangle1() {
		return oldangle1;
	}

	public void setOldangle1(double oldangle1) {
		this.oldangle1 = oldangle1;
	}

	public double getOldangle2() {
		return oldangle2;
	}

	public void setOldangle2(double oldangle2) {
		this.oldangle2 = oldangle2;
	}

	public double getInterspeedx() {
		return interspeedx;
	}

	public void setInterspeedx(double interspeedx) {
		this.interspeedx = interspeedx;
	}

	public double getInterspeedy() {
		return interspeedy;
	}

	public void setInterspeedy(double interspeedy) {
		this.interspeedy = interspeedy;
	}

	public double getInteranglespeed() {
		return interanglespeed;
	}

	public void setInteranglespeed(double interanglespeed) {
		this.interanglespeed = interanglespeed;
	}

	public double getInterx() {
		return interx;
	}

	public void setInterx(double interx) {
		this.interx = interx;
	}

	public double getIntery() {
		return intery;
	}

	public void setIntery(double intery) {
		this.intery = intery;
	}

	public double getInterangle() {
		return interangle;
	}

	public void setInterangle(double interangle) {
		this.interangle = interangle;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public double getLastlastTick() {
		return lastlastTick;
	}

	public void setLastlastTick(double lastlastTick) {
		this.lastlastTick = lastlastTick;
	}

	public double getLastTick() {
		return lastTick;
	}

	public void setLastTick(double lastTick) {
		this.lastTick = lastTick;
	}
	
}