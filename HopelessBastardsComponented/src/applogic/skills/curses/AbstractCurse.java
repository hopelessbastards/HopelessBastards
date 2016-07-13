package applogic.skills.curses;

import applogic.elements.Entity;

public abstract class AbstractCurse {
	/*Curse osztályra azért van szükség, hogy ezeket rárakjuk a karakterre, akkor ez mint
	 a parazita rajta lesz, a karakter ennek meghívja a tick metódusát, és így tudja
	 lassítani, mérgezni, meglopni stb..*/
	
	private Entity cursedEntity;/*Ezt fogja mérgezni a curse*/
	private Entity curseOwner;/*Ez a curse tulajdonosa, ez rakta az átkot, néha ezt is jó ismerni.*/
	
	public AbstractCurse(Entity cursedEntity,Entity curseOwner) {
		this.cursedEntity = cursedEntity;
		this.curseOwner = curseOwner;
	}
	
	public Entity getCursedEntity() {
		return cursedEntity;
	}

	public void setCursedEntity(Entity cursedEntity) {
		this.cursedEntity = cursedEntity;
	}

	public Entity getCurseOwner() {
		return curseOwner;
	}

	public void setCurseOwner(Entity curseOwner) {
		this.curseOwner = curseOwner;
	}

	public abstract void tick(double appTime);
}