package dwfms.framework;

import dwfms.framework.references.InstanceReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskExecution extends UserAction {

    private InstanceReference instance;
    private String task;

}
