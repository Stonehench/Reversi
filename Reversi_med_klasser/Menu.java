package JanuarProject;

import java.io.File;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Menu extends Application {

    static Stage Gamerules = new Stage();
    static Stage Gamerules2 = new Stage();
    static Button startGame = new Button();
    static Button rules = new Button();
    static Button exit = new Button();
    static Button BacktoMenu = new Button();
    static Button BacktoMenu2 = new Button();
    static Button Next = new Button();
    static Button back = new Button("Back");
    static Media media = new Media(new File("Intro-Reversi.wav").toURI().toString());
    static MediaPlayer intro = new MediaPlayer(media);
    static Pane pane = new Pane();
    Scene menuScene = new Scene(pane, 1200, 675);
    static Stage menuStage = new Stage();

    @Override
    public void start(Stage first) throws Exception {
        menuStage = first;
        Image image = new Image(new File(
                "lib/billeder/reversi-game.jpg").toURI().toURL().toString());
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
                "lib/billeder/Reversi-start.png").toURI().toURL().toString());
        ImageView imageView2 = new ImageView(image2);
        gamerulesPane.getChildren().add(imageView2);
        imageView2.setFitWidth(250);
        imageView2.setFitHeight(250);
        imageView2.setLayoutX(gamerulesPane.getWidth() + 100);
        imageView2.setLayoutY(gamerulesPane.getHeight() + 225);

        Image image3 = new Image(new File(
                "lib/billeder/Reversi-startv2.png").toURI().toURL().toString());
        ImageView imageView3 = new ImageView(image3);
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
                + "\nYou must place the piece so that an opponent's piece, or a row of opponent's pieces, is flanked by your pieces."
                + "\nAll of the opponent's pieces between your pieces are then turned over to become your color. "
                + "\n\nThe object of the game is to own more pieces than your opponent when the game is over."
                + "\nThe game is over when neither player has a move. Usually, this means the board is full. ");
        gamerulesPane.getChildren().add(label);
        label.setLayoutX(gamerulesPane.getWidth() + 10);
        Gamerules.setResizable(false);

        Scene gamerulesScene = new Scene(gamerulesPane, 800, 600);
        Gamerules.setScene(gamerulesScene);
        gamerulesPane.setStyle("-fx-background-color: #33CC66;"
                + " -fx-font-size : 17px;");

        Gamerules2.setTitle("Game rules");
        Pane gamerulesPane2 = new Pane();

        Image image4 = new Image(new File(
                "lib/billeder/Reversi-arrows.png").toURI().toURL().toString());
        ImageView imageView4 = new ImageView(image4);
        gamerulesPane2.getChildren().add(imageView4);
        imageView4.setFitWidth(550);
        imageView4.setFitHeight(325);
        imageView4.setLayoutX(gamerulesPane2.getWidth() + 200);
        imageView4.setLayoutY(gamerulesPane2.getHeight() + 225);

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
                "\nThe game is started in the position shown below on a reversi board consisting of 64 squares in an 8x8 grid. "
                        + "\n\nThe gray rings on the board are the places where it's legal to place your next piece."
                        + "\n\nOn the right side of the board you can see the current score and who is winnig."
                        + "\n\nYou can press the 'Pass' button on the right side if you dont have an option to place your piece \nand the turn goes to the opponent.");
        gamerulesPane2.getChildren().add(label2);
        label2.setLayoutX(gamerulesPane2.getWidth() + 10);

        Scene gamerulesScene2 = new Scene(gamerulesPane2, 800, 600);
        Gamerules2.setScene(gamerulesScene2);
        gamerulesPane2.setStyle("-fx-background-color: #33CC66;"
                + " -fx-font-size : 17px;");

        // SoundFX: Intro-music when open game
        media = new Media(new File("Intro-Reversi.wav").toURI().toString());
        intro = new MediaPlayer(media);
        intro.setVolume(0.1);
        intro.play();

        
    }

}
