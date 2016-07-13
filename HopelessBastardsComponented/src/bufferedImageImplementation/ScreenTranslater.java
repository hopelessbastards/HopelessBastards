package bufferedImageImplementation;

import java.awt.Graphics2D;

import graphicsEngine.CanvasCommand;

public class ScreenTranslater extends CanvasCommand{
	/*Ez egy olyan elem, ami szintén bufferedImage implementáció fûggõ.Ez arréb transzformálja a képet,
	 ez valósítja meg a cameramozgatást. */
	private Graphics2D g2d;
	
	public ScreenTranslater(int x, int y) {
		super(x, y);
	}

	@Override
	public void render(Object graphicContext) {
		g2d = (Graphics2D)graphicContext;
	
		/*A render metódus amit a fõ ciklus meghív.Ez fogja minden pályaelem render metódusát meghívni.
		 Elõször eltolja a képernyõt a camera objektum szerint úgy,hogy a player legyen középen.*/
		g2d.translate(getX(),getY());
	}
}