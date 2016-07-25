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
					
					/*Ha �j j�t�kos csatlakozik, elk�ldi mindenkinek az id-j�t �s a karakter t�pus�t
					 ezek szerint l�tre tudjua minden enemy hozni �t a saj�t lok�lis ter�ben.*/
					
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
					/*id alapj�n t�r�lj�k a list�b�l(nincs k�sz)*/
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
						
						/*Minden player karakter�t �gy hozzuk l�tre, hogy a jsonb�l kiszedj�k,
						 hogy mi a karakter�nek t�pusa, �s aszerint hozzuk l�tre.*/
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
							/*ezzel a sorral �ll�tom be a user
							nev�t a Musclemanoknak meg sat�bbiknek, nem pedig kosntruktor�ba(�gy m�k�dik
							am�gy pedig nem igaz�n akart.)*/
							
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