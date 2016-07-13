package applogic;

public interface IGameLoop {
	/*Egy gameloopnak olyannak kell lennie, hogy valemilyen algoritmus alkalmazásában,hívogatnia kell
	 a tick és a render metódusokat.*/
	public void tick(double appTime);
	public void render();
}
