package controller;

import controller.events.CursorInformation;
import controller.events.KeyValue;

public interface MenuListener {
	public void cursorClicked(CursorInformation screen);
	public void cursorMOved(CursorInformation screen);
	public void cursorRelease();
	public void characterTyped(KeyValue value);
}
