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
		/*az els� a kapcsol�d�si esem�nyre reag�l*/
		this.socket.on("socketID", new Emitter.Listener() {
			
			@Override
			public void call(Object... arg0) {
				JSONObject data = (JSONObject)arg0[0];
				try{
					System.out.println("socketid");
					String id = data.getString("id");
					
					/*Amikor l�trehozzuk a kapcsolatot a szerverrel, akkor az ad nek�nk egy egy�ni azonos�t�t,
					 �s kiv�ltja ezta socketid esem�nyt, amire ez a met�dus h�v�dik meg.Mivel regisztr�ljuk magunkat
					 a playerek k�z� j� lenne eljutttatni a szervernek, hogy mi a jelenlegi �jj j�t�kos
					 karakter�nek t�pusa.Ezt itt m�r tudjuk a dui var�zsl�ban kiv�lasztja a player
					 �s kiv�ltunk egy charactertype esem�nyt a szerver fel�, ahol elk�ldj�k neki
					 a karakter t�pus�t, �s �gy � ezt tov�bb�tani tudja minden m�s playernek mint 
					 newPlayer esem�ny  egy id �s egy karaktert�pus el�g ahhoz, hogy
					 l�trehozhassa minden m�s karakter ezt az �j j�t�kost ellenf�lk�nt
					 a saj�t kliens�ben.*/
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
					
					/*Ha �j j�t�kos csatlakozik, elk�ldi mindenkinek az id-j�t �s a karakter t�pus�t
					 ezek szerint l�tre tudjua minden enemy hozni �t a saj�t lok�lis ter�ben.*/
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
					/*id alapj�n t�r�lj�k a list�b�l(nincs k�sz)*/
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
						
						/*Minden player karakter�t �gy hozzuk l�tre, hogy a jsonb�l kiszedj�k,
						 hogy mi a karakter�nek t�pusa, �s aszerint hozzuk l�tre.*/
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
							entity.username = data.getString("username");/*ezzel a sorral �ll�tom be a user
							nev�t a Musclemanoknak meg sat�bbiknek, nem pedig kosntruktor�ba(�gy m�k�dik
							am�gy pedig nem igaz�n akart.)*/
							
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