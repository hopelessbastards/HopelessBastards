package screenconverter;

import java.awt.Rectangle;
import java.util.List;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.ILineViewBuilder;
import applogic.viewbuilder.IOvalViewBuilder;
import applogic.viewbuilder.IPolygonViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;
import applogic.viewbuilder.IStringViewBuilder;
import graphicsEngine.IRenderer;
import screenconverter.descriptors.ImageDescriptor;
import screenconverter.descriptors.LineDescriptor;
import screenconverter.descriptors.OvalDescriptor;
import screenconverter.descriptors.RectangleDescriptor;
import screenconverter.descriptors.StringDescriptor;

public class Converter implements IConverter{
	public static boolean doit;
	private IEstimator estimator;
	
	private IRenderer renderer;
	
	/*Ez itt lényegében egy objektum pool.Lényege, hogy 6 féle alakzat létezik(kép,oval,polygon,
	 rectangle,vonal,szöveg), és ezek mindegyikéhez létrehoztam egy osztályt,ami leír egy adott alakzatot.
	 Például egy magehez a képet, azaz a kép logikai nevét, és az animációban a sorszámát,x,ykoord,stb.
	 Ezek a leírok szolgálnak arra, hogy a Renderer objektumnak a render metódusának átadjuk,
	 az pedig ezek alapján el tudja dönteni, hogy mit is rajzoljon.Ezek azért poolt alkotnak, 
	 mert így nem kell mindegyikbõl több példányt létrehozni, hanem csak az adattagjai értékeit
	 módosítom.*/
	private OvalDescriptor oval = new OvalDescriptor(1010, 10, 0, 0,0,100,100,null,false);
	private StringDescriptor string = new StringDescriptor(100, 100,100, 0, 0,0, "alma",null,"fontName");
	private RectangleDescriptor rectangle = new RectangleDescriptor(0, 0,0 ,0,0, 80, 80,null,false);
	private ImageDescriptor image = new ImageDescriptor(400, 400, 0,0,0, 1000,100, "fire",1);
	private LineDescriptor line = new LineDescriptor(10, 100,0,0,0,200 , 200,null);
	
	private ICamera camera;
	
	/**/
	private Rectangle cameraMonitorCheckingElementsHelper;
	
	private Rectangle visibleArea;/*A Converter komponens tudja, hogy mi a kirajzolandó képek
	köre, és csak azokat a leírókat küldi kirajzolásra, amelyek a képernyõn látszani fognak, ez hatalmas
	gyorsítást jelent.*/
	
	private IMonitorScreenManager monitorScreenManager;/*A Converter komponens egyik szolgáltatása, hogy
	megutdja válaszolni, hogy a skillképek,skillbar, healtbar pontosan milyen koordinátákon
	helyeszkednek el.Mivel azokat a kamerával együtt kell mozgatni.Az APP-Logic komponens ViewBuilder
	egységei ettõl kérnek adatokat szolgáltatás formályában.*/
	
	private ICanvasSizeRefresher canvasSizeRefresher;/*Ez a komponens frissíti a Canvas méretének adatait.*/
	
	/*Ez a két változó tartalmazza a középpontot, amihez a kamera majd viszonyítani fog.Ez a 
	 player lesz.*/
	private int centerX;
	private int centerY;
	
	public Converter(IRenderer renderer) {
		 this.renderer = renderer;
		 this.camera = new Camera();
		 this.cameraMonitorCheckingElementsHelper = new Rectangle();
		 this.visibleArea = new Rectangle();
		 this.canvasSizeRefresher = new CanvasSizeRefresher(renderer);
		 this.monitorScreenManager = new MonitorScreenManager(this.renderer,7,this.canvasSizeRefresher);
		 this.estimator = new Estimator();
	}
	
	@Override
	public void moveCameraToDefault() {
		renderer.cameraMove(0, 0);
		this.centerX = 0;
		this.centerY = 0;
	}
	
	@Override
	public void moveCamera(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
		camera.setCamera(-centerX + renderer.getCanvasSize().getWIDTH()/2,-centerY + renderer.getCanvasSize().getHEIGHT()/2);
		renderer.cameraMove(camera.getX(),camera.getY());
	}
	
	/*A Converter komponens szolgaltatás metódusa.Itt a Renderer komponens render() túlterhelt metódusát
	 hívjuk meg annyiszor amennyi elemet átadott az AppLogic, és még néhány sajátot is hozzárak,
	 pl.kósza madarak, stb jelentéktelen vizuális effektek.*/
	@Override
	public void stickImages(List<IImageViewBuilder> describer, double renderTime) {       
        for(int i=0;i<describer.size();i++){
        	if(describer.get(i).getImageDescriptor() != null){
        		
        		if(describer.get(i).getPositionEstimate() != null && !describer.get(i).getPositionEstimate().getEntity().isThisEntityIsThePlayer()){
					
        			estimator.estimateNewPosition(describer.get(i), describer.get(i).getPositionEstimate(), renderTime);
        			
        		}
        		
        		for(int j=0;j<describer.get(i).getImageDescriptor().length;j++){
     				/*A megvizsgált elemet ebbe rakjuk, ez segédváltozó.*/
     				this.cameraMonitorCheckingElementsHelper.setBounds(describer.get(i).getImageDescriptor()[j].getX(),describer.get(i).getImageDescriptor()[j].getY() ,describer.get(i).getImageDescriptor()[j].getWidth() ,describer.get(i).getImageDescriptor()[j].getHeight());
     				
     				/*Csak azokat a komponenseket rajzoljuk ki, melyek benne lesznek a kirajzolt
     				képernyõn.*/
     				if(this.cameraMonitorCheckingElementsHelper.intersects(getVisibleArea())){
     					//describer.get(i).getImageDescriptor()[j].
     					
     						
     				//	doit = true;
     					renderer.render(describer.get(i).getImageDescriptor()[j]);
     					//doit = false;
     				}
        		}
        	}
        }
	}

	@Override
	public void stickLines(List<ILineViewBuilder> describer) {
		 for(int i=0;i<describer.size();i++){
				for(int j=0;j<describer.get(i).getLineDescriptor().length;j++){
						/*A megvizsgált elemet ebbe rakjuk, ez segédváltozó.*/
						this.cameraMonitorCheckingElementsHelper.setBounds(describer.get(i).getLineDescriptor()[j].getX(),describer.get(i).getLineDescriptor()[j].getY() ,10,10);
						
						/*Csak azokat a komponenseket rajzoljuk ki, melyek benne lesznek a kirajzolt
						 képernyõn.*/
						if(this.cameraMonitorCheckingElementsHelper.intersects(getVisibleArea())){
							renderer.render(describer.get(i).getLineDescriptor()[j]);
						}
				}
	     }
	}

	@Override
	public void stickPolygones(List<IPolygonViewBuilder> describer) {
		 for(int i=0;i<describer.size();i++){
				for(int j=0;j<describer.get(i).getPolygonDescriptor().length;j++){
						/*A megvizsgált elemet ebbe rakjuk, ez segédváltozó.*/
						this.cameraMonitorCheckingElementsHelper.setBounds(describer.get(i).getPolygonDescriptor()[j].getX(),describer.get(i).getPolygonDescriptor()[j].getY() ,10 ,10);
						
						/*Csak azokat a komponenseket rajzoljuk ki, melyek benne lesznek a kirajzolt
						 képernyõn.*/
						if(this.cameraMonitorCheckingElementsHelper.intersects(getVisibleArea())){
							renderer.render(describer.get(i).getPolygonDescriptor()[j]);
						}
				}
	     }
	}

	@Override
	public void stickRectangles(List<IRectangleViewBuilder> describer) {
		 for(int i=0;i<describer.size();i++){
				for(int j=0;j<describer.get(i).getRectangleDescriptor().length;j++){
						/*A megvizsgált elemet ebbe rakjuk, ez segédváltozó.*/
						this.cameraMonitorCheckingElementsHelper.setBounds(describer.get(i).getRectangleDescriptor()[j].getX(),describer.get(i).getRectangleDescriptor()[j].getY() ,describer.get(i).getRectangleDescriptor()[j].getWidth() ,describer.get(i).getRectangleDescriptor()[j].getHeight());
						
						/*Csak azokat a komponenseket rajzoljuk ki, melyek benne lesznek a kirajzolt
						 képernyõn.*/
						if(this.cameraMonitorCheckingElementsHelper.intersects(getVisibleArea())){
							renderer.render(describer.get(i).getRectangleDescriptor()[j]);
						}
				}
	     }
	}

	@Override
	public void stickOvals(List<IOvalViewBuilder> describer) {
		 for(int i=0;i<describer.size();i++){
				for(int j=0;j<describer.get(i).getOvalDescriptor().length;j++){
						/*A megvizsgált elemet ebbe rakjuk, ez segédváltozó.*/
						this.cameraMonitorCheckingElementsHelper.setBounds(describer.get(i).getOvalDescriptor()[j].getX(),describer.get(i).getOvalDescriptor()[j].getY() ,describer.get(i).getOvalDescriptor()[j].getWidth() ,describer.get(i).getOvalDescriptor()[j].getHeight());
						
						/*Csak azokat a komponenseket rajzoljuk ki, melyek benne lesznek a kirajzolt
						 képernyõn.*/
						if(this.cameraMonitorCheckingElementsHelper.intersects(getVisibleArea())){
							renderer.render(describer.get(i).getOvalDescriptor()[j]);
						}
				}
	     }
	}

	@Override
	public void stickStrings(List<IStringViewBuilder> describer) {
		 for(int i=0;i<describer.size();i++){
				for(int j=0;j<describer.get(i).getStringDescriptor().length;j++){
						/*A megvizsgált elemet ebbe rakjuk, ez segédváltozó.*/
						this.cameraMonitorCheckingElementsHelper.setBounds(describer.get(i).getStringDescriptor()[j].getX(),describer.get(i).getStringDescriptor()[j].getY() ,10,10);
						
						/*Csak azokat a komponenseket rajzoljuk ki, melyek benne lesznek a kirajzolt
						 képernyõn.*/
						if(this.cameraMonitorCheckingElementsHelper.intersects(getVisibleArea())){
							renderer.render(describer.get(i).getStringDescriptor()[j]);
						}
				}
	     }
	}
	
	private Rectangle getVisibleArea(){
		/*A describer objektumban megkapjuk a követendõ player pozícióját.*/
		
		/*Ez azért kell,hogy ne rendereljük már mindent ki,csak azt ami a kivetített képernyõn lesz,ez kevés megjelenítendõ
		 dolognál nem szükséges,de ha sok minden van a képernyõn,akkor nélkülözhetetlen.*/
		
		/*-5 azért kell,mert a képernyõn kívül 5 pixelt vastagon még kirajzolunk frissítünk.*/
		/*10 mivel kétoldalról 5 plussz pixel*/
		
		
		/*Az alapértelmezett érték az a (0,0) amikoris nincs elmozdítva a kamera,ez a bejelentkezõ
		 képernyõ ilyen, akkor az a speckó eset kell,  ha pedig van kamera a képben, akkor
		 az alsó.*/
		if(centerX == 0 && centerY == 0){
			this.visibleArea.setBounds(0,0,renderer.getCanvasSize().getWIDTH(),renderer.getCanvasSize().getHEIGHT());
		}else{
			this.visibleArea.setBounds(this.centerX-(renderer.getCanvasSize().getWIDTH()/2),this.centerY-(renderer.getCanvasSize().getHEIGHT()/2),renderer.getCanvasSize().getWIDTH(),renderer.getCanvasSize().getHEIGHT());
		}
		
		return this.visibleArea;
	}

	@Override
	public IMonitorScreenManager getMonitorScreenManager() {
		return this.monitorScreenManager;
	}

	@Override
	public Size getCanvasSize() {
		return renderer.getCanvasSize();
	}

	@Override
	public void endOfPieces() {
		renderer.canvasRefresh();
	}

	@Override
	public void startOfPieces() {
		renderer.canvasInit();
	}

	@Override
	public Rectangle getCanvasRectangleOnScreen() {
		return this.renderer.getCanvasRectangleOnScreen();
	}
}