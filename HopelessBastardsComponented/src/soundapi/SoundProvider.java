package soundapi;

import java.util.Map;

import xmlhandlers.ISoundLoader;
import xmlhandlers.SaxSoundLoader;

public class SoundProvider implements ISoundProvider{

	private Map<String,String> sounds;
	private ISoundLoader soundLoader;
	
	public SoundProvider() {
		/*Betölti Map-be a logikai-elérési útvonal párokat.*/
		soundLoader = new SaxSoundLoader();
		sounds = soundLoader.Parse("./sounds.xml");
	}
	
	@Override
	public Map<String, String> getSoundMap() {
		return this.sounds;
	}	
}