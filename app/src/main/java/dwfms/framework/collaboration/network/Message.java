package dwfms.framework.collaboration.network;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dwfms.framework.Action;
import dwfms.framework.TaskExecution;
import dwfms.framework.collaboration.Signature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private TaskExecution taskExecution;
    @JsonIgnore
    private Signature signature;

    public Message(TaskExecution taskExecution) {
        this.taskExecution = taskExecution;
        sign();
    }

    public void sign() {
        this.signature = new Signature("sig", "pubKey");
    }

}
