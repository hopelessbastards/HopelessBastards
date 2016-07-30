package applogic.engine;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import applogic.CursorInformationProvider;
import applogic.GameLoop;
import applogic.IGarbageCollector;
import applogic.IMapLoader;
import applogic.IViewBuilderContainer;
import applogic.MapLoader;
import applogic.elements.Entity;
import applogic.elements.Player;
import applogic.elements.Tile;
import applogic.elements.controllers.PlayerRectangle;
import applogic.skills.imageicons.ImageIconTimerPicture;
import applogic.viewbuilder.HealthBarViewBuilder;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.ILineViewBuilder;
import applogic.viewbuilder.IOvalViewBuilder;
import applogic.viewbuilder.IPolygonViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;
import applogic.viewbuilder.IStringViewBuilder;
import applogic.viewbuilder.MouseViewBuilder;
import applogic.viewbuilder.SkillBarViewBuilder;
import applogic.viewbuilder.entities.DoublePointtt;
import applogic.viewbuilder.entities.LineEquation;
import applogic.viewbuilder.entities.MageViewBuilder;
import screenconverter.IConverter;
import screenconverter.IMonitorScreenManager;


public class ViewBuilderContainer implements IViewBuilderContainer{
	public double newxx;
	public double newyy;
	
	
	/*Ezt a komponenst fogja továbbhívni a GameState, amikor renderelnie kell.Ez a szolgáltatás 
	 gyûjti össze az összes kirajzolandó alakzatot, és képet, és adja át értelmezhetõ
	 formában a ScreenConverter szolgáltatásnak.*/
	
	private List<IOvalViewBuilder> ovalBuilder;
	private List<IStringViewBuilder> stringBuilder;
	private List<IRectangleViewBuilder> rectangleBuilder;
	private List<IPolygonViewBuilder> polygonBuilder;
	private List<ILineViewBuilder> lineBuilder;
	
	private List<IImageViewBuilder> viewBuilder;/*Ebbe van lényegében minden kép, kivéve párat.*/
	
	private List<IImageViewBuilder> notDestroyableView;/*Ebbe a listába van a talajközet 
	és az elpusztíthatatlan elemek, amik mindig ugyan ott vannak, és nem pusztíthatók el,
	ezeket azért raktam külön listába, hogy a GarbageCollectornak ne kelljen ezen feleslegesen
	végigmennie.*/
	
	private List<IImageViewBuilder> staticviewBuilder;/*Ebbe a folyamatosan a képpel mozgó elemeket
	rakom, ezeket azért kellett külön leválasztani a többitõl, hogy ezeket mindig utolsóként
	rajzoljuk ki, ezek legyenek mindenek felett, gondolok itt a skilbarra és a healthbarra, amiken
	ha keresztül gyalogolna a player az nem lenne túl esztétikus.*/
	
	private List<IImageViewBuilder> skillAnimationViewBuilders;
	
	private List<IStringViewBuilder> cdTimes;
	
	private IGarbageCollector garbageCollector;
	
	private Entity player;
	private PlayerRectangle playerRectangle;
	private IMapLoader mapLoader;
	
	private CursorInformationProvider cursor;
	
	private IConverter converter;
	
	private File file;
	private String path;
	
	public ViewBuilderContainer(List<Tile> tiles,List<Tile> nonBlockingTile,IConverter converter,Player player, PlayerRectangle playerRectangle, IGarbageCollector garbageCollector) {
		this.player = player;
		this.playerRectangle = playerRectangle;
		this.converter = converter;
		this.garbageCollector = garbageCollector;
		
		skillAnimationViewBuilders = new ArrayList<IImageViewBuilder>();
		notDestroyableView = new ArrayList<IImageViewBuilder>();
		
		staticviewBuilder = new ArrayList<IImageViewBuilder>();
		viewBuilder = new ArrayList<IImageViewBuilder>();
		
		this.polygonBuilder = new ArrayList<IPolygonViewBuilder>();
		this.rectangleBuilder = new ArrayList<IRectangleViewBuilder>();
		this.ovalBuilder = new ArrayList<IOvalViewBuilder>();
		this.lineBuilder = new ArrayList<ILineViewBuilder>();
		this.stringBuilder = new ArrayList<IStringViewBuilder>();
			
		try {
			path = new File(".").getCanonicalPath() + "./res/";
			
			/*A mapLoader kopmponenst használva betöltjük az összes statikus pályaelemet a memóriába.*/
			file = new File(path + "bitMap.png");
			BufferedImage bitmap = ImageIO.read(file);
			file = new File(path + "tileMap.png");
			BufferedImage tileBitMap = ImageIO.read(file);
			this.mapLoader = new MapLoader();
			this.mapLoader.loadMap(bitmap,tileBitMap, tiles,nonBlockingTile,notDestroyableView);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		staticviewBuilder.add(new SkillBarViewBuilder(converter.getMonitorScreenManager(), this));
		staticviewBuilder.add(new HealthBarViewBuilder(converter.getMonitorScreenManager(), this));
		
		this.cdTimes = new ArrayList<IStringViewBuilder>();
		
		//stringBuilder.add(new SimpleText(500, 500,0, 0,0, "mukodj", Color.red));
		//rectangleBuilder.add(new SimpleRectangle(600, 600, 45,600,600, 50, 50, Color.green, false));
		//lineBuilder.add(new SimpleLine(0, 0, 0,0,0, 1000,1000, Color.blue));
		//polygonBuilder.add(new SimplePolygon(800, 800,0,0,0,Color.pink, true, new Polygon()));
		//ovalBuilder.add(new SimpleOval(800,800, 0,0,0,1000,1000, Color.pink, true));
		
	}

	@Override
	public void viewPrepare(double lastTickTime, double nextTickTime) {
		/*Ezt a metódust hívja tovább a GameState, amikor renderelni szeretne.*/
		
		
		/*Elsõ lépésben meghívom a GarbageCollectort, hogy minden olyan grafikai elemet töröljön,
		 ami már meg kell, hogy szûnjön.Itt a Collectornak csak azokat a listákat adom át,
		 ahol grafikus elemleírók vannak, azokat megszûri, és csak úgy foglya a metódus tovább adni
		 egy másik komponensnek.*/
		this.garbageCollector.cleanViewBuilders(rectangleBuilder,viewBuilder,ovalBuilder,stringBuilder,lineBuilder,polygonBuilder);
		
		converter.startOfPieces();	
		
		
		/*Elõször a kamerát állíttatjuk be vele, megadva, hogy melyik pont legyen a képernyõ középpontjában.*/
		converter.moveCamera((int)playerRectangle.getX() + playerRectangle.getWidth()/2, (int)playerRectangle.getY() + playerRectangle.getHeight()/2);
		//converter.moveCamera(3000, 3000);
		/*Majd a kép,vonal,poly leírókat adjuk át, és ez szépen összeragaszt belõle egy kirajzolható képet.
		 A képleíróknak két lista is van, a viewBuilder, és a staticviewBuilder.Ezekre azért van szükség, hogy
		 ki tudjuk egymásutáni sorrendet rakni.Például a staticviewBuilderben vannak azok az elemek, 
		 amelyek mindig a képernyõn vannak, gondolok itt a healtbarra és stb, tehát ezeknek mindenek felett
		 kell kirajzolódnia, azaz mindenek után.*/
		
		converter.stickImages(notDestroyableView);
		
		converter.stickImages(viewBuilder);
		converter.stickImages(skillAnimationViewBuilders);
		
		
		converter.stickLines(lineBuilder);
		converter.stickOvals(ovalBuilder);
		converter.stickPolygones(polygonBuilder);
		converter.stickRectangles(rectangleBuilder);
		converter.stickStrings(stringBuilder);
		
		
		
		converter.stickImages(staticviewBuilder);
		
		converter.stickStrings(cdTimes);
		
		/*Itt jelezzük a ScreenManager komponens felé, hogy ebben a kirajzolási ciklusban nincs több kép,
		 sem alakzat, mostmár teljes a képernyõ.*/
		converter.endOfPieces();
	}
	
	@Override
	public CursorInformationProvider getCursorInformationProvider() {
		return this.cursor;
	}
	
	@Override
	public void setCursor(CursorInformationProvider cursor) {
		this.cursor = cursor;
		staticviewBuilder.add(new MouseViewBuilder(this.cursor));
	}

	@Override
	public List<IOvalViewBuilder> getOvalBuilder() {
		return ovalBuilder;
	}
	
	@Override
	public void setOvalBuilder(List<IOvalViewBuilder> ovalBuilder) {
		this.ovalBuilder = ovalBuilder;
	}
	
	@Override
	public List<IStringViewBuilder> getStringBuilder() {
		return stringBuilder;
	}
	
	@Override
	public void setStringBuilder(List<IStringViewBuilder> stringBuilder) {
		this.stringBuilder = stringBuilder;
	}
	
	@Override
	public List<IRectangleViewBuilder> getRectangleBuilder() {
		return rectangleBuilder;
	}
	
	@Override
	public void setRectangleBuilder(List<IRectangleViewBuilder> rectangleBuilder) {
		this.rectangleBuilder = rectangleBuilder;
	}
	
	@Override
	public List<IPolygonViewBuilder> getPolygonBuilder() {
		return polygonBuilder;
	}
	
	@Override
	public void setPolygonBuilder(List<IPolygonViewBuilder> polygonBuilder) {
		this.polygonBuilder = polygonBuilder;
	}
	
	@Override
	public List<ILineViewBuilder> getLineBuilder() {
		return lineBuilder;
	}
	
	@Override
	public void setLineBuilder(List<ILineViewBuilder> lineBuilder) {
		this.lineBuilder = lineBuilder;
	}
	
	@Override
	public List<IImageViewBuilder> getNotDestroyableView() {
		return notDestroyableView;
	}
	
	@Override
	public void setNotDestroyableView(List<IImageViewBuilder> notDestroyableView) {
		this.notDestroyableView = notDestroyableView;
	}
	
	@Override
	public void setViewBuilder(List<IImageViewBuilder> viewBuilder) {
		this.viewBuilder = viewBuilder;
	}
	
	@Override
	public void setStaticviewBuilder(List<IImageViewBuilder> staticviewBuilder) {
		this.staticviewBuilder = staticviewBuilder;
	}
	
	@Override
	public IMonitorScreenManager getMonitorScreenManager(){
		return converter.getMonitorScreenManager();
	}

	@Override
	public List<IImageViewBuilder> getStaticviewBuilder() {
		return this.staticviewBuilder;
	}

	@Override
	public List<IImageViewBuilder> getViewBuilder() {
		return this.viewBuilder;
	}

	@Override
	public void setPlayer(Entity player) {
		this.player = player;
		for(int i=0;i<7;i++){
			cdTimes.add(new ImageIconTimerPicture(i, converter.getMonitorScreenManager(), player));
		}
	}

	@Override
	public Entity getPlayer() {
		return this.player;
	}

	@Override
	public void setSkillAnimationViewBuilders(List<IImageViewBuilder> skillanimations) {
		this.skillAnimationViewBuilders = skillanimations;
	}

	@Override
	public List<IImageViewBuilder> getSkillAnimationViewBuilders() {
		return this.skillAnimationViewBuilders;
	}
}