package dwfms.framework.core;


import dwfms.framework.action.TaskExecution;

/**
 * This interface must be provided to the dWFMS for any model-machine combination.
 * It ensures that the semantic is defined for the modelling elements.
 *
 * @param <M> the type of the model
 * @param <E> the type of the execution machine
 */
public interface ITransformer<M extends BaseModel, E extends BaseExecutionMachine> {

    /**
     * Transforms the given model to a corresponding execution machine
     * @param model
     * @return
     */
    E transform(M model);

    /**
     * The user-dWFMS communication and the dWFMS-dWFMS communication is based upon the modelling language.
     * Hence, to check conformance of a certain user input, the dWFMS must be able to transform the modelling action
     * to a corresponding action of the execution machine.
     * @param taskExecution The action to be executed, denoted in a certain modelling language
     * @param machine The execution machine that must execute the given action
     * @return Probably a java method that can be called by the dWFMS to query the machine
     */
    Object modelTaskToMachineAction(TaskExecution taskExecution, E machine);
}
