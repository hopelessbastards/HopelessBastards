package applogic.elements;

public enum TileType {
	GRASS(0),WALLCORNER(1),WALL1(2),WALL2(3),TREE(4),TRUNK(5),WATER(6);
	
	private int number;
	
	private TileType(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	
	@Override
	public String toString() {
		if(this == GRASS){
			return "grass";
		}else if(this == TREE){
			return "tree";
		}else if(this == TRUNK){
			return "trunk";
		}else if(this == WALLCORNER){
			return "wallcorner";
		}else if(this == WALL1){
			return "wall1";
		}else if(this == WALL2){
			return "wall2";
		}else if(this == WATER){
			return "water";
		}		
		return null;
	}
}