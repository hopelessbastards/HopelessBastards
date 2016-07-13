package applogic.viewbuilder;

import screenconverter.descriptors.StringDescriptor;

public abstract class IStringViewBuilder implements IViewBuilder{
	private boolean deletable;
	
	public abstract StringDescriptor[] getStringDescriptor();
	
	@Override
	public boolean isDeletable() {
		
		return this.deletable;
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
		
	}
}