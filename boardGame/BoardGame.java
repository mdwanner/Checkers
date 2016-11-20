package boardGame;
import java.util.Scanner;

public class BoardGame {
	
	private GamePiece[][] board = new GamePiece[8][8];
	private int totalRed = 12;
	private int totalBlack = 12;
	public boolean isOpponentsTurn;
	private GamePiece[] yourPieces = new GamePiece[12];
	private GamePiece[] opponentsPieces = new GamePiece[12];
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		BoardGame checkers = new BoardGame();
		boolean forfeit = false;
		int[] coordinates = new int[2];
		int direction;

		while (checkers.totalRed != 0 ^ checkers.totalBlack != 0 ^ !forfeit) {
			checkers.isOpponentsTurn = false;
			checkers.print();
			System.out.println("You have " + checkers.totalRed + " pieces\nYour opponent has " + checkers.totalBlack + " pieces\n");
			System.out.print("What piece would you want to move? (Enter '-1 -1' to forfeit) :::: ");
			coordinates[0] = s.nextInt();
			coordinates[1] = s.nextInt();
			if (coordinates[0] == -1 && coordinates[1] == -1) {
				forfeit = true;
				System.out.println("You have forfeited the game!");
			}
			else if (coordinates[0] < 0 || coordinates[0] > 7 || coordinates[1] < 0 || coordinates[1] > 7) {
				while (coordinates[0] < 0 || coordinates[0] > 7 || coordinates[1] < 0 || coordinates[1] > 7) {
					System.out.print("What piece would you want to move? (Enter '-1 -1' to forfeit) :::: ");
					coordinates[0] = s.nextInt();
					coordinates[1] = s.nextInt();
					if (coordinates[0] == -1 && coordinates[1] == -1) {
						forfeit = true;
						System.out.println("You have forfeited the game!");
						break;
					}
				}
			}
			else {
				if (checkers.board[coordinates[0]][coordinates[1]] != null
						&& checkers.board[coordinates[0]][coordinates[1]].getColor().equals("Red")) {
					System.out.print("\n(1) forward right\n(2) forward left\n");
					if (checkers.board[coordinates[0]][coordinates[1]] instanceof King) {
						System.out.print("(3) backward right\n(4) backward left\n");
					}
					System.out.print("\nWhere? :::: ");
					direction = s.nextInt();
					while (direction < 1 || direction > 4) {
						System.out.print("Where? :::: ");
						direction = s.nextInt();
					}
					
					switch (direction) {
					case 1:
						checkers.isOpponentsTurn = checkers.movePiece(checkers.board[coordinates[0]][coordinates[1]], "forward_right");
						break;
					case 2:
						checkers.isOpponentsTurn = checkers.movePiece(checkers.board[coordinates[0]][coordinates[1]], "forward_left");
						break;
					case 3:
						checkers.isOpponentsTurn = checkers.movePiece(checkers.board[coordinates[0]][coordinates[1]], "backward_right");
						break;
					case 4:
						checkers.isOpponentsTurn = checkers.movePiece(checkers.board[coordinates[0]][coordinates[1]], "backward_left");
						break;
					}
					
					if (checkers.isOpponentsTurn) {
						checkers.isOpponentsTurn = false;
						checkers.opponentsTurn();
					}
					else {
						continue;
					}
				}
				if (!checkers.isOpponentsTurn) {
					System.out.println("\nThere is nothing here, try another spot\n");
				}
			}
		}
		s.close();
	}

	public BoardGame() {
		// Red game pieces are populated onto the board, the player
		int total = 0;
		for (int i=0; i<8; i++) {
			if (i % 2 != 0) {
				board[6][i] = new GamePiece("Red", 6, i);
				yourPieces[total++] = board[6][i];
			}
			else {
				board[5][i] = new GamePiece("Red", 5, i);
				board[7][i] = new GamePiece("Red", 7, i);
				yourPieces[total++] = board[5][i];
				yourPieces[total++] = board[7][i];
			}
		}
		
		// Black game pieces are populated onto the board, the computer-controlled opponent
		total = 0;
		for (int i=0; i<8; i++) {
			if (i % 2 != 0) {
				board[0][i] = new GamePiece("Black", 0, i);
				board[2][i] = new GamePiece("Black", 2, i);
				opponentsPieces[total++] = board[0][i];
				opponentsPieces[total++] = board[2][i];
			}
			else {
				board[1][i] = new GamePiece("Black", 1, i);
				opponentsPieces[total++] = board[1][i];
			}
		}
		System.out.println("\nWelcome to Checkers! You are the red pieces. Pawns are\n"
				+ "represented as lowercase letters, and kings as uppercase letters.\n"
				+ "To pick a piece to move, enter two integers separated by a space\n"
				+ "according to the coordinates marked on the board (row column).\nGood luck!\n");
	}
	
	public void opponentsTurn() {
		boolean hasCompletedMove = false;
		int choice;
		while (!hasCompletedMove) {
			choice = (int)(Math.random() * 12);
			switch ((int)(Math.random() * 4)) {
			case 0:
				hasCompletedMove = movePiece(opponentsPieces[choice], "forward_right");
				break;
			case 1:
				hasCompletedMove = movePiece(opponentsPieces[choice], "forward_left");
				break;
			case 2:
				hasCompletedMove = movePiece(opponentsPieces[choice], "backward_right");
				break;
			case 3:
				hasCompletedMove = movePiece(opponentsPieces[choice], "backward_left");
				break;
			}
		}
		System.out.println("\nYour opponent has made a move");
	}
	
	public boolean movePiece(GamePiece piece, String dir) {
		int changeX;
		int changeY;
		if (dir.equals("forward_right")) {
			if (piece.getColor().equals("Red")) {
				changeX = -1;
				changeY = 1;
			}
			else {
				changeX = 1;
				changeY = -1;
			}
		}
		else if (dir.equals("forward_left")) {
			if (piece.getColor().equals("Red")) {
				changeX = -1;
				changeY = -1;
			}
			else {
				changeX = 1;
				changeY = 1;
			}
		}
		else if (dir.equals("backward_right") && piece instanceof King) {
			if (piece.getColor().equals("Red")) {
				changeX = 1;
				changeY = 1;
			}
			else {
				changeX = -1;
				changeY = -1;
			}
		}
		else if (dir.equals("backward_left") && piece instanceof King) {
			if (piece.getColor().equals("Red")) {
				changeX = 1;
				changeY = -1;
			}
			else {
				changeX = -1;
				changeY = 1;
			}
		}
		else {
			if (!isOpponentsTurn) {
				System.out.println("Invalid move, try again\n");
			}
			return false;
		}
		
		if (piece.getPosition()[0] + changeX >= 8 || piece.getPosition()[0] + changeX < 0
				|| piece.getPosition()[1] + changeY >= 8 || piece.getPosition()[1] + changeY < 0) {
			if (!isOpponentsTurn) {
				System.out.println("\nInvalid move, try again\n");
			}
			return false;
		}
		else if (board[piece.getPosition()[0] + changeX][piece.getPosition()[1] + changeY] != null) {
			if (board[piece.getPosition()[0] + changeX][piece.getPosition()[1] + changeY].getColor().equals("Red")) {
				if (!isOpponentsTurn) {
					System.out.println("\nInvalid move, try again\n");
				}
				return false;
			}
			else if (board[piece.getPosition()[0] + changeX][piece.getPosition()[1] + changeY].getColor().equals("Black")) {
				if (piece.getPosition()[0] + changeX >= 8 || piece.getPosition()[0] + changeX < 0
						|| piece.getPosition()[1] + changeY >= 8 || piece.getPosition()[1] + changeY < 0) {
					if (!isOpponentsTurn) {
						System.out.println("\nInvalid move, try again\n");
					}
					return false;
				}
				else if (board[piece.getPosition()[0] + 2 * changeX][piece.getPosition()[1] + 2 * changeY] == null) {
					board[piece.getPosition()[0] + 2 * changeX][piece.getPosition()[1] + 2 * changeY] = board[piece.getPosition()[0]][piece.getPosition()[1]];
					board[piece.getPosition()[0]][piece.getPosition()[1]] = null;
					board[piece.getPosition()[0] + changeX][piece.getPosition()[1] + changeY] = null;
					board[piece.getPosition()[0] + 2 * changeX][piece.getPosition()[1] + 2 * changeY].setPosition(piece.getPosition()[0] + 2 * changeX, piece.getPosition()[1] + 2 * changeY);

					if (!isOpponentsTurn) {
						System.out.println("You have taken an opponent's piece");
					}
					else {
						System.out.println("Your opponent has taken one of your pieces");
					}
					totalBlack--;
					int row = piece.getPosition()[0] + 2 * changeX;
					int col = piece.getPosition()[1] + 2 * changeY;
					if (board[piece.getPosition()[0] + 2 * changeX][piece.getPosition()[1] + 2 * changeY].getPosition()[0] == 0) {
						board[piece.getPosition()[0] + 2 * changeX][piece.getPosition()[1] + 2 * changeY] = new King(board[row][col].getColor(), row, col);
						if (!isOpponentsTurn) {
							System.out.println("You have attained a king");
						}
						else {
							System.out.println("Your opponent has attained a king");
						}
					}
					return true;
				}
				else {
					if (!isOpponentsTurn) {
						System.out.println("\nInvalid move, try again\n");
					}
					return false;
				}
			}
			else {
				if (!isOpponentsTurn) {
					System.out.println("\nInvalid move, try again\n");
				}
				return false;
			}
		}
		else {
			board[piece.getPosition()[0] + changeX][piece.getPosition()[1] + changeY] = board[piece.getPosition()[0]][piece.getPosition()[1]];
			board[piece.getPosition()[0]][piece.getPosition()[1]] = null;
			board[piece.getPosition()[0] + changeX][piece.getPosition()[1] + changeY].setPosition(piece.getPosition()[0] + changeX, piece.getPosition()[1] + changeY);
			return true;
		}
	}
	
	public void print() {
		String result = "\t     0 1 2 3 4 5 6 7\n\n\t";
		for (int i=0; i<8; i++) {
			result += i + "   ";
			for (int j=0; j<8; j++) {
				if (board[i][j] == null) {
					result += "| ";
				}
				else {
					result += board[i][j];
				}
			}
			result += "|\n\t";
		}
		System.out.println(result + "\n");
	}
}
