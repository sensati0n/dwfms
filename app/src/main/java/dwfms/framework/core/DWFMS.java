package dwfms.framework.core;


import dwfms.framework.action.Action;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.action.User;
import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.references.Instance;
import lombok.Builder;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;

@Builder
@Getter
public class DWFMS {

    private static final Logger logger = LogManager.getLogger(DWFMS.class);

    private final BaseModel model;
    private final ITransformer transformer;
    private BaseExecutionMachine executionMachine;
    private final BaseCollaboration collaboration;

    //who keeps the user object?
    private User user;

    public void init(User user) throws MalformedURLException {

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
            logger.warn("The requested action is not model conform. Processing canceled.");
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
        logger.debug("Machined updated: (" + te.getTask() + ", " + te.getUser().getUserReference().getName() +  ") was executed.");
        this.executionMachine.execute(instance, a);
    }

    public void getMyWorklist(Instance instance) {

        this.executionMachine.getWorkList(instance, user.getUserReference());

    }

    /**
     * To be updated to use a store instead of class variable
     * @param instance
     * @return
     */
    public BaseModel getModelByInstanceReference(Instance instance) {
        return this.model;
    }

}
