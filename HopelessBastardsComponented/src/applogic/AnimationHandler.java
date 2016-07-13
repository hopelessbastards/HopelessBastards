package applogic;

public class AnimationHandler implements IAnimationHandler{

	private int frameChangeSpeed;/*Ha 60 fps a játék, akkor ha ennek az értéke 4, akkor 4 fps-enként fog
	animációs képkockát cserélni.*/
	private int frame;
	private int framePerSec;
	private int frameLimit;/*Ez adja meg, hogy hány képbõl áll az animáció.*/
	private int returnValue;/*Ez a visszatérõ érték, ami azt adja, hogy az animációnak éppen
	hanyadik elemét kell visszaadni.*/
	
	private boolean animationAble;/*Ki kell e rajzolni az animációt vagy sem.*/
	private boolean infinityAnimation;/*Ez a változó igaz, ha az animációnak több mint egyszer kell 
	lepörögnie, ilyen például a mage animációja, hogy folyamatosan mozog.Viszont ha ez false,
	akkor azt jelenti, hogy az animációt egyszer kell lejáttszani, íilyen például a villámlás támadása
	 a magenak.*/
	
	private String logicname;/*Az animáció képanyagának logikai elérésének neve,*/
	
	public AnimationHandler(int frameChangeSpeed,int frameLimit,boolean infinityAnimation) {
		this.frameChangeSpeed = frameChangeSpeed;/*4*/
		this.frameLimit = frameLimit;/*animáció mérete*/
		this.infinityAnimation = infinityAnimation;
	}
	
	public AnimationHandler(int frameChangeSpeed,int frameLimit,boolean infinityAnimation,String logicname) {
		this.frameChangeSpeed = frameChangeSpeed;/*4*/
		this.frameLimit = frameLimit;/*animáció mérete*/
		this.infinityAnimation = infinityAnimation;
		this.logicname=logicname;
	}
	
	/*Ez a metódus arra jó, hogy mindig visszaadja, a következõ animációs sorszámot, tehát
	 kiszámolja, hogy kell e a következõ animációs elem, vagy még maradjon amit eddig rajzolt.*/
	@Override
	public int animationPiece() {
		
		if(framePerSec < frameChangeSpeed){
			if(frame < frameLimit){
				this.returnValue = frame;
			}
			framePerSec++;
		}else{
		  /*Ha a FramePerSec nagyobb mint frameChangeSpeed,akkor azt jelenti,hogy a következõ képet kell kirajzolni az 
		   animációban.Ezért megint nullára megy a számláló.*/
		  framePerSec = 0;
		  
		  /*Ha a kirajzolt kép még nem az utolsó animációs kép, akkor növeljük az indexértéket,hisz nem lesz
		   ArrayOutIndexException.*/
		  	if(frame<frameLimit - 1){
				this.returnValue = frame;
				frame++;
			}else{
				/*Viszont ,ha már az utolsó animációs képkocka vetítési ideje is lejárt,akkor minden kezdõdik
				 elõlról.*/
				
				if(!infinityAnimation){
					/*Ha ez az animáció nem végtelen ismétlõdésû, akkor az animationable boolean értékét az
					 animáció lejátszásakor falsera kell állítanunk, hogy még1x autómatikusan ne játtsza le.*/
					this.animationAble = false;
				}
				frame = 0;
				framePerSec = 0;
				this.returnValue = frame;
			}
		}
		return this.returnValue;
	}

	@Override
	public boolean getAnimationable() {
		return this.animationAble;
	}

	@Override
	public void setAnimationable(boolean animationable) {
		this.animationAble = animationable;
	}

	@Override
	public String getLogicName() {
		return this.logicname;
	}
}