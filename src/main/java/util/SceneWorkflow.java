package util;

/***
 * Holds the default order of scenes
 */
public interface SceneWorkflow {

    PositionList<SceneWrapper> getWorkflow();


    void addScene(SceneWrapper sceneWrapper);
}
