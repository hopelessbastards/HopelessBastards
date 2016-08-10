package applogic.elements.controllers;

import java.util.ArrayList;
import java.util.List;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import applogic.elements.controllers.ai.ElementDescriptionToAI;

public class FirstLuaAI extends EntityCommander{

	private List<ElementDescriptionToAI> elements;
	private ElementDescriptionToAI toAIHelper;
	
	private LuaValue[] environmentai;
	private List<LuaValue> skills;
	private List<LuaValue> state;
	private LuaValue[] descs;
	private LuaValue _G;
	private LuaValue ai;
	
	public FirstLuaAI(IEnvironment environment) {
		super(environment);
		elements = new ArrayList<ElementDescriptionToAI>();
		
		this.skills = new ArrayList<LuaValue>();
		this.state = new ArrayList<LuaValue>();
		
		_G = JsePlatform.standardGlobals();
		_G.get("dofile").call( LuaValue.valueOf("./firstLuaAI.lua"));        
		ai = _G.get("command");
		        
	}

	@Override
	public void command(double appTime) {
		toAIHelper = null;
		elements = new ArrayList<ElementDescriptionToAI>();
		int envNumber = 1;
		for(int i=0;i < getControlledEntity().getEnemyPlayers().size();i++){
			if(getControlledEntity().getEnemyPlayers().get(i).isFullyInitialized()){
				toAIHelper = getControlledEntity().getEnemyPlayers().get(i).createElementDescriptionToAI(getControlledEntity());
				if(toAIHelper != null){
					elements.add(toAIHelper);
					System.out.println(toAIHelper.getCollidedArea());
				}
			}
		}
		
		environmentai = new LuaValue[elements.size() * 2];
		int j = 1;
		descs = new LuaValue[10];
		
		for(int i=0;i<elements.size();i++){
	        descs[0] = CoerceJavaToLua.coerce(elements.get(i).getCollidedArea().x);
	        descs[1] = CoerceJavaToLua.coerce(elements.get(i).getCollidedArea().y);
	        descs[2] = CoerceJavaToLua.coerce(elements.get(i).getCollidedArea().getWidth());
	        descs[3] = CoerceJavaToLua.coerce(elements.get(i).getCollidedArea().getHeight());
	        descs[4] = CoerceJavaToLua.coerce(elements.get(i).getElementType());
	        descs[5] = CoerceJavaToLua.coerce(elements.get(i).isEnemy());
	        descs[6] = CoerceJavaToLua.coerce(elements.get(i).getHealth());
	        descs[7] = CoerceJavaToLua.coerce(elements.get(i).getMaxHealth());
	        descs[8] = CoerceJavaToLua.coerce(elements.get(i).getPower());
	        descs[9] = CoerceJavaToLua.coerce(elements.get(i).getMaxPower());
	        
	        environmentai[i] =  CoerceJavaToLua.coerce(envNumber);
	        environmentai[j] = CoerceJavaToLua.coerce(descs);
	        envNumber++;
	        j+= 2;
		}
		
        LuaValue[] vv = new LuaValue[4];
        vv[0] = CoerceJavaToLua.coerce(100);/*kulcs*/
        vv[1] = CoerceJavaToLua.coerce(200);/*érték*/
        vv[2] = CoerceJavaToLua.coerce(1400);/*kulcs*/
        vv[3] = CoerceJavaToLua.coerce(900);
        
        LuaValue[] arrays = new LuaValue[4];
        arrays[0] = CoerceJavaToLua.coerce(1);/*kulcs*/
        arrays[1] = CoerceJavaToLua.coerce(vv);/*érték*/
        arrays[2] = CoerceJavaToLua.coerce("x");/*kulcs*/
        arrays[3] = CoerceJavaToLua.coerce(10);/*érték*/
        
        LuaValue[] fog = new LuaValue[12];
        fog[0] = CoerceJavaToLua.coerce(1);
        fog[1] = CoerceJavaToLua.coerce(getControlledEntity().getFogOfWar().x);
        fog[2] = CoerceJavaToLua.coerce(2);
        fog[3] = CoerceJavaToLua.coerce(getControlledEntity().getFogOfWar().y);
        fog[4] = CoerceJavaToLua.coerce(3);
        fog[5] = CoerceJavaToLua.coerce(getControlledEntity().getFogOfWar().width);
        fog[6] = CoerceJavaToLua.coerce(4);
        fog[7] = CoerceJavaToLua.coerce(getControlledEntity().getFogOfWar().height);
        /*center*/
        fog[8] = CoerceJavaToLua.coerce(5);
        fog[9] = CoerceJavaToLua.coerce(getControlledEntity().getX() + getControlledEntity().getWidth() / 2);
        fog[10] = CoerceJavaToLua.coerce(6);
        fog[11] = CoerceJavaToLua.coerce(getControlledEntity().getY() + getControlledEntity().getHeight() / 2);
       
        if(environmentai.length > 0){
        	LuaValue retvals = ai.call(LuaValue.tableOf(environmentai), LuaValue.valueOf(getControlledEntity().getAngle()), LuaValue.tableOf(fog));
        	System.out.println("left: " + retvals.get(1));
            System.out.println("right: " + retvals.get(2));
            
            if(retvals.get(1).toboolean()){
            	/*left*/
            	getControlledEntity().turnLeft();
            }
            
            if(retvals.get(2).toboolean()){
            	/*right*/
            	getControlledEntity().trunRight();
            }
            
            if(retvals.get(3).toboolean()){
            	/*up*/
            	getControlledEntity().moveForward();
            }
            
            if(retvals.get(4).toboolean()){
            	/*down*/
            	getControlledEntity().moveBack();
            }
        }
	}
}