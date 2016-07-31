package applogic;

import java.util.List;
import applogic.elements.Entity;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.ILineViewBuilder;
import applogic.viewbuilder.IOvalViewBuilder;
import applogic.viewbuilder.IPolygonViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;
import applogic.viewbuilder.IStringViewBuilder;
import screenconverter.IMonitorScreenManager;

public interface IViewBuilderContainer {
	public void viewPrepare(double renderTime);
	public List<IImageViewBuilder> getStaticviewBuilder();
	public List<IImageViewBuilder> getViewBuilder();
	public IMonitorScreenManager getMonitorScreenManager();
	public void setPlayer(Entity player);
	public Entity getPlayer();
	
	public CursorInformationProvider getCursorInformationProvider();
	
	public void setCursor(CursorInformationProvider cursor);
	
	public List<IOvalViewBuilder> getOvalBuilder();

	public void setOvalBuilder(List<IOvalViewBuilder> ovalBuilder);

	public List<IStringViewBuilder> getStringBuilder();

	public void setStringBuilder(List<IStringViewBuilder> stringBuilder);

	public List<IRectangleViewBuilder> getRectangleBuilder();

	public void setRectangleBuilder(List<IRectangleViewBuilder> rectangleBuilder);

	public List<IPolygonViewBuilder> getPolygonBuilder();

	public void setPolygonBuilder(List<IPolygonViewBuilder> polygonBuilder);

	public List<ILineViewBuilder> getLineBuilder();

	public void setLineBuilder(List<ILineViewBuilder> lineBuilder);

	public List<IImageViewBuilder> getNotDestroyableView();

	public void setNotDestroyableView(List<IImageViewBuilder> notDestroyableView);

	public void setViewBuilder(List<IImageViewBuilder> viewBuilder);

	public void setStaticviewBuilder(List<IImageViewBuilder> staticviewBuilder);
	
	public void setSkillAnimationViewBuilders(List<IImageViewBuilder> skillanimations);
	public List<IImageViewBuilder> getSkillAnimationViewBuilders();
}