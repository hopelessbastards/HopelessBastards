package applogic.viewbuilder;

import screenconverter.descriptors.ImageDescriptor;

public abstract class IImageViewBuilder implements IViewBuilder{
	private boolean deletable;
	
	public abstract ImageDescriptor[] getImageDescriptor();
	
	@Override
	public boolean isDeletable() {
		
		return this.deletable;
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
		
	}
}
