package dwfms.framework.bpm.execution;

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
public class Instance {
    private String instanceRef;
    private String modelRef;
}
