

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class BoilerPlate extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    //Instance variables
int gridSize = 600;


    
    @Override
    public void start(Stage stage) {        
       
    
    	//Constructs pane
    	GridPane root = new GridPane();
    	
        //Size of Window
        Scene scene = new Scene(root, gridSize, gridSize);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Reversi");
        Button button1 = new Button();
        root.getChildren().addAll(button1);
        
        
       for( int row = 0; row<8; row++) {
            for( int column = 0; column<8; column++){
                Button felt = new Button();
                felt.setPrefSize(gridSize/8,gridSize/8);
                root.add(felt,row,column);
                
        
            }
            
        }
        






        
    }
    
    
    
}