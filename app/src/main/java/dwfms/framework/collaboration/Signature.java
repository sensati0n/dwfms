package dwfms.framework.collaboration;

import dwfms.framework.action.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Signature {

    @Getter
    private String signature;
    private User user;

}
