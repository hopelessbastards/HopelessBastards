package applogic;

import applogic.engine.Engine;
import applogic.loginstate.Login;
import controller.IController;
import screenconverter.IConverter;
import soundapi.ISoundProvider;

public class GameStateContainer extends AbstractGameStateContainer{

	public GameStateContainer(IConverter converter, IController controller,ISoundProvider soundProvider) {
		super(converter, controller,soundProvider);
		
		/*Ennek szüksége van a soundProviderre, mert a felsõbb szintektõl ez tudja elkérni,
		 és továbbadni lejebb, mert ebben van benne a logikai-fizikai sound resource összerendelés.*/

		/*Kezdéskor ez legyen a gamestate.*/
		//setActualGameState(new Engine(converter));
		setActualGameState(new Login(converter,getSoundProvider()));
		/*Az alábbiakban a GameStatenkat átadjuk a controllernek, hogy ez lesz az esemény
		 figyelõ osztályom.*/
		if(getActualGameState().getOwnGameStateType() == GameStateEnum.GAME){
			getController().addGameListener(getActualGameState());
		}else if(getActualGameState().getOwnGameStateType() == GameStateEnum.MENU){
			getController().addMenuListener(getActualGameState());
		}else if(getActualGameState().getOwnGameStateType() == GameStateEnum.LOGIN){
			getController().addMenuListener(getActualGameState());
		}
	}

	/*Ezt a tick metódust hívja meg a GameLoop.*/
	@Override
	public void tick(double appTime) {
		
		/*A GameStatek tick metódusa egy GameStateEnumot ad vissza(LOGIN,GAME,MENU), ami azt jelzi, hogy
		 mi legyen a következõ gamestate.Tehát az Engine addig null-t fog visszaadni, amíg nem kell
		 GameStatet váltani, viszont ha kell akkor a nextGameStetet adja vissza.*/
		setNextGameState(getActualGameState().tick(appTime));
		
		/*Nullt ad vissza a gamestate, ha nem kell más gamestatera válatni.Viszont ha nem null,
		 akkor új gamestatenk van, és be kell jegyeznünk mint új figyelõt.*/
		if(getNextGameState() != null){
			getController().removeAllListeners();
			if(getNextGameState() == GameStateEnum.GAME){
				setActualGameState(new Engine(getConverter(),getSoundProvider()));
				getController().addGameListener(getActualGameState());
			}else if(getNextGameState() == GameStateEnum.MENU){
				setActualGameState(new Menu(getSoundProvider()));
				getController().addMenuListener(getActualGameState());
			}else if(getNextGameState() == GameStateEnum.LOGIN){
				setActualGameState(new Login(getConverter(),getSoundProvider()));
				getController().addMenuListener(getActualGameState());
			}
		}
	}

	/*A render metódusban, amit a GameLoop hív meg annyit csinálunk, hogy az aktuális GameStatenak
	 meghívjuk a viewPreapre metódusát, ami összerakja és kirajzolja a guit.*/
	@Override
	public void render() {
		getActualGameState().viewPrepare();	
	}
}
