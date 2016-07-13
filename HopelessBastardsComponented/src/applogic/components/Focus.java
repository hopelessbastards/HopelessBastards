package applogic.components;

public class Focus implements IFocus{

	private IComponent focusOwner;
	
	@Override
	public IComponent getFocusOwnerComponent() {
		return this.focusOwner;
	}

	@Override
	public void setFocusOwnerComponent(IComponent component) {
		this.focusOwner = component;
	}
}