package network;

import io.socket.client.IO;
import io.socket.client.Socket;

public class Connector implements IConnector{
	private Socket socket;
	
	public Connector() {
		
	}

	@Override
	public void connect(String serverURL) {
		try{
			socket = IO.socket(serverURL);
			// = IO.socket("http://localhost:8080");
			socket.connect();
		}catch(Exception e){
			e.getMessage();
		}
	}

	@Override
	public Object getLivingConnection() {
		return this.socket;
	}	
}