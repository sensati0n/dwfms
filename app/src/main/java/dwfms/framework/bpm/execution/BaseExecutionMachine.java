package dwfms.framework.bpm.execution;

import dwfms.framework.action.Action;
import dwfms.framework.core.UserReference;
import lombok.Getter;

public abstract class BaseExecutionMachine {

    @Getter
    protected EventLog eventLog = new EventLog();

    public abstract boolean isConform(Instance instance, Action action);

    public abstract void execute(Instance instance, Action action);

    public abstract void getWorkList(Instance instanceReference, UserReference userReference);

}
