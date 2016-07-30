package applogic.elements.controllers;

public class UserActionCommander extends EntityCommander{
	
	
	public UserActionCommander(IEnvironment environment) {
		super(environment);
	}

	/*Ez implementálja az EntityCommander interfacet.Itt lényegében a felhasználói beavatkozások
	 hatására parancsol, az általa irányított entitásnak.*/
	@Override
	public void command(double appTime) {
		
		/*Elõször beállítom, hogy az irányított entitás most nem mozog, aztán, ha valamerre mégis
		 mozog az úgyis átállítja, ha pedig nem akkor a nemmozgást ügyesen beállítottuk.*/
		
		getControlledEntity().setMoving(false);
		
		if(!getControlledEntity().isStunned()){
			/*Csak akkor csinálhat bármit is a karakter, ha az nincs lestunnolva.*/
			if(isUp()){
				/*if(getControlledEntity().getMovementSpeed() < getControlledEntity().getMaxMovementSpeed()){
					getControlledEntity().setMovementSpeed(getControlledEntity().getMovementSpeed() + 0.1);
				}*/
				getControlledEntity().moveForward();
				getControlledEntity().setMoving(true);
				getEnvironment().PlayerMoved(getControlledEntity());
				
			}/*else if(getControlledEntity().getMovementSpeed() > 1){
				/*Az else ág azért kell, mertha nemnyomja a gombot a player, mozogni a lassulás
				 végett azért még kell*/
				/*getControlledEntity().setMovementSpeed(getControlledEntity().getMovementSpeed() - 0.1);
				getControlledEntity().moveForward();
				getControlledEntity().setMoving(true);
				getEnvironment().PlayerMoved(getControlledEntity());
			}*/
			
			if(isDown()){
				getControlledEntity().moveBack();
				getControlledEntity().setMoving(true);
				getEnvironment().PlayerMoved(getControlledEntity());
				
			}
			
			if(isLeft()){
				getControlledEntity().turnLeft();
				getControlledEntity().setMoving(true);
				
			}
			
			if(isRight()){
				getControlledEntity().trunRight();
				getControlledEntity().setMoving(true);
				
			}
			
			
			for(int i=0;i<getSkillActivated().length;i++){
				if(getSkillActivated()[i]){
					getControlledEntity().activateSkill(i,appTime);
					/*Ezt innen ki kell majd törölni, ha askillek meglesznek rendesen csinálva.*/
					setSkillActivated(i, false);
				}
			}
		}else{
			/*Ha valaki le van stunnolva, akkor a lenyomott skillaktivitásokat visszaállítjuk
			 hamisra, hogy ne történjen meg ha kijön stunnból.*/
			for(int i=0;i<getSkillActivated().length;i++){
				setSkillActivated(i, false);
			}
		}
	}
}