package soundapi;

public interface ISound {
	/*A sound interface, melyet az MP3 és  Wav valósít meg.*/
	public void play();
	public void stop();
	public void close();
	public void setLogicName(String logicName);
}