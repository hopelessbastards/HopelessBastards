package network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.Messaging.SyncScopeHelper;

import applogic.GameLoop;
import applogic.elements.Entity;
import applogic.elements.EntityPositionEstimate;
import applogic.elements.controllers.IEnvironment;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class NetworkSetup implements INetworkSetup{

	private Socket socket;
	private IEnvironment environment;
	private EntityPositionEstimate estimatePosition;
	
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
					
					environment.makePlayer(2600, 3048, id, environment.getSelectedCharacterType().toString());
						
					ob.put("characterType",environment.getSelectedCharacterType().toString());	
					socket.emit("characterType",ob);
				
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
					
					
					environment.makeEnemyPlayer(3400, 3048, playerId, characterType);
					
					
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
							environment.getEnemyPlayers().get(i).setDeletable(true);
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
					
					for(int i=0;i<environment.getEnemyPlayers().size();i++){
						if(environment.getEnemyPlayers().get(i).getId().equals(playerId)){
							enemy = environment.getEnemyPlayers().get(i);
							break;
						}
					}
					
					
						if(enemy != null){
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
							
							NetworkSetup.this.estimatePosition = enemy.getPositionEstimate();
							
							
							estimatePosition.setLastlastTick(estimatePosition.getLastTick());
							estimatePosition.setLastTick((double)System.nanoTime() / 1000000000.0);
							
							/*enemy.oldx2 = enemy.oldx1;
							enemy.oldx1 = data.getDouble("x");*/
							estimatePosition.setOldx2(estimatePosition.getOldx1());
							estimatePosition.setOldx1(data.getDouble("x"));
							
							
							/*enemy.oldy2 = enemy.oldy1;
							enemy.oldy1 = data.getDouble("y");*/
							estimatePosition.setOldy2(estimatePosition.getOldy1());
							estimatePosition.setOldy1(data.getDouble("y"));
							
							/*enemy.oldangle2 = enemy.oldangle1;
							enemy.oldangle1 = data.getDouble("angle");*/
							estimatePosition.setOldangle2(estimatePosition.getOldangle1());
							estimatePosition.setOldangle1(data.getDouble("angle"));
							
							
							/*if(enemy.interangle < data.getDouble("angle")){
								enemy.interanglespeed = enemy.oldangle1 - enemy.oldangle2 + Math.abs(enemy.interangle - data.getDouble("angle"));
							}else if(enemy.interangle > data.getDouble("angle")){
								enemy.interanglespeed = enemy.oldangle1 - enemy.oldangle2 - Math.abs(enemy.interangle - data.getDouble("angle"));
							}else{
								enemy.interanglespeed = enemy.oldangle1 - enemy.oldangle2;
							}*/
							
							if(estimatePosition.getInterangle() < data.getDouble("angle")){
								estimatePosition.setInteranglespeed(estimatePosition.getOldangle1() - estimatePosition.getOldangle2() + Math.abs(estimatePosition.getInterangle() - data.getDouble("angle")));
							}else if(estimatePosition.getInterangle() > data.getDouble("angle")){
								estimatePosition.setInteranglespeed(estimatePosition.getOldangle1() - estimatePosition.getOldangle2() - Math.abs(estimatePosition.getInterangle() - data.getDouble("angle")));
							}else{
								estimatePosition.setInteranglespeed(estimatePosition.getOldangle1() - estimatePosition.getOldangle2());
							}
							
							
							/*if(enemy.interx < data.getDouble("x")){
								enemy.interspeedx = enemy.oldx1 - enemy.oldx2 + Math.abs(enemy.interx - data.getDouble("x"));
							}else if(enemy.interx > enemy.getX()){
								enemy.interspeedx = enemy.oldx1 - enemy.oldx2 - Math.abs(enemy.interx - data.getDouble("x"));
							}else{
								enemy.interspeedx = enemy.oldx1 - enemy.oldx2;
							}*/
							
							if(estimatePosition.getInterx() < data.getDouble("x")){
								estimatePosition.setInterspeedx(estimatePosition.getOldx1() - estimatePosition.getOldx2() + Math.abs(estimatePosition.getInterx() - data.getDouble("x")));
							}else if(estimatePosition.getInterx() > data.getDouble("x")){
								estimatePosition.setInterspeedx(estimatePosition.getOldx1() - estimatePosition.getOldx2() - Math.abs(estimatePosition.getInterx() - data.getDouble("x")));
							}else{
								estimatePosition.setInterspeedx(estimatePosition.getOldx1() - estimatePosition.getOldx2());
							}
						
							
							/*if(enemy.intery < data.getDouble("y")){
								enemy.interspeedy = enemy.oldy1 - enemy.oldy2 + Math.abs(enemy.intery - data.getDouble("y"));
							}else if(enemy.intery > enemy.getY()){
								enemy.interspeedy = enemy.oldy1 - enemy.oldy2 - Math.abs(enemy.intery - data.getDouble("y"));
							}else{
								enemy.interspeedy = enemy.oldy1 - enemy.oldy2;
							}*/
							
							if(estimatePosition.getIntery() < data.getDouble("y")){
								estimatePosition.setInterspeedy(estimatePosition.getOldy1() - estimatePosition.getOldy2() + Math.abs(estimatePosition.getIntery() - data.getDouble("y")));
							}else if(estimatePosition.getIntery() > data.getDouble("y")){
								estimatePosition.setInterspeedy(estimatePosition.getOldy1() - estimatePosition.getOldy2() - Math.abs(estimatePosition.getIntery() - data.getDouble("y")));
							}else{
								estimatePosition.setInterspeedy(estimatePosition.getOldy1() - estimatePosition.getOldy2());
							}	
					
							
							
							
							enemy.setX(data.getDouble("x"));
							enemy.setY(data.getDouble("y"));
							enemy.setAngle(data.getDouble("angle"));
							enemy.setNetworkHealth(data.getInt("health"));
							enemy.setMaxhealth(data.getInt("maxhealth"));
							enemy.setMana(data.getInt("mana"));
							enemy.setMaxMana(data.getInt("maxmana"));
							enemy.setDead(data.getBoolean("dead"));
							if(data.getInt("skillStarted") >= 0){
								enemy.setSkillStarted(data.getInt("skillStarted"), true);
							}
							
							for(int k=0;k<enemy.getSkillCount();k++){
								
								if(enemy.getSkillStarted(k)){
									enemy.getSkills()[k].activateSkillByServer(enemy.getAppTime());
									enemy.setSkillStarted(k, false);
									enemy.getSkills()[k].setNetworkActivate(false);
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