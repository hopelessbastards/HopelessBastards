package applogic.elements;

public enum CharacterType {
	MAGE(0),STEVE(1),ZOMBIE(2);
	
	private int number;
	
	private CharacterType(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	
	@Override
	public String toString() {
		if(this == CharacterType.MAGE){
			return "MAGE";
		}else if(this == CharacterType.STEVE){
			return "STEVE";
		}else if(this == CharacterType.ZOMBIE){
			return "ZOMBIE";
		}
		return null;
	}
}