package screenconverter.descriptors;

import java.awt.Color;

public class StringDescriptor extends DrawObjectDescriptor{
	private String string;
	private int size;
	private String fontName;

	public StringDescriptor(int x, int y,int size, double angle, int angleCenterPointX, int angleCenterPointY,
			String string,Color color,String fontName) {
		super(x, y, angle, angleCenterPointX, angleCenterPointY,color,false);
		this.string = string;
		this.size = size;
		this.fontName = fontName;
	}
	
	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}	
}