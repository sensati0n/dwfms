package dwfms.model.bpmn;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public abstract class BPMNElement {

    @Getter
    @Setter
    private String name;
}
