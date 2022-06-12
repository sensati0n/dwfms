package dwfms.bpm.execution;

import dwfms.bpm.execution.ruleengine.EasyRuleEngine;
import dwfms.bpm.execution.petrinet.PetriNet;
import dwfms.bpm.execution.petrinet.Transition;
import dwfms.framework.action.Action;
import dwfms.framework.bpm.execution.BaseExecutionMachine;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.error.NonCompliantExecutionException;
import dwfms.framework.core.Event;
import dwfms.framework.bpm.execution.Instance;
import dwfms.framework.core.UserReference;
import dwfms.bpm.BPMNToHybridExecutionMachineTransformer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A HybridExecutionMachine keeps track of the internal PM and RE
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class HybridExecutionMachine extends BaseExecutionMachine {

    private static final Logger logger = LogManager.getLogger(HybridExecutionMachine.class);

    private PetriNet pn;
    private EasyRuleEngine ere;

    @Override
    public boolean isConform(Instance instance, Action action) {

        boolean pnConform = false;
        boolean reConform = false;
        if(action instanceof TaskExecution) {
            TaskExecution taskExecution = (TaskExecution) action;
            Transition transitionToFire = (Transition) new BPMNToHybridExecutionMachineTransformer().modelTaskToMachineAction(taskExecution, this);
            pnConform = transitionToFire.canFire();
            reConform = ere.isConform(taskExecution);
        }

        return pnConform && reConform;
    }

    @Override
    public void execute(Instance instance, Action action) {

        // should not happen, because we have signed that the action is valid
        if(!isConform(instance, action)) {
            throw new NonCompliantExecutionException(action, this);
        }

        if(action instanceof TaskExecution) {
            TaskExecution taskExecution = (TaskExecution) action;
            Transition transitionToFire = (Transition) new BPMNToHybridExecutionMachineTransformer().modelTaskToMachineAction(taskExecution, this);
            transitionToFire.fire();
            ere.addFact(taskExecution);

            logger.debug("(" + taskExecution.getTask() + ", " + taskExecution.getUser().getUserReference().getName() + ") executed.");

        }


    }

    @Override
    public void getWorkList(Instance instance, UserReference userReference) {

        // algorithm...
    }


}
