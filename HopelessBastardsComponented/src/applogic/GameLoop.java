package applogic;

public class GameLoop implements Runnable,IGameLoop{
	/*A GameLoop megval�s�t�sa, ez k�l�n sz�lon fog futni.*/
	
	private Thread thread;
	private boolean running = false;/*Sz�l ind�t�sn�l van szerepe.*/
	
	private double delta;
	private boolean runFlag;
	private int sleepTime;
	private double currTime;
	private double nextTime;
	private double maxTimeDiff;
	private int skippedFrames;
	private int maxSkippedFrames;
	
	
	private double startingAppTime;
	/*Van referenci�ja egy GameStateContainerre, aminek tov�bbhivja a tick �s render met�dus�t,
	 �gy a gameloop, az nem tud semmit az aktu�lis GameStater�l, teh�t nem is ismeri.K�zvetve,
	 a GameStateContaineren kereszt�l h�vogatja.*/
	private AbstractGameStateContainer gameStateContainer;
	
	public GameLoop(AbstractGameStateContainer gameStateContainer) {
		this.gameStateContainer = gameStateContainer;
		start();/*Elind�tjuk a sz�lat.*/
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
		/*Sz�l ind�t�sa*/
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
                // �breszt�s ellen�rz�s
                if(sleepTime > 0)
                {
                    // sleep until the next update(k�vetkez� tick h�v�sik altatni a sz�lat)
                    try
                    {
                        Thread.sleep(sleepTime);
                    }
                    catch(InterruptedException e)
                    {//itt ne csin�ljon semmit
                    }
                }
            }
        }
        stop();
	}
}