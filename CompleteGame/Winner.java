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
        Font font = new Font ("Impact", 30);
        layout.getChildren().add(label);
        label.setFont(font);
        
        Button winner = new Button();
        winner.setText("    " + Model.winner + " WINS   ");
        winner.setStyle("-fx-background-radius: 15");
        layout.getChildren().add(winner);
        Font font1 = new Font("Arial Blac", 18);
        winner.setFont(font1);
        winner.setStyle("-fx-background-color: linear-gradient(to bottom, gold, goldenrod);");
        

        Button closeButton = new Button();
        closeButton.setText("    NEW GAME    ");
        closeButton.setOnAction(e -> winStage.close());
        closeButton.setStyle("-fx-background-radius: 15");
        layout.getChildren().add(closeButton);
        Font font11 = new Font("Rockwell", 18);
        closeButton.setFont(font11);
        closeButton.setStyle("-fx-background-color: linear-gradient(to bottom, gold, goldenrod);");

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
        layout.setStyle("-fx-background-color: #33CC66;");
        
    }
    
}