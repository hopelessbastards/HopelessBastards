package applogic.skills.imageicons;

import java.awt.Color;
import java.awt.Point;
import applogic.elements.Entity;
import applogic.viewbuilder.IStringViewBuilder;
import screenconverter.IMonitorScreenManager;
import screenconverter.descriptors.StringDescriptor;

public class ImageIconTimerPicture extends IStringViewBuilder{

	/*Hanyadik skillre állítom a számlálót..*/
	private int skillnumber;
	
	/*segédváltozó*/
	private double timeLeftFromCd;
	
	/*Ez a location pontja.*/
	private Point skillPoint;
	
	/*A player aki használja*/
	private Entity player;
	
	private IMonitorScreenManager monitorScreenManager;
	
	/*Kis skillképeknél ez mindig egy elemû tömb, hisz csak egy kis 52x52-es képet kell elõállítania.*/
	private StringDescriptor[] describers;
	
	public ImageIconTimerPicture(int skillnumber,IMonitorScreenManager monitorScreenManager,Entity player) {
		this.monitorScreenManager = monitorScreenManager;
		
		describers = new StringDescriptor[1];
		describers[0] = new StringDescriptor(0,0,20,0,0,0,"2",Color.white,"Arial");
		
		this.skillPoint = new Point();
		
		this.skillnumber = skillnumber;
		this.player = player;
	}
	
	/*Ez azért kell, hogy mindig az aktuális playerre mutasson, csak azt nézze.*/
	public void setPlayer(Entity player) {
		this.player = player;
	}

	@Override
	public StringDescriptor[] getStringDescriptor() {

		if(player != null && player.getSkills()[this.skillnumber] != null && player.getSkills()[this.skillnumber].getSkillStartedMainTime() + 
				player.getSkills()[this.skillnumber].getCdtime() >= player.getAppTime()){
			
			this.timeLeftFromCd = (player.getSkills()[this.skillnumber].getSkillStartedMainTime() + 
					player.getSkills()[this.skillnumber].getCdtime()) - player.getAppTime();
			this.skillPoint = this.monitorScreenManager.getSkillImageLocation(this.skillnumber,(int)player.getX() + player.getWidth()/2,(int)player.getY() + player.getHeight()/2);
			
			/*Ez az alábbi feltétel azért kell, mert lehet a cd time 1-jegyû vagy 2-jegyû
			 vagy 3 jegyû, és ennek függvényében jobban vagy kevésbé kell eltolni, hogy a kép
			 közepén maradjon a szám.*/
			if(this.timeLeftFromCd < 10){
				describers[0].setX(skillPoint.x + 20);
			}else if(this.timeLeftFromCd > 9 && this.timeLeftFromCd < 100){
				describers[0].setX(skillPoint.x + 15);
			}else{
				describers[0].setX(skillPoint.x + 10);
			}
			
			describers[0].setY(skillPoint.y + 35);
			describers[0].setString(Integer.toString((int)this.timeLeftFromCd));
		}else{
			this.describers[0].setX(-20000);
		}
		
		return this.describers;
	}
}