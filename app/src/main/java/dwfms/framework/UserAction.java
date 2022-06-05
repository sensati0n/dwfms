package dwfms.framework;

import dwfms.framework.references.UserReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserAction extends Action {

    private UserReference user;

}
