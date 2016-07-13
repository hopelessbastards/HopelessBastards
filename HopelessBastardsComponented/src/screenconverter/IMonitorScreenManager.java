package screenconverter;

import java.awt.Point;

public interface IMonitorScreenManager {
	public Point getSkillImageLocation(int skillnumber,int centerPointOfMapX,int centerPointOfMapY);
	public Point getHealthBarLocation(int centerPointOfMapX,int centerPointOfMapY);
	public Point getSkillBarLocation(int centerPointOfMapX,int centerPointOfMapY);
	public Point getHealtbarHealtLineLocation(int centerPointOfMapX,int centerPointOfMapY);
	public Point getHealtbarManaLineLocation(int centerPointOfMapX,int centerPointOfMapY);
}