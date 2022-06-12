package dwfms.framework.collaboration.consensus;

import dwfms.framework.action.TaskExecution;
import dwfms.framework.collaboration.network.Message;
import dwfms.framework.core.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Acknowledgement extends Message {

    @Getter
    @Setter
    private TaskExecution action;

    @Override
    public void sign(User user) {

    }
}
