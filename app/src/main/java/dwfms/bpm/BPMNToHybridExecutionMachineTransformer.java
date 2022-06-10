package dwfms.bpm;

import dwfms.ExampleDataFactory;
import dwfms.bpm.execution.HybridExecutionMachine;
import dwfms.bpm.execution.petrinet.PetriNet;
import dwfms.bpm.execution.petrinet.Transition;
import dwfms.bpm.execution.ruleengine.EasyRuleEngine;
import dwfms.bpm.model.bpmn.BPMNModel;
import dwfms.framework.Database;
import dwfms.framework.action.TaskExecution;
import dwfms.bpm.model.bpmn.BPMNElement;
import dwfms.framework.bpm.ITransformer;

public class BPMNToHybridExecutionMachineTransformer implements ITransformer<BPMNModel, HybridExecutionMachine> {

    /**
     * This is only a dummy implementation and provides a hard-coded example model.
     * This should implement the algorithms to transform a BPMN model into a petri net.
     *
     * Multi perspective rules, e.g. organizational perspective, are currently enforced by a rule engine.
     * Hence, this method must extract those responsibilities from a BPMN model.
     *
     * If a more powerful modeling language is used, also more advanced rules can be mapped.
     *
     * @param model
     * @return
     */
    @Override
    public HybridExecutionMachine transform(BPMNModel model) {

        PetriNet pn = ExampleDataFactory.exampleNet();
        EasyRuleEngine ere = new EasyRuleEngine();
        ere.setRules(ExampleDataFactory.exampleRules());

        return new HybridExecutionMachine(pn, ere);
    }

    /**
     * During the transformation of a BPMN model into a petri net,
     * we label the transitions of the net with the corresponding BPMN activity.
     * It is then straightforward to receive the transition based on an activity.
     * @param taskExecution The action to be executed, denoted in a certain modelling language
     * @param machine The execution machine that must execute the given action
     * @return
     */
    @Override
    public Object modelTaskToMachineAction(TaskExecution taskExecution, HybridExecutionMachine machine) {

        BPMNModel model = (BPMNModel) Database.getModelByInstanceReference(taskExecution.getInstance());
        BPMNElement activity = model.getBPMNElements().stream().filter(a -> a.getName().equals(taskExecution.getTask())).findFirst().orElseThrow();
        Transition transition = machine.getPn().getTransitions().stream().filter(t -> t.getActivity().equals(activity.getName())).findFirst().orElseThrow();

       return transition;

    }


}
