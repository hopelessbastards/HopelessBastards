package applogic;

import controller.events.ActivationNumber;
import controller.events.CursorInformation;
import controller.events.KeyValue;
import controller.events.MovedWay;
import soundapi.ISoundProvider;

public class Menu extends GameState{

	
	public Menu(ISoundProvider soundProvider) {
		super(soundProvider);
		setOwnGameStateType(GameStateEnum.MENU);
	}
	
	@Override
	public void characterMoved(MovedWay way) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void characterStopMoving(MovedWay way) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void characterActivation(ActivationNumber number) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cursorClicked(CursorInformation screen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cursorMOved(CursorInformation screen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cursorRelease() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void characterTyped(KeyValue value) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public GameStateEnum tick(double appTime) {
		return null;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewPrepare(double renderTime) {
		// TODO Auto-generated method stub
		
	}

}
