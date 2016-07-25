package applogic.elements.characters;

import applogic.IViewBuilderContainer;
import applogic.elements.CharacterType;
import applogic.elements.Player;
import applogic.elements.controllers.EnemyAndFriendlyEntityProvider;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.BombTrap;
import applogic.skills.ChangePlayerSkill;
import applogic.skills.MageLightning;
import applogic.skills.PoisonBombBladeStorm;
import applogic.skills.SimpleGunShoot;
import applogic.skills.vehicles.PoisonBomb;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.entities.SteveShooterViewBuilder;
import soundapi.ISoundProvider;

public class SteveShooter extends Player{

	private IImageViewBuilder steve;
	
	public SteveShooter(int x, int y, int health, int maxhealth, int mana,
			int maxMana, String networkId, CharacterType characterType, int skillCount, IViewBuilderContainer container,
			IEnvironment environment,EnemyAndFriendlyEntityProvider provider,ISoundProvider soundProvider) {
		super(x, y, 100, 100, 0, health, maxhealth, mana, maxMana, networkId, characterType, skillCount, container,
				environment,provider,soundProvider);
		
		getSkills()[0] = new MageLightning(this, environment, container,0,soundProvider);
		getSkills()[1] = new BombTrap(this, environment, container,1);
		getSkills()[2] = new SimpleGunShoot(this, environment, container,2,soundProvider);
		getSkills()[3] = new PoisonBombBladeStorm(this, environment, container,3);
		//getSkills()[4] = new MageLightning(this, environment, container,4);
		//getSkills()[5] = new MageLightning(this, environment, container,5);
		getSkills()[6] = new ChangePlayerSkill(this, environment, container,6);
		
		steve = new SteveShooterViewBuilder(this,container);
		container.getViewBuilder().add(steve);
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		super.setDeletable(deletable);
		steve.setDeletable(deletable);
	}

	@Override
	public void activateSkill(int skillnumber,double appTime) {
		getSkills()[skillnumber].activateSkill(appTime);	
	}

	@Override
	public boolean isDead() {
		
		return false;
	}
}