
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Reversi extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	// Global Instance variables her:
	static int windowSize = 600;
	static int gridSize = 8;
	static int clickCount = 0;
	static int butSize = windowSize / 8;
	static String playerTurn;
	MyButton clickedButton;
	MyButton[][] buttons2D = new MyButton[8][8];
	int turn;

	@Override
	public void start(Stage stage) {

		// Constructs pane
		GridPane root = new GridPane();

		// Size of Window
		Scene scene = new Scene(root, windowSize, windowSize);
		stage.setScene(scene);
		stage.show();
		stage.setTitle("Reversi");

		//Construction the 8x8 Grid with 64 buttons
		for( int row = 0; row < gridSize; row++) {
			for( int column = 0; column < gridSize; column++){
				MyButton myButton = new MyButton(0);
                myButton.setStyle("-fx-focus-color: transparent");
				myButton.setPrefSize(butSize,butSize);  //Size of one cell
                    if (column%2==0 && row%2==0){
                        myButton.setStyle("-fx-base: #8B4513");
                    } else {
                        myButton.setStyle("-fx-base: #D2B48C;");
                    } if (column%2!=0 && row%2!=0){
                        myButton.setStyle("-fx-base: #8B4513");
                    }
                    
                
				root.add(myButton,row,column); 
				buttons2D[row][column] = myButton; //Add coordinates and accessibility to all buttons.
                
            }
		
        }

		//////////////////////////////////// On-click
		//////////////////////////////////// function///////////////////////////////////////////

		// Adds the same function for all buttons on gridPane
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				buttons2D[x][y].setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						// FOR DATA TESTING PURPOSE
						System.out.println("clickcount: " + clickCount);
						clickedButton = (MyButton) event.getSource();

						int x = GridPane.getColumnIndex(clickedButton);
						int y = GridPane.getRowIndex(clickedButton);
						System.out.println("clickposition: (" + x + ", " + y + ")");
						System.out.println("legal: " + legal(buttons2D, x, y));
						System.out.println("value " + buttons2D[x][y].getMyValue());
						System.out.println();

						// Actual Code here:
						if (legal(buttons2D, x, y) == true) {
							placePiece(buttons2D[x][y]);
							clickCount++;
						}

					} // End click function
				}); // End Event-handler
			} // Inner loop end
		} // Outer loop end

		clickCount++;
		placePiece(buttons2D[(gridSize / 2) - 1][(gridSize / 2) - 1]);
		clickCount++;
		placePiece(buttons2D[(gridSize / 2)][(gridSize / 2) - 1]);
		clickCount++;
		placePiece(buttons2D[(gridSize / 2)][(gridSize / 2)]);
		clickCount++;
		placePiece(buttons2D[(gridSize / 2) - 1][(gridSize / 2)]);
		clickCount++;

	} // End stage

	////////////////////////////// Action1: Places a piece on the board
	////////////////////////////////////////////////////////////////////////
	public static void placePiece(MyButton clickedButton) {

		// Design of the WHITE piece
		Circle whitePiece = new Circle(butSize / 3 - 1);
		whitePiece.setFill(Color.WHITE);
		whitePiece.setStroke(Color.BLACK);

		// Design of the BLACK piece
		Circle blackPiece = new Circle(butSize / 3 - 2);
		blackPiece.setFill(Color.BLACK);

		// Players turn: White starts
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

	public static void placePiece(MyButton[][] cell, int x, int y, MyButton clickedButton) {

		// Design of the WHITE piece
		Circle whitePiece = new Circle(butSize / 3 - 1);
		whitePiece.setFill(Color.WHITE);
		whitePiece.setStroke(Color.BLACK);

		// Design of the BLACK piece
		Circle blackPiece = new Circle(butSize / 3 - 2);
		blackPiece.setFill(Color.BLACK);

		// Players turn: White starts
		if (clickCount % 2 != 0) {
			clickedButton.setGraphic(whitePiece); // places White piece on-board
			playerTurn = "white";
			cell[x][y].setMyValue(1);

		} else {
			clickedButton.setGraphic(blackPiece); // places Black piece on-board
			playerTurn = "black";
			cell[x][y].setMyValue(-1);
		}
	}

	//////////////////////////////////// Peters kode
	//////////////////////////////////// ///////////////////////////////////////////

	// Tjekker om feltet er tomt, går kun videre hvis der ikke allerede er en værdi
	// tilknyttet feltet
	public boolean legal(MyButton[][] cell, int x, int y) {

		if (cell[x][y].getMyValue() == 0) {
			return omringet(cell, x, y);
		} else {
			return false;
		}
	}

	// Tjekker om der en brik af modsat farve rundt om det valgte felt
	public boolean omringet(MyButton[][] cell, int x, int y) {

		// Først sikres at der ikke kan gåes uden for arrayets størrelse for ikke at få
		// fejl.
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

		// Dernæst tjekkes om modsatte farve er rundt om feltet, hvis ja gåes videre til næste trin
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

	// Tjekker om på den anden side af den modsat farvede brik der din farve brik.
	// Hvis der er samme farve kaldes metoden igen.
	// Hvis der er tomt fortsætter programmet ikke.
	public boolean line(MyButton[][] cell, int side_x, int side_y, int old_x, int old_y) {
		int new_x = side_x + (side_x - old_x);
		int new_y = side_y + (side_y - old_y);
		if (cell[new_x][new_y].getMyValue() == turn * -1) {
			return line(cell, new_x, new_y, side_x, side_y);
		} else if (cell[new_x][new_y].getMyValue() == turn) {
			return true;
		} else {
			return false;
		}
	}

} 	// End class

	// En klasse så en knap kan tilegnes en int værdi.
class MyButton extends Button {

	private int MyValue;

	public MyButton(int MyValue) {
		this.MyValue = MyValue;
	}

	public int getMyValue() {
		return MyValue;
	}

	public void setMyValue(int MyValue) {
		this.MyValue = MyValue;
	}
}