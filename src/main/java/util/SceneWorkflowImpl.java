package util;

/***
 * simple implementation of {@link SceneWorkflow}
 */
public class SceneWorkflowImpl implements SceneWorkflow {

    public SceneWorkflowImpl(SceneWrapper... wrappers) {
        for (SceneWrapper wrapper : wrappers) {
            if (wrapper != null) {
                addScene(wrapper);
            }
        }
    }

    @Override
    public PositionList<SceneWrapper> getWorkflow() {
        return workflow;
    }

    @Override
    public void addScene(SceneWrapper sceneWrapper) {
        workflow.addLast(sceneWrapper);
    }
}
