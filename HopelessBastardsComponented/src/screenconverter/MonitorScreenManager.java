package screenconverter;

import java.awt.Point;
import graphicsEngine.IRenderer;

public class MonitorScreenManager implements IMonitorScreenManager{

	/*a player 7 skillképének helyét meghatározó rectangle*/
	private Point[] skills;
	
	private Point skillBarLocation;
	private Point healthBarLocation;
	
	private Point healtLineLocation;
	private Point manaLineLocation;
	
	private IRenderer renderer;
	
	private Size canvasSize;
	
	private int skillbarSize;
	
	private ICanvasSizeRefresher canvasSizeRefresher;
	
	private int[] xMovedIntoSkillBar={267,326,385,445,506,565,626};
	
	public MonitorScreenManager(IRenderer renderer,int skillbarSize,ICanvasSizeRefresher canvasSizeRefresher) {
		this.renderer = renderer;
		this.skills = new Point[skillbarSize];
		this.skillbarSize = skillbarSize;
		
		this.canvasSizeRefresher = canvasSizeRefresher;
		
		
		/*Itt beállítom a skillképek helyzetét, a rectangleben, a rectangle objektum fogja képviselni a helyüket.
		 Ebbe az a jó,hogy minden képernyõn ugyan oda teszi.*/
		this.canvasSize = this.renderer.getCanvasSize();
		
		for(int i=0;i<this.skillbarSize;i++){
			skills[i] = new Point();
		}
		
		this.healthBarLocation = new Point();
		this.skillBarLocation = new Point();
		
		this.manaLineLocation = new Point();
		this.healtLineLocation = new Point();
		
		
	}
	
	/*Az alábbi metódusoknak az a lényege, hogy a skillképeknek, skillbarnak és healtbarnak
	 amik mindig a képernyõn mozognak a playerrel visszaadja az elhelyeszkedésüket a kameramozgás
	 függvényében.*/
	
	@Override
	public Point getSkillImageLocation(int skillnumber,int centerPointOfMapX,int centerPointOfMapY) {
		this.canvasSizeRefresher.refresh(this.canvasSize);
		
		skills[skillnumber].setLocation(centerPointOfMapX - this.canvasSize.getWIDTH()/2 + (this.canvasSize.getWIDTH()-1140)/2 + xMovedIntoSkillBar[skillnumber], centerPointOfMapY - this.canvasSize.getHEIGHT()/2 + this.canvasSize.getHEIGHT() - 160 + 82);
		return this.skills[skillnumber];
	}

	@Override
	public Point getHealthBarLocation(int centerPointOfMapX,int centerPointOfMapY) {
		this.canvasSizeRefresher.refresh(this.canvasSize);
		
		this.healthBarLocation.setLocation(centerPointOfMapX - this.canvasSize.getWIDTH()/2,(int) centerPointOfMapY - this.canvasSize.getHEIGHT()/2);
		return this.healthBarLocation;
	}

	@Override
	public Point getSkillBarLocation(int centerPointOfMapX,int centerPointOfMapY) {
		this.canvasSizeRefresher.refresh(this.canvasSize);
		
		this.skillBarLocation.setLocation(centerPointOfMapX - this.canvasSize.getWIDTH()/2 + (this.canvasSize.getWIDTH()-1140)/2, centerPointOfMapY - this.canvasSize.getHEIGHT()/2 + this.canvasSize.getHEIGHT() - 160);
		return this.skillBarLocation;
	}
	
	@Override
	public Point getHealtbarHealtLineLocation(int centerPointOfMapX, int centerPointOfMapY) {
		this.canvasSizeRefresher.refresh(this.canvasSize);
		
		this.healtLineLocation.setLocation(centerPointOfMapX - this.canvasSize.getWIDTH()/2 + 120,centerPointOfMapY - this.canvasSize.getHEIGHT()/2 + 43);
		return this.healtLineLocation;
	}

	@Override
	public Point getHealtbarManaLineLocation(int centerPointOfMapX, int centerPointOfMapY) {
		this.canvasSizeRefresher.refresh(this.canvasSize);
		
		this.manaLineLocation.setLocation(centerPointOfMapX - this.canvasSize.getWIDTH()/2 + 101,centerPointOfMapY - this.canvasSize.getHEIGHT()/2 + 88);
		return this.manaLineLocation;
	}
}