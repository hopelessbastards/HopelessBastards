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
import applogic.viewbuilder.entities.SteveShooterViewBuilder;

public class SteveShooter extends Player{

	public SteveShooter(int x, int y, int width, int height, double angle, int health, int maxhealth, int mana,
			int maxMana, String networkId, CharacterType characterType, int skillCount, IViewBuilderContainer container,
			IEnvironment environment,EnemyAndFriendlyEntityProvider provider) {
		super(x, y, width, height, angle, health, maxhealth, mana, maxMana, networkId, characterType, skillCount, container,
				environment,provider);
		
		getSkills()[0] = new MageLightning(this, environment, container,0);
		getSkills()[1] = new BombTrap(this, environment, container,1);
		getSkills()[2] = new SimpleGunShoot(this, environment, container,2);
		getSkills()[3] = new PoisonBombBladeStorm(this, environment, container,3);
		//getSkills()[4] = new MageLightning(this, environment, container,4);
		//getSkills()[5] = new MageLightning(this, environment, container,5);
		getSkills()[6] = new ChangePlayerSkill(this, environment, container,6);
		

		container.getViewBuilder().add(new SteveShooterViewBuilder(this,container));
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