package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import util.SceneWorkflow;
import util.SceneWorkflowImpl;
import util.SceneWrapper;

/***
 * Class that wraps given JavaFX Objects to enable easy scene navigation
 */
public class SceneConnector {

    private AnchorPane anchorPane = new AnchorPane();
    private SceneWrapper child;
    private SceneWorkflow workflow = new SceneWorkflowImpl();
    private final double NON = Double.NEGATIVE_INFINITY;
    private final double PADDING = 10.0;
    private ListView<SceneWrapper> listView = new ListView<>();

    /***
     * pane which allows to select whether the side pane should allow jumps
     * @param node initial node to display
     * @param jumpable
     * @param workflow own workflow to work on
     */
    public SceneConnector(Node node, boolean jumpable, SceneWorkflow workflow) {
        this(node, 800, 600, jumpable, workflow);
    }

    /***
     * pane which allows to select whether the side pane should allow jumps
     * @param node initial node to display
     * @param jumpable
     */
    public SceneConnector(Node node, boolean jumpable) {
        this(node, jumpable, new SceneWorkflowImpl());
    }

    /***
     * pane with default configuration
     * @param node initial node to display
     */
    public SceneConnector(Node node) {
        this(node, true);
    }

    /***
     * maximum of configuration options
     * @param node initial node to display
     * @param width of wrapping pane
     * @param height of wrapping pane
     * @param jumpable
     * @param workflow own workflow to work on
     */
    public SceneConnector(Node node, double width, double height, boolean jumpable, SceneWorkflow workflow) {
        child = new SceneWrapper(null, null, "initial");
        this.workflow = workflow;
        if (node != null) {
            setChild(node);
        }
        Button next = createNext();
        Button previous = createPrevious();
        anchorPane.getChildren().addAll(previous, next, listView);
        listView.setPrefWidth(170.0);
        setPosition(next, NON, PADDING, NON, PADDING);
        setPosition(previous, NON, PADDING, PADDING, NON);
        setPosition(listView, 10.0, 45.0, 10.0, NON);
        setSize(width, height);
        listView.setItems(FXCollections.observableArrayList(workflow.getWorkflow()));
        if (jumpable) {
            listView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    int index = listView.getSelectionModel().getSelectedIndex();
                    select(index);
                }
            });
        }
    }

    public void setWidth(double width) {
        anchorPane.setPrefWidth(width);
    }

    public void setHeight(double height) {
        anchorPane.setPrefHeight(height);
    }

    /***
     * Sets the size of the wrapping Pane
     * @param width
     * @param height
     */
    public void setSize(double width, double height) {
        anchorPane.setPrefSize(width, height);
    }

    /***
     *
     * @return the constructed wrapping pane to construct a scene for thr primary stage
     */
    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public SceneWorkflow getWorkflow() {
        return workflow;
    }

    /***
     *
     * @return the currently displayed node
     */
    public Node getChild() {
        return child.getScene();
    }

    /***
     *
     * @return the wrapping object of provided node that shall be wrapped
     */
    public SceneWrapper getScene(){
        return child;
    }

    /***
     * selects and opens the scene that is defined next in the workflow
     * @param event
     */
    @FXML
    public void next(ActionEvent event) {
        SceneWrapper next = workflow.getWorkflow().next();
        if (next != null) {
            setChild(next.getScene());
            System.out.println("Next");
        }
    }

    /***
     * selects and opens the scene that is defined previous in the workflow
     * @param event
     */
    @FXML
    public void previous(ActionEvent event) {
        SceneWrapper previous = workflow.getWorkflow().previous();
        if (previous != null) {
            setChild(previous.getScene());
            System.out.println("Previous");
        }
    }

    /***
     * selects and opens the scene that is defined at this position in the workflow
     * @param index
     */
    public void select(int index) {
        SceneWrapper now = workflow.getWorkflow().get(index);
        if (now != null) {
            setChild(now.getScene());
            System.out.println("Select");
        }
    }

    private Button createNext() {
        Button next = new Button("Next");
        next.setOnAction(this::next);
        return next;
    }

    private Button createPrevious() {
        Button previous = new Button("Previous");
        previous.setOnAction(this::previous);
        return previous;
    }

    /***
     * Method used to anchor the additional Nodes in the wrapping pane
     * @param child
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    private void setPosition(Node child, double top, double bottom, double left, double right) {
        if (child != null) {
            if (top > Double.NEGATIVE_INFINITY) {
                AnchorPane.setTopAnchor(child, top);
            }
            if (bottom > Double.NEGATIVE_INFINITY) {
                AnchorPane.setBottomAnchor(child, bottom);
            }
            if (left > Double.NEGATIVE_INFINITY) {
                AnchorPane.setLeftAnchor(child, left);
            }
            if (right > Double.NEGATIVE_INFINITY) {
                AnchorPane.setRightAnchor(child, right);
            }
        }
    }

    private void configureTestWorkflow() {
        for (int i = 0; i < 5; i++) {
            Label label = new Label(i + ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            workflow.addScene(new SceneWrapper(label, null, "Label " + i));
        }
    }

    /***
     * sets the node that should be wrapped and anchors it
     * @param node
     */
    private void setChild(Node node) {
        anchorPane.getChildren().remove(child.getScene());
        child.setScene(node);
        anchorPane.getChildren().add(child.getScene());
        setPosition(child.getScene(), 10.0, 45.0, 180.0, 10.0);
    }


}
