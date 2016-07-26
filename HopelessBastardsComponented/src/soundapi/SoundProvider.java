package soundapi;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import xmlhandlers.ISoundLoader;
import xmlhandlers.SaxSoundLoader;

public class SoundProvider implements ISoundProvider{

	private Map<String,String> sounds;
	private ISoundLoader soundLoader;
	
	public SoundProvider() {
		/*Betölti Map-be a logikai-elérési útvonal párokat.*/
		soundLoader = new SaxSoundLoader();
		String path;
		try {
			path = new File(".").getCanonicalPath();
			sounds = soundLoader.Parse(path + "/sounds.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	@Override
	public Map<String, String> getSoundMap() {
		return this.sounds;
	}	
}