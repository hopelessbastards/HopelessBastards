package graphicsEngine;

public class ImageDescriptor {
	/*Képleíró objektum.Lényege, hogy ez alapján elõállítható Animation objektum.Az xml docból
	 ezt állít elõ a SaxParserem.*/
	
	private String path;/*kép filerendszerbeli helye*/
	private int column;/*Ha spriteról van szó, ami a nagyképen egy kicsi, akkor ez az, hogy hanyadik
	elem a sorban*/
	private int row;/*Ha spriteról van szó, ami a nagyképen egy kicsi, akkor ez az, hogy hanyadik sorban 
	kell keresni*/
	private int sheetwidth;/*sheet szélessége*/
	private int sheetheight;/*sheet magassága*/
	private String logicName;/*A kép logikai hivatkozási neve, az animációnak is ez a hivatkozási neve.*/
	private int animation;/*Egy sorszám, ami azt mutatja, hogy ez a kép hanyadik elem az animációban.
	Az indexelés nullával kezdõdik.*/
	private boolean isSprite;/*Egy érték, ami azt mutatja, hogy ez a kép egy önálló kép, vagy egy 
	Sprite, ami egy nagyobb kép része.*/
	
	
	/*Két konstruktort definiáltam ehhez az osztályhoz, mivel van sima teljes kép, és Sprite, és 
	 így autómatikusan kitöltöm az értékeket a különbözõ esetben.*/
	public ImageDescriptor(String path, int column, int row, int sheetwidth,
			int sheetheight,String logicName,int animation, boolean isSprite) {
		
		/*Ha a kép Sprite, akkor ezzel a konstruktorral kell létrehozni ezt a leíró objektumot, mivel minden
		 adattagnak értéket kell kapnia.*/
		this.isSprite = isSprite;
		this.path = path;
		this.column = column;
		this.row = row;
		this.sheetwidth = sheetwidth;
		this.sheetheight = sheetheight;
		this.animation = animation;
		this.logicName = logicName;
	}
	public ImageDescriptor(String path,String logicName,int animation,boolean isSprite) {
		/*Ha egy képrõl van szó, akkor azt nem kell vágdosni, és a Spritera vonatkozó tételeket ez
		 autómatikusan kitölti.*/
		this.isSprite = isSprite;
		this.path = path;
		this.column = -1;
		this.row = -1;
		this.sheetwidth = -1;
		this.sheetheight = -1;
		this.animation = animation;
		this.logicName = logicName;
	}
	/*Alul getterek és setterek találhatóak.*/
	public int getAnimation() {
		return animation;
	}
	public void setAnimation(int animation) {
		this.animation = animation;
	}
	public boolean isSprite() {
		return isSprite;
	}
	public void setSprite(boolean isSprite) {
		this.isSprite = isSprite;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getColumn() {
		return column;
	}
	public int getRow() {
		return row;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getSheetwidth() {
		return sheetwidth;
	}
	public void setSheetwidth(int sheetwidth) {
		this.sheetwidth = sheetwidth;
	}
	public int getSheetheight() {
		return sheetheight;
	}
	public void setSheetheight(int sheetheight) {
		this.sheetheight = sheetheight;
	}
	public String getLogicName() {
		return logicName;
	}
	public void setLogicName(String logicName) {
		this.logicName = logicName;
	}
	/*Test miatt hagytam benne.*/
	@Override
	public String toString() {
		return "Path:" + this.path  + " sheetx:" +this.column + " sheety:" + this.row +
				 " sheetwidth:" + this.sheetwidth + " sheetheight:" + this.sheetheight + 
				 " logicname:" + this.logicName + " animation:" + this.animation + 
				 " sprite:" + this.isSprite;
	}
}