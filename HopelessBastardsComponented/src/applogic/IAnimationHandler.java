package applogic;

public interface IAnimationHandler {
	/*Egy animationHandlernek vissza kell tudni, adnia, hogy melyik darabja jön az animációnak.*/
	public int animationPiece();
	
	/*Vissza kell tudni adnia, hogy ez kianimálható-e*/
	public boolean getAnimationable();
	
	/*Illetve biztosítanie kell szolgáltatást, hogy állíthatónak kell legyen, hogy
	 animáljon-e vagy sem.*/
	public void setAnimationable(boolean animationable);
	
	public String getLogicName();
}
