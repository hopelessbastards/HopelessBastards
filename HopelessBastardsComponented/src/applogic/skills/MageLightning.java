package applogic.skills;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.viewbuilder.MageLightningViewBuilder;
import math.RotatePoints;
import soundapi.ISound;
import soundapi.ISoundProvider;
import soundapi.MP3;

public class MageLightning extends AbstractSkill{
		
	private ISound lightning;
	
	 private int damageValue = 100;/*Ennyi életerõt vesz le arról,akit ér a támadás*/
	 private int manacost = 100;/*Ennyi manát vesz le a használata a támadásnak*/
	 
	 private double x, y, angle; // x,y and angle(szög)
	 private int width, height; //width and height 
	 
	 private Point[] collideAreaPoints;
	   
	 public MageLightning(Entity skillOwner,IEnvironment environment,IViewBuilderContainer container,int skillnumber,ISoundProvider soundProvider) {
		 super(skillOwner,environment,container,skillnumber);
		 setCdtime(2);
		 this.collideAreaPoints = new Point[4];
		 for(int i=0;i<collideAreaPoints.length;i++){
			 this.collideAreaPoints[i] = new Point();
		 }
		 
		 this.lightning = new MP3("lightning", soundProvider);
		 
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
			this.lightning.play();
			
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
			   
			
			   //animaionStillRuning = true;/*most már kirajzolhatjuk az animációt.*/
			
			   /*Ezzel állítjuk be a playernél, hogy ez a sorszámú képessége el lett tolva és volt rá
			    elegendõ manája is.*/
			   getSkillOwner().setSkillStarted(getSkillnumber(), true);
			   
			   setNetworkActivate(true);
			   //player.skill0started = true; 
		}
	}

	@Override
	public void activateSkillByServer(double appTime) {
		setSkillStartedMainTime(appTime);/*A skillkezdési idõt beállítom a játék fõidejére*/
		setIsactivated(true);/*aktívnak tekintjük innentõl a skillt*/
		
		setViewBuilderActivate(true);
		this.lightning.play();
		
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
		
		getSkillOwner().setSkillStarted(getSkillnumber(), true);
	
		   
		getSkillOwner().setSkillStarted(getSkillnumber(), false);
		//player.skill0started = false;
		
	}

	@Override
	public void tick(double appTime) {
		/*A tick metódus kiszámolja,hogy hány másodperc van a cd lejárásáig,ez azért kell,hogy a MonitorScreenManager
		 kitudja írni visszaszámlálás formájában minden másopercben.*/
		
		/*A tick metódusában megvizsgálom,hogy most sebez-e a skill.Ezt a változót az activateSkill metódusban állítom igazra.
	    Végigmegy az összes entitáson, és ellenõrzi,hogy melyik entitásnak van mettszéspontja a villámlási
	    területtel(a bolt osztály polygonja), és azoknak az entitásoknak az életét csökkenti.*/
		
	   if(isIsactivated()){
		   
		   for(int i=0;i<getSkillOwner().getEnemyEntities().size();i++){
			   Entity en = getSkillOwner().getEnemyEntities().get(i);
			   if(getPolygon().intersects(en.getCollideArea())){
				   en.setHealth(-damageValue);
				   //player.handler.damagetext.add(new DamagingText(en.x, en.y,String.valueOf(damageValue),true, player.handler));
			   }
		   }
		   
		   for(int i = 0;i < getSkillOwner().getEnemyPlayers().size();i++){
			   Entity player = getSkillOwner().getEnemyPlayers().get(i);
			   if(getPolygon().intersects(player.getCollideArea())){
				   player.setHealth(-damageValue);
			   }
		   }
		   
		   /*for(int i=0;i<getEnvironment().getEnemyEntities().size();i++){
			   getEnvironment().getEnemyEntities().get(i).setDeletable(true);
		   }*/
		   
		   /*Elõször az entitásokat ellenõrizzük végig.(Itt nem mozgo játékososk(pl zombi), és
			 az adott user karaktere található.)*/
			/*	for(int i=0;i<player.handler.entity.size();i++){
					en = player.handler.entity.get(i);
					if(en.id == Id.PLAYER){
						
						ene = (Player)player.handler.entity.get(i);

						if(!(ene.networkId.equals(player.networkId)) && getPolygon().intersects(ene.getDamagedArea())){
							ene.setHealth(-400);
							player.handler.damagetext.add(new DamagingText(ene.x, ene.y, String.valueOf(400),true, player.handler));
						}
					}else{
						enem = player.handler.entity.get(i);
						if(getPolygon().intersects(enem.getDamagedArea())){
							enem.setHealth(-400);
							player.handler.damagetext.add(new DamagingText(enem.x, enem.y, String.valueOf(400),true, player.handler));
						}
					}		
				}
				
				for(int i=0;i<player.handler.enemies.size();i++){
					enemy = player.handler.enemies.get(i);
					if(!(enemy.networkId.equals(player.networkId)) && getPolygon().intersects(enemy.getDamagedArea())){
						enemy.setHealth(-400);
						player.handler.damagetext.add(new DamagingText(enemy.x, enemy.y, String.valueOf(400),true, player.handler));
					}	
				}
		   
		 /*Mivel csak egyszer szeretnénk,hogy sebezzen a skill,nem pedig másodpercenként 60-szor, ezért rögtön az elsõ használat
		 után hamisra állítjuk a damagingNow változót, így a sebzés nem fog megtörténni többet, csak ha újra elnyomjuk a skillt
		 tehát ez a skill egyszeri sebzés,nem olyan,hogy másodpercenként sebez.*/
		//isactivated = false;
		   
		setIsactivated(false);
		getSkillOwner().setSkillStarted(getSkillnumber(), false);
	   }
		
		
		/*this.timeuntilcdend = this.skillStartedMainTime + this.cdtime - (Game.maintime);
		/*Illetve mindíg megnézi,hogy a cd lejárt-e már,mertha igen ,akkor a hudmanagernek a változóját
		 truera kell állítani,hogy kivilágosítsa a skillképet.*/
		/*if(this.skillStartedMainTime + this.cdtime < Game.maintime || skillStartedMainTime == 0){
			player.monitorScreenmanager.skill0useable = true;/*Ezzel látható,hogy az a baj,hogy ha 10 percig nem használom a skillt,
			akkor 10 percig elérhetõ,és folymatosan mindíg truere állítja az értéket, ez pazarló lehet,javítani kellene
			valami propertychangelistenert tudnék elképzelni.*/
			
		//}
	}
	
	 public Polygon getPolygon(){
		   /*Visszaadja egy bizonyos szöggel elforgatott polygont.Ez a polygon vizsgálja,hogy van-e mettszéspontja vmelyik
		    entitással,mert akkor sebzés az mehet.*/
		 
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