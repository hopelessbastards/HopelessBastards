package controller;

import controller.events.ActivationNumber;
import controller.events.CursorInformation;
import controller.events.KeyValue;
import controller.events.MovedWay;

public interface GameListener {
	public void characterMoved(MovedWay way);
	public void characterStopMoving(MovedWay way);
	public void characterActivation(ActivationNumber number);
	public void cursorClicked(CursorInformation screen);
	public void cursorMOved(CursorInformation screen);
	public void cursorRelease();
	public void characterTyped(KeyValue value);
}
