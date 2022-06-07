package dwfms.framework.collaboration.network;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dwfms.framework.Action;
import dwfms.framework.TaskExecution;
import dwfms.framework.User;
import dwfms.framework.collaboration.Signature;
import dwfms.framework.collaboration.security.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.security.PublicKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private TaskExecution taskExecution;
    private String signature;

    public Message(TaskExecution taskExecution, User user) {
        this.taskExecution = taskExecution;
        sign(user.getPrivateKey());
    }

    public void sign(String privateKey) {
        this.signature = Utils.sign(taskExecution.toString(), Utils.stringToPrivateKey(privateKey));
    }

}
