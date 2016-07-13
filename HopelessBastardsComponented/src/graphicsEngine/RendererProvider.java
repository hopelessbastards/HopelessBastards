package graphicsEngine;

import bufferedImageImplementation.Canvas;
import bufferedImageImplementation.ImageLoader;
import bufferedImageImplementation.Renderer;

public class RendererProvider implements IRendererProvider{

	@Override
	public IRenderer getRenderer() {
		IWindowFrame frame = new WindowFrame();
		ICanvas canvas = new Canvas(0,0, true);
		frame.addCanvas(canvas);
		IimageLoader imageLoader = new ImageLoader();
		IRenderer renderer = new Renderer(canvas,imageLoader);
		return renderer;
	}
}
