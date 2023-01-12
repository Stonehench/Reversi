package JanuarProject;

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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
	static GridPane root = new GridPane();
	Scene scene = new Scene(root, Model.windowSize + (Model.windowSize / 2), Model.windowSize);
	static Timeline whiteTimeLine = new Timeline();
	static Timeline blackTimeLine = new Timeline();
	static Label timeDisplayWhite = new Label();
	static Label timeDisplayBlack = new Label();
	static Media media = new Media(new File("Intro-Reversi.wav").toURI().toString());
	static MediaPlayer legalMoveSound = new MediaPlayer(media);
	static MediaPlayer IllegalMoveSound = new MediaPlayer(media);

	public View() {
		normal = Font.font(normal.getFamily(), FontWeight.NORMAL, normal.getSize());
		bold = Font.font(bold.getFamily(), FontWeight.BOLD, bold.getSize());

		// The display of Labels "White score" and "Black score" and all the buttons
		timeDisplayWhite = new Label(" Time: 00:00 ");
		timeDisplayBlack = new Label(" Time: 00:00 ");

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
			} else if (i == Model.gridSize - 1) {
				// Replay button
				replay.setPrefSize((Model.windowSize / 4) - (Model.gridSize * 2.5),
						Model.butSize - (Model.gridSize * 2.5)); // Size of the //
				// // button
				replay.setStyle("-fx-base: white;"); // Button color
				replay.setStyle("-fx-background-radius: 50"); // Gives button smooth edges
				root.add(replay, Model.gridSize, i);
				GridPane.setConstraints(replay, Model.gridSize + 1, i, 1, 1, HPos.CENTER, VPos.CENTER);
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

		// Constructs pane
		root.setStyle("-fx-background-color: #33CC66;"); // Sets backgground color

		// Makes CSS and sidepane possible
		scene.getStylesheets().add(getClass().getResource("Reversi.css").toExternalForm());

	}

	// Stage setup
	@Override
	public void start(Stage primaryStage) {

		primaryStage.setScene(scene);
		primaryStage.setTitle("Reversi");
		primaryStage.setResizable(false);
		primaryStage.show();

		// Create a TimeLine for white
		// Settings:
		double countSpeedRate = 1; // 1 = normal, ? < 1 = faster, ? > 1 = slower

		whiteTimeLine = new Timeline(new KeyFrame(Duration.seconds(countSpeedRate),
				event -> {
					// Displays and updates time
					timeDisplayWhite.textProperty()
							.bind(Bindings.format(" Time: %02d:%02d ", Model.whiteMinut, Model.whiteTimer++));
					// Every 60 second, store 1 minute
					if (Model.whiteTimer % 60 == 0) {
						Model.whiteMinut++;
						System.out.println("sort minut: " + Model.whiteMinut);
						Model.whiteTimer = 0; // Reset seconds to 00
					}
				}));
		whiteTimeLine.setCycleCount(Timeline.INDEFINITE);

		// Create a TimeLine for white
		blackTimeLine = new Timeline(new KeyFrame(Duration.seconds(countSpeedRate),
				event -> {

					// Displays and updates time
					timeDisplayBlack.textProperty()
							.bind(Bindings.format(" Time: %02d:%02d ", Model.blackMinut, Model.blackTimer++));
					// Every 60 second, store 1 minute
					if (Model.blackTimer % 60 == 0) {
						Model.blackMinut++;
						System.out.println("sort minut: " + Model.blackMinut);
						Model.blackTimer = 0; // Reset seconds to 00
					}
				}));
		blackTimeLine.setCycleCount(Timeline.INDEFINITE);

	}
}
