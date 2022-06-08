package dwfms.framework.collaboration.network;

import dwfms.framework.action.Action;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.action.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Acknowledgement extends Message {

    @Getter
    @Setter
    private TaskExecution action;

    @Override
    public void sign(User user) {

    }
}
