package launch;

import controller.SceneConnector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.SceneWorkflowImpl;

/***
 * opens an empty wrapping window
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneConnector controller = new SceneConnector(null, true, new SceneWorkflowImpl());
        primaryStage.setScene(new Scene(controller.getAnchorPane()));
        primaryStage.show();
    }
}
