package xmlhandlers;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import graphicsEngine.ImageDescriptor;

public class SaxImageParse implements DescriptorLoader{
	/*Saját képleíróobjektum elõállító modulom.Az xml filet feldolgozva ImageDescriptor listát
	 állít elõ, és ad vissza a hívójának.Ezt az ImageLoader komponens hívja meg(használja).*/
	
	private List<ImageDescriptor> imgs = null;
	
	@Override
	public List<ImageDescriptor> Parse(String documentPath){
		try {
			SAXParserFactory fact = SAXParserFactory.newInstance();
			SAXParser pars = fact.newSAXParser();
			DefaultHandler dh = new DefaultHandler() {/* contenthandler */
				private int inside;
				private List<String> elements = new ArrayList<String>();
				private List<ImageDescriptor> img = new ArrayList<ImageDescriptor>();

				
				@Override
				public void startDocument() throws SAXException {
					super.startDocument();
					inside = 0;/* nem vagyunk bent */
				}

				public void startElement(String url, String localname,
						String qname, Attributes att) {
					if (qname.equals("sprite")) {
						inside = 1;
					}
				}

				@Override
				public void endElement(String uri, String localName,
						String qName) throws SAXException {
				
					super.endElement(uri, localName, qName);
					if (qName.equals("logicname")) {
						inside = 0;
					}
					if(qName.equals("image")){
						elements.add("separator");
					}
				}

				@Override
				public void characters(char[] ch, int start, int length)
						throws SAXException {
					super.characters(ch, start, length);
					StringBuilder sb = new StringBuilder();
					for (int i = start; i < start + length; i++) {
						sb.append(ch[i]);
					}
					Character s = sb.toString().charAt(0);
					if(!Character.isWhitespace(s)){
						elements.add(sb.toString());
					}
				}

				@Override
				public void endDocument() throws SAXException {
					
					for(int i=0;i<elements.size();i++){
						if(elements.get(i).equals("separator")){
							
							if(boolValue(elements.get(i-1))){
								img.add(new ImageDescriptor(elements.get(i-8), Integer.parseInt(elements.get(i-7)), 
										Integer.parseInt(elements.get(i-6)), 
										Integer.parseInt(elements.get(i-5)),
										Integer.parseInt(elements.get(i-4)),
										elements.get(i-3),Integer.parseInt(elements.get(i-2)),
										boolValue(elements.get(i-1))));
								
							}else{
								img.add(new ImageDescriptor(elements.get(i-4), 
										elements.get(i-3), Integer.parseInt(elements.get(i-2)),
										boolValue(elements.get(i-1))));
							}
							
						}
					}
					imgs = img;
					super.endDocument();
				}
				
				private boolean boolValue(String bool){
					if(bool.equals("true")){
						return true;
					}else{
						return false;
					}
				}

			};
			pars.parse(documentPath, dh);
			
		} catch (Exception e) {
			System.out.println("Gáz van!!!!");
		}
		return imgs;
	}
}
