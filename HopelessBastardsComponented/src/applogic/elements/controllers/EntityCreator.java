package applogic.elements.controllers;

import applogic.IViewBuilderContainer;
import applogic.elements.CharacterType;
import applogic.elements.Entity;
import applogic.elements.characters.Mage;
import applogic.elements.characters.SteveShooter;
import soundapi.ISoundProvider;

public class EntityCreator implements IEntityCreator{

	private IEnvironment environment;
	private ISoundProvider soundProvider;
	private IViewBuilderContainer container;
	
	public EntityCreator(IEnvironment environment, ISoundProvider soundProvider, IViewBuilderContainer container) {
		super();
		this.environment = environment;
		this.soundProvider = soundProvider;
		this.container = container;
	}

	@Override
	public Entity createEntity(int x, int y, String networkid, String characterType) {
		if(characterType.equals("MAGE")){
			return new Mage(x, y, 0, 400, 1000, 300, 1000, networkid, CharacterType.MAGE, 7, container, environment, new EnemyAndFriendlyEntityProvider(environment,false),this.soundProvider);
		}else if(characterType.equals("STEVE")){
			return new SteveShooter(x, y, 0, 400, 1000, 300, 1000, networkid, CharacterType.STEVE, 7, container, environment, new EnemyAndFriendlyEntityProvider(environment,false),this.soundProvider);
		}
		return null;
	}
}