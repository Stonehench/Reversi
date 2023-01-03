package januarProjektet2023;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class JanuarProject2023 extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	//Global Instance variables her:
	static int windowSize = 600;
	static int gridSize = 8;
	static int clickCount = 0;
	static int butSize = windowSize/8;
	static String playerTurn;
	Button clickedButton;
	Button[][] buttons2D = new Button[8][8];
	


	@Override
	public void start(Stage stage) {        


		//Constructs pane
		GridPane root = new GridPane();

		//Size of Window
		Scene scene = new Scene(root, windowSize, windowSize);
		stage.setScene(scene);
		stage.show();
		stage.setTitle("Reversi");
	
		
		//Construction the 8x8 Grid with 64 buttons
		for( int row = 0; row < gridSize; row++) {
			for( int column = 0; column < gridSize; column++){
				Button myButton = new Button();
				myButton.setPrefSize(butSize,butSize);  //Size of one cell
				root.add(myButton,row,column); 
				buttons2D[row][column] = myButton; //Add coordinates and accessibility to all buttons.
			}
		}

		
		////////////////////////////////////On-click function///////////////////////////////////////////
		
		//Adds the same function for all buttons on gridPane
		for( int x = 0; x < gridSize; x++) {
			for( int y = 0; y < gridSize; y++){
				buttons2D[x][y].setOnAction(new EventHandler<ActionEvent>() {

		        	@Override
		        	public void handle(ActionEvent event) {
		        		clickCount++;
		        		
		        		//FOR DATA TESTING PURPOSE
		        		System.out.println("clickcount: " + clickCount);
		        		clickedButton = (Button) event.getSource();
		        		
		        		int x = GridPane.getRowIndex(clickedButton);
		        		int y = GridPane.getColumnIndex(clickedButton);
		        		System.out.println("clickposition: (" + x + ", " + y + ")");
		        		
		        		
		        		//Actual Code here:
		        		boolean tjekPetersCode = true; // hehe :D
		        		
		        		if (tjekPetersCode == true) {
		        			placePiece(clickedButton);	
		        		}
		     
		        	} //End click function
		        }); //End Event-handler
			} //Inner loop end
		} //Outer loop end
		
		
	} //End stage


	
	//////////////////////////////Action1: Places a piece on the board //////////////////////////////////////////
	public static void placePiece(Button clickedButton){
		
		//Design of the WHITE piece
		Circle whitePiece = new Circle(butSize/3 -1);
		whitePiece.setFill(Color.WHITE);
		whitePiece.setStroke(Color.BLACK);
		
		//Design of the BLACK piece
		Circle blackPiece = new Circle(butSize/3 -2);
		blackPiece.setFill(Color.BLACK);
		
		
		/////KOMMENTAR OG HUSK!
		//TEST: Integeres med Peters kode...
		//TODO: Sørge for at kunne identificer hver knaps cirkel, for derved at sammenligne dem.
		//Skal bruges til peters del
				if (clickedButton.getGraphic() == whitePiece) {
					System.out.println("Du trykkede på en allerede hvid");
					
				}
		
		
		//Players turn: White starts
		if (clickCount % 2 != 0) {
			clickedButton.setGraphic(whitePiece); //places White piece on-board
			playerTurn = "white";
		} else { 
			clickedButton.setGraphic(blackPiece); //places Black piece on-board
			playerTurn = "black";
		}

		


	}
		


} //End class