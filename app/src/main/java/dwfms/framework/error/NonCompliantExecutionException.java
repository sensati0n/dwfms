package dwfms.framework.error;

import dwfms.framework.Action;
import dwfms.framework.IExecutionMachine;
import dwfms.framework.TaskExecution;

public class NonCompliantExecutionException extends RuntimeException {

    public NonCompliantExecutionException(Action a, IExecutionMachine machineInstance) {
        super("The given Action is not process conform but was still tried to be executed.");
    }

}
