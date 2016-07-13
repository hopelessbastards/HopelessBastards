package applogic.components;

public interface IFocus {
	public IComponent getFocusOwnerComponent();
	public void setFocusOwnerComponent(IComponent component);
}