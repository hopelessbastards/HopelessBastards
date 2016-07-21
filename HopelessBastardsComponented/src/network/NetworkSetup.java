package network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import applogic.elements.CharacterType;
import applogic.elements.characters.Mage;
import applogic.elements.controllers.IEnvironment;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class NetworkSetup implements INetworkSetup{

	private Socket socket;
	private IEnvironment environment;
	
	public NetworkSetup(Socket socket,IEnvironment environment) {
		super();
		this.socket = socket;
		this.environment = environment;
	}

	@Override
	public void setupNetwork() {
		/*az elsõ a kapcsolódási eseményre reagál*/
		this.socket.on("socketID", new Emitter.Listener() {
			
			@Override
			public void call(Object... arg0) {
				JSONObject data = (JSONObject)arg0[0];
				try{
					System.out.println("socketid");
					String id = data.getString("id");
					
					/*Amikor létrehozzuk a kapcsolatot a szerverrel, akkor az ad nekünk egy egyéni azonosítót,
					 és kiváltja ezta socketid eseményt, amire ez a metódus hívódik meg.Mivel regisztráljuk magunkat
					 a playerek közé jó lenne eljutttatni a szervernek, hogy mi a jelenlegi újj játékos
					 karakterének típusa.Ezt itt már tudjuk a dui varázslóban kiválasztja a player
					 és kiváltunk egy charactertype eseményt a szerver felé, ahol elküldjük neki
					 a karakter típusát, és így õ ezt továbbítani tudja minden más playernek mint 
					 newPlayer esemény  egy id és egy karaktertípus elég ahhoz, hogy
					 létrehozhassa minden más karakter ezt az új játékost ellenfélként
					 a saját kliensében.*/
					/*if(environment.getPlayer().getCharacterType() == CharacterType.MAGE){
						addEntity(new Mage(1600,1600,63,63,Id.PLAYER,id,Handler.this,gsm.username));
						environment.getFriendlyPlayers().add(new Mage(1600, 1600, 64, 64, 0, 1000, 1000, 1000, 1000, id, CharacterType.MAGE, 7, container, environment, provider, soundProvider));
						JSONObject ob = new JSONObject();
						try{
								
								ob.put("characterType","MAGE");
								
								socket.emit("characterType",ob);
						}catch(JSONException e){
							e.getMessage();
						}
					}else if(Handler.this.characterType == CharacterType.SOLDIER){
						addEntity(new Muscleman(1600,1600,63,63,Id.PLAYER,id,Handler.this,gsm.username));
						JSONObject ob = new JSONObject();
						try{
								
								ob.put("characterType","MUSCLEMAN");
								
								socket.emit("characterType",ob);
						}catch(JSONException e){
							e.getMessage();
						}
					}
					
					//addTile(new Wall(25*64,25*64,64,64,false,Id.WALL,WallType.FOLD,Handler.this));
					for(int i=0;i<entity.size();i++){
						if(entity.get(i).getId() == Id.PLAYER){
							player = (Player)entity.get(i);
						}
					}
					entityTrade();
					Entity trade = null;
					for(int i=0;i<entity.size();i++){
						if(entity.get(i).id == Id.PLAYER){
							trade = entity.get(i);
						}
					}
					/*for(int i=0;i<25;i++){
						addEntity(new Zombie(i*68+1000, i*68+1000, 63, 63,Id.ZOMBIE,player,Handler.this));
					}*/
				}catch(JSONException e){
					e.getMessage();
				}
			}
		}).on("newPlayer", new Emitter.Listener() {
			
			@Override
			public void call(Object... arg0) {
				JSONObject data = (JSONObject)arg0[0];
				try{
					System.out.println("newPlayer");
					String playerId = data.getString("id");
					String characterType = data.getString("characterType");
					
					/*Ha új játékos csatlakozik, elküldi mindenkinek az id-jét és a karakter típusát
					 ezek szerint létre tudjua minden enemy hozni õt a saját lokális terében.*/
					/*if(!playerId.equals(player.networkId)){
						if(characterType.equals("MAGE")){
							enemies.add(new Mage(25*63,25*63, 63, 63, Id.ENEMYPLAYER, playerId, Handler.this,gsm.username));
						}else if(characterType.equals("MUSCLEMAN")){
							enemies.add(new Muscleman(25*63,25*63, 63, 63, Id.ENEMYPLAYER, playerId, Handler.this,gsm.username));
						}
					}*/
					
				}catch(JSONException e){
					e.getMessage();
				}
				
			}
		}).on("playerDisconnected", new Emitter.Listener() {
			
			@Override
			public void call(Object... arg0) {
				System.out.println("disconnect");
				JSONObject data = (JSONObject)arg0[0];
				try{
					String id = data.getString("id");
					/*id alapján töröljük a listából(nincs kész)*/
					//enemies.removeById(id);
				}catch(JSONException e){
					e.getMessage();
				}
			}
		}).on("getPlayers", new Emitter.Listener() {
			
			@Override
			public void call(Object... arg0) {
				JSONArray objects = (JSONArray)arg0[0];
				
				try{
					for(int i=0;i<objects.length();i++){
						String id = objects.getJSONObject(i).getString("id");
						double x = objects.getJSONObject(i).getDouble("x");
						double y = objects.getJSONObject(i).getDouble("y");
						String characterType = objects.getJSONObject(i).getString("characterType");
						
						/*Minden player karakterét úgy hozzuk létre, hogy a jsonbõl kiszedjük,
						 hogy mi a karakterének típusa, és aszerint hozzuk létre.*/
						System.out.println("getPlayers");
						if(characterType.equals("MAGE")){
							//enemies.add(new Mage(x, y, 63, 63, Id.ENEMYPLAYER, id, Handler.this,gsm.username));
						}else if(characterType.equals("MUSCLEMAN")){
							//enemies.add(new Muscleman(x, y, 63, 63, Id.ENEMYPLAYER, id, Handler.this,gsm.username));
						}
					}
				}catch(JSONException e){
					e.getMessage();
				}
			}
		}).on("playerMoved", new Emitter.Listener() {
			
			@Override
			public void call(Object... arg0) {
				JSONObject data = (JSONObject)arg0[0];
				
				try{
					String playerId = data.getString("id");
					
						/*if(enemies.getById(playerId) != null){
							Player entity = enemies.getById(playerId);
							entity.username = data.getString("username");/*ezzel a sorral állítom be a user
							nevét a Musclemanoknak meg satöbbiknek, nem pedig kosntruktorába(így mûködik
							amúgy pedig nem igazán akart.)*/
							
						/*	if(enemies.getById(data.getString("selectedPlayer")) != null){
								entity.selectedPlayer = enemies.getById(data.getString("selectedPlayer"));
							}else if(friends.getById(data.getString("selectedPlayer")) != null){
								entity.selectedPlayer = friends.getById(data.getString("selectedPlayer"));
							}else{
								entity.selectedPlayer = player;
							}
							
							
							entity.x = data.getDouble("x");
							entity.y = data.getDouble("y");
							entity.angle = data.getDouble("angle");
							entity.health = data.getInt("health");
							entity.maxHealth = data.getInt("maxhealth");
							entity.mana = data.getInt("mana");
							entity.maxMana = data.getInt("maxmana");
							entity.onegunman = data.getBoolean("onegunman");
							entity.twogunman = data.getBoolean("twogunman");
							entity.dead = data.getBoolean("dead");
							entity.live = data.getBoolean("live");
							entity.skill0started = data.getBoolean("skill0started");
							entity.skill1started = data.getBoolean("skill1started");
							entity.skill2started = data.getBoolean("skill2started");
							entity.skill3started = data.getBoolean("skill3started");
							entity.skill4started = data.getBoolean("skill4started");
							entity.skill5started = data.getBoolean("skill5started");
							entity.skill6started = data.getBoolean("skill6started");
							
							if(entity.skill0started){
								entity.skills[0].activateSkillByServer();
							}else if(entity.skill1started){
								entity.skills[1].activateSkillByServer();
							}else if(entity.skill2started){
								entity.skills[2].activateSkillByServer();
							}else if(entity.skill3started){
								entity.skills[3].activateSkillByServer();
							}else if(entity.skill4started){
								entity.skills[4].activateSkillByServer();
							}else if(entity.skill5started){
								entity.skills[5].activateSkillByServer();
							}else if(entity.skill6started){
								entity.skills[6].activateSkillByServer();
							}
						}*/
						
				}catch(JSONException e){
					e.getMessage();
				}
			}
		});	
	}
}