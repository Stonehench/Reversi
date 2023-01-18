package completeReversiGame;

import javafx.animation.FillTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Model {
    static int gameCounter = 0;
    static int availableMoves = 0;
    static int fullBoard;
    static int gridSize = 8;
    static int windowSize = 600;
    static int clickCount = 0;
    static int butSize = windowSize / gridSize;
    static int numberOfWhite;
    static int numberOfBlack;
    static int turn;
    static int blackTimer = 59; 
    static int blackMinut = 5; 
    static int whiteTimer = 59; 
    static int whiteMinut = 5; 
    static int clickedMute;
    static int whiteWinCounter2 = 0;
    static int blackWinCounter2 = 0;
    static int blackValue;
    static int whiteValue;
    static int numberOfBoards;
    static boolean soundOn = true;
    static String winner = "";

    public static void placePiece(MyButton clickedButton) {

        // Design of the WHITE piece
        View.whitePiece = new Circle(butSize / 3 - gridSize / 7.5);
        View.whitePiece.setFill(Color.WHITE);
        View.whitePiece.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        LinearGradient fadeWhiteGrey = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.WHITE), new Stop(1, Color.GREY));
        View.whitePiece.setFill(fadeWhiteGrey);

        // Design of the BLACK piece
        View.blackPiece = new Circle(butSize / 3 - (gridSize / 6) + 1);
        View.blackPiece.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        View.blackPiece.setStroke(Color.BLACK);
        LinearGradient fadeBlackGrey = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(1, Color.DIMGREY), new Stop(0.5, Color.BLACK));
        View.blackPiece.setFill(fadeBlackGrey);

        // Adjust the TransitionSpeed
        int tSpeed = 1;
        double rSpeed = 0.40;

        // White turn:
        if (clickCount % 2 != 0) {

            // Animation of white capture black pieces
            if (clickedButton.getMyValue() != 0) {
                FillTransition blackToWhite = new FillTransition(Duration.seconds(tSpeed), View.whitePiece);
                blackToWhite.setToValue(Color.WHITE);
                blackToWhite.setCycleCount(Timeline.INDEFINITE);
                blackToWhite.play();

                // Rotation of captured white pieces
                RotateTransition rtWhite = new RotateTransition(Duration.seconds(rSpeed), View.whitePiece);
                rtWhite.setByAngle(180);
                rtWhite.setCycleCount(1);
                rtWhite.setAxis(Rotate.Y_AXIS);
                rtWhite.play();
            }
            clickedButton.setGraphic(View.whitePiece); // places White piece on-board
            clickedButton.setMyValue(1);
            View.playerTurn = "white";

            // Blacks turn:
        } else {

            // Animation of black capture white pieces
            if (clickedButton.getMyValue() != 0) {
                FillTransition whiteToBlack = new FillTransition(Duration.seconds(tSpeed), View.blackPiece);
                whiteToBlack.setToValue(Color.BLACK);
                whiteToBlack.setCycleCount(1);
                whiteToBlack.play();

                // Rotation of captured black pieces
                RotateTransition rtBlack = new RotateTransition(Duration.seconds(rSpeed), View.blackPiece);
                rtBlack.setByAngle(180);
                rtBlack.setCycleCount(1);
                rtBlack.setAxis(Rotate.Y_AXIS);
                rtBlack.play();

            }
            clickedButton.setGraphic(View.blackPiece); // places Black piece on-board
            clickedButton.setMyValue(-1);
            View.playerTurn = "black";
        }
    }

    // Used for transparent circles to highlight legal moves
    public static void placePieceTransparent(MyButton[][] cell, int x, int y) {

        double centerX = butSize / 2;
        double centerY = butSize / 2;
        double radiusX = (butSize / 6) + butSize / gridSize;
        double radiusY = (butSize / 6) + butSize / gridSize;

        Ellipse blackRing = new Ellipse(centerX, centerY, radiusX, radiusY);
        blackRing.setFill(null);
        blackRing.setStroke(Color.GRAY);
        blackRing.setStrokeWidth(butSize / 8);

        cell[x][y].setGraphic(blackRing);
    }

    // Checks if every move is "Legal" else "Illegal" according to "Rules of
    // Reversi"
    public static boolean legal(MyButton[][] cell, int x, int y) {

        if (cell[x][y].getMyValue() == 0) {
            return surrounded(cell, x, y);
        } else {
            return false;
        }
    }

    // Checks if there are enemy pieces around the placed-Piece
    public static boolean surrounded(MyButton[][] cell, int x, int y) {

        // Coordinates E/W/S/N of the piece placed
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

        // The actual boolean check for if enemy piece around your piece
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

    // Checks the whole row, column and diagonal for enemy pieces.
    // If your own piece --> Recursive method call
    // If empty --> Break and return false.
    public static boolean line(MyButton[][] cell, int side_x, int side_y, int old_x, int old_y) {
        int new_x = side_x + (side_x - old_x);
        int new_y = side_y + (side_y - old_y);

        if (new_x >= gridSize) {
            return false;
        }
        if (new_x < 0) {
            return false;
        }
        if (new_y >= gridSize) {
            return false;
        }
        if (new_y < 0) {
            return false;
        }

        if (cell[new_x][new_y].getMyValue() == turn * -1) {
            return line(cell, new_x, new_y, side_x, side_y);
        } else if (cell[new_x][new_y].getMyValue() == turn) {
            return true;
        } else {
            return false;
        }
    }

    // Starts the process of capturing a piece
    public static void capturePiece(MyButton[][] cell, int x, int y) {

        // Coordinates E/W/S/N of the piece placed
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

        // The actual boolean check for if enemy piece around your piece
        // Thereafter calls method to tjek if there's an allied
        // Piece on the other side of the enemy piece
        // If true then it calls the method to change the pieces
        // Repeats in all 8 directions
        if (cell[right][down].getMyValue() == turn * -1) {
            if (line(cell, right, down, x, y)) {
                changePiece(cell, right, down, x, y);
            }
        }
        if (cell[right][y].getMyValue() == turn * -1) {
            if (line(cell, right, y, x, y)) {
                changePiece(cell, right, y, x, y);
            }
        }
        if (cell[right][up].getMyValue() == turn * -1) {
            if (line(cell, right, up, x, y)) {
                changePiece(cell, right, up, x, y);
            }
        }
        if (cell[x][down].getMyValue() == turn * -1) {
            if (line(cell, x, down, x, y)) {
                changePiece(cell, x, down, x, y);
            }
        }
        if (cell[x][up].getMyValue() == turn * -1) {
            if (line(cell, x, up, x, y)) {
                changePiece(cell, x, up, x, y);
            }
        }
        if (cell[left][down].getMyValue() == turn * -1) {
            if (line(cell, left, down, x, y)) {
                changePiece(cell, left, down, x, y);
            }
        }
        if (cell[left][y].getMyValue() == turn * -1) {
            if (line(cell, left, y, x, y)) {
                changePiece(cell, left, y, x, y);
            }
        }
        if (cell[left][up].getMyValue() == turn * -1) {
            if (line(cell, left, up, x, y)) {
                changePiece(cell, left, up, x, y);
            }

        }
    }

    // changes the pieces inbetween the placed piece and a same colored piece
    public static void changePiece(MyButton[][] cell, int side_x, int side_y, int old_x, int old_y) {
        int new_x = side_x + (side_x - old_x);
        int new_y = side_y + (side_y - old_y);

        if (new_x >= gridSize) {
            return;
        }
        if (new_x < 0) {
            return;
        }
        if (new_y >= gridSize) {
            return;
        }
        if (new_y < 0) {
            return;
        }

        // Changes piece color
        placePiece(cell[side_x][side_y]);

        // Contiunes code until it hits a same colored piece
        if (cell[new_x][new_y].getMyValue() == turn * -1) {
            changePiece(cell, new_x, new_y, side_x, side_y); // Rekursiv
        } else if (cell[new_x][new_y].getMyValue() == turn) {
            return;
        }
    }

    // Highlight function
    public static void highlight() {
        availableMoves = 0;
        fullBoard = 0;
        numberOfBlack = 0;
        numberOfWhite = 0;
        numberOfBoards++;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (legal(View.buttons2D, i, j)) {
                    placePieceTransparent(View.buttons2D, i, j);
                    availableMoves++;
                    fullBoard++;
                } else if (View.buttons2D[i][j].getMyValue() == 0) {
                    View.buttons2D[i][j].setGraphic(null);
                    fullBoard++;
                } else if (View.buttons2D[i][j].getMyValue() == 1) {
                    numberOfWhite++;
                } else if (View.buttons2D[i][j].getMyValue() == -1) {
                    numberOfBlack++;
                }
            }
        }
        if (availableMoves == 0) {
            // Gøre pass knappen mulig at bruge
            View.pass.setDisable(false);
            clickCount++;
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    if (legal(View.buttons2D, i, j)) {
                        availableMoves++;
                    }
                }
            }
            System.out.println("available moves "+availableMoves);
            if (availableMoves == 0 && numberOfBlack < numberOfWhite && fullBoard != 0) {
                winner = "WHITE";
                winner(new Stage());
            } else if (availableMoves == 0 && numberOfBlack > numberOfWhite && fullBoard != 0) {
                winner = "BLACK";
                winner(new Stage());
            } else if (availableMoves == 0 && numberOfBlack == numberOfWhite && fullBoard != 0) {
                winner = "NOBODY";
                winner(new Stage());
            }
            clickCount--;
        } else {
            View.pass.setDisable(true);
        }
        if (numberOfWhite == 0) {
            winner = "BLACK";
            winner(new Stage());
        }
        if (numberOfBlack == 0) {
            winner = "WHITE";
            winner(new Stage());
        }
        if (fullBoard == 0 && numberOfBlack > numberOfWhite) {
            winner = "BLACK";
            winner(new Stage());
        } else if (fullBoard == 0 && numberOfBlack < numberOfWhite) {
            winner = "WHITE";
            winner(new Stage());
        } else if (fullBoard == 0 && numberOfBlack == numberOfWhite) {
            winner = "NOBODY";
            winner(new Stage());
        }
        
        
        
    }

    // Highlights player turn in label
    public static void focusPlayer() {
        if (clickCount % 2 != 0) {
            View.whiteScore.setFont(View.bold);
            View.blackScore.setFont(View.normal);
            View.timeDisplayWhite.setFont(View.bold);
            View.timeDisplayBlack.setFont(View.normal);

        } else {
            View.whiteScore.setFont(View.normal);
            View.blackScore.setFont(View.bold);
            View.timeDisplayWhite.setFont(View.normal);
            View.timeDisplayBlack.setFont(View.bold);
        }
    }

    // Constructs the default 4 pieces in the center of Board
    public static void startFour() {
        clickCount++;
        placePiece(View.buttons2D[(gridSize / 2) - 1][(gridSize / 2) - 1]);
        clickCount++;
        placePiece(View.buttons2D[(gridSize / 2)][(gridSize / 2) - 1]);
        clickCount++;
        placePiece(View.buttons2D[(gridSize / 2)][(gridSize / 2)]);
        clickCount++;
        placePiece(View.buttons2D[(gridSize / 2) - 1][(gridSize / 2)]);
        clickCount++;
        highlight();
        View.whiteScore.setText("White Score: " + numberOfWhite);
        View.blackScore.setText("Black Score: " + numberOfBlack);
        focusPlayer();
    }

    // Pops up with a little window declaring the winner
    public static void winner(Stage winStage) {
        new Winner().start(winStage);
    }

    public static void restartGame(MyButton[][] cell) {
        gameCounter++;

        clickCount = 0;

        // Reset the timer
        blackValue = 2;
        whiteValue = 2;
        blackTimer = 59; // new
        blackMinut = 5; // new
        whiteTimer = 59; // new
        whiteMinut = 5; // new

        // Reset time display
        View.whiteTimeLine.stop();
        View.blackTimeLine.stop();
        View.timeDisplayWhite.textProperty().unbind();
        View.timeDisplayBlack.textProperty().unbind();
        View.timeDisplayWhite.setText(" Time: 05:00 ");
        View.timeDisplayBlack.setText(" Time: 05:00 ");

        // Update GUI to reflect reset game state
        View.blackScore.setText("Black Score: 2");
        View.whiteScore.setText("White Score: 2");
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {

                cell[row][column].setGraphic(null); // Add coordinates and accessibility to all buttons.
                cell[row][column].setMyValue(0);
            }
        }
        if (gameCounter % 2 != 0) {
            clickCount++;
        }
        startFour();
        View.whiteWinCounter.setText("" + whiteWinCounter2);
        View.blackWinCounter.setText("" + blackWinCounter2);
    }

}