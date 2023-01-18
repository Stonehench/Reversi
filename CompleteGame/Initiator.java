package completeReversiGame;

import javafx.application.Application;
import javafx.stage.Stage;

public class Initiator extends Application {
    static boolean lock = false;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        View view = new View();
        new Controller(stage);
        view.start(stage);
        stage.setScene(view.scene);
    }
}