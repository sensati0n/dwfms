package dwfms.framework.collaboration.security;

import dwfms.framework.core.User;
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
