package xmlhandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxSoundLoader implements ISoundLoader{

	private Map<String,String> soundMap;
	
	@Override
	public Map<String, String> Parse(String documentPath) {
		
		try{
			SAXParserFactory fact = SAXParserFactory.newInstance();
			SAXParser pars = fact.newSAXParser();
			DefaultHandler dh = new DefaultHandler() {/* contenthandler */
				private int inside;
				private List<String> elements = new ArrayList<String>();
				private Map<String,String> sounds = new HashMap<String, String>();

				
				@Override
				public void startDocument() throws SAXException {
					super.startDocument();
					inside = 0;/* nem vagyunk bent */
				}

				public void startElement(String url, String localname,
						String qname, Attributes att) {
					if (qname.equals("path")) {
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
					if(qName.equals("sound")){
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
							sounds.put(elements.get(i - 1),elements.get(i - 2));		
						}
					}
					
					soundMap = sounds;
					super.endDocument();
				}
			};
			pars.parse(documentPath, dh);
		}catch(Exception e){
			
		}
		return soundMap;
	}
}