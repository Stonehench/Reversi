package januarProjektet2023;

	import java.io.File;
	import javafx.util.Duration;

	import javafx.animation.KeyFrame;
	import javafx.animation.Timeline;
	import javafx.application.Application;
	import javafx.beans.binding.Bindings;
	import javafx.event.ActionEvent;
	import javafx.event.EventHandler;
	import javafx.geometry.HPos;
	import javafx.geometry.Pos;
	import javafx.geometry.VPos;
	import javafx.scene.Scene;
	import javafx.scene.control.Button;
	import javafx.scene.control.Label;
	import javafx.scene.layout.GridPane;
	import javafx.scene.layout.Pane;
	import javafx.scene.layout.StackPane;
	import javafx.scene.layout.VBox;
	import javafx.scene.media.Media;
	import javafx.scene.media.MediaPlayer;
	import javafx.scene.paint.Color;
	import javafx.scene.shape.Circle;
	import javafx.scene.shape.Ellipse;
	import javafx.scene.text.Font;
	import javafx.scene.text.FontWeight;
	import javafx.scene.text.Text;
	import javafx.scene.text.TextBoundsType;
	import javafx.stage.Modality;
	import javafx.stage.Stage;

	public class Reversi extends Application {
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
		static int clickCount = 0;
		static int butSize = windowSize / gridSize;
		static int numberOfWhite;
		static int numberOfBlack;
		static int turn;
		static int blackTimer = 1;
		static int blackMinut = 0;
		static int whiteTimer = 1;
		static int whiteMinut = 0;
		static int clickedMute;
		static int whiteWinCounter2 = 0;
		static int blackWinCounter2 = 0;
		static int blackValue;
		static int whiteValue;
		static boolean soundOn = true;
		
		static Circle whitePiece;
		static Circle blackPiece;
		static String playerTurn;
		static MyButton clickedButton;
		static MyButton[][] buttons2D = new MyButton[gridSize][gridSize];
		static Font normal = new Font("Verdana", 15);
		static Font bold = new Font("Verdana", 17);
		static Font numberFont = new Font("Verdana", 20);
		static Label whiteScore = new Label("White Score 2");
		static Label blackScore = new Label("Black Score 2");
		static Circle circle = new Circle();
		static Text whiteWinCounter = new Text("" + whiteWinCounter2);
		static Text blackWinCounter = new Text("" + blackWinCounter2);
		static Button pass = new Button("Pass");
		static Button restart = new Button("Restart game");
		static GridPane root = new GridPane();
		static Scene scene = new Scene(root, windowSize + (windowSize / 2), windowSize);
		static Timeline whiteTimeLine = new Timeline();
		static Timeline blackTimeLine = new Timeline();
		static Label timeDisplayWhite = new Label();
		static Label timeDisplayBlack = new Label();
		static Media media = new Media(new File("Intro-Reversi.wav").toURI().toString());
		static MediaPlayer intro = new MediaPlayer(media);
		static MediaPlayer legalMoveSound = new MediaPlayer(media);
		static MediaPlayer IllegalMoveSound = new MediaPlayer(media);

		// Class Constructor
		public Reversi() {
			// Code to make the fonts look correct
			normal = Font.font(normal.getFamily(), FontWeight.NORMAL, normal.getSize());
			bold = Font.font(bold.getFamily(), FontWeight.BOLD, bold.getSize());

			// The display of Labels "White score" and "Black score" and all the buttons
			timeDisplayWhite = new Label(" Time: 00:00 ");
			timeDisplayBlack = new Label(" Time: 00:00 ");

			// Creates the first column of the menu on the right
			for (int i = 0; i < gridSize; i++) {
				if (i == 1) {
					whiteScore.setPrefSize(windowSize / 4, butSize);
					whiteScore.setTextFill(Color.BLACK);
					whiteScore.setStyle("-fx-background-color: white;"
							+ " -fx-border-style: solid;"
							+ " -fx-border-width: 2;"
							+ " -fx-border-color: black;"
							+ "-fx-border-radius: 4;"
							+ "-fx-background-insets: 2;");
					whiteScore.setFont(normal);
					root.add(whiteScore, gridSize, i);

					// Time display for white
					timeDisplayWhite.setStyle("-fx-background-color: white;"
							+ " -fx-border-style: solid;"
							+ " -fx-border-width: 2;"
							+ " -fx-border-color: black;"
							+ "-fx-border-radius: 2;"
							+ "-fx-background-insets: 2;");
					timeDisplayWhite.setFont(normal);
					root.add(timeDisplayWhite, gridSize, i - 1);

				} else if (i == 3) {
					blackScore.setPrefSize(windowSize / 4, butSize);
					blackScore.setTextFill(Color.WHITE);
					blackScore.setStyle("-fx-background-color: black;"
							+ " -fx-border-style: solid;"
							+ " -fx-border-width: 2;"
							+ " -fx-border-color: black;"
							+ "-fx-border-radius: 2;"
							+ "-fx-background-insets: 2;");
					blackScore.setFont(normal);
					root.add(blackScore, gridSize, i);

					// Time display for black
					timeDisplayBlack.setStyle("-fx-background-color: black;"
							+ " -fx-border-style: solid;"
							+ " -fx-border-width: 2;"
							+ " -fx-border-color: black;"
							+ "-fx-border-radius: 2;"
							+ "-fx-background-insets: 2;");
					timeDisplayBlack.setTextFill(Color.WHITE);
					timeDisplayBlack.setFont(normal);
					root.add(timeDisplayBlack, gridSize, i + 1);

				} else if (i == 2) {
					pass.setPrefSize((windowSize / 4) - (gridSize * 2.5), butSize - (gridSize * 2.5)); // Size of the button
					pass.setStyle("-fx-base: white;"); // Button color
					pass.setStyle("-fx-background-radius: 50"); // Gives button smooth edges
					pass.setDisable(true);
					root.add(pass, gridSize, i);
					GridPane.setConstraints(pass, gridSize, i, 1, 1, HPos.CENTER, VPos.CENTER);
				} else if (i == gridSize - 1) {
					
					//Restart button
					restart.setPrefSize((windowSize / 4) - (gridSize * 2.5), butSize - (gridSize * 2.5)); // Size of the //																				// button
					restart.setStyle("-fx-base: white;"); // Button color
					restart.setStyle("-fx-background-radius: 50"); // Gives button smooth edges
					root.add(restart, gridSize, i);
					GridPane.setConstraints(restart, gridSize, i, 1, 1, HPos.CENTER, VPos.CENTER);
				} else if (i == gridSize-2) {
					
					//Mute button
					Button Mute = new Button("SoundFX: on");
					Mute.setPrefSize((windowSize / 4), butSize/2); // Size of the button
					Mute.setStyle("-fx-base: white;"); // Button color
					Mute.setStyle("-fx-background-radius: 15"); // Gives button smooth edges
					root.add(Mute, gridSize, i);
					
					//On-click action for mute: Mute all sound FX
					Mute.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							clickedMute++;

							if(clickedMute % 2 != 0) {
								soundOn = false;
								Mute.setText("SoundFX: off");
							} 
							else {
								soundOn = true;
								Mute.setText("SoundFX: on");
							}
						}
					});  
				} //end Mute
				
			} //sidePane MENU buttons end

			
			// Creates the second menu on the right

			for (int i = 0; i < gridSize; i++) {
				if (i == 1) {
					whiteWinCounter.setBoundsType(TextBoundsType.VISUAL);
					StackPane stack = new StackPane();
					stack.getChildren().addAll(circle, whiteWinCounter);
					whiteWinCounter.setFill(Color.BLACK);
					whiteWinCounter.setStyle("-fx-background-color: white;");
					whiteWinCounter.setFont(numberFont);
					root.add(whiteWinCounter, gridSize + 1, i);
					GridPane.setConstraints(whiteWinCounter, gridSize + 1, i, 1, 1, HPos.CENTER, VPos.CENTER);
				} else if (i == 3) {
					blackWinCounter.setFill(Color.WHITE);
					blackWinCounter.setStyle("-fx-background-color: black;");
					blackWinCounter.setFont(numberFont);
					root.add(blackWinCounter, gridSize + 1, i);
					GridPane.setConstraints(blackWinCounter, gridSize + 1, i, 1, 1, HPos.CENTER, VPos.CENTER);
				} else if (i >= 0) {
					MyButton emptySpace = new MyButton(0);
					emptySpace.setPrefSize(windowSize / 4, butSize);
					emptySpace.setVisible(false);
					root.add(emptySpace, gridSize + 1, i);
				}
			}

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

			//////////////////////////////////// On-click
			//////////////////////////////////// function///////////////////////////////////////////

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
								focusPlayer();

								// Whites turn
								if (clickCount % 2 != 0) {
									whiteScore.setFont(bold);
									blackScore.setFont(normal);
									timeDisplayWhite.setFont(bold);
									timeDisplayBlack.setFont(normal);
									
									System.out.println("White counter started: ");
									whiteTimeLine.play();
									blackTimeLine.stop();

								}
								// Blacks turn
								else {
									whiteScore.setFont(normal);
									blackScore.setFont(bold);
									timeDisplayWhite.setFont(normal);
									timeDisplayBlack.setFont(bold);
									
									System.out.println("Black counter started: ");
									blackTimeLine.play();
									whiteTimeLine.stop();
									
								}

								// SoundFX every time you make a "Legal" move.
								media = new Media(new File("Reversi-game-sound.wav").toURI().toString());
								legalMoveSound = new MediaPlayer(media);
								legalMoveSound.setVolume(0.1);
								if(soundOn) {
									legalMoveSound.play();
								}
								

							} else {
								// SoundFX every time you make a "Illegal" move.
								media = new Media(new File("Sounds_whoosh.mp3").toURI().toString());
								IllegalMoveSound = new MediaPlayer(media);
								IllegalMoveSound.setVolume(0.1);
								if(soundOn) {
									IllegalMoveSound.play();
								}
								
								

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
			} // Outer loop end
			
			
		
			
			
			//Pass button
			pass.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					// Skip the current player's turn
					if (turn == -1) {
						turn = 1;
						clickCount++;
						playerTurn = "white";
						highlight();
						focusPlayer();
					} else {
						turn = -1;
						playerTurn = "black";
						clickCount++;
						highlight();
						focusPlayer();
					}
				}
			});

			//Restart button
			restart.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					// Reset game state variables here
					clickCount = 0;

					//Reset the timer
					blackValue = 2;
					whiteValue = 2;
					blackTimer = 0; //new
					blackMinut = 0; //new
					whiteTimer = 0; //new
					whiteMinut = 0; //new
					
					//Reset time display
					  whiteTimeLine.stop();
					  blackTimeLine.stop();
					  timeDisplayWhite.textProperty().unbind();
					  timeDisplayBlack.textProperty().unbind();
					  timeDisplayWhite.setText(" Time: 00:00 ");
					  timeDisplayBlack.setText(" Time: 00:00 ");
					  
					
				
					// Update GUI to reflect reset game state
					blackScore.setText("Black Score: 2");
					whiteScore.setText("White Score: 2");
					for (int row = 0; row < gridSize; row++) {
						for (int column = 0; column < gridSize; column++) {

							buttons2D[row][column].setGraphic(null); // Add coordinates and accessibility to all buttons.
							buttons2D[row][column].setMyValue(0);

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
						}
					}
				}
			});

			
		}

		// Stage setup
		@Override
		public void start(Stage primaryStage) {

			// Create a new stage
			Stage menuStage = new Stage();
			menuStage.setTitle("Menu");

			// Set the scene for the stage
			Pane pane = new Pane();
			Scene menuScene = new Scene(pane, 400, 400);
			menuStage.setScene(menuScene);
			menuStage.show();

			// Button to the scene
			Button startGame = new Button();
			startGame.setPrefSize(100, 50);
			startGame.setText("Start Game");
			startGame.setStyle("-fx-base: #8B4513");
			pane.getChildren().add(startGame);
			// Starts game when clicked
			startGame.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					primaryStage.show();
					menuStage.close();
				}
			});

			// Constructs pane
			root.setStyle("-fx-background-color: #33CC66;"); // Sets backgground color

			// Makes CSS and sidepane possible
			scene.getStylesheets().add(getClass().getResource("Reversi.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Reversi");
			primaryStage.setResizable(false);

			// Constructs the default 4 pieces in the center of Board
			startFour();

			// SoundFX: Intro-music when open game
			media = new Media(new File("Intro-Reversi.wav").toURI().toString());
			intro = new MediaPlayer(media);
			intro.setVolume(0.1);
			intro.play();

			
			// Create a TimeLine for white
			// Settings:
			double countSpeedRate = 1; // 1 = normal, ? < 1 = faster, ? > 1 = slower

			whiteTimeLine = new Timeline(new KeyFrame(Duration.seconds(countSpeedRate),
					event -> {
						// Displays and updates time
						timeDisplayWhite.textProperty()
								.bind(Bindings.format(" Time: %02d:%02d ", whiteMinut, whiteTimer++));
						// Every 60 second, store 1 minute
						if (whiteTimer % 60 == 0) {
							whiteMinut++;
							System.out.println("sort minut: " + whiteMinut);
							whiteTimer = 0; // Reset seconds to 00
						}
					}));
			whiteTimeLine.setCycleCount(Timeline.INDEFINITE);

			// Create a TimeLine for white
			blackTimeLine = new Timeline(new KeyFrame(Duration.seconds(countSpeedRate),
					event -> {

						// Displays and updates time
						timeDisplayBlack.textProperty()
								.bind(Bindings.format(" Time: %02d:%02d ", blackMinut, blackTimer++));
						// Every 60 second, store 1 minute
						if (blackTimer % 60 == 0) {
							blackMinut++;
							System.out.println("sort minut: " + blackMinut);
							blackTimer = 0; // Reset seconds to 00
						}
					}));
			blackTimeLine.setCycleCount(Timeline.INDEFINITE);

		} // End stage

		
		
		public static void SoundFX() {
			if (soundOn) {
				legalMoveSound.play();
				}
			else if (soundOn) {
				IllegalMoveSound.play();
				 }
		}
		
		
		
		////////////////////////////// Action1: Places a piece on the board
		////////////////////////////// ////////////////////////////////////////////////////////
		public static void placePiece(MyButton clickedButton) {

			// Design of the WHITE piece
			whitePiece = new Circle(butSize / 3 - gridSize / 7.5);
			whitePiece.setFill(Color.WHITE);
			whitePiece.setStroke(Color.BLACK);

			// Design of the BLACK piece
			blackPiece = new Circle(butSize / 3 - (gridSize / 6) + 1);
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

		// Used for transparent circles to highlight legal moves
		public static void placePieceTransparent(MyButton[][] cell, int x, int y) {

			double centerX = butSize / 2;
			double centerY = butSize / 2;
			double radiusX = (butSize / 6) + butSize / gridSize;
			double radiusY = (butSize / 6) + butSize / gridSize;

			Ellipse blackRing = new Ellipse(centerX, centerY, radiusX, radiusY);
			blackRing.setFill(null);
			blackRing.setStroke(Color.GRAY);
			blackRing.setStrokeWidth(butSize / 8);

			cell[x][y].setGraphic(blackRing);
		}

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

		// Starts the process of capturing a piece
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
			fullBoard = 0;
			numberOfBlack = 0;
			numberOfWhite = 0;
			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					if (legal(buttons2D, i, j) == true) {
						placePieceTransparent(buttons2D, i, j);
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
			}
		}

		// Highlights player turn in label
		public static void focusPlayer() {
			if (clickCount % 2 != 0) {
				whiteScore.setFont(bold);
				blackScore.setFont(normal);
				timeDisplayWhite.setFont(bold);
				timeDisplayBlack.setFont(normal);

			} else {
				whiteScore.setFont(normal);
				blackScore.setFont(bold);
				timeDisplayWhite.setFont(normal);
				timeDisplayBlack.setFont(bold);
			}
		}


		// Constructs the default 4 pieces in the center of Board
		public static void startFour() {
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
			whiteScore.setText("White Score: " + numberOfWhite);
			blackScore.setText("Black Score: " + numberOfBlack);
			focusPlayer();
		}

		// Pops up with a little window declaring the winner
		public static void winner(String winner, Stage winStage) {

			winStage.initModality(Modality.APPLICATION_MODAL);
	        winStage.setTitle("Pop-up Window");
	        winStage.setMinWidth(400);
	        winStage.setMinHeight(200);

			VBox layout = new VBox(10);
	        layout.setAlignment(Pos.CENTER);

			Font font = new Font ("Impact", 20);

			Label label = new Label("THE WINNER IS " + winner + " !!!!!");
			label.setFont(font);
			layout.getChildren().add(label);

			Button closeButton = new Button();
			closeButton.setText("Start a New Game");
	        closeButton.setOnAction(e -> winStage.close());        
	        closeButton.setStyle("-fx-background-radius: 15");
			layout.getChildren().add(closeButton);

			closeButton.setOnAction(event -> {
				// Reset game state variables here
				clickCount = 0;

				//Reset the timer
				blackValue = 2;
				whiteValue = 2;
				blackTimer = 0; //new
				blackMinut = 0; //new
				whiteTimer = 0; //new
				whiteMinut = 0; //new
				
				//Resets timer display
				whiteTimeLine.stop();
				blackTimeLine.stop();
				timeDisplayWhite.textProperty().unbind();
				timeDisplayBlack.textProperty().unbind();
				timeDisplayWhite.setText(" Time: 00:00 ");
				timeDisplayBlack.setText(" Time: 00:00 ");
				

				// Update GUI to reflect reset game state
				blackScore.setText("Black Score: 2");
				whiteScore.setText("White Score: 2");
				for (int row = 0; row < gridSize; row++) {
					for (int column = 0; column < gridSize; column++) {

						buttons2D[row][column].setGraphic(null); // Add coordinates and accessibility to all buttons.
						buttons2D[row][column].setMyValue(0);

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
						
						winStage.close();
					}
				}
			});

			Scene scene = new Scene(layout);
	        winStage.setScene(scene);
	        winStage.show();
	        layout.setStyle("-fx-background-color: #33CC66;");
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
