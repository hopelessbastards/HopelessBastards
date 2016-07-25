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
	}
}