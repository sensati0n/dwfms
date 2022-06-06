package dwfms.framework;

import dwfms.framework.references.UserReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private UserReference userReference;
    private String publicKey;

    // Nullable for foreign users
    @Nullable private String privateKey;



}
