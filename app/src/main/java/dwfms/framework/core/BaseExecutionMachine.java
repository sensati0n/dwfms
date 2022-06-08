package dwfms.framework.core;

import dwfms.framework.action.Action;
import dwfms.framework.execution.EventLog;
import dwfms.framework.references.Instance;
import dwfms.framework.references.UserReference;
import lombok.Getter;

public abstract class BaseExecutionMachine {

    @Getter
    protected EventLog eventLog = new EventLog();

    public abstract boolean isConform(Instance instance, Action action);

    protected abstract void execute(Instance instance, Action action);

    protected abstract void getWorkList(Instance instanceReference, UserReference userReference);

}
