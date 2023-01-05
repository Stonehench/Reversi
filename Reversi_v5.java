package januarProjektet2023;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Reversi extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	// Global Instance variables her:
	static int availableMoves = 0;
	static int gridSize = 8;
	static int windowSize = gridSize * 75;
	static int clickCount = 0;
	static int butSize = windowSize / 8;
	static int blackValue;
	static int whiteValue;
	static int numberOfWhite;
	static int numberOfBlack;
	static int turn;
	static int blackTimer = 1; //new
	static int blackMinut = 0; //new
	static int whiteTimer = 1; //new
	static int whiteMinut = 0; //new

	static Circle whitePiece = new Circle();
	static Circle blackPiece = new Circle();
	static String playerTurn;
	static MyButton clickedButton;
	static MyButton[][] buttons2D = new MyButton[8][8];
	
	static Timeline whiteTimeLine = new Timeline(); //new
	static Timeline blackTimeLine = new Timeline(); //new
	static Label timeDisplayWhite = new Label(); //new
	static Label timeDisplayBlack = new Label(); //new
	Font normal = new Font("Verdana",15);
	Font bold = new Font("Verdana",17);

	

	@Override
	public void start(Stage primaryStage) {

		// Constructs pane
		GridPane root = new GridPane();
		root.setStyle("-fx-background-color: #33CC66;"); // Sets background color

		// Size of Window
		Scene scene = new Scene(root, windowSize + (windowSize / 4), windowSize);

		// Makes CSS and sidepane possible
		scene.getStylesheets().add(getClass().getResource("Reversi.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Reversi");

		// Construction the 8x8 Grid with 64 buttons
		for (int row = 0; row < gridSize; row++) {
			for (int column = 0; column < gridSize; column++) {
				MyButton myButton = new MyButton(0);
				myButton.setPrefSize(butSize, butSize); // Size of one cell

				// Background
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

		
		
		// Code to make the fonts look correct
		normal = Font.font(normal.getFamily(),FontWeight.NORMAL, normal.getSize());
		bold = Font.font(bold.getFamily(), FontWeight.BOLD, bold.getSize());
		

		//The display of Labels "White score" and "Black score" and all the buttons
		//Final keyword = cannot be changed by player, only used for display purpose
		final Label whiteScore = new Label("White Score 2");
		final Label blackScore = new Label("Black Score 2");
		timeDisplayWhite = new Label("Time: 00:00");
		timeDisplayBlack = new Label("Time: 00:00");
		
		
		//Positions of all the side pane buttons and interface
		for (int i = 0; i < gridSize; i++) {
			
			if (i == 2) {
				//White scoreBoard
				whiteScore.setPrefSize(windowSize / 4, butSize);
				whiteScore.setTextFill(Color.BLACK);
				whiteScore.setStyle("-fx-background-color: white;");
				whiteScore.setFont(normal);
				root.add(whiteScore, gridSize, i);
				
				//Time display for white
				timeDisplayWhite.setStyle("-fx-background-color: white;");
				timeDisplayWhite.setFont(normal);
				root.add(timeDisplayWhite, gridSize,i-1);

			} if (i == 4) {
				//Black scoreBoard
				blackScore.setPrefSize(windowSize / 4, butSize);
				blackScore.setTextFill(Color.WHITE);
				blackScore.setStyle("-fx-background-color: black;");
				blackScore.setFont(normal);
				root.add(blackScore, gridSize, i);
				
				//Time display for black
				timeDisplayBlack.setStyle("-fx-background-color: black;");
				timeDisplayBlack.setTextFill(Color.WHITE);
				timeDisplayBlack.setFont(normal);
				root.add(timeDisplayBlack, gridSize,i+1);
				
				
			} else if (i == 0 || i == 2 || i == 4 || i == 5) {
				MyButton emptySpace = new MyButton(0);
				emptySpace.setPrefSize(windowSize / 4, butSize);
				emptySpace.setVisible(false);
				root.add(emptySpace, gridSize, i);

			} else if (i == gridSize - 2) {
				Button pass = new Button("Pass");
				pass.setPrefSize((windowSize / 4), butSize); // Size of the button
				pass.setStyle("-fx-base: white;"); // Button color
				pass.setStyle("-fx-background-radius: 15"); // Gives button smooth edges
				root.add(pass, gridSize, i);

			} else if (i == gridSize - 1) {
				Button restart = new Button("Restart game");
				restart.setPrefSize((windowSize / 4), butSize); // Size of the button
				restart.setStyle("-fx-base: white;"); // Button color
				restart.setStyle("-fx-background-radius: 15"); // Gives button smooth edges
				root.add(restart, gridSize, i);

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
							capturePiece(buttons2D, x, y); // Check if there is a capture
							clickCount++;
							highlight();
							whiteScore.setText("White score " + numberOfWhite);
							blackScore.setText("Black score " + numberOfBlack);

							//Whites turn
							if(clickCount%2!=0){ 
								whiteScore.setFont(bold);
								blackScore.setFont(normal);
								timeDisplayWhite.setFont(bold);
								timeDisplayBlack.setFont(normal);
								playTimerWhite();}
							//Blacks turn
							else {
								whiteScore.setFont(normal);
								blackScore.setFont(bold);
								timeDisplayWhite.setFont(normal);
								timeDisplayBlack.setFont(bold);
								playTimerBlack();
							}

							// SoundFX every time you make a "Legal" move.
							Media media = new Media(new File("Reversi-game-sound.wav").toURI().toString());
							MediaPlayer legalMoveSound = new MediaPlayer(media);
							legalMoveSound.setVolume(0.1);
							legalMoveSound.play();

						} else {
							// SoundFX every time you make a "Illegal" move.
							Media media = new Media(new File("Sounds_whoosh.mp3").toURI().toString());
							MediaPlayer IllegalMoveSound = new MediaPlayer(media);
							IllegalMoveSound.setVolume(0.1);
							IllegalMoveSound.play();

						} // End if-else

						// FOR DATA TESTING PURPOSE
						System.out.println("----------------------");
						System.out.println("clickcount: " + clickCount);
						System.out.println("clickposition: (" + x + ", " + y + ")");
						System.out.println("Player: " + playerTurn);
						System.out.println("value " + buttons2D[x][y].getMyValue());
						System.out.println("turn: " + turn);
						System.out.println("legal: " + legal(buttons2D, x, y));
						System.out.println("");

					} // End click function
				}); // End Event-handler
			} // Inner loop end
		} // Outer on-click function loop end

		
		
		// Constructs the default 4 pieces in the center of Board
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

		//Showcase whose turn it is when starting the game: Always white
		if(clickCount%2!=0){
			whiteScore.setFont(bold);
			blackScore.setFont(normal);
			timeDisplayWhite.setFont(bold);
			timeDisplayBlack.setFont(normal);

		}else {
			whiteScore.setFont(normal);
			blackScore.setFont(bold);
			timeDisplayWhite.setFont(normal);
			timeDisplayBlack.setFont(bold);
		}

		// SoundFX: Intro-music when open game
		Media media = new Media(new File("Intro-Reversi.wav").toURI().toString());
		MediaPlayer intro = new MediaPlayer(media);
		intro.setVolume(0.1);
		intro.play();



		///////////////////////////////////// Timeline for time used  /////////////////////////////////////

		//Create a TimeLine for white
		
		//Settings:
		double countSpeedRate = 1; // 1 = normal, ? < 1 = faster,   ? > 1 = slower
		
		
		whiteTimeLine = new Timeline(new KeyFrame(Duration.seconds(countSpeedRate), 
				event -> {
					//Displays and updates time
					timeDisplayWhite.textProperty().bind(Bindings.format("Time: %02d:%02d",whiteMinut ,whiteTimer++));
					//Every 60 second, store 1 minute
					if (whiteTimer % 60 == 0) {
						whiteMinut++;
						System.out.println("sort minut: " + whiteMinut);
						whiteTimer = 0; //Reset seconds to 00
					}
				}));
		whiteTimeLine.setCycleCount(Timeline.INDEFINITE);


		//Create a TimeLine for white
		blackTimeLine = new Timeline(new KeyFrame(Duration.seconds(countSpeedRate), 
				event -> {
					
					//Displays and updates time
					timeDisplayBlack.textProperty().bind(Bindings.format("Time: %02d:%02d",blackMinut, blackTimer++));
					//Every 60 second, store 1 minute
					if (blackTimer % 60 == 0) {
						blackMinut++;
						System.out.println("sort minut: " + blackMinut);
						blackTimer = 0; //Reset seconds to 00
					}
				}));
		blackTimeLine.setCycleCount(Timeline.INDEFINITE);
		
		
	} // End stage
	
	
	//Trigger the player time counter depending on which players turn: used in line 198 & 205
	public static void playTimerWhite() {
		System.out.println("White counter started: ");
		whiteTimeLine.play();
		blackTimeLine.stop();
		
	}
	
	public static void playTimerBlack() {
			System.out.println("Black counter started: ");
			blackTimeLine.play();
			whiteTimeLine.stop();
			
	}
	
	
	////////////////////////////// Action1: Places a piece on the board
	////////////////////////////// ////////////////////////////////////////////////////////
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
			playerTurn = "white";
			clickedButton.setGraphic(whitePiece); // places White piece on-board
			clickedButton.setMyValue(1);
			
		} else {
			playerTurn = "black";
			clickedButton.setGraphic(blackPiece); // places Black piece on-board
			clickedButton.setMyValue(-1);
		}
	}

	
	
				
	
	// Used for transparent circles to highlight legal moves
	public static void placePieceTransparent(MyButton[][] cell, int x, int y) {

		double centerX = butSize / 2;
		double centerY = butSize / 2;
		double radiusX = butSize / 3.4;
		double radiusY = butSize / 3.4;

		Ellipse blackRing = new Ellipse(centerX, centerY, radiusX, radiusY);
		blackRing.setFill(null);
		blackRing.setStroke(Color.GRAY);
		blackRing.setStrokeWidth(butSize / 7.5);

		cell[x][y].setGraphic(blackRing);
	}

	//////////////////////////////////// Peters kode ////////////
	//////////////////////////////////// ///////////////////////////////////////////

	// Checks if every move is "Legal" else "Illegal" according to "Rules of
	// Reversi"
	public static boolean legal(MyButton[][] cell, int x, int y) {

		if (cell[x][y].getMyValue() == 0) {
			return surrounded(cell, x, y);
		} else {
			return false;
		}
	}

	// Checks if there are enemy pieces around the placed-Piece
	public static boolean surrounded(MyButton[][] cell, int x, int y) {

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

	public static void capturePiece(MyButton[][] cell, int x, int y) {

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
		// Thereafter calls method to tjek if there's an allied
		// Piece on the other side of the enemy piece
		// If true then it calls the method to change the pieces
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

	// changes the pieces inbetween the placed piece and a same colored piece
	public static void changePiece(MyButton[][] cell, int side_x, int side_y, int old_x, int old_y) {
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
		numberOfBlack = 0;
		numberOfWhite = 0;
		System.out.println(numberOfBlack);
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (legal(buttons2D, i, j) == true) {
					placePieceTransparent(buttons2D, i, j);
					availableMoves++;
				} else if (buttons2D[i][j].getMyValue() == 0) {
					buttons2D[i][j].setGraphic(null);
				} else if (buttons2D[i][j].getMyValue() == 1) {
					numberOfWhite++;
				} else if (buttons2D[i][j].getMyValue() == -1) {
					numberOfBlack++;
				}
			}
		}
		if (availableMoves == 0) {
			// Gør pass knappen mulig at bruge
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