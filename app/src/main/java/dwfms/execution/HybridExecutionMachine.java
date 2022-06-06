package dwfms.execution;

import dwfms.execution.petrinet.PetriNet;
import dwfms.execution.petrinet.Transition;
import dwfms.execution.ruleengine.EasyRuleEngine;
import dwfms.framework.Action;
import dwfms.framework.IExecutionMachine;
import dwfms.framework.TaskExecution;
import dwfms.framework.error.NonCompliantExecutionException;
import dwfms.framework.execution.EventLog;
import dwfms.framework.references.Instance;
import dwfms.framework.references.UserReference;
import dwfms.model.BPMNToHybridExecutionMachineTransformer;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A HybridExecutionMachine keeps track of the internal PM and RE
 */
@Data
@AllArgsConstructor
public class HybridExecutionMachine implements IExecutionMachine {

    private PetriNet pn;
    private EasyRuleEngine ere;
    private EventLog log;

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
        }

    }

    @Override
    public void getWorkList(Instance instance, UserReference userReference) {

        // algorithm...
    }


}
