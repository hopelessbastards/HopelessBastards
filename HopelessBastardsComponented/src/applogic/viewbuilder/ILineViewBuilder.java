package applogic.viewbuilder;

import screenconverter.descriptors.LineDescriptor;

public abstract class ILineViewBuilder implements IViewBuilder{
	private boolean deletable;
	
	public abstract LineDescriptor[] getLineDescriptor();
	
	@Override
	public boolean isDeletable() {
		
		return this.deletable;
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
		
	}
}
