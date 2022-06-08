package dwfms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.action.User;
import dwfms.framework.references.UserReference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JacksonTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void serializeTaskExecution() throws JsonProcessingException {

        TaskExecution taskExecution = new TaskExecution(null, "Start");
        taskExecution.setUser(new User(new UserReference("hans"), null, null));


        String taskExecutionString = objectMapper.writeValueAsString(taskExecution);
        assertEquals("{\"signature\":null,\"user\":{\"userReference\":{\"name\":\"hans\"},\"publicKey\":null,\"privateKey\":null},\"instance\":null,\"task\":\"Start\"}", taskExecutionString);

    }
}
