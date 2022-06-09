package dwfms.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dwfms.collaboration.example.SimpleCollaboration;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.core.DWFMS;
import dwfms.framework.references.Instance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.OutputStream;

public class TaskExecutionHandler implements HttpHandler {

    private static final Logger logger = LogManager.getLogger(TaskExecutionHandler.class);

    private ObjectMapper objectMapper = new ObjectMapper();
    private DWFMS dwfms;

    public TaskExecutionHandler(DWFMS dwfms) {
        this.dwfms = dwfms;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        logger.trace("Message received in TaskExecutionHandler...");

        // Create Message-Object from Request
        String requestBodyText = SimpleCollaboration.getTextFromInputStream(exchange.getRequestBody());
        TaskExecutionDTO taskExecutionDTO = this.objectMapper.readValue(requestBodyText, TaskExecutionDTO.class);

        logger.trace("I want to execute " + taskExecutionDTO.getTask());

        TaskExecution taskExecution = new TaskExecution(new Instance(taskExecutionDTO.getInstanceRef(), null), taskExecutionDTO.getTask());
        this.dwfms.executeTask(taskExecution);

        String response = "OK";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

}

@Data
@NoArgsConstructor
class TaskExecutionDTO {

    private String instanceRef;
    private String task;
}

