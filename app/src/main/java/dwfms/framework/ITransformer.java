package dwfms.framework;

import dwfms.execution.HybridExecutionMachine;
import dwfms.framework.IExecutionMachine;
import dwfms.framework.IModel;
import dwfms.model.bpmn.BPMNModel;

public interface ITransformer<M extends IModel, E extends IExecutionMachine> {

    E transform(M model);

    Object modelTaskToMachineAction(TaskExecution taskExecution, E machine);
}
