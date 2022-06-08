package dwfms.framework.collaboration.network;

import dwfms.framework.action.TaskExecution;
import dwfms.framework.action.User;
import dwfms.framework.collaboration.Signature;
import dwfms.framework.collaboration.security.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private TaskExecution taskExecution;
    private Signature signature;

    public Message(TaskExecution taskExecution, User user) {
        this.taskExecution = taskExecution;
        sign(user);
    }

    public void sign(User user) {
        this.signature = new Signature(Utils.sign(taskExecution.toString(), Utils.stringToPrivateKey(user.getPrivateKey())), user);
    }

}
