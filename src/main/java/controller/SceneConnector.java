package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    private SceneWrapper wrapped;
    private SceneWorkflow workflow;
    private final double NON = Double.NEGATIVE_INFINITY;
    private final double PADDING = 10.0;
    private ListView<SceneWrapper> listView = new ListView<>();

    /***
     * pane which allows to select whether the side pane should allow jumps
     * @param jumpable
     * @param workflow own workflow to work on
     */
    public SceneConnector(boolean jumpable, SceneWorkflow workflow) {
        this(800, 600, jumpable, workflow);
    }

    /***
     * pane which allows to select whether the side pane should allow jumps
     * @param jumpable
     */
    public SceneConnector(boolean jumpable) {
        this(jumpable, new SceneWorkflowImpl());
    }

    /***
     * pane with default configuration
     */
    public SceneConnector() {
        this(true);
    }

    /***
     * maximum of configuration options
     * @param width of wrapping pane
     * @param height of wrapping pane
     * @param jumpable
     * @param workflow own workflow to work on
     */
    public SceneConnector(double width, double height, boolean jumpable, SceneWorkflow workflow) {
        this.workflow = workflow;
        if ((setWrapped(this.workflow.getWorkflow().first())) == null){
            wrapped = new SceneWrapper(null, null, "Initial");
        }
        Button next = createNext();
        Button previous = createPrevious();
        anchorPane.getChildren().addAll(previous, next, listView);
        listView.setPrefWidth(170.0);
        setPosition(next, NON, PADDING, NON, PADDING);
        setPosition(previous, NON, PADDING, PADDING, NON);
        setPosition(listView, 10.0, 45.0, 10.0, NON);
        setSize(width, height);
        listView.setItems(workflow.getWorkflow());
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
        listView.refresh();
        return anchorPane;
    }

    public SceneWorkflow getWorkflow() {
        listView.refresh();
        return workflow;
    }

    /***
     *
     * @return the currently displayed node
     */
    public Node getWrapped() {
        return wrapped.getScene();
    }

    /***
     *
     * @return the wrapping object of provided node that shall be wrapped
     */
    public SceneWrapper getScene(){
        return wrapped;
    }

    /***
     * selects and opens the scene that is defined next in the workflow
     * @param event
     */
    @FXML
    public void next(ActionEvent event) {
        SceneWrapper next = workflow.getWorkflow().next();
        if (next != null) {
            setWrapped(next);
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
            setWrapped(previous);
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
            setWrapped(now);
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

    /***
     * sets the node that should be wrapped and anchors it
     * @param wrapper
     */
    private SceneWrapper setWrapped(SceneWrapper wrapper) {
        if (wrapped != null && wrapped.getScene() != null) {
            anchorPane.getChildren().remove(wrapped.getScene());
        }
        wrapped = wrapper;
        anchorPane.getChildren().add(wrapped.getScene());
        setPosition(wrapped.getScene(), 10.0, 45.0, 180.0, 10.0);
        return wrapped;
    }


}
