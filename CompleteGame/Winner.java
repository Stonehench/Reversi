package JanuarProject;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Winner extends Application{

    @Override
    public void start(Stage winStage) {
        
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.setTitle("Game over");
        winStage.setMinWidth(400);
        winStage.setMinHeight(200);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Font font = new Font("Impact", 20);

        Label label = new Label("THE WINNER IS " + Model.winner + " !!!!!");
        label.setFont(font);
        layout.getChildren().add(label);

        Button closeButton = new Button();
        closeButton.setText("Start a New Game");
        closeButton.setOnAction(e -> winStage.close());
        closeButton.setStyle("-fx-background-radius: 15");
        layout.getChildren().add(closeButton);

        closeButton.setOnAction(event -> {
            if (Model.winner.equals("BLACK")) {
                Model.blackWinCounter2++;
            } else if (Model.winner.equals("WHITE")) {
                Model.whiteWinCounter2++;
            }
            Model.restartGame(View.buttons2D);
            winStage.close();
        });

        Scene scene = new Scene(layout);
        winStage.setScene(scene);
        winStage.show();
        layout.setStyle("-fx-background-color: #33CC66;");
        
    }
    
}
