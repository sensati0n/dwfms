package dwfms.collaboration.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dwfms.framework.references.Instance;

import java.io.IOException;

public class DeploymentHandler implements HttpHandler {

    ObjectMapper objectMapper = new ObjectMapper();
    SimpleConnector simpleConnector;

    public DeploymentHandler(SimpleConnector simpleConnector) {
        this.simpleConnector = simpleConnector;
    }

    /**
     * Provided by HttpHandler interface from com.sun.net.httpserver package.
     * This method is called, when the HttpServer in this class receives a message.
     * @param exchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println("[MULTI::handle] Message received in DeploymentHandler...");

        // Create Message-Object from Request
        String requestBodyText = SimpleConnector.getTextFromInputStream(exchange.getRequestBody());
        Instance instance = objectMapper.readValue(requestBodyText, Instance.class);

        simpleConnector.instanceReceived(instance);

    }
}