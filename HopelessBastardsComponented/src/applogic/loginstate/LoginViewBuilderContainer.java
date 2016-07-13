package applogic.loginstate;

import java.util.ArrayList;
import java.util.List;
import applogic.CursorInformationProvider;
import applogic.IViewBuilderContainer;
import applogic.components.IContainer;
import applogic.elements.Entity;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.ILineViewBuilder;
import applogic.viewbuilder.IOvalViewBuilder;
import applogic.viewbuilder.IPolygonViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;
import applogic.viewbuilder.IStringViewBuilder;
import applogic.viewbuilder.MouseViewBuilder;
import screenconverter.IConverter;
import screenconverter.IMonitorScreenManager;

public class LoginViewBuilderContainer implements IViewBuilderContainer{
	/*Minden GameStatehoz külön IViewBuilderContainert implementáltam.A Loginnak ez
	 az osztály a sajátja.Erre azért van szükség, mert a kamerakezelés meg a többi eltérhet.
	 */
	
	private List<IOvalViewBuilder> ovalBuilder;
	private List<IStringViewBuilder> stringBuilder;
	private List<IRectangleViewBuilder> rectangleBuilder;
	private List<IPolygonViewBuilder> polygonBuilder;
	private List<ILineViewBuilder> lineBuilder;
	
	private List<IImageViewBuilder> viewBuilder;/*Ebbe van lényegében minden kép, kivéve párat.*/
	private List<IImageViewBuilder> staticViewBuilder;
	
	private BackgroundImageViewBuilder backgroundImage;
	private IConverter converter;
	
	private CursorInformationProvider cursor;
	
	private IContainer componentContainer;
	
	public LoginViewBuilderContainer(IConverter converter,CursorInformationProvider cursor,IContainer container) {
		this.converter = converter;
		this.componentContainer = container;
		this.cursor = cursor;
		this.viewBuilder = new ArrayList<IImageViewBuilder>();
		this.staticViewBuilder = new ArrayList<IImageViewBuilder>();
		
		this.backgroundImage = new BackgroundImageViewBuilder(converter);
		viewBuilder.add(this.backgroundImage);
		staticViewBuilder.add(new LoginDragonViewBuilder());
		staticViewBuilder.add(new MouseViewBuilder(cursor));
	}
	
	@Override
	public void viewPrepare() {
		/*Elõször a kamerát állíttatjuk be vele, megadva, hogy melyik pont legyen a képernyõ középpontjában.*/
		
		converter.startOfPieces();
		converter.moveCameraToDefault();
		converter.stickImages(viewBuilder);
		
		componentContainer.stickComponents(converter);
		converter.stickImages(staticViewBuilder);
		/*converter.stickLines(lineBuilder);
		converter.stickOvals(ovalBuilder);
		converter.stickPolygones(polygonBuilder);
		converter.stickRectangles(rectangleBuilder);
		converter.stickStrings(stringBuilder);*/
		
		converter.endOfPieces();
	}

	@Override
	public List<IImageViewBuilder> getStaticviewBuilder() {
		return this.staticViewBuilder;
	}

	@Override
	public List<IImageViewBuilder> getViewBuilder() {
		return this.viewBuilder;
	}

	@Override
	public IMonitorScreenManager getMonitorScreenManager() {
		return null;
	}

	@Override
	public void setPlayer(Entity player) {
	}

	@Override
	public Entity getPlayer() {
		return null;
	}

	@Override
	public CursorInformationProvider getCursorInformationProvider() {
		return this.cursor;
	}

	@Override
	public void setCursor(CursorInformationProvider cursor) {
		this.cursor = cursor;
	}

	@Override
	public List<IOvalViewBuilder> getOvalBuilder() {
		return this.ovalBuilder;
	}

	@Override
	public void setOvalBuilder(List<IOvalViewBuilder> ovalBuilder) {
		this.ovalBuilder = ovalBuilder;
	}

	@Override
	public List<IStringViewBuilder> getStringBuilder() {
		return this.stringBuilder;
	}

	@Override
	public void setStringBuilder(List<IStringViewBuilder> stringBuilder) {
		this.stringBuilder = stringBuilder;
	}

	@Override
	public List<IRectangleViewBuilder> getRectangleBuilder() {
		return this.rectangleBuilder;
	}

	@Override
	public void setRectangleBuilder(List<IRectangleViewBuilder> rectangleBuilder) {
		this.rectangleBuilder = rectangleBuilder;
	}

	@Override
	public List<IPolygonViewBuilder> getPolygonBuilder() {
		return this.polygonBuilder;
	}

	@Override
	public void setPolygonBuilder(List<IPolygonViewBuilder> polygonBuilder) {
		this.polygonBuilder = polygonBuilder;
	}

	@Override
	public List<ILineViewBuilder> getLineBuilder() {
		return this.lineBuilder;
	}

	@Override
	public void setLineBuilder(List<ILineViewBuilder> lineBuilder) {
		this.lineBuilder = lineBuilder;
	}

	@Override
	public List<IImageViewBuilder> getNotDestroyableView() {
		return null;
	}

	@Override
	public void setNotDestroyableView(List<IImageViewBuilder> notDestroyableView) {
	}

	@Override
	public void setViewBuilder(List<IImageViewBuilder> viewBuilder) {
		this.viewBuilder = viewBuilder;
	}

	@Override
	public void setStaticviewBuilder(List<IImageViewBuilder> staticviewBuilder) {
		this.staticViewBuilder = staticviewBuilder;
	}

	@Override
	public void setSkillAnimationViewBuilders(List<IImageViewBuilder> skillanimations) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IImageViewBuilder> getSkillAnimationViewBuilders() {
		// TODO Auto-generated method stub
		return null;
	}
}