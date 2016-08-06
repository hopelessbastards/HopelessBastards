package applogic.elements.controllers;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.luaj.vm2.Globals;
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
	
	
	
	
	public FirstLuaAI(IEnvironment environment) {
		super(environment);
		elements = new ArrayList<ElementDescriptionToAI>();
		
		this.skills = new ArrayList<LuaValue>();
		this.state = new ArrayList<LuaValue>();
		
		
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
		

		// Use any of the "call()" or "invoke()" functions directly on the chunk.
		//run the lua script defining your function
	    LuaValue _G = JsePlatform.standardGlobals();
		
		
		
        _G.get("dofile").call( LuaValue.valueOf("./firstLuaAI.lua"));
        
        LuaValue myAdd = _G.get("command");
		
		environmentai = new LuaValue[elements.size() * 2];
		int j = 1;
		descs = new LuaValue[10];
		
		for(int i=0;i<elements.size();i++){
			System.out.println("elkementszize: " + elements.size());
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
		
		/*for(int i=0;i<environmentai.length;i++){
			System.out.println("a " + i + ". elem " + environmentai[i]);
		}*/
		
		
        
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
        
        LuaValue[] fog = new LuaValue[6];
        fog[0] = CoerceJavaToLua.coerce(getControlledEntity().getFogOfWar().x);
        fog[1] = CoerceJavaToLua.coerce(getControlledEntity().getFogOfWar().y);
        fog[2] = CoerceJavaToLua.coerce(getControlledEntity().getFogOfWar().width);
        fog[3] = CoerceJavaToLua.coerce(getControlledEntity().getFogOfWar().height);
        fog[4] = CoerceJavaToLua.coerce(getControlledEntity().getX() + getControlledEntity().getWidth() / 2);
        fog[5] = CoerceJavaToLua.coerce(getControlledEntity().getY() + getControlledEntity().getHeight() / 2);
       
     
       
       
        if(environmentai.length > 0){
        	
        	LuaValue retvals = myAdd.call(LuaValue.tableOf(environmentai), LuaValue.valueOf(getControlledEntity().getAngle()), LuaValue.tableOf(fog));
            System.out.println(retvals.toint());
        }else{
        	
        }
        
	
	}
}