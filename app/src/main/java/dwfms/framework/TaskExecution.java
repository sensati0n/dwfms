package dwfms.framework;

import dwfms.framework.references.Instance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskExecution extends UserAction {

    private Instance instance;
    private String task;

}
