package dwfms.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import dwfms.collaboration.simple.SimpleConnector;
import dwfms.framework.core.DWFMS;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.references.Instance;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpInterface implements HttpHandler {

    private static final Logger logger = LogManager.getLogger(HttpInterface.class);

    DWFMS dwfms;
    ObjectMapper objectMapper;
    HttpServer httpServer;

    public HttpInterface(DWFMS dwfms, int port) throws IOException {

        this.dwfms = dwfms;
        this.objectMapper = new ObjectMapper();

        this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);

        httpServer.createContext("/ui", this);
        httpServer.setExecutor(null); // creates a default executor
        httpServer.start();

        logger.trace("Started UI-HttpServer on port: " + port);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        logger.trace("Message received in HttpInterfaceHandler...");

        // Create Message-Object from Request
        String requestBodyText = SimpleConnector.getTextFromInputStream(exchange.getRequestBody());
        TaskExecutionDTO taskExecutionDTO = objectMapper.readValue(requestBodyText, TaskExecutionDTO.class);

        logger.trace("I want to execute " + taskExecutionDTO.getTask());

        TaskExecution taskExecution = new TaskExecution(new Instance(taskExecutionDTO.getInstanceRef(), null), taskExecutionDTO.getTask());
        dwfms.executeTask(taskExecution);

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
