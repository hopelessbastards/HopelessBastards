package network;

import org.json.JSONException;
import org.json.JSONObject;

import applogic.elements.Entity;
import io.socket.client.Socket;

public class WorldUpdater implements IWorldUpdater{

	private Socket socket;
	private boolean update;
	
	public WorldUpdater(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void updateServer(Entity entity) {
		if(true){
			update = false;
			
			JSONObject data = new JSONObject();
			try{
					
				/*	data.put("id",entity.getId());
					data.put("username",entity.getUsername());
					data.put("characterType", entity.getCharacterType().toString());
					if(entity.getSelectedEntity() != null){
						data.put("selectedPlayer", entity.getSelectedEntity().getId());
					}else{
						data.put("selectedPlayer", "null");
					}*/
					data.put("x", entity.getX());
					data.put("y", entity.getY());
					data.put("angle",entity.getAngle());
					data.put("health", entity.getHealth());
					data.put("mana", entity.getMana());
					/*data.put("onegunman", this.onegunman);
					data.put("twogunman", this.twogunman);*/
					data.put("dead", entity.isDead());
					data.put("live", entity.isLive());
					data.put("maxhealth", entity.getMaxhealth());
					data.put("maxmana", entity.getMaxMana());
					
					for(int i=0;i<entity.getSkills().length;i++){
						if(entity.getSkills()[i] != null){
							data.put("skill" + i + "started", entity.getSkillStarted(i));
						}else{
							data.put("skill" + i + "started", false);
						}
					}
					
					for(int i=0;i<entity.getSkills().length;i++){
						if(entity.getSkillStarted(i)){
							entity.setSkillStarted(i, false);
							break;
						}
					}
					
					/*if(this.skill0started){
						this.skill0started = false;
					}else if(this.skill1started){
						this.skill1started = false;
					}else if(this.skill2started){
						this.skill2started = false;
					}else if(this.skill3started){
						this.skill3started = false;
					}else if(this.skill4started){
						this.skill4started = false;
					}else if(this.skill5started){
						this.skill5started = false;
					}else if(this.skill6started){
						this.skill6started = false;
					}*/
					
					socket.emit("playerMoved",data);
						
			}catch(JSONException e){
				e.getMessage();
			}
		}else{
			update = true;
		}
	}
}