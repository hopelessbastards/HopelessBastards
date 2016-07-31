package screenconverter;

import applogic.elements.EntityPositionEstimate;
import applogic.viewbuilder.IImageViewBuilder;

public interface IEstimator {
	public void estimateNewPosition(IImageViewBuilder viewBuilder, EntityPositionEstimate positionEstimate, double renderTime);
}
