package dwfms.model.bpmn;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SequenceFlow extends BPMNElement {

    public BPMNElement sourceRef;
    public BPMNElement targetRef;

    public SequenceFlow() {
        super.setName("Flow");
    }

}
