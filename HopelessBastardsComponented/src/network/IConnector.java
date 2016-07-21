package network;

public interface IConnector {
	public void connect(String serverURL);
	public Object getLivingConnection();
}
