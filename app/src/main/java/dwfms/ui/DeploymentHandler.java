package dwfms.ui;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dwfms.collaboration.example.SimpleCollaboration;
import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.bpm.execution.Instance;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The DeploymentHandler is called, when a participant deploys a new process model
 */
@AllArgsConstructor
public class DeploymentHandler implements HttpHandler {

    private static final Logger logger = LogManager.getLogger(TaskExecutionHandler.class);

    private BaseCollaboration collaboration;

    /**
     * When a new instance is received, we must register that in our database.
     * @param exchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        logger.trace("Message received in DeploymentHandler...");

        String requestBodyText = SimpleCollaboration.getTextFromInputStream(exchange.getRequestBody());

        //TODO: Allow user defined models
        logger.trace("New proces instance available: " + requestBodyText);
        this.collaboration.instanceReceived(new Instance());


        String response = "OK";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
