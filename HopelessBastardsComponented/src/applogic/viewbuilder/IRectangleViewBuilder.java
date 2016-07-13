package applogic.viewbuilder;

import screenconverter.descriptors.RectangleDescriptor;

public abstract class IRectangleViewBuilder implements IViewBuilder{
	private boolean deletable;
	
	public abstract RectangleDescriptor[] getRectangleDescriptor();

	@Override
	public boolean isDeletable() {
		return this.deletable;
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;		
	}
}