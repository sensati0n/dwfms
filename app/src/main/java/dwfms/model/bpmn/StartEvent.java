package dwfms.model.bpmn;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class StartEvent extends BPMNElement {

    SequenceFlow outgoing;

    public StartEvent(SequenceFlow outgoing) {
        this.outgoing = outgoing;
        super.setName("Start");
    }
}
