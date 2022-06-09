package dwfms.framework.collaboration.network;

import dwfms.framework.core.User;
import dwfms.framework.collaboration.security.Signature;
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
