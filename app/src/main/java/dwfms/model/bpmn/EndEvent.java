package dwfms.model.bpmn;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EndEvent extends BPMNElement {

    SequenceFlow incoming;

    public EndEvent(SequenceFlow incoming) {
        this.incoming = incoming;
        super.setName("End");

    }
}
