package dwfms.framework;

import dwfms.framework.references.Instance;
import dwfms.framework.references.UserReference;

public interface IExecutionMachine {

    boolean isConform(Instance instance, Action action);

    void execute(Instance instance, Action action);

    void getWorkList(Instance instanceReference, UserReference userReference);

}
