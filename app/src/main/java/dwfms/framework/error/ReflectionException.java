package dwfms.framework.error;

import dwfms.framework.action.Action;
import dwfms.framework.core.BaseExecutionMachine;

public class ReflectionException extends RuntimeException {

    public ReflectionException() {
        super("This exception is thrown, when a method is invoked by reflection, e.g. to call a smart contract function in the ethereum package.");
    }

}
