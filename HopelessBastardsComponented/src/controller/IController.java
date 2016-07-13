package controller;

public interface IController{
	public void addMenuListener(MenuListener listener);
	public void addGameListener(GameListener listener);
	public void removeMenuListener();
	public void removeGameListener();
	public void removeAllListeners();
}
