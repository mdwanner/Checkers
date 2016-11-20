package boardGame;

public class GamePiece {
	
	private String color = "";
	private int[] position = new int[2];
	
	public GamePiece(String color, int x, int y) {
		this.color += color;
		position[0] = x;
		position[1] = y;
	}
	
	public String getColor() {
		return color;
	}
	
	public int[] getPosition() {
		return position;
	}
	
	public void setPosition(int x, int y) {
		position[0] = x;
		position[1] = y;
	}
	
	public String toString() {
		if (color.equals("Red")) {
			return "|r";
		}
		else {
			return "|b";
		}
	}
}