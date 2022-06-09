package dwfms.ui;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dwfms.collaboration.example.SimpleCollaboration;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.core.DWFMS;
import dwfms.framework.references.Instance;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

@AllArgsConstructor
public class DeploymentHandler implements HttpHandler {

    private static final Logger logger = LogManager.getLogger(TaskExecutionHandler.class);

    private DWFMS dwfms;

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        logger.trace("Message received in DeploymentHandler...");

        String requestBodyText = SimpleCollaboration.getTextFromInputStream(exchange.getRequestBody());

        //TODO: Allow user defined models
        logger.trace("I want to deploy " + requestBodyText + ". But instead, the example model is deployed.");

        this.dwfms.deployProcessModel();

        String response = "OK";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
