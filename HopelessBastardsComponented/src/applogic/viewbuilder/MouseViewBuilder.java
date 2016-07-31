package applogic.viewbuilder;

import applogic.CursorInformationProvider;
import applogic.elements.BasicElement;
import screenconverter.descriptors.ImageDescriptor;

public class MouseViewBuilder extends IImageViewBuilder{

	private ImageDescriptor[] describers;
	private CursorInformationProvider cursor;
	
	public MouseViewBuilder(CursorInformationProvider cursor) {
		this.describers = new ImageDescriptor[1];
		this.describers[0] = new ImageDescriptor(0,0,0,0,0,40,40,"mouse",0);
		
		this.cursor = cursor;
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		if(cursor != null){
			describers[0].setX(cursor.getMouse().x);
			describers[0].setY(cursor.getMouse().y);
		}
		
		return this.describers;
	}

	@Override
	public BasicElement getTheRepresentetedElement() {
		return null;
	}
}