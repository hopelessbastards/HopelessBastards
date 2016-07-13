package screenconverter;

public class Size {
	/*Saját méret osztály, lényege hogy egységbe foglalja a szélesség és a magasság tulajdonságokat.*/
	private int width;
	private int height;
	
	public Size() {
		/*Paraméter nélküli konstruktor.*/
	}
	
	public Size(int width,int height){
		this.width = width;
		this.height = height;
	}

	public int getWIDTH() {
		return this.width;
	}

	public void setWIDTH(int width) {
		this.width = width;
	}

	public int getHEIGHT() {
		return this.height;
	}

	public void setHEIGHT(int height) {
		this.height = height;
	}	
}