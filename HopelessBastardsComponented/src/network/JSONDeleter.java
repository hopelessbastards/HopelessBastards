package network;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONDeleter implements IJSONDeletor{

	@Override
	public void clearJSONArray(JSONArray array) {
		for(int i=0;i<array.length();i++){
			array.remove(i);
		}
	}

	@Override
	public void clearJSONObject(JSONObject object) {
		/*Ezt itt még pontosítani kell*/
		object.remove("id");
		object.remove("username");
		object.remove("characterType");
		object.remove("angle");
		object.remove("health");
		object.remove("mana");
		object.remove("dead");
		object.remove("maxhealth");
		object.remove("maxmana");
		object.remove("skillStarted");
		
	}
}