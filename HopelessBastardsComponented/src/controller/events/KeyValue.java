package controller.events;

public class KeyValue {
	/*Ez az objektum adódik át, amikor billentyûleütés történik.Itt két lehetõség lehet,
	 vagy valami betût üt, vagy valami whitespace karaktert(illetve escape).
	 Ha karaktert akkor a keyType null,ha entert akkor a value null.Tehát a két adattag
	 közül az egyik értéke mindig null.*/
	
	private Character value;
	private KeyValueConstant keyType;
	
	public KeyValue() {
	}
	
	public KeyValue(Character value) {
		super();
		this.value = value;
	}
	
	public KeyValue(KeyValueConstant value) {
		this.keyType = value;
	}
	
	
	
	public KeyValueConstant getKeyType() {
		return keyType;
	}

	public void setKeyType(KeyValueConstant keyType) {
		this.keyType = keyType;
	}

	public Character getValue() {
		return value;
	}
	public void setValue(Character c) {
		this.value = c;
	}
}
