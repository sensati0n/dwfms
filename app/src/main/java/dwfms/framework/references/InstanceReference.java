package dwfms.framework.references;

import dwfms.framework.IModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A reference value to identify the process model instance in the collaboration.
 * Could be a contract address in case of ethereum.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstanceReference {

    private String instanceRef;
    private IModel model;
}
