package applogic;

public class GameLoop implements Runnable,IGameLoop{
	/*A GameLoop megvalósítása, ez külön szélon fog futni.*/
	
	private Thread thread;
	private boolean running = false;/*Szál indításnál van szerepe.*/
	
	private double delta;
	private boolean runFlag;
	private int sleepTime;
	private double currTime;
	private double nextTime;
	private double maxTimeDiff;
	private int skippedFrames;
	private int maxSkippedFrames;
	
	
	private double startingAppTime;
	/*Van referenciája egy GameStateContainerre, aminek továbbhivja a tick és render metódusát,
	 így a gameloop, az nem tud semmit az aktuális GameStateról, tehát nem is ismeri.Közvetve,
	 a GameStateContaineren keresztül hívogatja.*/
	private AbstractGameStateContainer gameStateContainer;
	
	public GameLoop(AbstractGameStateContainer gameStateContainer) {
		this.gameStateContainer = gameStateContainer;
		start();/*Elindítjuk a szálat.*/
	}
	
	private void gameLoopInitialization(){
		 runFlag = true;
	     delta = 0.016;
	     nextTime = (double)System.nanoTime() / 1000000000.0;
	     maxTimeDiff = 0.5;
	     skippedFrames = 1;
	     maxSkippedFrames = 5;
	}
	
	private synchronized void start(){
		/*Szál indítása*/
		if(running){
			return;
		}
		running = true;
		thread = new Thread(this,"Thread");
		thread.start();
	}
	@Override
	public void tick(double appTime){
		gameStateContainer.tick(appTime);
	}
	@Override
	public void render(){
		gameStateContainer.render();
	}
	
	private synchronized void stop(){
		if(!running){
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		gameLoopInitialization();
	    
		startingAppTime = (double)System.nanoTime() / 1000000000.0; 
        while(runFlag){
            currTime = (double)System.nanoTime() / 1000000000.0;
            if((currTime - nextTime) > maxTimeDiff){
            	nextTime = currTime;
            }
            if(currTime >= nextTime){
                // assign the time for the next update
                nextTime += delta;
                tick(currTime - startingAppTime);
               // tick++;
                if((currTime < nextTime) || (skippedFrames > maxSkippedFrames)){
                	
                    render();
                   // render++;
                    skippedFrames = 1;
                }else{
                    skippedFrames++;
                }
            }else{
                sleepTime = (int)(1000.0 * (nextTime - currTime));
                // ébresztés ellenõrzés
                if(sleepTime > 0)
                {
                    // sleep until the next update(következõ tick hívásik altatni a szálat)
                    try
                    {
                        Thread.sleep(sleepTime);
                    }
                    catch(InterruptedException e)
                    {//itt ne csináljon semmit
                    }
                }
            }
        }
        stop();
	}
}