package applogic.elements.controllers;

import applogic.elements.Entity;

public class DoNothingCommander extends AiCommander{
	/*Ezt azért hoztam létre, mivel minden entitásnak kell valami commander,
	 és ha szimplán azt szeretném, hogy ne csináljon semmit az adott entitást, akkor 
	 Commandernek ezt állítom be neki.*/

	@Override
	public void command(double appTime) {
		
	}

	@Override
	public void setControlledEntity(Entity controlledEntity) {
			
	}
}