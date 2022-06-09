package dwfms.framework.action;

import dwfms.framework.references.UserReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

/**
 * This class describes users (human resources) of a process model in a collaboration.
 * //TODO: Future Work: Extend with more attributes or zero-knowledge proofs etc.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private UserReference userReference;
    private String publicKey;

    // Nullable for foreign users
    @Nullable private String privateKey;


    public User(UserReference userReference, String publicKey) {
        this.userReference = userReference;
        this.publicKey = publicKey;
    }
}
