package completeReversiGame;
import java.io.File;

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
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

        Button closeButton = new Button();
        closeButton.setText("    NEW GAME    ");
        closeButton.setOnAction(e -> winStage.close());
        closeButton.setStyle("-fx-background-radius: 15");
        layout.getChildren().add(closeButton);
        Font font11 = new Font("Arial", 18);
        closeButton.setFont(font11);
        closeButton.setStyle("-fx-background-color: linear-gradient(to bottom, gold, goldenrod);");

        closeButton.setOnAction(event -> {
        	Model.winnerFound = false;
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
        
    }
    
}