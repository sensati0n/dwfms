package dwfms.model.bpmn;

import dwfms.framework.bpm.model.BaseModel;
import dwfms.framework.bpm.model.ModelReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * This is a container class for all elements of a BPMN model.
 * Currently, only few BPMN elements are supported, i.e.
 * Start Node, Activity, Sequence Flow, (XOR)-Gateway, End Node.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BPMNModel extends BaseModel {

    StartEvent start;
    EndEvent end;
    Set<Activity> activities = new HashSet<>();
    Set<Gateway> gateways = new HashSet<>();
    Set<SequenceFlow> sequenceFlows = new HashSet<>();

    public Set<BPMNElement> getBPMNElements() {
        Set<BPMNElement> elements = new HashSet<>();
        elements.addAll(activities);
        elements.addAll(gateways);
        elements.addAll(sequenceFlows);
        elements.add(start);
        elements.add(end);

        return elements;
    }

    @Override
    public ModelReference getModelReference() {
        return new ModelReference("0xNewHash");
    }
}
