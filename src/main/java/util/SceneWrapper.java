package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/***
 * Class that wraps scenes that should be displayed with additional information
 */
public class SceneWrapper {
    /***
     * will be displayed in the ListView in {@link controller.SceneConnector} as an overview over all wrapped scenes
     */
    private String name;
    /***
     * the wrapped scene or other object to display
     */
    private Node scene;
    /***
     * the controller that is connected to the wrapped scene
     */
    private Object controller;

    /***
     * Default constructor that creates necessary data from the provided absolute path of fxml file
     * @param path has to be the absolute path to resolve everything correctly
     * @param name that is displayed in Scene-ListView-Overview
     * @throws IOException
     */
    public SceneWrapper(String path, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        File fxml = new File(path);
        loader.setLocation(new URL(fxml.getAbsolutePath()));
        scene = loader.load();
        controller = loader.getController();
    }

    /***
     * Constructor which can be used to insert scene and connected controller programmatically
     * @param node scene that should be wrapped
     * @param controller that is attached to scene
     * @param name that is displayed in Scene-ListView-Overview
     */
    public SceneWrapper(Node node, Object controller, String name) {
        this.scene = node;
        this.controller = controller;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getScene() {
        return scene;
    }

    public void setScene(Node scene) {
        this.scene = scene;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        return getName();
    }
}
