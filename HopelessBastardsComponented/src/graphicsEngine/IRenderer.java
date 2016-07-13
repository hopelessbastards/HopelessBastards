package graphicsEngine;

import screenconverter.Size;
import screenconverter.descriptors.ImageDescriptor;
import screenconverter.descriptors.LineDescriptor;
import screenconverter.descriptors.OvalDescriptor;
import screenconverter.descriptors.PolygonDescriptor;
import screenconverter.descriptors.RectangleDescriptor;
import screenconverter.descriptors.StringDescriptor;

/*A Renderer szolgáltatás ezen az interfacen keresztül érhetõ el.*/
public interface IRenderer {
	/*Itt túlterhelt metódusokat láthatunk.*/
	public void render(ImageDescriptor describer);
	public void render(RectangleDescriptor describer);
	public void render(OvalDescriptor describer);
	public void render(LineDescriptor describer);
	public void render(StringDescriptor describer);
	public void render(PolygonDescriptor describer);
	public ICanvas getCanvas();
	public void cameraMove(int x,int y);
	public Size getCanvasSize();
	
	public void canvasInit();
	public void canvasRefresh();
}