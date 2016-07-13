package applogic.viewbuilder.string;

import java.awt.Color;
import java.util.Random;
import applogic.elements.Entity;
import applogic.viewbuilder.IStringViewBuilder;
import screenconverter.descriptors.StringDescriptor;

public class DamageTextViewBuilder extends IStringViewBuilder{
	
	private StringDescriptor[] describers;
	private Entity damagedEntity;
	
	private boolean jump;/*az ugró hatás*/
	private boolean right;/*jobbra vagy ballra ugorjon el a karaktertõl a szám,ha jobbra akkor a right változó értéke true,
	ha ballra akkor a right értéke false, tehát külön left booleant nem csináltam.*/
	
	/*Meddig legyen a képernyõn*/
	private float lifeTime = 25;
	
	/*Maximum meddig ugorjon*/
	private float maxUp = 2.5f;
	
	/*Jelenleg milyen magasságnál jár az ugrásban.*/
	private float currentUp;
	

	private boolean isAlive;/*Egy idõ után el kell tûnnie a képernyõröl a számnak,és ez a változó jelzi,hogy meddig van képernyõn.*/
	private Random random;
	private Random intrandom;
	
	private float x;
	private float y;
	
	public DamageTextViewBuilder(Entity damagedEntity,int health) {
		this.damagedEntity = damagedEntity;
		this.describers = new StringDescriptor[1];
		
		this.random = new Random();
		this.intrandom = new Random();
		
		if(health < 0){
			describers[0] = new StringDescriptor((int)damagedEntity.getX() + damagedEntity.getWidth()/2 + intrandom.nextInt(40) - 20,(int)damagedEntity.getY() + damagedEntity.getHeight()/2 + intrandom.nextInt(40) - 20,25,0,0,0,Integer.toString(health*-1),Color.red,"Impact");
		}else{
			describers[0] = new StringDescriptor((int)damagedEntity.getX() + damagedEntity.getWidth()/2 + intrandom.nextInt(40) - 20,(int)damagedEntity.getY() + damagedEntity.getHeight()/2 + intrandom.nextInt(40) - 20,25,0,0,0,Integer.toString(health),Color.green,"Impact");
		}
		
		/*Minden damageszöveg véletlenszerûen ugrik jobbra vagy ballra.*/
		
		int rand = intrandom.nextInt(2);
		if(rand == 0){
			right = true;
		}else if(rand == 1){
			right = false;
		}
		x = (float)damagedEntity.getX();
		y = (float)damagedEntity.getY();
		isAlive = true;
	}
	
	@Override
	public StringDescriptor[] getStringDescriptor() {
		if(isAlive){
			if(lifeTime > 0){
				lifeTime-=0.1;
			}else if(lifeTime <= 0){
				isAlive = false;
			}
		}else{
			setDeletable(true);
		}
		return describers;
	}
}