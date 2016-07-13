package bufferedImageImplementation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import graphicsEngine.Animation;
import graphicsEngine.ImageDescriptor;
import graphicsEngine.IimageLoader;
import xmlhandlers.DescriptorLoader;
import xmlhandlers.SaxImageParse;

public class ImageLoader implements IimageLoader{
	@Override
	public Map<String, Animation> imageLoad() {
		Map<String, Animation> images;
		images = new HashMap<String, Animation>();
		
		
		/*Itt létrehozom a képkonfigurációs file beolvasót, amit interfacen keresztül érek el.*/
		DescriptorLoader docParser = new SaxImageParse();
		
		
		/*Ebbe a képleíró objektum listába olvas bele xml-bõl.Az interfacet megvalósíthatná 
		 json, stb megoldásokkal is.*/
		List<ImageDescriptor> descriptors = docParser.Parse("./images.xml");
		
		
		for(int i=0;i<descriptors.size();i++){
			if(!images.containsKey(descriptors.get(i).getLogicName())){
				images.put(descriptors.get(i).getLogicName(), new Animation());
			}
		}
		
		ImageDescriptor desc;
		Animation animation;
		for(int i=0;i<descriptors.size();i++){
			desc = descriptors.get(i);
			if(desc.isSprite()){
			
				Sprite sprite = new Sprite(new SpriteSheet(desc.getPath()),desc.getColumn() ,desc.getRow(), desc.getSheetwidth(), desc.getSheetheight());

				animation = images.get(desc.getLogicName());
				animation.addAnimationSliceByIndex(desc.getAnimation(), sprite.getBufferedImage());
			}else{
				try {
					animation = images.get(desc.getLogicName());
					animation.addAnimationSliceByIndex(desc.getAnimation(),ImageIO.read(getClass().getResource("/res/" + desc.getPath())));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
		return images;
	}
}