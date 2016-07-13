package screenconverter.descriptors;

public class ImageDescriptor extends DrawObjectDescriptor{

	private int width;
	private int height;
	private String imageLogicalName;/*A kép logikai neve*/
	private int animation;/*A kép animációban betöltött sorrendi szerepe.*/
	private double widthscale;
	private double heightscale;
	
	public ImageDescriptor(int x, int y, double angle, int angleCenterPointX, int angleCenterPointY,
			int width, int height, String imageLogicalName, int animation) {
		super(x, y, angle, angleCenterPointX, angleCenterPointY,null,false);
		this.width = width;
		this.height = height;
		this.imageLogicalName = imageLogicalName;
		this.animation = animation;
		this.widthscale = 1;
		this.heightscale = 1;
	}
	
	
	
	public double getWidthscale() {
		return widthscale;
	}



	public void setWidthscale(double widthscale) {
		this.widthscale = widthscale;
	}



	public double getHeightscale() {
		return heightscale;
	}



	public void setHeightscale(double heightscale) {
		this.heightscale = heightscale;
	}



	public int getAnimation() {
		return animation;
	}
	public void setAnimation(int animation) {
		this.animation = animation;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getImageLogicalName() {
		return imageLogicalName;
	}
	public void setImageLogicalName(String imageLogicalName) {
		this.imageLogicalName = imageLogicalName;
	}	
}