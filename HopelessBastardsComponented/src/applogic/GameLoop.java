package applogic;

public class GameLoop implements Runnable,IGameLoop{
	/*A GameLoop megvalósítása, ez külön szélon fog futni.*/
	
	private Thread thread;
	private boolean running = false;/*Szál indításnál van szerepe.*/
	
	private double delta;
	private boolean runFlag;
	
	private double currTime;
	
	
	private double lastTickTime;
	
	private double lastRenderTime;
	private double nextRendererTime;
	private double renderTime;
	
	
	public static double lastTick;
	public static double lastlastTick;
	public static double nextTick;
	public static double currentTick;
	
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
	     delta = 1;
	     /*nextTime = (double)System.nanoTime() / 1000000000.0;
	     maxTimeDiff = 0.5;
	     skippedFrames = 1;
	     maxSkippedFrames = 5;*/
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
	public void render(double renderTime){
		gameStateContainer.render(renderTime);
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
	    int nticks = 0;
		startingAppTime = (double)System.nanoTime() / 1000000000.0; 
		
		
       /* while(runFlag){
            currTime = (double)System.nanoTime() / 1000000000.0;
            if((currTime - nextTime) > maxTimeDiff){
            	nextTime = currTime;
            }
            if(currTime >= nextTime){
                
                nextTime += delta;
                tick(currTime - startingAppTime);
                nticks++;
                double tickTime = (currTime - startingAppTime)/ nticks;
               
                if((currTime < nextTime) || (skippedFrames > maxSkippedFrames)){
                    render();
                   
                    skippedFrames = 1;
                }else{
                    skippedFrames++;
                }
            }else{
                sleepTime = (int)(1000.0 * (nextTime - currTime));
                if(sleepTime > 0)
                {
                    try
                    {
                        Thread.sleep(sleepTime);
                    }
                    catch(InterruptedException e)
                    {
                    }
                }
            }
        }*/
		
		  while(runFlag){
	    	   currTime = (double)System.nanoTime() / 1000000000.0;
	    	   
	            if(lastTickTime + delta <= currTime){

	                tick(currTime - startingAppTime);
	                
	                currTime = (double)System.nanoTime() / 1000000000.0; 
	                lastTickTime = currTime;
	                
                	
	                while(currTime < lastTickTime + delta){
	                	currTime = (double)System.nanoTime() / 1000000000.0;
	                	
	                	lastRenderTime = (double)System.nanoTime() / 1000000000.0;
	                	render(renderTime);
	                	nextRendererTime = (double)System.nanoTime() / 1000000000.0;
	               
	                	//System.out.println(dd);
	                	renderTime = nextRendererTime - lastRenderTime;
	                	//System.out.println(nextRendererTime - lastRenderTime);
	                	
	                }
	            }
	       }
        stop();
	}
}