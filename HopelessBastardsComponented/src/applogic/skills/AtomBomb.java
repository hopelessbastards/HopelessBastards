package applogic.skills;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.viewbuilder.MageLightningViewBuilder;
import math.RotatePoints;

public class AtomBomb extends AbstractSkill{
		 public int damageValue = 250;/*Ennyi életerõt vesz le arról,akit ér a támadás*/
		 private int manacost = 100;/*Ennyi manát vesz le a használata a támadásnak*/
		 
		 private double x, y, angle; // x,y and angle(szög)
		 private int width, height; //width and height 
		 
		 private Point[] collideAreaPoints;
		   
		 public AtomBomb(Entity skillOwner,IEnvironment environment,IViewBuilderContainer container,int skillnumber) {
			 super(skillOwner,environment,container,skillnumber);
			 setCdtime(2);
			 this.collideAreaPoints = new Point[4];
			 for(int i=0;i<collideAreaPoints.length;i++){
				 this.collideAreaPoints[i] = new Point();
			 }
			 
			 setSkillViewBuilder(new MageLightningViewBuilder(this,container, skillOwner));
			 setWidth(512);
			 setHeight(300);
		 }
	   
		@Override
		public void activateSkill(double appTime) {
			/*Csak akkor aktiválódik a  skill, ha a jelenlegi játékidõ nagyobb,mint a skillkezdés és a skillcd idõ
			 összeadva, azaz ha lejárt a cd,csak akkor aktiválhatom újra a skillt.Illetve hs a skillstartedtime == 0, az azt jelenti
			 hogy mióta megy a játék,azaz inicializálva lett a skill, azóta még nem volt használva,tehát cd sem lehet rajta.*/
			if(getSkillStartedMainTime() + this.getCdtime() < appTime || getSkillStartedMainTime() == 0){
				setSkillStartedMainTime(appTime);/*A skillkezdési idõt beállítom a játék fõidejére*/
				setIsactivated(true);/*aktívnak tekintjük innentõl a skillt*/
				//player.monitorScreenmanager.skill0useable = false;/*A skillbaron elszürkítjük a képet, ami a skillt képviseli*/
				
				
				setViewBuilderActivate(true);
			//	boltSong.play();
				
				/*A playernél leveszem a manát,amibe a skill került.*/
				   if(getSkillOwner().getMana() - this.manacost < 0){
				    	getSkillOwner().setMana(0);
				    }else{
				    	getSkillOwner().setMana(getSkillOwner().getMana()-this.manacost);
				    }
				   
				   /*beállítom a paraméterben kapott koordinátákat és szöget, ami a player saját helyzete is egyben.*/
				   this.x = getSkillOwner().getX() + getSkillOwner().getWidth();
				   this.y = getSkillOwner().getY() - 118;
				   this.angle = getSkillOwner().getAngle();
				   this.width = 512;
				   this.height = 300;
				
				   /*Ezzel állítjuk be a playernél, hogy ez a sorszámú képessége el lett tolva és volt rá
				    elegendõ manája is.*/
				   getSkillOwner().setSkillStarted(getSkillnumber(), true);
			}
		}

		@Override
		public void activateSkillByServer(double appTime) {
			
		}

		@Override
		public void tick(double appTime) {
			
		   if(isIsactivated()){

			   for(int i=0;i<getEnvironment().getEnemyEntities().size();i++){
				   Entity en = getEnvironment().getEnemyEntities().get(i);
				   if(getPolygon().intersects(en.getCollideArea())){
					   en.setHealth(-damageValue);
					   //player.handler.damagetext.add(new DamagingText(en.x, en.y,String.valueOf(damageValue),true, player.handler));
				   }
			   }
			setIsactivated(false);
			getSkillOwner().setSkillStarted(getSkillnumber(), false);
		   }
		}
		
		 public Polygon getPolygon(){			 
			this.collideAreaPoints[0].setLocation((int)x,(int)y);
			this.collideAreaPoints[1].setLocation((int)x + width,(int)y);
			this.collideAreaPoints[2].setLocation((int)x+width,(int)y+height);
			this.collideAreaPoints[3].setLocation((int)x,(int)y+height);
			return RotatePoints.rotate(collideAreaPoints[0],collideAreaPoints[1],collideAreaPoints[2],
					  collideAreaPoints[3],angle,(int)(getSkillOwner().getX() + getSkillOwner().getWidth()/2), (int)(getSkillOwner().getY() + getSkillOwner().getHeight()/2));
		   }

		@Override
		public Rectangle getBounds() {
			
			return null;
		}
}