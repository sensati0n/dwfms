package dwfms.collaboration.example.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dwfms.collaboration.example.SimpleCollaboration;
import dwfms.framework.action.TaskExecution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ActionHandler implements HttpHandler {

    private static final Logger logger = LogManager.getLogger(ActionHandler.class);

    ObjectMapper objectMapper = new ObjectMapper();
    SimpleCollaboration simpleCollaboration;

    public ActionHandler(SimpleCollaboration multipleConnector) {
        this.simpleCollaboration = multipleConnector;
    }


    /**
     * Provided by HttpHandler interface from com.sun.net.httpserver package.
     * This method is called, when the HttpServer in this class receives a message.
     * Messages will come from other participants and include messages and acknowledgements.
     * @param exchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        logger.trace("Message received in ActionHandler...");

        // Create Message-Object from Request
        String requestBodyText = SimpleCollaboration.getTextFromInputStream(exchange.getRequestBody());
        TaskExecution taskExecution = objectMapper.readValue(requestBodyText, TaskExecution.class);

        logger.debug(taskExecution.getUser().getUserReference() + " wants to execute " + taskExecution.getTask());

        simpleCollaboration.taskExecutionReceived(taskExecution);
    }
}
