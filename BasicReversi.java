package JanuarProject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BasicReversi extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	///////////////////////// Banen kan være mellem 6 og 12 i gridsize :D

	// Global Instance variables her:
	static int gameCounter = 0;
	static int availableMoves = 0;
	static int fullBoard;
	static int gridSize = 8;
	static int windowSize = 600;
	static int clickCount = 1;
	static int butSize = windowSize / gridSize;
	static int numberOfWhite;
	static int numberOfBlack;
	static int turn;
	static int blackValue;
	static int whiteValue;

	static Circle whitePiece;
	static Circle blackPiece;
	static String playerTurn;
	static MyButton2 clickedButton;
	static MyButton2[][] buttons2D = new MyButton2[gridSize][gridSize];
	static Button pass = new Button("Pass");
	static Button restart = new Button("Restart game");
	static Label showTurn = new Label();
	static GridPane root = new GridPane();
	static Scene scene = new Scene(root, windowSize + 150, windowSize);

	// Class Constructor
	public BasicReversi() {

		// Creates the first column of the menu on the right
		for (int i = 0; i < gridSize; i++) {
			if (i == 1) {

				// Display whose turn it is
				showTurn.setPrefSize(windowSize / 4, butSize);
				showTurn.setFont(Font.font("Verdana", 15));
				showTurn.setText(" Next Turn: White");
				showTurn.setStyle("-fx-background-color: white;"
						+ " -fx-border-style: solid;"
						+ " -fx-border-width: 2;"
						+ " -fx-border-color: black;"
						+ "-fx-border-radius: 4;"
						+ "-fx-background-insets: 2;");
				root.add(showTurn, gridSize, i);
			}
			if (i == gridSize - 2) {

				// Pass button
				pass.setPrefSize((windowSize / 4) - (gridSize * 2.5), butSize - (gridSize * 2.5)); // Size of the button
				pass.setStyle("-fx-base: white;"); // Button color
				pass.setStyle("-fx-background-radius: 50"); // Gives button smooth edges
				pass.setDisable(true);
				root.add(pass, gridSize, i);
				GridPane.setConstraints(pass, gridSize, i, 1, 1, HPos.CENTER, VPos.CENTER);
			} else if (i == gridSize - 1) {

				// Restart button
				restart.setPrefSize((windowSize / 4) - (gridSize * 2.5), butSize - (gridSize * 2.5)); // Size of the //
				restart.setStyle("-fx-base: white;"); // Button color
				restart.setStyle("-fx-background-radius: 50"); // Gives button smooth edges
				root.add(restart, gridSize, i);
				GridPane.setConstraints(restart, gridSize, i, 1, 1, HPos.CENTER, VPos.CENTER);
			}
		}

		// Construction the 8x8 Grid with 64 buttons
		for (int row = 0; row < gridSize; row++) {
			for (int column = 0; column < gridSize; column++) {
				MyButton2 MyButton2 = new MyButton2(0);
				MyButton2.setPrefSize(butSize, butSize); // Size of one cell

				// Background
				if (column % 2 == 0 && row % 2 == 0) {
					MyButton2.setStyle("-fx-base: #8B4513");
				} else {
					MyButton2.setStyle("-fx-base: #D2B48C;");
				}
				if (column % 2 != 0 && row % 2 != 0) {
					MyButton2.setStyle("-fx-base: #8B4513");
				}

				root.add(MyButton2, row, column);
				buttons2D[row][column] = MyButton2; // Add coordinates and accessibility to all buttons.
			}
		}

		//////////////////////////////////// On-click
		//////////////////////////////////// function///////////////////////////////////////////

		// Adds all the functionality for when a button is clicked
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				buttons2D[x][y].setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						clickedButton = (MyButton2) event.getSource();
						int x = GridPane.getColumnIndex(clickedButton);
						int y = GridPane.getRowIndex(clickedButton);

						// Constructs the default 4 pieces in the center of Board
						if (clickCount < 7 + gameCounter%2) {
							startFour(clickedButton);
							turnDisplay();

							// Checks if the clicked move is legal/illegal
						} else if (legal(buttons2D, x, y) == true) {
							placePiece(buttons2D[x][y]);
							capturePiece(buttons2D, x, y); // Check if there is a capture
							clickCount++;
							highlight();
							turnDisplay();

						} // End if-else

						// FOR DATA TESTING PURPOSE
						System.out.println("----------------------");
						System.out.println("clickcount: " + clickCount);
						System.out.println("clickposition: (" + x + ", " + y + ")");
						System.out.println("Player info: " + playerTurn);
						System.out.println("value " + buttons2D[x][y].getMyValue());
						System.out.println("turn: " + turn);
						System.out.println("legal: " + legal(buttons2D, x, y));
						System.out.println("black score: " + numberOfBlack);
						System.out.println("white score: " + numberOfWhite);
						System.out.println("");

					}
				});
			}
		} // end for loop

		// Pass button
		pass.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// Skip the current player's turn
				if (turn == -1) {
					clickCount++;
					playerTurn = "white";
					highlight();
				} else {
					playerTurn = "black";
					clickCount++;
					highlight();
				}
			}
		});

		// Restart button
		restart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				restartGame(buttons2D);
				turnDisplay();
			}
		});
	}

	// Stage setup
	@Override
	public void start(Stage primaryStage) {

		// Constructs pane
		root.setStyle("-fx-background-color: #33CC66;"); // Sets background color: Green

		primaryStage.setScene(scene);
		primaryStage.setTitle("Reversi");
		primaryStage.show();

	} // End stage

	// Display whose turn it is:
	public static void turnDisplay() {
		if (clickCount % 2 != 0) {
			showTurn.setText(" Next Turn: White");
		} else {
			showTurn.setText(" Next Turn: Black");
		}
	}

	////////////////////////////// Action1: Places a piece on the board
	////////////////////////////// ////////////////////////////////////////////////////////
	public static void placePiece(MyButton2 clickedButton) {

		// Design of the WHITE piece
		whitePiece = new Circle(butSize / 3 - gridSize / 7.5);
		whitePiece.setFill(Color.WHITE);
		whitePiece.setStroke(Color.BLACK);

		// Design of the BLACK piece
		blackPiece = new Circle(butSize / 3 - (gridSize / 6) + 1);
		blackPiece.setStroke(Color.BLACK);

		// White turn:
		if (clickCount % 2 != 0) {
			clickedButton.setGraphic(whitePiece); // places White piece on-board
			clickedButton.setMyValue(1);
			playerTurn = "white";

			// Blacks turn:
		} else {
			clickedButton.setGraphic(blackPiece); // places Black piece on-board
			clickedButton.setMyValue(-1);
			playerTurn = "black";
		}

	}

	// Checks if every move is "Legal" else "Illegal" according to "Rules of
	// Reversi"
	public static boolean legal(MyButton2[][] cell, int x, int y) {

		if (cell[x][y].getMyValue() == 0) {
			return surrounded(cell, x, y);
		} else {
			return false;
		}
	}

	// Checks if there are enemy pieces around the placed-Piece
	public static boolean surrounded(MyButton2[][] cell, int x, int y) {

		// Coordinates E/W/S/N of the piece placed
		int right = x + 1;
		int left = x - 1;
		int down = y + 1;
		int up = y - 1;

		// Serves as a border so there wont be an OutOfBounds error
		if (clickCount % 2 != 0) {
			turn = 1;
		} else {
			turn = -1;
		}
		if (right >= gridSize) {
			right = gridSize - 1;
		}
		if (left < 0) {
			left = 0;
		}
		if (down >= gridSize) {
			down = gridSize - 1;
		}
		if (up < 0) {
			up = 0;
		}

		// The actual boolean check for if enemy piece around your piece
		if (cell[right][down].getMyValue() == turn * -1) {
			if (line(cell, right, down, x, y)) {
				return true;
			}
		}
		if (cell[right][y].getMyValue() == turn * -1) {
			if (line(cell, right, y, x, y)) {
				return true;
			}
		}
		if (cell[right][up].getMyValue() == turn * -1) {
			if (line(cell, right, up, x, y)) {
				return true;
			}
		}
		if (cell[x][down].getMyValue() == turn * -1) {
			if (line(cell, x, down, x, y)) {
				return true;
			}
		}
		if (cell[x][up].getMyValue() == turn * -1) {
			if (line(cell, x, up, x, y)) {
				return true;
			}
		}
		if (cell[left][down].getMyValue() == turn * -1) {
			if (line(cell, left, down, x, y)) {
				return true;
			}
		}
		if (cell[left][y].getMyValue() == turn * -1) {
			if (line(cell, left, y, x, y)) {
				return true;
			}
		}
		if (cell[left][up].getMyValue() == turn * -1) {
			if (line(cell, left, up, x, y)) {
				return true;
			}
		}
		return false;
	}

	// Checks the whole row, column and diagonal for enemy pieces.
	// If your own piece --> Recursive method call
	// If empty --> Break and return false.
	public static boolean line(MyButton2[][] cell, int side_x, int side_y, int old_x, int old_y) {
		int new_x = side_x + (side_x - old_x);
		int new_y = side_y + (side_y - old_y);

		if (new_x >= gridSize) {
			return false;
		}
		if (new_x < 0) {
			return false;
		}
		if (new_y >= gridSize) {
			return false;
		}
		if (new_y < 0) {
			return false;
		}

		if (cell[new_x][new_y].getMyValue() == turn * -1) {
			return line(cell, new_x, new_y, side_x, side_y);
		} else if (cell[new_x][new_y].getMyValue() == turn) {
			return true;
		} else {
			return false;
		}
	}

	// Starts the process of capturing a piece
	public static void capturePiece(MyButton2[][] cell, int x, int y) {

		// Coordinates E/W/S/N of the piece placed
		int right = x + 1;
		int left = x - 1;
		int down = y + 1;
		int up = y - 1;

		// Serves as a border so there wont be an OutOfBounds error
		if (clickCount % 2 != 0) {
			turn = 1;
		} else {
			turn = -1;
		}

		if (right >= gridSize) {
			right = gridSize - 1;
		}
		if (left < 0) {
			left = 0;
		}
		if (down >= gridSize) {
			down = gridSize - 1;
		}
		if (up < 0) {
			up = 0;
		}

		// The actual boolean check for if enemy piece around your piece
		// Repeats in all 8 directions
		if (cell[right][down].getMyValue() == turn * -1) {
			if (line(cell, right, down, x, y) == true) {
				changePiece(cell, right, down, x, y);
			}
		}
		if (cell[right][y].getMyValue() == turn * -1) {
			if (line(cell, right, y, x, y) == true) {
				changePiece(cell, right, y, x, y);
			}
		}
		if (cell[right][up].getMyValue() == turn * -1) {
			if (line(cell, right, up, x, y) == true) {
				changePiece(cell, right, up, x, y);
			}
		}
		if (cell[x][down].getMyValue() == turn * -1) {
			if (line(cell, x, down, x, y) == true) {
				changePiece(cell, x, down, x, y);
			}
		}
		if (cell[x][up].getMyValue() == turn * -1) {
			if (line(cell, x, up, x, y) == true) {
				changePiece(cell, x, up, x, y);
			}
		}
		if (cell[left][down].getMyValue() == turn * -1) {
			if (line(cell, left, down, x, y) == true) {
				changePiece(cell, left, down, x, y);
			}
		}
		if (cell[left][y].getMyValue() == turn * -1) {
			if (line(cell, left, y, x, y) == true) {
				changePiece(cell, left, y, x, y);
			}
		}
		if (cell[left][up].getMyValue() == turn * -1) {
			if (line(cell, left, up, x, y) == true) {
				changePiece(cell, left, up, x, y);
			}

		}
	}

	// changes the pieces in between the placed piece and a same colored piece
	public static void changePiece(MyButton2[][] cell, int side_x, int side_y, int old_x, int old_y) {
		int new_x = side_x + (side_x - old_x);
		int new_y = side_y + (side_y - old_y);

		if (new_x >= gridSize) {
			return;
		}
		if (new_x < 0) {
			return;
		}
		if (new_y >= gridSize) {
			return;
		}
		if (new_y < 0) {
			return;
		}

		// Changes piece color
		placePiece(cell[side_x][side_y]);

		// Contiunes code until it hits a same colored piece
		if (cell[new_x][new_y].getMyValue() == turn * -1) {
			changePiece(cell, new_x, new_y, side_x, side_y); // Rekursiv
		} else if (cell[new_x][new_y].getMyValue() == turn) {
			return;
		}
	}

	// Highlight function
	public static void highlight() {
		availableMoves = 0;
		fullBoard = 0;
		numberOfBlack = 0;
		numberOfWhite = 0;

		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (legal(buttons2D, i, j) == true) {
					availableMoves++;
					fullBoard++;
				} else if (buttons2D[i][j].getMyValue() == 0) {
					buttons2D[i][j].setGraphic(null);
					fullBoard++;
				} else if (buttons2D[i][j].getMyValue() == 1) {
					numberOfWhite++;
				} else if (buttons2D[i][j].getMyValue() == -1) {
					numberOfBlack++;
				}
			}
		}

		if (availableMoves == 0) {
			// Gør pass knappen mulig at bruge
			pass.setDisable(false);
		} else {
			pass.setDisable(true);
		}
		if (numberOfWhite == 0) {
			winner("BLACK", new Stage());
		}
		if (numberOfBlack == 0) {
			winner("WHITE", new Stage());
		}
		if (fullBoard == 0 && numberOfBlack > numberOfWhite) {
			winner("BLACK", new Stage());
		} else if (fullBoard == 0 && numberOfBlack < numberOfWhite) {
			winner("WHITE", new Stage());
		} else if (fullBoard == 0 && numberOfBlack == numberOfWhite) {
			winner("NOBODY", new Stage());
		}
	}

	// Constructs the default 4 pieces in the center of Board
	public static void startFour(MyButton2 clickButton2) {
		if (clickButton2.getMyValue() == 0) {
			if (clickButton2 == buttons2D[(gridSize / 2) - 1][(gridSize / 2) - 1]) {
				System.out.println("sut den");
				placePiece(clickButton2);
				clickCount++;
			} else if (clickButton2 == buttons2D[(gridSize / 2)][(gridSize / 2) - 1]) {
				System.out.println("sut den");
				placePiece(clickButton2);
				clickCount++;
			} else if (clickButton2 == buttons2D[(gridSize / 2)][(gridSize / 2)]) {
				System.out.println("sut den");
				placePiece(clickButton2);
				clickCount++;
			} else if (clickButton2 == buttons2D[(gridSize / 2) - 1][(gridSize / 2)]) {
				System.out.println("sut den");
				placePiece(clickButton2);
				clickCount++;
			}
		} 
		if (gameCounter%2==0){
			if(clickCount == 2){
				clickCount++;
			} else if(clickCount == 5){
				clickCount++;
			}
		} else {
			if(clickCount == 3){
				clickCount++;
			} else if(clickCount == 6){
				clickCount++;
			}
		}
		
	}

	// Pops up with a little window declaring the winner
	public static void winner(String winner, Stage winStage) {

		winStage.initModality(Modality.APPLICATION_MODAL);
		winStage.setTitle("Game over");
		winStage.setMinWidth(400);
		winStage.setMinHeight(200);

		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);

		Font font = new Font("Impact", 20);

		Label label = new Label("THE WINNER IS " + winner + " !!!!!");
		label.setFont(font);
		layout.getChildren().add(label);

		Button closeButton = new Button();
		closeButton.setText("Start a New Game");
		closeButton.setOnAction(e -> winStage.close());
		closeButton.setStyle("-fx-background-radius: 15");
		layout.getChildren().add(closeButton);

		closeButton.setOnAction(event -> {

			// Game over - Start new game button appears
			restartGame(buttons2D);
			winStage.close();
		});

		Scene scene = new Scene(layout);
		winStage.setScene(scene);
		winStage.show();
		layout.setStyle("-fx-background-color: #33CC66;");
	}

	// Restarts the game without closing program
	public static void restartGame(MyButton2[][] cell) {

		// Counter that makes it possible to switch which player starts when new game
		gameCounter++;

		// Reset the timer and all values
		clickCount = 1;
		blackValue = 2;
		whiteValue = 2;

		// Update GUI to reflect reset game state
		for (int row = 0; row < gridSize; row++) {
			for (int column = 0; column < gridSize; column++) {
				cell[row][column].setGraphic(null); // Add coordinates and accessibility to all buttons.
				cell[row][column].setMyValue(0);
			}
		}
		if (gameCounter % 2 != 0) {
			clickCount++;
		}
	}

} // End class

// A customized class that makes it possible to assign a INT value to each
// button
class MyButton2 extends Button {

	private int MyValue;

	public MyButton2(int MyValue) {
		this.MyValue = MyValue;
	}

	// A method to get the value of a button
	public int getMyValue() {
		return MyValue;
	}

	// A method to change the value of a button
	public void setMyValue(int MyValue) {
		this.MyValue = MyValue;
	}
}
