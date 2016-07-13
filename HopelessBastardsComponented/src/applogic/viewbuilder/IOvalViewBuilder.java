package applogic.viewbuilder;

import screenconverter.descriptors.OvalDescriptor;

public abstract class IOvalViewBuilder implements IViewBuilder{
	private boolean deletable;
	
	public abstract OvalDescriptor[] getOvalDescriptor();
	
	@Override
	public boolean isDeletable() {
		return this.deletable;
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;		
	}
}