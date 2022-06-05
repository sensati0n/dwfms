package dwfms.model.bpmn;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=false)
public class Gateway extends BPMNElement {

    final Set<SequenceFlow> incoming;
    final Set<SequenceFlow> outgoing;

    public Gateway(Set<SequenceFlow> incoming, Set<SequenceFlow> outgoing) {
        this.incoming = incoming;
        this.outgoing = outgoing;

        super.setName("Gateway");

    }

}
