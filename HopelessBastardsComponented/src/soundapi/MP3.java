package soundapi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javazoom.jl.player.Player;

public class MP3 implements ISound{
	/*A sound MP3 megvalósítása, ez a wavnál azért jobb mert sokkal kevesebb helyet foglal.*/
	
	/*Az osztályok nem ismerik a zene file elérési útvonalát, õk csak egy logikai nevet
	 ismernek, melyhez tartozó elérési útvonalat egy Mapbõl tudják kiszedni, amit a soundProvidertõl
	 tudnak elkérni.*/
	private String logicFilename;
    private Player player; 
    private ISoundProvider soundProvider;
    private String path;
    
    public MP3(String logicFilename,ISoundProvider soundProvider) {
        this.logicFilename = logicFilename;
        this.soundProvider = soundProvider;
        
        try {
			this.path = new File(".").getCanonicalPath() + "/res/";
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    public void stop() { 
    	if (player != null)
    		player.close();
    }

    // play the MP3 file to the sound card
    @Override
    public void play() {
        try {
        	FileInputStream fis = new FileInputStream(path + soundProvider.getSoundMap().get(this.logicFilename));
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + this.logicFilename);
            System.out.println(e);
        }
        
        // run in new thread to play in background
       new Thread() {
            public void run() {
                try { player.play(); }
                catch (Exception e) { System.out.println(e); }
            }
        }.start();
    }

	@Override
	public void close() {
	}

	@Override
	public void setLogicName(String logicName) {
		this.logicFilename = logicName;
	}
}