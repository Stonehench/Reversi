package JanuarProject;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Controller {

    public Controller(Stage primaryStage) {
        // On-click action for mute: Mute all sound FX
        View.mute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Model.clickedMute++;

                if (Model.clickedMute % 2 != 0) {
                    Model.soundOn = false;
                    View.mute.setText("SoundFX: off");
                } else {
                    Model.soundOn = true;
                    View.mute.setText("SoundFX: on");
                }
            }
        });
        // Adds all the functionality for when a button is clicked
        for (int x = 0; x < Model.gridSize; x++) {
            for (int y = 0; y < Model.gridSize; y++) {
                View.buttons2D[x][y].setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        View.clickedButton = (MyButton) event.getSource();
                        int x = GridPane.getColumnIndex(View.clickedButton);
                        int y = GridPane.getRowIndex(View.clickedButton);

                        // Checks if the clicked move is legal/illegal
                        if (Model.legal(View.buttons2D, x, y) == true) {

                            Model.placePiece(View.buttons2D[x][y]);
                            Model.capturePiece(View.buttons2D, x, y); // Check if there is a capture
                            Model.clickCount++;
                            Model.highlight();
                            View.whiteScore.setText("White score " + Model.numberOfWhite);
                            View.blackScore.setText("Black score " + Model.numberOfBlack);
                            Model.focusPlayer();

                            // Whites turn
                            if (Model.clickCount % 2 != 0) {
                                View.whiteScore.setFont(View.bold);
                                View.blackScore.setFont(View.normal);
                                View.timeDisplayWhite.setFont(View.bold);
                                View.timeDisplayBlack.setFont(View.normal);

                                System.out.println("White counter started: ");
                                View.whiteTimeLine.play();
                                View.blackTimeLine.stop();

                            }
                            // Blacks turn
                            else {
                                View.whiteScore.setFont(View.normal);
                                View.blackScore.setFont(View.bold);
                                View.timeDisplayWhite.setFont(View.normal);
                                View.timeDisplayBlack.setFont(View.bold);

                                System.out.println("Black counter started: ");
                                View.blackTimeLine.play();
                                View.whiteTimeLine.stop();

                            }

                            // SoundFX every time you make a "Legal" move.
                            View.media = new Media(new File("Reversi-game-sound.wav").toURI().toString());
                            View.legalMoveSound = new MediaPlayer(View.media);
                            View.legalMoveSound.setVolume(0.1);
                            if (Model.soundOn) {
                                View.legalMoveSound.play();
                            }

                        } else {
                            // SoundFX every time you make a "Illegal" move.
                            View.media = new Media(new File("Sounds_whoosh.mp3").toURI().toString());
                            View.IllegalMoveSound = new MediaPlayer(View.media);
                            View.IllegalMoveSound.setVolume(0.1);
                            if (Model.soundOn) {
                                View.IllegalMoveSound.play();
                            }

                        } // End if-else

                        // FOR DATA TESTING PURPOSE
                        System.out.println("----------------------");
                        System.out.println("clickcount: " + Model.clickCount);
                        System.out.println("clickposition: (" + x + ", " + y + ")");
                        System.out.println("Player: " + View.playerTurn);
                        System.out.println("value " + View.buttons2D[x][y].getMyValue());
                        System.out.println("turn: " + Model.turn);
                        System.out.println("legal: " + Model.legal(View.buttons2D, x, y));
                        System.out.println("");

                    } // End click function
                }); // End Event-handler
            } // Inner loop end
        } // Outer loop end

        // Pass button
        View.pass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                // Skip the current player's turn
                if (Model.turn == -1) {
                    Model.clickCount++;
                    View.playerTurn = "white";
                    Model.highlight();
                    Model.focusPlayer();

                } else {
                    View.playerTurn = "black";
                    Model.clickCount++;
                    Model.highlight();
                    Model.focusPlayer();
                }
            }
        });

        // Restart button
        View.restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                Model.restartGame(View.buttons2D);
            }
        });

        View.Next.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                View.Gamerules2.show();
                View.Gamerules.close();
            }
        });

        View.back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                View.Gamerules.show();
                View.Gamerules2.close();
            }
        });

        View.BacktoMenu2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                View.menuStage.show();
                View.Gamerules2.close();
            }
        });

        View.BacktoMenu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                View.menuStage.show();
                View.Gamerules.close();
            }
        });

        View.rules.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                View.Gamerules.show();
                View.menuStage.close();
            }
        });
        // Exit game when clicked
        View.exit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                View.menuStage.close();
            }
        });
    }
}
