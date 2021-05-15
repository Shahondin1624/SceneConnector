package util;

/***
 * Holds the default order of scenes
 */
public interface SceneWorkflow {
    /***
     * all scenes are stored in the order they should appear
     */
    PositionList<SceneWrapper> workflow = new PositionList<>();

    PositionList<SceneWrapper> getWorkflow();

    void addScene(SceneWrapper sceneWrapper);
}
