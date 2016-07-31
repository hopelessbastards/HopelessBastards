package applogic.viewbuilder;

import applogic.elements.BasicElement;
import applogic.elements.EntityPositionEstimate;
import screenconverter.descriptors.ImageDescriptor;

public abstract class IImageViewBuilder implements IViewBuilder{
	private boolean deletable;
	private EntityPositionEstimate positionEstimate;
	private double x;
	private double y;
	private double angle;
	
	public abstract ImageDescriptor[] getImageDescriptor();
	public abstract BasicElement getTheRepresentetedElement();
	
	public EntityPositionEstimate getPositionEstimate() {
		return positionEstimate;
	}

	public void setPositionEstimate(EntityPositionEstimate positionEstimate) {
		this.positionEstimate = positionEstimate;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	@Override
	public boolean isDeletable() {
		
		return this.deletable;
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}
}
