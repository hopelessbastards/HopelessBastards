package screenconverter;

import graphicsEngine.IRenderer;

public class CanvasSizeRefresher implements ICanvasSizeRefresher{
	/*Ez egy olyan komponens, ami a Canvas aktuális szélességét és magasságát tudja biztosítani.*/
	private IRenderer renderer;
	
	public CanvasSizeRefresher(IRenderer renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public void refresh(Size canvasSize) {
		canvasSize = renderer.getCanvasSize();		
	}
}