package boardGame;

public class King extends GamePiece {
	
	public King(String color, int x, int y) {
		super(color, x, y);
	}
	
	public String toString() {
		if (getColor().equals("Red")) {
			return "|R";
		}
		else {
			return "|B";
		}
	}
}
