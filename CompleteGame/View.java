package completeReversiGame;

import java.io.File;
import java.net.MalformedURLException;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class View extends Application {
	static Circle whitePiece;
	static Circle blackPiece;
	static String playerTurn;
	static MyButton clickedButton;
	static MyButton[][] buttons2D = new MyButton[Model.gridSize][Model.gridSize];
	static Font normal = new Font("Verdana", 15);
	static Font bold = new Font("Verdana", 17);
	static Font numberFont = new Font("Verdana", 20);
	static Label whiteScore = new Label("White Score 2");
	static Label blackScore = new Label("Black Score 2");
	static Circle whiteCircle = new Circle(Model.butSize / 3);
	static Circle blackCircle = new Circle(Model.butSize / 3);
	static Text whiteWinCounter = new Text("" + Model.whiteWinCounter2);
	static Text blackWinCounter = new Text("" + Model.blackWinCounter2);
	static Button pass = new Button("Pass");
	static Button restart = new Button("Restart game");
	static Button replay = new Button("Replay");
	static Button mute = new Button("SoundFX: on");
	static Button backToMenu = new Button("Back to menu");
	static GridPane root = new GridPane();
	Scene scene = new Scene(root, Model.windowSize + (Model.windowSize / 2), Model.windowSize);
	static Timeline whiteTimeLine = new Timeline();
	static Timeline blackTimeLine = new Timeline();
	static Label timeDisplayWhite = new Label();
	static Label timeDisplayBlack = new Label();
	static Media media = new Media(new File("Intro-Reversi.wav").toURI().toString());
	static MediaPlayer intro = new MediaPlayer(media);
	static MediaPlayer legalMoveSound = new MediaPlayer(media);
	static MediaPlayer IllegalMoveSound = new MediaPlayer(media);
	static MediaPlayer backgroundMusik = new MediaPlayer(media);

	static Stage Gamerules = new Stage();
	static Stage Gamerules2 = new Stage();
	static Button startGame = new Button();
	static Button rules = new Button();
	static Button exit = new Button();
	static Button BacktoMenu = new Button();
	static Button BacktoMenu2 = new Button();
	static Button Next = new Button();
	static Button back = new Button("Back");
	static Pane pane = new Pane();
	Scene menuScene = new Scene(pane, 1200, 625);
	static Stage menuStage = new Stage();

	public View() throws Exception {
		normal = Font.font(normal.getFamily(), FontWeight.NORMAL, normal.getSize());
		bold = Font.font(bold.getFamily(), FontWeight.BOLD, bold.getSize());

		// The display of Labels "White score" and "Black score" and all the buttons
		timeDisplayWhite = new Label(" Time: 05:00 ");
		timeDisplayBlack = new Label(" Time: 05:00 ");

		// Creates the first column of the menu on the right
		for (int i = 0; i < Model.gridSize; i++) {
			if (i == 1) {
				whiteScore.setPrefSize(Model.windowSize / 4, Model.butSize);
				whiteScore.setTextFill(Color.BLACK);
				whiteScore.setStyle("-fx-background-color: white;"
						+ " -fx-border-style: solid;"
						+ " -fx-border-width: 2;"
						+ " -fx-border-color: black;"
						+ "-fx-border-radius: 4;"
						+ "-fx-background-insets: 2;");
				whiteScore.setFont(normal);
				root.add(whiteScore, Model.gridSize, i);

				// Time display for white
				timeDisplayWhite.setStyle("-fx-background-color: white;"
						+ " -fx-border-style: solid;"
						+ " -fx-border-width: 2;"
						+ " -fx-border-color: black;"
						+ "-fx-border-radius: 2;"
						+ "-fx-background-insets: 2;");
				timeDisplayWhite.setFont(normal);
				root.add(timeDisplayWhite, Model.gridSize, i - 1);

			} else if (i == 3) {
				blackScore.setPrefSize(Model.windowSize / 4, Model.butSize);
				blackScore.setTextFill(Color.WHITE);
				blackScore.setStyle("-fx-background-color: black;"
						+ " -fx-border-style: solid;"
						+ " -fx-border-width: 2;"
						+ " -fx-border-color: black;"
						+ "-fx-border-radius: 2;"
						+ "-fx-background-insets: 2;");
				blackScore.setFont(normal);
				root.add(blackScore, Model.gridSize, i);

				// Time display for black
				timeDisplayBlack.setStyle("-fx-background-color: black;"
						+ " -fx-border-style: solid;"
						+ " -fx-border-width: 2;"
						+ " -fx-border-color: black;"
						+ "-fx-border-radius: 2;"
						+ "-fx-background-insets: 2;");
				timeDisplayBlack.setTextFill(Color.WHITE);
				timeDisplayBlack.setFont(normal);
				root.add(timeDisplayBlack, Model.gridSize, i + 1);

			} else if (i == 2) {
				pass.setPrefSize((Model.windowSize / 4) - (Model.gridSize * 2.5),
						Model.butSize - (Model.gridSize * 2.5)); // Size of the button
				pass.setStyle("-fx-base: white;"); // Button color
				pass.setStyle("-fx-background-radius: 50"); // Gives button smooth edges
				pass.setDisable(true);
				root.add(pass, Model.gridSize, i);
				GridPane.setConstraints(pass, Model.gridSize, i, 1, 1, HPos.CENTER, VPos.CENTER);
			} else if (i == Model.gridSize - 1) {

				// Restart button
				restart.setPrefSize((Model.windowSize / 4) - (Model.gridSize * 2.5),
						Model.butSize - (Model.gridSize * 2.5)); // Size of the //
				// // button
				restart.setStyle("-fx-base: white;"); // Button color
				restart.setStyle("-fx-background-radius: 50"); // Gives button smooth edges
				root.add(restart, Model.gridSize, i);
				GridPane.setConstraints(restart, Model.gridSize, i, 1, 1, HPos.CENTER, VPos.CENTER);
			} else if (i == Model.gridSize - 2) {

				// Mute button
				mute.setPrefSize((Model.windowSize / 4) - (Model.gridSize * 2.5),
						Model.butSize - (Model.gridSize * 2.5)); // Size of the button
				mute.setStyle("-fx-base: white;"); // Button color
				mute.setStyle("-fx-background-radius: 50"); // Gives button smooth edges
				root.add(mute, Model.gridSize, i);
				GridPane.setConstraints(mute, Model.gridSize, i, 1, 1, HPos.CENTER, VPos.CENTER);
			} else if (i == Model.gridSize - 3) {

				// back button
				backToMenu.setPrefSize((Model.windowSize / 4) - (Model.gridSize * 2.5),
						Model.butSize - (Model.gridSize * 2.5)); // Size of the button
				backToMenu.setStyle("-fx-base: white;"); // Button color
				backToMenu.setStyle("-fx-background-radius: 50"); // Gives button smooth edges
				root.add(backToMenu, Model.gridSize, i);
				GridPane.setConstraints(backToMenu, Model.gridSize, i, 1, 1, HPos.CENTER, VPos.CENTER);

			} else if (i >= 0) {
				MyButton emptySpace = new MyButton(0);
				emptySpace.setPrefSize(Model.windowSize / 4, Model.butSize);
				emptySpace.setVisible(false);
				root.add(emptySpace, Model.gridSize, i);
			}

		}
		for (int i = 0; i < Model.gridSize; i++) {
			if (i == 1) {
				whiteWinCounter.setBoundsType(TextBoundsType.VISUAL);
				whiteCircle.setFill(Color.WHITE);
				whiteCircle.setStroke(Color.BLACK);
				StackPane whiteCircleNText = new StackPane();
				whiteCircleNText.getChildren().addAll(whiteCircle, whiteWinCounter);
				whiteWinCounter.setFill(Color.BLACK);
				whiteWinCounter.setStyle("-fx-background-color: white;");
				whiteWinCounter.setFont(numberFont);
				root.add(whiteCircleNText, Model.gridSize + 1, i);
				GridPane.setConstraints(whiteCircleNText, Model.gridSize + 1, i, 1, 1, HPos.CENTER, VPos.CENTER);
			} else if (i == 3) {
				whiteWinCounter.setBoundsType(TextBoundsType.VISUAL);
				blackCircle.setFill(Color.BLACK);
				StackPane blackCircleNText = new StackPane();
				blackCircleNText.getChildren().addAll(blackCircle, blackWinCounter);
				blackWinCounter.setFill(Color.WHITE);
				blackWinCounter.setStyle("-fx-background-color: black;");
				blackWinCounter.setFont(numberFont);
				root.add(blackCircleNText, Model.gridSize + 1, i);
				GridPane.setConstraints(blackCircleNText, Model.gridSize + 1, i, 1, 1, HPos.CENTER, VPos.CENTER);
			} else if (i >= 0) {
				MyButton emptySpace = new MyButton(0);
				emptySpace.setPrefSize(Model.windowSize / 4, Model.butSize);
				emptySpace.setVisible(false);
				root.add(emptySpace, Model.gridSize + 1, i);
			}
		}
		// Construction the 8x8 Grid with 64 buttons
		for (int row = 0; row < Model.gridSize; row++) {
			for (int column = 0; column < Model.gridSize; column++) {
				MyButton myButton = new MyButton(0);
				myButton.setPrefSize(Model.butSize, Model.butSize); // Size of one cell

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

		// Constructs the default 4 pieces in the center of Board
		Model.startFour();

		// Constructs background
		Image image5 = new Image(new File(
				"Adlon3_Amerikansk-Valdnød.jpg").toURI().toString());
		root.setBackground(new Background(new BackgroundImage(image5,
				BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT,
				new BackgroundSize(1.0, 1.0, true, true, false, false))));

		// Makes CSS and sidepane possible
		scene.getStylesheets().add(getClass().getResource("Reversi.css").toExternalForm());

	}

	// Stage setup
	@Override
	public void start(Stage primaryStage) throws MalformedURLException {
		Image image = new Image(new File(
				"reversi-game.jpg").toURI().toString());
		ImageView imageView = new ImageView(image);
		pane.getChildren().add(imageView);

		// Create a new stage
		menuStage.setTitle("Menu");

		// Set the scene for the stage
		menuStage.setScene(menuScene);
		menuStage.setResizable(false);
		menuStage.show();

		// Button to the scene
		ScaleTransition startButten = new ScaleTransition(Duration.millis(300), startGame);
		startButten.setFromX(1);
		startButten.setFromY(1);
		startButten.setToX(1.1);
		startButten.setToY(1.1);

		ScaleTransition startShrink = new ScaleTransition(Duration.millis(200), startGame);
		startShrink.setFromX(1.1);
		startShrink.setFromY(1.1);
		startShrink.setToX(1);
		startShrink.setToY(1);

		startGame.setOnMouseEntered(e -> startButten.playFromStart());
		startGame.setOnMouseExited(e -> startShrink.playFromStart());

		startGame.setPrefSize(300, 60);
		startGame.setText("Start Game");
		pane.getChildren().add(startGame);
		startGame.setLayoutX(pane.getWidth() / 2 - 150);
		startGame.setLayoutY(pane.getHeight() / 2);
		startGame.setStyle("-fx-background-color: #33CC66;"
				+ "-fx-border-color: black;"
				+ "-fx-border-radius: 50;"
				+ " -fx-background-radius: 50;"
				+ " -fx-text-fill : black;"
				+ " -fx-border-width: 2;"
				+ " -fx-font-size : 30px");

		startGame.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				primaryStage.show();
				Model.gameCounter = 0;
				Model.blackWinCounter2 = 0;
				Model.whiteWinCounter2 = 0;
				Model.restartGame(buttons2D);
				menuStage.close();
			}
		});

		backToMenu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				menuStage.show();
				primaryStage.close();
				backgroundMusik.stop();
			}
		});

		ScaleTransition rulesButten = new ScaleTransition(Duration.millis(300), rules);
		rulesButten.setFromX(1);
		rulesButten.setFromY(1);
		rulesButten.setToX(1.1);
		rulesButten.setToY(1.1);

		ScaleTransition rulesShrink = new ScaleTransition(Duration.millis(200), rules);
		rulesShrink.setFromX(1.1);
		rulesShrink.setFromY(1.1);
		rulesShrink.setToX(1);
		rulesShrink.setToY(1);

		rules.setOnMouseEntered(e -> rulesButten.playFromStart());
		rules.setOnMouseExited(e -> rulesShrink.playFromStart());

		rules.setPrefSize(150, 25);
		rules.setText("Game rules");
		pane.getChildren().add(rules);
		rules.setLayoutX(pane.getWidth() / 2 - 75);
		rules.setLayoutY(pane.getHeight() / 2 + 75);
		rules.setStyle("-fx-background-color: #33CC66;"
				+ "-fx-border-color: black;"
				+ "-fx-border-radius: 50;"
				+ " -fx-background-radius: 50;"
				+ " -fx-text-fill : black;"
				+ " -fx-border-width: 2;"
				+ " -fx-font-size : 20px");

		ScaleTransition exitButten = new ScaleTransition(Duration.millis(300), exit);
		exitButten.setFromX(1);
		exitButten.setFromY(1);
		exitButten.setToX(1.1);
		exitButten.setToY(1.1);

		ScaleTransition exitShrink = new ScaleTransition(Duration.millis(200), exit);
		exitShrink.setFromX(1.1);
		exitShrink.setFromY(1.1);
		exitShrink.setToX(1);
		exitShrink.setToY(1);

		exit.setOnMouseEntered(e -> exitButten.playFromStart());
		exit.setOnMouseExited(e -> exitShrink.playFromStart());

		exit.setPrefSize(150, 25);
		exit.setText("Exit");
		pane.getChildren().add(exit);
		exit.setLayoutX(pane.getWidth() / 2 - 75);
		exit.setLayoutY(pane.getHeight() / 2 + 130);
		exit.setStyle("-fx-background-color: #33CC66;"
				+ " -fx-background-radius: 50;"
				+ " -fx-border-color: Black;"
				+ " -fx-border-radius: 50;"
				+ " -fx-text-fill : black;"
				+ " -fx-border-width: 2;"
				+ " -fx-font-size : 20px");

		Gamerules.setTitle("Game rules");

		Pane gamerulesPane = new Pane();
		Image image2 = new Image(new File(
				"Reversi-start.png").toURI().toString());
		ImageView imageView2 = new ImageView(image2);
		gamerulesPane.getChildren().add(imageView2);
        InnerShadow innerShadow = new InnerShadow();
            innerShadow.setColor(Color.BLACK);
            imageView2.setEffect(innerShadow);
		imageView2.setFitWidth(250);
		imageView2.setFitHeight(250);
		imageView2.setLayoutX(gamerulesPane.getWidth() + 100);
		imageView2.setLayoutY(gamerulesPane.getHeight() + 225);

		Image image3 = new Image(new File(
				"Reversi-startv2.png").toURI().toString());
		ImageView imageView3 = new ImageView(image3);
        innerShadow.setColor(Color.BLACK);
            innerShadow.setRadius(5);
            innerShadow.setChoke(0.7);
            imageView3.setEffect(innerShadow);
		gamerulesPane.getChildren().add(imageView3);
		imageView3.setFitWidth(250);
		imageView3.setFitHeight(250);
		imageView3.setLayoutX(gamerulesPane.getWidth() + 450);
		imageView3.setLayoutY(gamerulesPane.getHeight() + 225);

		BacktoMenu.setPrefSize(150, 25);
		BacktoMenu.setText("Back to menu");
		gamerulesPane.getChildren().add(BacktoMenu);
		BacktoMenu.setLayoutX(gamerulesPane.getWidth() / 2 + 650);
		BacktoMenu.setLayoutY(gamerulesPane.getHeight() / 2 + 550);
		BacktoMenu.setStyle("-fx-background-color: #8B4513;"
				+ "-fx-border-color: black;"
				+ "-fx-border-radius: 50;"
				+ " -fx-background-radius: 50;"
				+ " -fx-text-fill : black;"
				+ " -fx-border-width: 2;"
				+ " -fx-font-size : 20px");

		Next.setPrefSize(150, 25);
		Next.setText("Next");
		gamerulesPane.getChildren().add(Next);
		Next.setLayoutX(gamerulesPane.getWidth() / 2 + 475);
		Next.setLayoutY(gamerulesPane.getHeight() / 2 + 550);
		Next.setStyle("-fx-background-color: #8B4513;"
				+ "-fx-border-color: black;"
				+ "-fx-border-radius: 50;"
				+ " -fx-background-radius: 50;"
				+ " -fx-text-fill : black;"
				+ " -fx-border-width: 2;"
				+ " -fx-font-size : 20px");

		Label label = new Label("\nEach reversi piece has a black side and a white side."
				+ "\nOn your turn, you place one piece on the board with your color. "
				+ "\n\nYou must place the piece so that an opponent's piece, or a row of opponent's pieces, is flanked by your pieces."
				+ "\nAll of the opponent's pieces between your pieces are then turned over to become your color. "
				+ "\n\nThe object of the game is to own more pieces than your opponent when the game is over."
				+ "\nThe game is over when neither player has a move. Usually, this means the board is full. ");
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        label.setTextFill(Color.web("White"));
        gamerulesPane.getChildren().add(label);
		label.setLayoutX(gamerulesPane.getWidth() + 10);
		Gamerules.setResizable(false);

		Scene gamerulesScene = new Scene(gamerulesPane, 800, 600);
		Gamerules.setScene(gamerulesScene);
		Image image6 = new Image(new File(
				"Adlon3_Amerikansk-Valdnød.jpg").toURI().toString());
		gamerulesPane.setBackground(new Background(new BackgroundImage(image6,
				BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT,
				new BackgroundSize(1.0, 1.0, true, true, false, false))));

		Gamerules2.setTitle("Game rules");
		Pane gamerulesPane2 = new Pane();

		Image image4 = new Image(new File(
				"Reversi-arrows.png").toURI().toString());
		ImageView imageView4 = new ImageView(image4);
        innerShadow.setColor(Color.BLACK);
            innerShadow.setRadius(5);
            innerShadow.setChoke(0.7);
            imageView4.setEffect(innerShadow);
		gamerulesPane2.getChildren().add(imageView4);
		imageView4.setFitWidth(550);
		imageView4.setFitHeight(325);
		imageView4.setLayoutX(gamerulesPane2.getWidth() + 125);
		imageView4.setLayoutY(gamerulesPane2.getHeight() + 180);

		back.setPrefSize(150, 25);
		back.setText("Back");
		gamerulesPane2.getChildren().add(back);
		back.setLayoutX(gamerulesPane2.getWidth());
		back.setLayoutY(gamerulesPane2.getHeight() + 550);
		back.setStyle("-fx-background-color: #8B4513;"
				+ "-fx-border-color: black;"
				+ "-fx-border-radius: 50;"
				+ " -fx-background-radius: 50;"
				+ " -fx-text-fill : black;"
				+ " -fx-border-width: 2;"
				+ " -fx-font-size : 20px");

		BacktoMenu2.setPrefSize(150, 25);
		BacktoMenu2.setText("Back to menu");
		gamerulesPane2.getChildren().add(BacktoMenu2);
		BacktoMenu2.setLayoutX(gamerulesPane2.getWidth() / 2 + 650);
		BacktoMenu2.setLayoutY(gamerulesPane2.getHeight() / 2 + 550);
		BacktoMenu2.setStyle("-fx-background-color: #8B4513;"
				+ "-fx-border-color: black;"
				+ "-fx-border-radius: 50;"
				+ " -fx-background-radius: 50;"
				+ " -fx-text-fill : black;"
				+ " -fx-border-width: 2;"
				+ " -fx-font-size : 20px");

		Label label2 = new Label(
				"\nThe game starts in the position as shown below and "
						+ "\nthe gray rings on the board are the places where it's legal to place your next piece."
						+ "\n\nOn the right side of the board you can see the current score and who is currently winning."
                        + "\nHere you can also see the timer that starts when it's your turn. If the time runs out the opponent wins."
						+ "\n\nIf you dont have the option to place your piece you can press the 'Pass' button \nand the turn goes to the opponent.");
                label2.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
                label2.setTextFill(Color.web("White"));
                gamerulesPane2.getChildren().add(label2);
		label2.setLayoutX(gamerulesPane2.getWidth() + 10);

		Scene gamerulesScene2 = new Scene(gamerulesPane2, 800, 600);
		Gamerules2.setScene(gamerulesScene2);
		gamerulesPane2.setBackground(new Background(new BackgroundImage(image6,
				BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT,
				new BackgroundSize(1.0, 1.0, true, true, false, false))));

		// SoundFX: Intro-music when open game
		media = new Media(new File("Intro-Reversi.wav").toURI().toString());
		intro = new MediaPlayer(media);
		intro.setVolume(0.1);
		intro.play();

		primaryStage.setScene(scene);
		primaryStage.setTitle("Reversi");
		primaryStage.setResizable(false);

		// Time counter speed-settings:
		double countSpeedRate = 1; // 1 = normal, 0.05 = fast, 2 = slow

		// Create a TimeLine for white
		whiteTimeLine = new Timeline(new KeyFrame(Duration.seconds(countSpeedRate),
				event -> {

					// Start value for count down
					if (Model.whiteMinut == 5) {
						Model.whiteMinut = 4;
					}

					// Displays and updates time
					timeDisplayWhite.textProperty()
							.bind(Bindings.format(" Time: %02d:%02d ", Model.whiteMinut, Model.whiteTimer--));

					// Count down of white timer
					if (Model.whiteTimer == 0) {

						// Winner decision: white time runs out -> black wins
						if (Model.whiteMinut == 0 && Model.whiteTimer == 0) {
							Model.winner = "BLACK";
							Model.winner(new Stage());
							whiteTimeLine.stop();
						}

						Model.whiteMinut--;
						System.out.println("hvid minut: " + Model.whiteMinut);
						Model.whiteTimer = 59;
					}

				}));
		whiteTimeLine.setCycleCount(Timeline.INDEFINITE);

		// Create a TimeLine for black
		blackTimeLine = new Timeline(new KeyFrame(Duration.seconds(countSpeedRate),
				event -> {

					// Start value for count down
					if (Model.blackMinut == 5) {
						Model.blackMinut = 4;
					}

					// Displays and updates time
					timeDisplayBlack.textProperty()
							.bind(Bindings.format(" Time: %02d:%02d ", Model.blackMinut, Model.blackTimer--));

					// Count down of black timer
					if (Model.blackTimer == 0) {

						// Winner decision: black time runs out -> white wins
						if (Model.blackMinut == 0 && Model.blackTimer == 0) {
							Model.winner = "WHITE";
							Model.winner(new Stage());
							blackTimeLine.stop();
						}

						Model.blackMinut--;
						System.out.println("sort minut: " + Model.blackMinut);
						Model.blackTimer = 59;
					}

				}));
		blackTimeLine.setCycleCount(Timeline.INDEFINITE);

	}
}