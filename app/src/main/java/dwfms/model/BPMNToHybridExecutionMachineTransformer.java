package dwfms.model;

import dwfms.ExampleDataFactory;
import dwfms.execution.HybridExecutionMachine;
import dwfms.execution.petrinet.PetriNet;
import dwfms.execution.petrinet.Transition;
import dwfms.execution.ruleengine.EasyRuleEngine;
import dwfms.framework.*;
import dwfms.framework.execution.EventLog;
import dwfms.model.bpmn.Activity;
import dwfms.model.bpmn.BPMNElement;
import dwfms.model.bpmn.BPMNModel;

public class BPMNToHybridExecutionMachineTransformer implements ITransformer<BPMNModel, HybridExecutionMachine> {

    @Override
    public HybridExecutionMachine transform(BPMNModel model) {

        PetriNet pn = ExampleDataFactory.exampleNet();
        EasyRuleEngine ere = new EasyRuleEngine();
        ere.setRules(ExampleDataFactory.exampleRules());

        return new HybridExecutionMachine(pn, ere, new EventLog());
    }

    @Override
    public Object modelTaskToMachineAction(TaskExecution taskExecution, HybridExecutionMachine machine) {



        BPMNModel model = (BPMNModel) Database.getModelByInstanceReference(taskExecution.getInstance());
        BPMNElement activity = model.getBPMNElements().stream().filter(a -> a.getName().equals(taskExecution.getTask())).findFirst().orElseThrow();
        Transition transition = machine.getPn().getTransitions().stream().filter(t -> t.getActivity().equals(activity.getName())).findFirst().orElseThrow();

       return transition;

    }


}
