package network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import applogic.elements.Entity;
import applogic.elements.controllers.IEnvironment;
import io.socket.client.Socket;

public class WorldUpdater implements IWorldUpdater{

	private Socket socket;
	private boolean update;
	private JSONDeleter deleter;
	
	private JSONObject playerDescritpor;
	private JSONArray entities;
	private JSONObject entity;
	
	public WorldUpdater(Socket socket) {
		this.deleter = new JSONDeleter();
		this.socket = socket;
		this.playerDescritpor = new JSONObject();
		this.entities = new JSONArray();
		this.entity = new JSONObject();
	}
	
	@Override
	public void updateServer(IEnvironment environment) {
		if(true){
			update = false;
			deleter.clearJSONArray(entities);
			deleter.clearJSONObject(entity);
			deleter.clearJSONObject(playerDescritpor);
			
			try{
				entity.put("id",environment.getPlayer().getId());
				entity.put("username",environment.getPlayer().getUsername());
				entity.put("characterType", environment.getPlayer().getCharacterType().toString());
				if(environment.getPlayer().getSelectedEntity() != null){
					entity.put("selectedPlayer", environment.getPlayer().getSelectedEntity().getId());
				}else{
					entity.put("selectedPlayer", "null");
				}
				entity.put("x", environment.getPlayer().getX());
				entity.put("y", environment.getPlayer().getY());
				entity.put("angle",environment.getPlayer().getAngle());
				entity.put("health", environment.getPlayer().getHealth());
				entity.put("mana", environment.getPlayer().getMana());
				
				entity.put("dead", environment.getPlayer().isDead());
				entity.put("maxhealth", environment.getPlayer().getMaxhealth());
				entity.put("maxmana", environment.getPlayer().getMaxMana());
				
				for(int i=0;i<environment.getPlayer().getSkills().length;i++){
					if(environment.getPlayer().getSkillStarted()[i]){
						entity.put("skillStarted", i);
						break;
					}
				}
				
				if(!entity.has("skillStarted")){
					entity.put("skillStarted", -1);
				}
				for(int i=0;i<environment.getPlayer().getSkills().length;i++){
					if(environment.getPlayer().getSkillStarted(i)){
						environment.getPlayer().setSkillStarted(i, false);
						break;
					}
				}
				
				entities.put(entity);
				
				
				/*for(int i=0;i<environment.getOwnedEntities().size();i++){
					deleter.clearJSONObject(entity);
					entity.put("id",environment.getPlayer().getId());
					entity.put("username",environment.getPlayer().getUsername());
					entity.put("characterType", environment.getPlayer().getCharacterType().toString());
					if(environment.getPlayer().getSelectedEntity() != null){
						entity.put("selectedPlayer", environment.getPlayer().getSelectedEntity().getId());
					}else{
						entity.put("selectedPlayer", "null");
					}
					entity.put("x", environment.getPlayer().getX());
					entity.put("y", environment.getPlayer().getY());
					entity.put("angle",environment.getPlayer().getAngle());
					entity.put("health", environment.getPlayer().getHealth());
					entity.put("mana", environment.getPlayer().getMana());
					
					entity.put("dead", environment.getPlayer().isDead());
					entity.put("maxhealth", environment.getPlayer().getMaxhealth());
					entity.put("maxmana", environment.getPlayer().getMaxMana());
					
					for(int j=0;j<environment.getPlayer().getSkills().length;j++){
						if(environment.getPlayer().getSkillStarted()[j]){
							entity.put("skillStarted", j);
							break;
						}
					}
					
					if(!entity.has("skillStarted")){
						entity.put("skillStarted", -1);
					}
			
					entities.put(entity);		
				}*/
				
				playerDescritpor.put("descriptor", entities);
				socket.emit("playerMoved",playerDescritpor);
					
			}catch(JSONException e){
				e.getMessage();
			}
		}else{
			update = true;
		}
	}
}