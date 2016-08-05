package network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import applogic.elements.Entity;
import applogic.elements.EntityPositionEstimate;
import applogic.elements.controllers.IEnvironment;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import math.RotatePoints;

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
		}).on("nodeTick", new Emitter.Listener() {
			
			@Override
			public void call(Object... arg0) {
				JSONArray objects = (JSONArray)arg0[0];

				//System.out.println("network update: " + time.getDouble("tickTime"));
				try{
					Entity enemy = null;
					
					for(int i=0;i<objects.length();i++){
						String id = objects.getJSONObject(i).getString("id");
						boolean updated = objects.getJSONObject(i).getBoolean("livingconnection");
						enemy = null;
						
						/*Az updated azt jelenti, hogy frissítve volt szerveroldalon az entitás,
						 azaz új információ van róla.*/
						if(updated){
							for(int j=0;j<environment.getEnemyPlayers().size();j++){
								if(environment.getEnemyPlayers().get(j).getId().equals(id)){
									enemy = environment.getEnemyPlayers().get(j);
									
									if(enemy != null){
										//Player entity = enemies.getById(playerId);
										//entity.username = data.getString("username");
										/*ezzel a sorral állítom be a user
										nevét a Musclemanoknak meg satöbbiknek, nem pedig kosntruktorába(így mûködik
										amúgy pedig nem igazán akart.)*/
										
										Entity selectedEntity = null;
										
										
										
										for(int k=0;k<environment.getEnemyPlayers().size();k++){
											if(environment.getEnemyPlayers().get(k).getId().equals(objects.getJSONObject(i).getString("selectedPlayer"))){
												selectedEntity = environment.getEnemyPlayers().get(k);
												break;
											}
										}
										
										for(int k=0; k < environment.getFriendlyPlayers().size();k++){
											if(environment.getFriendlyPlayers().get(k).getId().equals(objects.getJSONObject(i).getString("selectedPlayer"))){
												selectedEntity = environment.getFriendlyPlayers().get(k);
												break;
											}
										}
										
										enemy.setSelectedEntity(selectedEntity);
										
										EntityPositionEstimate estimatePosition = enemy.getPositionEstimate();
										double now = (double)System.nanoTime() / 1000000000.0;
										if(now - estimatePosition.getTickTime() > 0.05){
											
											estimatePosition.setTickTime(now);
											
											estimatePosition.setLastlastTick(estimatePosition.getLastTick());
											estimatePosition.setLastTick(objects.getJSONObject(i).getDouble("lastTickTime"));
											
											estimatePosition.setOldx2(estimatePosition.getOldx1());
											estimatePosition.setOldx1(objects.getJSONObject(i).getDouble("x"));
		
											estimatePosition.setOldy2(estimatePosition.getOldy1());
											estimatePosition.setOldy1(objects.getJSONObject(i).getDouble("y"));
										
											estimatePosition.setOldangle2(estimatePosition.getOldangle1());
											estimatePosition.setOldangle1(objects.getJSONObject(i).getDouble("angle"));
											
											if(estimatePosition.getOldangle1() - estimatePosition.getOldangle2() == 0){
												estimatePosition.setInteranglespeed(RotatePoints.twoAngleDistance(estimatePosition.getInterangle(), estimatePosition.getOldangle1()));
											}else{	
												estimatePosition.setInteranglespeed(RotatePoints.twoAngleDistance(estimatePosition.getOldangle2(), estimatePosition.getOldangle1()));									
											}
											
											if(estimatePosition.getOldx1() - estimatePosition.getOldx2() == 0){
												estimatePosition.setInterspeedx(estimatePosition.getOldx1() - estimatePosition.getInterx());
											}else{
												estimatePosition.setInterspeedx(estimatePosition.getOldx1() - estimatePosition.getOldx2());
											}
											
											if(estimatePosition.getOldy1() - estimatePosition.getOldy2() == 0){
												estimatePosition.setInterspeedy(estimatePosition.getOldy1() - estimatePosition.getIntery());
											}else{
												estimatePosition.setInterspeedy(estimatePosition.getOldy1() - estimatePosition.getOldy2());
											}
											
											enemy.setX(objects.getJSONObject(i).getDouble("x"));
											enemy.setY(objects.getJSONObject(i).getDouble("y"));
											enemy.setAngle(objects.getJSONObject(i).getDouble("angle"));
										}
										
										
										enemy.setNetworkHealth(objects.getJSONObject(i).getInt("health"));
										enemy.setMaxhealth(objects.getJSONObject(i).getInt("maxhealth"));
										enemy.setMana(objects.getJSONObject(i).getInt("mana"));
										enemy.setMaxMana(objects.getJSONObject(i).getInt("maxmana"));
										enemy.setDead(objects.getJSONObject(i).getBoolean("dead"));
										
										if(objects.getJSONObject(i).getInt("skillStarted") >= 0){
											enemy.setSkillStarted(objects.getJSONObject(i).getInt("skillStarted"), true);
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
									break;
								}
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