package util;

/***
 * simple implementation of {@link SceneWorkflow}
 */
public class SceneWorkflowImpl implements SceneWorkflow {

    private PositionList<SceneWrapper> workflow;

    public SceneWorkflowImpl(SceneWrapper... wrappers) {
        workflow = new PositionList<>(wrappers[0]);
        for (int i = 1; i < wrappers.length; i++) {
            addScene(wrappers[i]);
        }
    }

    public SceneWorkflowImpl() {
        workflow = new PositionList<>();
    }

    @Override
    public PositionList<SceneWrapper> getWorkflow() {
        return workflow;
    }

    @Override
    public void addScene(SceneWrapper sceneWrapper) {
        workflow.add(sceneWrapper);
    }
}
