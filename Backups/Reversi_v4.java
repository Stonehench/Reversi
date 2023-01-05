package januarProjektet2023;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

public class Reversi extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	// Global Instance variables her:
	static int windowSize = 600;
	static int availableMoves = 0;
	static int gridSize = 8;
	static int clickCount = 0;
	static int butSize = windowSize / 8;
	static int turn;
	static int black = -1;
	static int white= 1;
	
	static Circle whitePiece = new Circle();
	static Circle blackPiece = new Circle();
	static String playerTurn;
	static MyButton clickedButton;
	static MyButton[][] buttons2D = new MyButton[8][8];

	@Override
	public void start(Stage primaryStage) {

		// Constructs pane
		GridPane root = new GridPane();
		root.setStyle("-fx-background-color: #33CC66;"); // Sets backgground color

		// Size of Window
		Scene scene = new Scene(root, windowSize + (windowSize / 4), windowSize);

		// Makes CSS and sidepane possible
		scene.getStylesheets().add(getClass().getResource("test.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Reversi");

		// Restart button
		Button restart = new Button("pass");
		restart.setPrefSize((windowSize / 4), butSize); // Size of the button
		restart.setStyle("-fx-base: white"); // Button color
		restart.setStyle("-fx-background-radius: 15"); // Gives button smooth edges
		root.add(restart, gridSize, 6);

		// Pass button
		Button pass = new Button("Restart game");
		pass.setPrefSize((windowSize / 4), butSize); // Size of the button
		pass.setStyle("-fx-base: white"); // Button color
		pass.setStyle("-fx-background-radius: 15"); // Gives button smooth edges
		root.add(pass, gridSize, 7);
		
		
		

		// Construction the 8x8 Grid with 64 buttons
		for (int row = 0; row < gridSize; row++) {
			for (int column = 0; column < gridSize; column++) {
				MyButton myButton = new MyButton(0);
				myButton.setPrefSize(butSize, butSize); // Size of one cell
				
				//Background
				if (column % 2 == 0 && row % 2 == 0) {
					myButton.setStyle("-fx-base: #8B4513");
				} else {
					myButton.setStyle("-fx-base: #D2B48C;");
				}
				if (column % 2 != 0 && row % 2 != 0) {
					myButton.setStyle("-fx-base: #8B4513");
				}

				root.add(myButton, row, column);
				buttons2D[row][column] = myButton; // Add coordinates and accessibility to all buttons.
			}

		}

		//////////////////////////////////// On-click function///////////////////////////////////////////

		// Adds all the functionality for when a button is clicked
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				buttons2D[x][y].setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						clickedButton = (MyButton) event.getSource();
						int x = GridPane.getColumnIndex(clickedButton);
						int y = GridPane.getRowIndex(clickedButton);
						
						// Checks if the clicked move is legal/illegal
						if (legal(buttons2D, x, y) == true) {
							placePiece(buttons2D[x][y]);
							capturePiece(buttons2D, x,y); // HEER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
							clickCount++;
							highlight();
							

						
						

							// SoundFX every time you make a "Legal" move.
							Media media = new Media(new File("Reversi-game-sound.wav").toURI().toString());
							MediaPlayer legalMoveSound = new MediaPlayer(media);
							legalMoveSound.setVolume(0.1);
							legalMoveSound.play();
							
						} else {
							// SoundFX every time you make a "Illegal" move.
							
							/*
							Media media = new Media(new File("emotinal-damage.mp3").toURI().toString());
							MediaPlayer IllegalMoveSound = new MediaPlayer(media);
							IllegalMoveSound.setVolume(0.1);
							IllegalMoveSound.play();
							*/
							} // End if-else
						
						// FOR DATA TESTING PURPOSE
						System.out.println("----------------------");
						System.out.println("clickcount: " + clickCount);
						System.out.println("clickposition: (" + x + ", " + y + ")");
						System.out.println("Player: " + playerTurn);
						System.out.println("value " + buttons2D[x][y].getMyValue());
						System.out.println("");
						
						
					} // End click function
				}); // End Event-handler
			} // Inner loop end
		} // Outer loop end

		
		//Constructs the default 4 pieces in the center of Board
		clickCount++;
		placePiece(buttons2D[(gridSize / 2) - 1][(gridSize / 2) - 1]);
		clickCount++;
		placePiece(buttons2D[(gridSize / 2)][(gridSize / 2) - 1]);
		clickCount++;
		placePiece(buttons2D[(gridSize / 2)][(gridSize / 2)]);
		clickCount++;
		placePiece(buttons2D[(gridSize / 2) - 1][(gridSize / 2)]);
		clickCount++;
		highlight();

		//SoundFX: Intro-music when open game
		Media media = new Media(new File("Intro-Reversi.wav").toURI().toString());
		MediaPlayer intro = new MediaPlayer(media);
		intro.setVolume(0.1);
		intro.play();
		
		

	} // End stage

	////////////////////////////// Action1: Places a piece on the board ////////////////////////////////////////////////////////
	public static void placePiece(MyButton clickedButton) {

		// Design of the WHITE piece
		whitePiece = new Circle(butSize / 3 - 1);
		whitePiece.setFill(Color.WHITE);
		whitePiece.setStroke(Color.BLACK);

		// Design of the BLACK piece
		blackPiece = new Circle(butSize / 3 - 2);
		blackPiece.setFill(Color.BLACK);

		// Players turn: White always starts, then Blacks turn. Repeat.
		if (clickCount % 2 != 0) {
			clickedButton.setGraphic(whitePiece); // places White piece on-board
			playerTurn = "white";
			clickedButton.setMyValue(1);

		} else {
			clickedButton.setGraphic(blackPiece); // places Black piece on-board
			playerTurn = "black";
			clickedButton.setMyValue(-1);
		}
	}


	//////////////////////////////Action2: Captures all pieces between placed and ally piece ////////////////////////////////////////////////////////
	
	public static void capturePiece(MyButton[][] cellPos, int clickX, int clickY) {

		//Decides who can capture who
		if (clickCount % 2 != 0) {whiteCapturesB(cellPos, clickX, clickY);} 
		else {blackCapturesW(cellPos, clickX, clickY);}
	
	} //End method 
	
					//CapturePiece (1/2) for white
					public static void whiteCapturesB(MyButton[][] cellPos, int clickX, int clickY) {
						int right = clickX+1;
						int left = clickX-1;
						int up = clickY-1;
						int down = clickY+1;
				
						//Border control
						if (right >= gridSize) {right = gridSize - 1;}
						if (left < 0) {left = 0;}
						if (down >= gridSize) {down = gridSize - 1;}
						if (up < 0) {up = 0;}
				
						//Replicate: Place a white piece
						Circle turnWhite = new Circle(butSize / 3 - 1);
						turnWhite.setFill(Color.WHITE);
						turnWhite.setStroke(Color.BLACK);
				
					
				
						//LEFT
						if (cellPos[left][clickY].getMyValue() == black) {
							cellPos[clickX][clickY].setGraphic(turnWhite); //Places a white piece
							cellPos[left][clickY].setGraphic(whitePiece); //Captures the black piece
							cellPos[left][clickY].setMyValue(1);
							highlight(); //Highlight available after-moves
						}
				
						//RIGHT
						if (cellPos[right][clickY].getMyValue() == black) {
							cellPos[clickX][clickY].setGraphic(turnWhite); //Places a white piece
							cellPos[right][clickY].setGraphic(whitePiece); //Captures the black piece
							cellPos[right][clickY].setMyValue(1);
							highlight(); //Highlight available after-moves
						}
				
						//UP
						if (cellPos[clickX][up].getMyValue() == black) {
							cellPos[clickX][clickY].setGraphic(turnWhite); //Places a white piece
							cellPos[clickX][up].setGraphic(whitePiece); //Captures the black piece
							cellPos[clickX][up].setMyValue(1);
							highlight(); //Highlight available after-moves
						}
				//kommentar her
						//DOWN
						if (cellPos[clickX][down].getMyValue() == black) {
							cellPos[clickX][clickY].setGraphic(turnWhite); //Places a white piece
							cellPos[clickX][down].setGraphic(whitePiece); //Captures the black piece
							cellPos[clickX][down].setMyValue(1);
							highlight(); //Highlight available after-moves
						}
						
						
					}
	
					//CapturePiece (2/2) for black
						public static void blackCapturesW(MyButton[][] cellPos, int clickX, int clickY) {
							int right = clickX+1;
							int left = clickX-1;
							int up = clickY-1;
							int down = clickY+1;
				
							//Border control
							if (right >= gridSize) {right = gridSize - 1;}
							if (left < 0) {left = 0;}
							if (down >= gridSize) {down = gridSize - 1;}
							if (up < 0) {up = 0;}
				
						
							//Replicate: Place a black piece
							Circle turnBlack = new Circle(butSize / 3 - 1);
							turnBlack.setFill(Color.BLACK);
				
				
							//LEFT
							if (cellPos[left-1][clickY].getMyValue() == white) {
								cellPos[clickX][clickY].setGraphic(turnBlack); //Places a white piece
								cellPos[left][clickY].setGraphic(blackPiece); //Captures the white piece
								cellPos[left][clickY].setMyValue(-1);
								highlight(); //Highlight available after-moves
							}
				
						}

	
	///////////////////////////////////////////////////////// Highlight next available move///////////////////////////////////////////////////////////////////

	//Used for transparent circles to highlight legal moves
	public static void placePieceTransparent(MyButton[][] cell, int x, int y) {

		double centerX = butSize / 2;
		double centerY = butSize / 2;
		double radiusX = 22;
		double radiusY = 22;

		Ellipse blackRing = new Ellipse(centerX, centerY, radiusX, radiusY);
		blackRing.setFill(null);
		blackRing.setStroke(Color.GRAY);
		blackRing.setStrokeWidth(10);

		cell[x][y].setGraphic(blackRing);

	}
	
	///////////////////////////////////////////// Rules of Reversi  //////////// ///////////////////////////////////////////

	// Checks if every move is "Legal" else "Illegal" according to "Rules of Reversi"
	public static boolean legal(MyButton[][] cell, int x, int y) {

		if (cell[x][y].getMyValue() == 0) {
			return omringet(cell, x, y);
		} else {
			return false;
		}
	}

	// Checks if there are enemy pieces around the placed-Piece 
	public static boolean omringet(MyButton[][] cell, int x, int y) {

		//Coordinates E/W/S/N of the piece placed
		int right = x + 1;
		int left = x - 1;
		int down = y + 1;
		int up = y - 1;

		
		if (clickCount % 2 != 0) {turn = 1;} 
		else {turn = -1;}

		// Serves as a border so there wont be an OutOfBounds error
		if (right >= gridSize) {right = gridSize - 1;}
		if (left < 0) {left = 0;}
		if (down >= gridSize) {down = gridSize - 1;}
		if (up < 0) {up = 0;}

		
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

	
	//Checks the whole row, column and diagonal for enemy pieces.
	// If your own piece --> Recursive method call
	// If empty --> Break and return false.
	public static boolean line(MyButton[][] cell, int side_x, int side_y, int old_x, int old_y) {
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

	
	//Highlight function
	public static void highlight() {
		availableMoves = 0;
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (legal(buttons2D, i, j) == true) {
					placePieceTransparent(buttons2D, i, j);
					availableMoves++;
				} else if (buttons2D[i][j].getMyValue() == 0) {
					buttons2D[i][j].setGraphic(null);
				}

			}
		}
	}
	
	
	
} // End class

// A class that makes it possible to assign a INT value to each button
class MyButton extends Button {

	private int MyValue;

	public MyButton(int MyValue) {
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
