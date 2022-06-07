package dwfms.framework;


import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.references.Instance;
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
    private final BaseCollaboration collaboration;

    //how keeps the user object?
    private User user;

    public void init(User user) {

        this.user = user;

        this.executionMachine = this.transformer.transform(model);
        this.collaboration.init(this);
    }

    public Instance deployProcessModel() {

        return this.collaboration.deployProcessModel(model);
    }

    /**
     * This updates the CandidateLog (maybe)
     * Is called (only?) from the ui
     * @param taskExecution
     */
    public void executeTask(TaskExecution taskExecution) {

        taskExecution.setUser(user);

        // Only conform actions are sent to participants
        if(this.executionMachine.isConform(null, taskExecution)) {
            this.collaboration.sendMessage(taskExecution.getInstance(), taskExecution);
        }
        else {
            System.out.println("Böser user.");
        }

    }

    /**
     * This is called, when the collaboration has agreed upon a new action.
     * This updates the EventLog.
     * @param instance
     * @param a
     */
    public void updateMachine(Instance instance, Action a) {
        TaskExecution te = (TaskExecution) a;
        System.out.println("Finished: " + te.getTask());
        this.executionMachine.execute(instance, a);
    }

    public void getMyWorklist(Instance instance) {

        this.executionMachine.getWorkList(instance, user.getUserReference());

    }

//    public List<String> getParticipants() {
//        return this.model.getParticipants();
//    }

    /**
     * To be updated to use a store instead of class variable
     * @param instance
     * @return
     */
    public IModel getModelByInstanceReference(Instance instance) {
        return this.model;
    }

}
