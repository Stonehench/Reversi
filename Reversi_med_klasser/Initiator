package JanuarProject;

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
        Menu menu = new Menu();
       
        Controller controller = new Controller(stage);
        menu.start(stage);
        stage.setScene(menu.menuScene);
        stage.show();
    }
}
