package dwfms.framework.action;

import dwfms.framework.references.Instance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskExecution extends UserAction {

    private Instance instance;
    private String task;

    @Override
    public void sign(User user) {

    }
}
