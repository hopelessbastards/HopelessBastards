package applogic.viewbuilder;

import screenconverter.descriptors.PolygonDescriptor;

public abstract class IPolygonViewBuilder implements IViewBuilder{
	private boolean deletable;
	
	public abstract PolygonDescriptor[] getPolygonDescriptor();
	
	@Override
	public boolean isDeletable() {
		return this.deletable;
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;		
	}
}