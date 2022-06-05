package dwfms.framework;


import dwfms.framework.collaboration.ICollaboration;
import dwfms.framework.references.InstanceReference;
import dwfms.framework.references.UserReference;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DWFMS {

    private final IModel model;
    private final ITransformer transformer;
    private IExecutionMachine executionMachine;
    private final ICollaboration collaboration;

    private final UserReference user;

    public void init() {

        this.executionMachine = this.transformer.transform(model);
        this.collaboration.init(this);
    }

    public InstanceReference deployProcessModel() {

        return this.collaboration.deployProcessModel(model);
    }

    /**
     * This updates the CandidateLog (maybe)
     * @param taskExecution
     */
    public void executeTask(TaskExecution taskExecution) {

        taskExecution.setUser(user);
        this.collaboration.sendMessage(taskExecution.getInstance(), taskExecution);

    }

    /**
     * This is called, when the collaboration has agreed upon a new action.
     * This updates the EventLog.
     * @param instance
     * @param a
     */
    public void updateMachine(InstanceReference instance, Action a) {
        TaskExecution te = (TaskExecution) a;
        System.out.println("Finished: " + te.getTask());
        this.executionMachine.execute(instance, a);
    }

    public void getMyWorklist(InstanceReference instance) {

        this.executionMachine.getWorkList(instance, user);

    }

    public List<String> getParticipants() {
        return this.model.getParticipants();
    }

    /**
     * To be updated to use a store instead of class variable
     * @param instance
     * @return
     */
    public IModel getModelByInstanceReference(InstanceReference instance) {
        return this.model;
    }

}