package dwfms.model.bpmn;

import dwfms.framework.IModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BPMNModel implements IModel {

    StartEvent start;
    EndEvent end;
    Set<Activity> activities = new HashSet<>();
    Set<Gateway> gateways = new HashSet<>();
    Set<SequenceFlow> sequenceFlows = new HashSet<>();

    public void readFromFile() {

    }

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
    public List<String> getParticipants() {
        // Should be extracted from pools or lanes
        return List.of("127.0.0.1");
    }
}
