package network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import applogic.elements.CharacterType;
import applogic.elements.Entity;
import applogic.elements.characters.Mage;
import applogic.elements.controllers.IEnvironment;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class NetworkSetup implements INetworkSetup{

	private Socket socket;
	private IEnvironment environment;
	
	private Entity player;
	
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
					
					JSONObject ob = new JSONObject();
					
					environment.makePlayer(3500, 3048, id, environment.getSelectedCharacterType().toString());
						
					ob.put("characterType",environment.getSelectedCharacterType().toString());	
					socket.emit("characterType",ob);
					
					/*for(int i=0;i<entity.size();i++){
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
					
					if(!playerId.equals(environment.getPlayer().getId())){
						environment.makeEnemyPlayer(3000, 3000, playerId, characterType);
					}
					
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
					for(int i=0;i<environment.getEnemyPlayers().size();i++){
						if(environment.getEnemyPlayers().get(i).getId().equals(id)){
							environment.getEnemyPlayers().remove(i);
						}
					}
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
						
						environment.makeEnemyPlayer((int)x, (int)y, id, characterType);
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
					Entity enemy = null;
					String playerId = data.getString("id");
					boolean exist = false;
					for(int i=0;i<environment.getEnemyPlayers().size();i++){
						if(environment.getEnemyPlayers().get(i).getId().equals(playerId)){
							exist = true;
							enemy = environment.getEnemyPlayers().get(i);
							break;
						}
					}
					
					
						if(exist){
							//Player entity = enemies.getById(playerId);
							//entity.username = data.getString("username");
							/*ezzel a sorral állítom be a user
							nevét a Musclemanoknak meg satöbbiknek, nem pedig kosntruktorába(így mûködik
							amúgy pedig nem igazán akart.)*/
							
							Entity selectedEntity = null;
							for(int j=0;j<environment.getEnemyPlayers().size();j++){
								if(environment.getEnemyPlayers().get(j).getId().equals(data.getString("selectedPlayer"))){
									selectedEntity = environment.getEnemyPlayers().get(j);
									break;
								}
							}
							
							if(selectedEntity == null){
								environment.getPlayer().setSelectedEntity(environment.getPlayer());
							}
							
							
							enemy.setX(data.getDouble("x"));
							enemy.setY(data.getDouble("y"));
							enemy.setAngle(data.getDouble("angle"));
							enemy.setHealth(data.getInt("health"));
							enemy.setMaxhealth(data.getInt("maxhealth"));
							enemy.setMana(data.getInt("mana"));
							enemy.setMaxMana(data.getInt("maxmana"));
							enemy.setDead(data.getBoolean("dead"));
							if(data.getInt("skillStarted") >= 0){
								enemy.setSkillStarted(data.getInt("skillStarted"), true);
							}
							
							for(int k=0;k<enemy.getSkillCount();k++){
								if(enemy.getSkillStarted(k)){
									enemy.getSkills()[k].activateSkillByServer();
									break;
								}
							}
						}
				}catch(JSONException e){
					e.getMessage();
				}
			}
		});	
	}
}