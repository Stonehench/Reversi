package Demonstration;

import java.io.File;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Winner extends Application{

	public static Button backToMenu2 = new Button();

	@Override
	public void start(Stage winStage) {

		winStage.initModality(Modality.APPLICATION_MODAL);
		winStage.setTitle("Game over");
		winStage.setMinWidth(400);
		winStage.setMinHeight(200);

		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		Label label = new Label("GAME OVER");
		Font font = new Font ("Impact", 25);
		layout.getChildren().add(label);
		label.setFont(font);

		Text text = new Text("  "+ Model.winner + " WINS  ");
		text.setFill(Paint.valueOf("linear-gradient(to bottom, gold, goldenrod)"));
		text.setFont(new Font("Impact",50));
		layout.getChildren().add(text);

		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(3));
		transition.setNode(text);
		transition.setFromY(-100);
		transition.setToY(0);
		transition.play();

		Button closeButton = new Button();
		closeButton.setText("  NEW GAME  ");
		closeButton.setOnAction(e -> winStage.close());
		closeButton.setStyle("-fx-background-radius: 18");
		layout.getChildren().add(closeButton);
		Font font11 = new Font("Arial", 12);
		closeButton.setFont(font11);
		closeButton.setStyle("-fx-background-color: linear-gradient(to bottom, green, #33CC66);"
				+ "-fx-border-color: black;"
				+ "-fx-border-radius: 15;"
				+ " -fx-background-radius: 15;"
				+ " -fx-text-fill : black;"
				+ " -fx-border-width: 1;"
				+ " -fx-font-size : 15px");

		closeButton.setOnMouseEntered(e -> closeButton.setScaleX(1.2));
		closeButton.setOnMouseExited(e -> closeButton.setScaleX(1));
		closeButton.setOnMouseEntered(e -> closeButton.setScaleY(1.2));
		closeButton.setOnMouseExited(e -> closeButton.setScaleY(1));

		closeButton.setOnAction(event -> {
			if (Model.winner.equals("BLACK")) {
				Model.blackWinCounter2++;
			} else if (Model.winner.equals("WHITE")) {
				Model.whiteWinCounter2++;
			}
			Model.restartGame(View.buttons2D);
			winStage.close();
		});

		Scene scene = new Scene(layout,300, 200);
		winStage.setScene(scene);
		winStage.show();

		Image image5 = new Image(new File(
				"Adlon3_Amerikansk-Valdnød.jpg").toURI().toString());
		layout.setBackground(new Background(new BackgroundImage(image5,
				BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT,
				new BackgroundSize(1.0, 1.0, true, true, false, false))));

		View.media = new Media(new File("victory.mp3").toURI().toString());
		View.backgroundMusik = new MediaPlayer(View.media);
		View.backgroundMusik.setVolume(0.1);
		View.backgroundMusik.play();
		View.backgroundMusik.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				View.backgroundMusik.stop();
			}
		});

	}

}
