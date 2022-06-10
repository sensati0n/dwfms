package dwfms.bpm.model.bpmn;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Activity extends BPMNElement {

    final String name;

    final SequenceFlow incoming;
    final SequenceFlow outgoing;

}
