package bufferedImageImplementation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Map;
import graphicsEngine.Animation;
import graphicsEngine.ICanvas;
import graphicsEngine.IRenderer;
import graphicsEngine.IimageLoader;
import screenconverter.Size;
import screenconverter.descriptors.ImageDescriptor;
import screenconverter.descriptors.LineDescriptor;
import screenconverter.descriptors.OvalDescriptor;
import screenconverter.descriptors.PolygonDescriptor;
import screenconverter.descriptors.RectangleDescriptor;
import screenconverter.descriptors.StringDescriptor;

public class Renderer implements IRenderer{
	/*Ez egy köztes szereplõ a kirajzolási terület és a logikai komponens között.*/
	
	/*Ez tárolja az animációkat.*/
	private Map<String,Animation> images;
	/*Ismeri a kirajzolási területet, és a képbetöltõ komponenst és interfacen keresztül hivja õket.*/
	private ICanvas canvas;
	private IimageLoader imageLoader;
	
	private ImageObject image;
	private RectangleObject rectangle;
	private StringObject string;
	private LineObject line;
	private OvalObject oval;
	private PolygonObject polygon;
	
	private ScreenTranslater screenTranslater;
	
	public Renderer(ICanvas canvas,IimageLoader imageLoader) {
		//Constructor
		this.canvas = canvas;
		this.imageLoader = imageLoader;
		
	
		
		/*A MAP feltöltése az ImageLoader komponens segíttségével.*/
		images = this.imageLoader.imageLoad();
		
		image = new ImageObject(0, 0, null, 0, 0,0,20,20,1,1, null);
		rectangle = new RectangleObject(100, 100, Color.red, 20, 150, 150, 100, 100, true);
		string = new StringObject(600, 600,30, Color.black, 0,0,0, "Az alma finom",new Font("Jokerman",Font.PLAIN,20));
		oval = new OvalObject(10, 10, Color.red, 0, 0, 0, 50, 50, true);
		line = new LineObject(300, 300, Color.blue, 0,0,0, 800, 800);
		polygon = new PolygonObject(100, 100,null,0,0,0,null,true);
		
		this.screenTranslater = new ScreenTranslater(0, 0);
	}
		
	@Override
	public ICanvas getCanvas() {
		return canvas;
	}

	@Override
	public void render(RectangleDescriptor describer) {

		rectangle.setAngle(describer.getAngle());
		if(describer.getColor() != null){
			rectangle.setColor(describer.getColor());
		}
		rectangle.setX(describer.getX());
		rectangle.setY(describer.getY());
		rectangle.setRotateAngleCenterX(describer.getAngleCenterPointX());
		rectangle.setRotateAngleCenterY(describer.getAngleCenterPointY());
		rectangle.setHeight(describer.getHeight());
		rectangle.setWidth(describer.getWidth());
		rectangle.setDraw(describer.isDraw());
		
		canvas.render(rectangle);
		
	}
	@Override
	public void render(ImageDescriptor describer){		
		
		image.setAngle(describer.getAngle());
		image.setX(describer.getX());
		image.setY(describer.getY());
		image.setRotateAngleCenterX(describer.getAngleCenterPointX());
		image.setRotateAngleCenterY(describer.getAngleCenterPointY());
		image.setImage(images.get(describer.getImageLogicalName()).getAnimationSlice(describer.getAnimation()));
		image.setWidthscale(describer.getWidthscale());
		image.setHeightscale(describer.getHeightscale());
		image.setWidth(describer.getWidth());
		image.setHeight(describer.getHeight());
		canvas.render(image);
	}

	@Override
	public void render(OvalDescriptor describer) {
		
		if(describer.getColor() != null){
			oval.setColor(describer.getColor());
		}
		oval.setAngle(describer.getAngle());
		oval.setRotateAngleCenterX(describer.getAngleCenterPointX());
		oval.setRotateAngleCenterY(describer.getAngleCenterPointY());
		oval.setX(describer.getX());
		oval.setY(describer.getY());
		oval.setDraw(describer.isDraw());
		
		canvas.render(oval);
	}

	@Override
	public void render(LineDescriptor describer) {
		if(describer.getColor() != null){
			line.setColor(describer.getColor());
		}
		line.setAngle(describer.getAngle());
		line.setRotateAngleCenterX(describer.getAngleCenterPointX());
		line.setRotateAngleCenterY(describer.getAngleCenterPointY());
		line.setX(describer.getX());
		line.setY(describer.getY());
		line.setX2(describer.getX2());
		line.setY2(describer.getY2());
		
		canvas.render(line);
	}

	@Override
	public void render(StringDescriptor describer) {
		
		string.setAngle(describer.getAngle());
		if(describer.getColor() != null){
			string.setColor(describer.getColor());
		}
		string.setX(describer.getX());
		string.setY(describer.getY());
		string.setRotateAngleCenterX(describer.getAngleCenterPointX());
		string.setRotateAngleCenterY(describer.getAngleCenterPointY());
		string.setString(describer.getString());
		string.setSize(describer.getSize());
		
		string.setFont(new Font(describer.getFontName(), Font.PLAIN, describer.getSize()));
		
		canvas.render(string);
	}

	@Override
	public void render(PolygonDescriptor describer) {
		if(describer.getColor() != null){
			polygon.setColor(describer.getColor());
		}
		polygon.setAngle(describer.getAngle());
		polygon.setDraw(describer.isDraw());
		polygon.setPolygon(describer.getPolygon());
		polygon.setRotateAngleCenterX(describer.getAngleCenterPointX());
		polygon.setRotateAngleCenterY(describer.getAngleCenterPointY());
		polygon.setX(describer.getX());
		polygon.setY(describer.getY());
		
		canvas.render(polygon);
	}

	@Override
	public void cameraMove(int x,int y) {
		/*A Renderer egyik szolgáltatása, hogy elõször is inicializálja ezzel a canvast, majd bemozgatja
		 a képet annyival, mint amiket a paraméterbe kap.*/
		this.screenTranslater.setX(x);
		this.screenTranslater.setY(y);
		canvas.render(this.screenTranslater);
	}

	@Override
	public Size getCanvasSize() {
		return canvas.getCanvasSize();
	}

	@Override
	public void canvasRefresh() {
		canvas.refresh();
	}

	@Override
	public void canvasInit() {
		canvas.init();
	}

	@Override
	public Rectangle getCanvasRectangleOnScreen() {
		return this.canvas.getCanvasRectangleOnScreen();
	}
}