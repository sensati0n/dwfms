package dwfms.framework.collaboration.network;

import dwfms.framework.Action;
import dwfms.framework.TaskExecution;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private TaskExecution taskExecution;

}
