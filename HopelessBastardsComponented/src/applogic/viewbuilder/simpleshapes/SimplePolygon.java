package applogic.viewbuilder.simpleshapes;

import java.awt.Color;
import applogic.skills.AbstractSkill;
import applogic.viewbuilder.IPolygonViewBuilder;
import screenconverter.descriptors.PolygonDescriptor;

public class SimplePolygon extends IPolygonViewBuilder{

	private PolygonDescriptor[] describers;

	private AbstractSkill skill;
	
	public SimplePolygon(AbstractSkill skill) {
		describers = new PolygonDescriptor[1];
		describers[0] = new PolygonDescriptor(0, 0, 0,0,0,Color.red, true, null);
	
		this.skill = skill;
	}
	
	@Override
	public PolygonDescriptor[] getPolygonDescriptor() {
		
		describers[0].setX((int)skill.getSkillOwner().getY());
		describers[0].setY((int)skill.getSkillOwner().getY());
		describers[0].setPolygon(skill.getPolygon());
		return describers;
	}

}
