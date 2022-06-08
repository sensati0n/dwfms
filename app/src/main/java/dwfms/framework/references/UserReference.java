package dwfms.framework.references;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A user is usually identified by a cryptographic primitive, e.g. a private/public key pair.
 * For a short human-readable identification of users, UserReferences are used.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReference {

    private String name;

}
