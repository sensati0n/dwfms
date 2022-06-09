package dwfms.framework.core;

import dwfms.framework.action.TaskExecution;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Nullable int timestamp;
    String activity;
    String resource;

    public Event(TaskExecution taskExecution) {
        this.activity = taskExecution.getTask();
        this.resource = taskExecution.getUser().getUserReference().getName();
    }
}
