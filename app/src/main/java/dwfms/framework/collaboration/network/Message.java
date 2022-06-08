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
public abstract class Message {

    private Signature signature;

    public abstract void sign(User user);

}
