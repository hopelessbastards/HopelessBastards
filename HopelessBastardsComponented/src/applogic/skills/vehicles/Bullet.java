package applogic.skills.vehicles;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.LivingObject;
import applogic.elements.SkillVehicle;
import applogic.skills.vehicles.viewBuilder.BulletViewBuilder;
import applogic.viewbuilder.IImageViewBuilder;
import math.RotatePoints;

public class Bullet extends SkillVehicle{
	 private double x, y, angle; 
	   
	 private double px,py;
	 
	 private Polygon polygon = null;
	 private Point distance;
	 private Rectangle rect;
	 private double velocityX;
	 private double velocityY;
	 private Point2D center;
	 private int dealingDamage = 50;
	 
	 private int damagingValue;
	 
	 /*Az alábbi két változó segédváltozó*/
		private LivingObject building;
		private Entity entity;
	   
	 private IImageViewBuilder bulletViewBuilder;
	 
	   public boolean startShoot;
	    
	   private Point bulletDamagedCenterPoint = new Point();
	   //private Tile t;
	   
	   private Point[] pointHelper;
	 
	 
	   private int bulletStartingX;
	   private int bulletStartingY;
	   
	   private Entity bulletOwner;
	   
	   public Bullet(double x, double y, double angle, int width, int height,Entity player,IViewBuilderContainer container) {
		   super((int)x, (int)y,width,height, angle,0,0, player,container);
	      this.x = x;
	      this.y = y;
	      
	      this.bulletOwner = player;
			setEnemyBuildings(bulletOwner.getEnemyBuildings());
			setEnemyEntities(bulletOwner.getEnemyEntities());
			setEnemyPlayers(bulletOwner.getEnemyPlayers());
	      
		this.bulletStartingX = (int)x + bulletOwner.getWidth()/2;
		this.bulletStartingY = (int)y + bulletOwner.getHeight()/2;
	      
	      this.damagingValue = 100;
	      
	      pointHelper = new Point[4];
	      for(int i=0;i<pointHelper.length;i++){
	    	  pointHelper[i] = new Point();
	      }
	      
	      setOwner(player);
	      
	      this.startShoot = true;/*Ez a puskacsõbõl kijövõ szikra animáció miatt kell,ez jelzi,hogy kezdõállapotban van a golyó
	      kilövése,mihejst vége az animációnak,a player ezt az értéket false-ra állítja.*/
	      
	      /*A golyóknál nagyon fontos szerepe van a konstruktornak.Ugyanis itt pont a pisztoly elé helyezzük a létrehozott golyót.
	       polygon változóban az elforgatott, a lövés irányába forgatott polygont rakjuk.
	       Ennek lekérdezve az elsõ x,y koordinátáját megkapjuk a balfelsõ sarok reprezentációját.
	       */
	 
	      
	      int[] xx = new int[4];
		   int[] yy = new int[4];
		   Point p = RotatePoints.rotatePoint(new Point((int)x,(int)y), angle,(int)(getOwner().getX()+getOwner().getWidth()/2), (int)(getOwner().getY()+getOwner().getHeight()/2));
		   pointHelper[0].setLocation(p.x,p.y);
		  xx[0] = p.x;
		  yy[0] = p.y;
		  
		   p = RotatePoints.rotatePoint(new Point((int)x+ getWidth(),(int)y), angle,(int)(getOwner().getX()+getOwner().getWidth()/2), (int)(getOwner().getY()+getOwner().getHeight()/2));   
		   pointHelper[1].setLocation(p.x,p.y);
		   xx[1] = p.x;
		   yy[1] = p.y; 
		   p = RotatePoints.rotatePoint(new Point((int)x + getWidth(),(int)y+ getHeight()), angle,(int)(getOwner().getX()+getOwner().getWidth()/2), (int)(getOwner().getY()+getOwner().getHeight()/2));
		   pointHelper[2].setLocation(p.x,p.y);
		   xx[2] = p.x;
		   yy[2] = p.y;
		   p = RotatePoints.rotatePoint(new Point((int)x,(int)y+ getHeight()), angle,(int)(getOwner().getX()+getOwner().getWidth()/2), (int)(getOwner().getY()+getOwner().getHeight()/2));
		   pointHelper[3].setLocation(p.x,p.y );
		   xx[3] = p.x;
		   yy[3] = p.y;
	      
	      
	     this.px = xx[0];
	     this.py = yy[0];
	      
	     /*A polygon csúcspontjai*/
	    /*  int[] polx = polygon.xpoints;
	      int[] poly = polygon.ypoints;*/
	      int[] polx = xx;
	      int[] poly = yy;
	      
	    /*Ltrehozok 4 db point objektumot,hisz tudom,hogy 4 szög polygonról lesz mindíg szó.Feltöltöm a polygon objektum csúcsainak
	     értékeivel.*/
	      Point2D[] ps = new Point2D[4];
	      for(int i=0;i<4;i++){
	    	  ps[i] = new Point2D.Float(polx[i], poly[i]);
	      }
	      
	      /*Ebbe a változóba a polygon középpontját rakjuk,amit a centerOfMass() metódus számol ki.*/
	      center = this.centerOfMass(ps);
	      
	      /*Mivel Rectangle nem forgatható, ezért számolgattam polygonnal, majd annak értékét átalakítom rectanglere.
	       Azaz a középpont megvan,akkor a bal felsõ sarok abból könnyen kitalálható, és be is állítom.*/
	      
	      
	      this.x=center.getX() - getWidth()/2;
	      this.y=center.getY() - getHeight()/2;
	      
	      /*Beállítok egy írányegyenest, amit követni fog a golyó.Ezt az irányvektort elforgatom adott szöggel.*/
	      px += Math.cos(Math.toRadians(angle))*6000;
	      py += Math.sin(Math.toRadians(angle))*6000;
	      
	      /*A távolság megvan.*/
	      distance = new Point((int)px,(int)py);
	   
	      
	      //this.bulletViewBuilder = new BombViewBuilder((int)getX(),(int)getY(),50,50);
	      this.bulletViewBuilder = new BulletViewBuilder(0,0,0,0,this);
	      container.getViewBuilder().add(this.bulletViewBuilder);
	      
	      //container.getRectangleBuilder().add(new SimpleRectangle((int)x, (int)y,0,0,0, width, height,Color.red,true));
	   }
	   
	   public double getX() {
	      return x;
	   }

	   public double getY() {
	      return y;
	   }

	   public void setX(double x) {
	      this.x = x;
	   }

	   public void setY(double y) {
	      this.y = y;
	   }
	 
	  public void moveForward(int speed) {
		   /*Elõre mozgatjuk a golyót.A konstruktorban kiszámolt irányvektort követve.A számolási mód a füzetben van, az a lényeg,hogy
		    adott pontot ,hogyan követhet egy objektum.*/
		   
		   double newX = this.distance.x;
	        double newY = this.distance.y;
	        double distX = newX - this.x;
	        double distY = newY - this.y;
	        double length = Math.sqrt(((distX * distX) + (distY * distY)));
	      
	        if(length >= 1){
	        	 velocityX = distX/length*speed;
	             velocityY = distY/length*speed;
	        }
	        
		    this.x += velocityX;
	        this.y += velocityY;
	   }
	   
	   public Rectangle getBounds(){
			return new Rectangle((int)x,(int)y,(int)getWidth(),(int)getHeight());
	   }
	   
	   public Polygon getPolygon(){
		   pointHelper[0].setLocation(x, y);
		   pointHelper[1].setLocation(x + getWidth(), y);
		   pointHelper[2].setLocation(x + getWidth(), y + getHeight());
		   pointHelper[3].setLocation(x, y + getHeight());
		  /* return RotatePoints.rotate(new Point((int)x,(int)y), new Point((int)x + getWidth(),(int)y), new Point((int)x+getWidth(),(int)y+getHeight()) ,
				   new Point((int)x,(int)y+getHeight()),angle,(int)(player.getX()+player.getWidth()/2), (int)(player.getY()+player.getHeight()/2));*/
		   
		   return RotatePoints.rotate(pointHelper[0],pointHelper[1],pointHelper[2],pointHelper[3],
				 angle,(int)(getOwner().getX()+getOwner().getWidth()/2), (int)(getOwner().getY()+getOwner().getHeight()/2));
	   }
	   
	   public Polygon getGunedPolygon(){
		   int[] xx = new int[4];
		   int[] yy = new int[4];
		   Point p = RotatePoints.rotatePoint(new Point((int)x,(int)y), angle,(int)(getOwner().getX()+getOwner().getWidth()/2), (int)(getOwner().getY()+getOwner().getHeight()/2));
		   pointHelper[0].setLocation(p.x,p.y);
		  xx[0] = p.x;
		  yy[0] = p.y;
		  
		   p = RotatePoints.rotatePoint(new Point((int)x+ getWidth(),(int)y), angle,(int)(getOwner().getX()+getOwner().getWidth()/2), (int)(getOwner().getY()+getOwner().getHeight()/2));   
		   pointHelper[1].setLocation(p.x,p.y);
		   xx[1] = p.x;
		   yy[1] = p.y; 
		   p = RotatePoints.rotatePoint(new Point((int)x + getWidth(),(int)y+ getHeight()), angle,(int)(getOwner().getX()+getOwner().getWidth()/2), (int)(getOwner().getY()+getOwner().getHeight()/2));
		   pointHelper[2].setLocation(p.x,p.y);
		   xx[2] = p.x;
		   yy[2] = p.y;
		   p = RotatePoints.rotatePoint(new Point((int)x,(int)y+ getHeight()), angle,(int)(getOwner().getX()+getOwner().getWidth()/2), (int)(getOwner().getY()+getOwner().getHeight()/2));
		   pointHelper[3].setLocation(p.x,p.y );
		   xx[3] = p.x;
		   yy[3] = p.y;
		   
		   
		   return new Polygon(xx,yy,xx.length);
	   }
	   
	   public Point getWay(){
		   return new Point((int)px,(int)py);
	   }
	   
	   public void tick(double appTime){
		   
		   
		   moveForward(10);
		   
		   double distx = this.bulletStartingX - this.bulletDamageAreaCenter().getX();
		   double disty = this.bulletStartingY - this.bulletDamageAreaCenter().getY();
		   
		   double distance = Math.sqrt((distx*distx)+(disty*disty));
		   
		   if(distance > 500){
			   setDeletable(true);
		   }
		   
		   
		   if(!isDeletable()){
			   for(int i=0;i<getEnemyBuildings().size();i++){
					building = getEnemyBuildings().get(i);
					if(building.getCollideArea().contains(this.bulletDamageAreaCenter())){
						building.setHealth(- this.damagingValue);
						setDeletable(true);
						break;
					}
				}
		   }
			
			if(!isDeletable()){
				for(int i=0;i<getEnemyEntities().size();i++){
					entity = getEnemyEntities().get(i);
					if(entity.getCollideArea().contains(this.bulletDamageAreaCenter())){
						if(!entity.isBlocking()){
							entity.setHealth(- this.damagingValue);
						}
						setDeletable(true);
						break;
					}
				}
			}
			
			if(!isDeletable()){
				for(int i=0;i<getEnemyPlayers().size();i++){
					entity = getEnemyPlayers().get(i);
					if(entity.getCollideArea().contains(this.bulletDamageAreaCenter())){
						if(!entity.isBlocking()){
							/*Csak nem blokkoló entitásokról veszi le az életet.*/
							entity.setHealth(- this.damagingValue);
						}
						setDeletable(true);
						break;
					}
				}
			}
		   
		   /*Végigmegy az összes entitáson,és ha ütkötik valamelyikkel,azaz a golyó középpontja benne van az entitás négyszög
		    területében ,akkor sebez,és a golyót eltüntetjük a player golyó listájából,ezálltal nem létezik tovább.*/
		   
		   /*Végignézzük az entityben lévõ és az enmy listában lévõ elemeket , hogy ütközött-e valamelyikkel.
		    Az entityben azért nem kell megvizsgálni, hogy playerrel ütközött-e, mert amelyik player kilõtte,
		    azzal ez sosem fog érintkezni, hisz a pisztoly csõbõl jön ki.*/
		  /* for(int i=0;i<handler.entity.size();i++){
			   ene = handler.entity.get(i);
			   if(ene.getPolygon().contains(this.bulletDamageAreaCenter())){
				   /*Itt megvizsgálom,hogy a playerhez tartozó buff rajta van-e,mert ha igen,akkor többet sebez a golyó.*/
				  /* if(player.skills[1].isactivated){
					   ene.setHealth(- this.dealingDamage * 10);
					   player.bullets.remove(this);
					   handler.damagetext.add(new DamagingText(ene.x, ene.y,String.valueOf(this.dealingDamage*10),true, handler));
				   }else{
					   ene.setHealth(- this.dealingDamage);
					   player.bullets.remove(this);
					   handler.damagetext.add(new DamagingText(ene.x, ene.y,String.valueOf(this.dealingDamage),true, handler));
				   }
			   }
		   }*/
		   
		  /* for(int i=0;i<handler.enemies.size();i++){
			   en = handler.enemies.get(i);
			   /*Csak azzal ez enemyvel ütközhet, amelyik nem lõtte ki a golyót, tehát mivel
			    tudom hogy a player adattagba van eltárolva az a muscleman, aki kilõtte a golyót.
			    Ha ez az enemy nem egyenlõ azzal aki kilõtte a golyót, és még ütközik
			    is a golyóval akkor sebzést kap.(networkid vizsgálat, arra nézve , hogy a golyó
			    tulajdonosa vajon megegyezik e az aktuálisan vizsgált enemy-vel.)*/
			   
			  /* if(!(en.networkId.equals(player.networkId)) && en.getPolygon().contains(this.bulletDamageAreaCenter())){
				   /*Itt megvizsgálom,hogy a playerhez tartozó buff rajta van-e,mert ha igen,akkor többet sebez a golyó.*/
				  /* if(player.skills[1].isactivated){
					   en.setHealth(- this.dealingDamage * 10);
					   player.bullets.remove(this);
					   handler.damagetext.add(new DamagingText(en.x, en.y,String.valueOf(this.dealingDamage*10),true, handler));
				   }else{
					   en.setHealth(- this.dealingDamage);
					   player.bullets.remove(this);
					   handler.damagetext.add(new DamagingText(en.x, en.y,String.valueOf(this.dealingDamage),true, handler));
				   }
			   }*/
		   }
		   
		   /*Végigmegy az összes pályalemen,és ami solid és ütközik a golyóval,az megfogja a golyót,így az nem megy tovább.*/
		  /* for(int i=0;i<handler.tile.size();i++){
			   t = handler.tile.get(i);
			   if(t.solid==true && t.getBounds().contains(this.bulletDamageAreaCenter())){
				   player.bullets.remove(this);
			   }
		   }
	   }*/
	   
	   public Point bulletDamageAreaCenter(){
		   /*Visszaadja,hogy a golyónak mely része sebez,azaz a képernyõn mely pontjával kell ütköznie pl a zombienak ,hogy
		    sebzõdjön.*/
		   
		   //Rectangle rect = this.getBounds();
		   this.bulletDamagedCenterPoint.setLocation(this.getBounds().x+getWidth()/2,this.getBounds().y+getHeight());
		   return this.bulletDamagedCenterPoint;
		   //return new Point(rect.x+width/2,rect.y+height);
	   }
	   
	   /*Az alábbi két metódus polygon középontot számol,különösebb magyarázatot nem fûznék hozzá.*/
	   public double area(Point2D[] polyPoints) {
			int i, j, n = polyPoints.length;
			double area = 0;

			for (i = 0; i < n; i++) {
				j = (i + 1) % n;
				area += polyPoints[i].getX() * polyPoints[j].getY();
				area -= polyPoints[j].getX() * polyPoints[i].getY();
			}
			area /= 2.0;
			return (area);
		}
	   public  Point2D centerOfMass(Point2D[] polyPoints) {
			double cx = 0, cy = 0;
			double area = area(polyPoints);
			// could change this to Point2D.Float if you want to use less memory
			Point2D res = new Point2D.Double();
			int i, j, n = polyPoints.length;

			double factor = 0;
			for (i = 0; i < n; i++) {
				j = (i + 1) % n;
				factor = (polyPoints[i].getX() * polyPoints[j].getY()
						- polyPoints[j].getX() * polyPoints[i].getY());
				cx += (polyPoints[i].getX() + polyPoints[j].getX()) * factor;
				cy += (polyPoints[i].getY() + polyPoints[j].getY()) * factor;
			}
			area *= 6.0f;
			factor = 1 / area;
			cx *= factor;
			cy *= factor;
			res.setLocation(cx, cy);
			return res;
		}
	   
	   @Override
	public void setDeletable(boolean deletable) {
		super.setDeletable(deletable);
		bulletViewBuilder.setDeletable(deletable);
	}
}