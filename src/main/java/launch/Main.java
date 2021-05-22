package launch;

import controller.SceneConnector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import util.SceneWorkflow;
import util.SceneWorkflowImpl;
import util.SceneWrapper;

/***
 * opens an empty wrapping window
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneWorkflow workflow = configureWorkflow();
        SceneConnector controller = new SceneConnector(true, workflow);
        primaryStage.setScene(new Scene(controller.getAnchorPane()));
        primaryStage.show();
    }

    private static SceneWorkflow configureWorkflow() {
        SceneWorkflow workflow = new SceneWorkflowImpl();
        SceneWrapper wrapper1 = new SceneWrapper(new Label("Initial"), null, "initial");
        SceneWrapper wrapper2 = new SceneWrapper(new Label("Page 1"), null, "Page 1");
        SceneWrapper wrapper3 = new SceneWrapper(new Label("Page 2"), null, "Page 2");
        SceneWrapper wrapper4 = new SceneWrapper(new Label("Page 3"), null, "Page 3");
        workflow.addScene(wrapper1);
        workflow.addScene(wrapper2);
        workflow.addScene(wrapper3);
        workflow.addScene(wrapper4);
        return workflow;
    }
}
