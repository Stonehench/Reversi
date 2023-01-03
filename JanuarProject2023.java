
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

		// Construction the 8x8 Grid with 64 buttons
		for (int row = 0; row < gridSize; row++) {
			for (int column = 0; column < gridSize; column++) {
				MyButton myButton = new MyButton(0);
				myButton.setPrefSize(butSize, butSize); // Size of one cell
				root.add(myButton, row, column);
				buttons2D[row][column] = myButton; // Add coordinates and accessibility to all buttons.
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

						clickCount++;

						// FOR DATA TESTING PURPOSE
						System.out.println("clickcount: " + clickCount);
						clickedButton = (MyButton) event.getSource();

						int x = GridPane.getRowIndex(clickedButton);
						int y = GridPane.getColumnIndex(clickedButton);
						System.out.println("clickposition: (" + x + ", " + y + ")");

						System.out.println("");

						// Actual Code here:
						if (lovlighed(buttons2D, x, y) == true) {
							placePiece(clickedButton);
						}

					} // End click function
				}); // End Event-handler
			} // Inner loop end
		} // Outer loop end

		placePiece(buttons2D[(gridSize / 2) - 1][(gridSize / 2) - 1]);
		placePiece(buttons2D[(gridSize / 2)][(gridSize / 2) - 1]);
		placePiece(buttons2D[(gridSize / 2)][(gridSize / 2)]);
		placePiece(buttons2D[(gridSize / 2) - 1][(gridSize / 2)]);

	} // End stage

	////////////////////////////// Action1: Places a piece on the board
	////////////////////////////// //////////////////////////////////////////
	public static void placePiece(MyButton clickedButton) {
		clickCount++;

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
			clickedButton.setMyIndex(1);

		} else {
			clickedButton.setGraphic(blackPiece); // places Black piece on-board
			playerTurn = "black";
			clickedButton.setMyIndex(-1);
		}
	}

	//////////////////////////////////// Peters kode
	//////////////////////////////////// ///////////////////////////////////////////

	// Tjekker om feltet er tomt, går kun videre hvis der ikke allerede er en værdi
	// tilknyttet feltet
	public boolean lovlighed(MyButton[][] felt, int x, int y) {

		if (felt[x][y].getMyIndex() != 0) {
			return omringet(felt, x, y);
		} else {
			return false;
		}
	}

	// Tjekker om der en brik af modsat farve rundt om det valgte felt
	public boolean omringet(MyButton[][] felt, int x, int y) {

		// Først sikres at der ikke kan gåes uden for arrayets størrelse for ikke at få
		// fejl.
		int op = x + 1;
		int ned = x - 1;

		int højre = y + 1;
		int venstre = y - 1;

		if (clickCount % 2 != 0) {
			turn = 1;

		} else {
			turn = -1;
		}

		if (op >= gridSize) {
			op = gridSize - 1;
		}
		if (ned < 0) {
			ned = 0;
		}
		if (højre >= gridSize) {
			højre = gridSize - 1;
		}
		if (venstre < 0) {
			venstre = 0;
		}

		// Dernæst tjekkes om modsatte farve er rundt om feltet
		if (felt[op][højre].getMyIndex() == turn*-1) {
			return linje(felt, op, højre, x, y);
		}
		if (felt[op][y].getMyIndex() == turn*-1) {
			return linje(felt, op, y, x, y);
		}
		if (felt[op][venstre].getMyIndex() == turn*-1) {
			return linje(felt, op, venstre, x, y);
		}
		if (felt[x][højre].getMyIndex() == turn*-1) {
			return linje(felt, x, højre, x, y);
		}
		if (felt[x][venstre].getMyIndex() == turn*-1) {
			return linje(felt, x, venstre, x, y);
		}
		if (felt[ned][højre].getMyIndex() == turn*-1) {
			return linje(felt, ned, højre, x, y);
		}
		if (felt[ned][y].getMyIndex() == turn*-1) {
			return linje(felt, ned, y, x, y);
		}
		if (felt[ned][venstre].getMyIndex() == turn*-1) {
			return linje(felt, ned, venstre, x, y);
		} else {
			return false;
		}
	}

	// Tjekker om på den anden side af den modsat farvede brik der din farve brik.
	// Hvis der er samme farve kaldes metoden igen.
	// Hvis der er tomt fortsætter programmet ikke.
	public boolean linje(MyButton[][] felt, int side_x, int side_y, int gammel_x, int gammel_y) {
		int ny_x = side_x + (side_x - gammel_x);
		int ny_y = side_y + (side_y - gammel_y);
		if (felt[ny_x][ny_y].getMyIndex() == turn*-1) {
			return linje(felt, ny_x, ny_y, side_x, side_y);
		} else if (felt[ny_x][ny_y].getMyIndex() == turn) {
			return true;
		} else {
			return false;
		}
	}

} // End class

class MyButton extends Button {

	private int myIndex;

	public MyButton(int myIndex) {
		this.myIndex = myIndex;
	}

	public int getMyIndex() {
		return myIndex;
	}

	public void setMyIndex(int myIndex) {
		this.myIndex = myIndex;
	}
}