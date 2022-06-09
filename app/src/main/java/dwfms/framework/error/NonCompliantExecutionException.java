package dwfms.framework.error;

import dwfms.framework.action.Action;
import dwfms.framework.bpm.execution.BaseExecutionMachine;

public class NonCompliantExecutionException extends RuntimeException {

    public NonCompliantExecutionException(Action a, BaseExecutionMachine machineInstance) {
        super("The given Action is not process conform but was still tried to be executed.");
    }

}
