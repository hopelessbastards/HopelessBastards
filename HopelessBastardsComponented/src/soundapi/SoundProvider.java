package soundapi;

import java.util.Map;

import xmlhandlers.ISoundLoader;
import xmlhandlers.SaxSoundLoader;

public class SoundProvider implements ISoundProvider{

	private Map<String,String> sounds;
	private ISoundLoader soundLoader;
	
	public SoundProvider() {
		/*Bet�lti Map-be a logikai-el�r�si �tvonal p�rokat.*/
		soundLoader = new SaxSoundLoader();
		sounds = soundLoader.Parse("./sounds.xml");
	}
	
	@Override
	public Map<String, String> getSoundMap() {
		return this.sounds;
	}	
}