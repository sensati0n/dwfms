package dwfms.framework;

import dwfms.framework.execution.EventLog;
import dwfms.framework.references.InstanceReference;
import dwfms.framework.references.UserReference;

import java.util.UUID;

public interface IExecutionMachine {

    boolean isConform(InstanceReference instance, Action action);

    void execute(InstanceReference instance, Action action);

    void getWorkList(InstanceReference instanceReference, UserReference userReference);

}
