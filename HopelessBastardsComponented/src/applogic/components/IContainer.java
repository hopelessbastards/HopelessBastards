package applogic.components;

import java.util.ArrayList;
import java.util.List;

import applogic.CursorInformationProvider;
import applogic.GarbageCollector;
import applogic.IGarbageCollector;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.ILineViewBuilder;
import applogic.viewbuilder.IOvalViewBuilder;
import applogic.viewbuilder.IPolygonViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;
import applogic.viewbuilder.IStringViewBuilder;
import screenconverter.IConverter;

public abstract class IContainer {
	/*Igazából ezt az osztályt egy üresen hagyott osztály implementálja, tehát ez 
	 nem is lenne muszály abstractra csinálni.*/
	
	/*Minden GameStatenak van egy Containere, amibe az általam készített komponensek
	 példányait tárolja, ez olyan mint az awt-s Frame containerre, amihez add()-el hozzá
	 lehet adni komponenseket.*/
	
	/*Ebbe tároljuk a komponenseket, és ezen iterálva hívogatjuk azok tick metódusait.*/
	private List<IComponent> components;
	
	/*Ezekben pedig a komponensekhez tartozó grafikus elemleírokat tárolja.A komponensek 
	 ezekhez rakják hozzá sajátjukat.*/
	private List<IImageViewBuilder> images;
	private List<IStringViewBuilder> labels;
	private List<IRectangleViewBuilder> rectangles;
	private List<IOvalViewBuilder> ovals;
	private List<IPolygonViewBuilder> polygons;
	private List<ILineViewBuilder> lines;
	
	private IGarbageCollector garbageCollector;
	
	private CursorInformationProvider cursor;
	
	private IFocus focus;
	
	/*A container pointert tartalmaz a modalis elemre ha van.Ha az OptionPane showolodik,
	 akkor beállítja magát modálisra itt, és minden componens megvizsgálja, hogy ennek a 
	 változónak van-e értéke, mertha igen, akkor nem reagálnak felhasználói beavatkozásokra.*/
	private IComponent modalComponent;
	
	public IContainer(CursorInformationProvider cursor) {
		this.components = new ArrayList<IComponent>();
		this.images = new ArrayList<IImageViewBuilder>();
		this.labels = new ArrayList<IStringViewBuilder>();
		this.ovals = new ArrayList<IOvalViewBuilder>();
		this.rectangles = new ArrayList<IRectangleViewBuilder>();
		this.polygons = new ArrayList<IPolygonViewBuilder>();
		this.lines = new ArrayList<ILineViewBuilder>();
		
		this.garbageCollector = new GarbageCollector();
		
		this.cursor = cursor;
		
		this.focus = new Focus();
	}
	
	public void stickComponents(IConverter converter){
		garbageCollector.cleanViewBuilders(images,rectangles,labels,ovals,polygons,lines);
		garbageCollector.cleanGuiComponents(this.components);
	
		converter.stickRectangles(rectangles);
		converter.stickImages(images);
		converter.stickLines(lines);
		converter.stickOvals(ovals);
		converter.stickPolygones(polygons);
	
		converter.stickStrings(labels);
	}
	
	public void tick(double apTime){
		for(int i=0;i<components.size();i++){
			components.get(i).tick();
		}
	}
	
	public IFocus getFocus() {
		return focus;
	}

	public void setFocus(IFocus focus) {
		this.focus = focus;
	}
	
	public IComponent getModalComponent() {
		return modalComponent;
	}

	public void setModalComponent(IComponent modalComponent) {
		this.modalComponent = modalComponent;
	}

	public void addComponent(IComponent component){
		this.components.add(component);
	}
	
	public void removeComponent(IComponent component){
		this.components.remove(component);
	}
	
	public void tickComponents(){
		for(int i=0;i<components.size();i++){
			components.get(i).tick();
		}
	}

	public List<IComponent> getComponents() {
		return components;
	}

	public void setComponents(List<IComponent> components) {
		this.components = components;
	}
	
	public CursorInformationProvider getCursor() {
		return cursor;
	}

	public void setCursor(CursorInformationProvider cursor) {
		this.cursor = cursor;
	}

	public List<IImageViewBuilder> getImages() {
		return images;
	}

	public void setImages(List<IImageViewBuilder> images) {
		this.images = images;
	}

	public List<IStringViewBuilder> getLabels() {
		return labels;
	}

	public void setLabels(List<IStringViewBuilder> labels) {
		this.labels = labels;
	}

	public List<IRectangleViewBuilder> getRectangles() {
		return rectangles;
	}

	public void setRectangles(List<IRectangleViewBuilder> rectangles) {
		this.rectangles = rectangles;
	}

	public List<IOvalViewBuilder> getOvals() {
		return ovals;
	}

	public void setOvals(List<IOvalViewBuilder> ovals) {
		this.ovals = ovals;
	}

	public List<IPolygonViewBuilder> getPolygons() {
		return polygons;
	}

	public void setPolygons(List<IPolygonViewBuilder> polygons) {
		this.polygons = polygons;
	}

	public List<ILineViewBuilder> getLines() {
		return lines;
	}

	public void setLines(List<ILineViewBuilder> lines) {
		this.lines = lines;
	}
}