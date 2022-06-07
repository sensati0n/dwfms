package dwfms.collaboration.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dwfms.framework.collaboration.network.Acknowledgement;

import java.io.IOException;

public class AcknowledgementHandler implements HttpHandler {

    ObjectMapper objectMapper = new ObjectMapper();
    SimpleConnector simpleConnector;

    public AcknowledgementHandler(SimpleConnector simpleConnector) {
        this.simpleConnector = simpleConnector;
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

        System.out.println("[SIMPLE::handle] Message received in AcknowledgementHandler...");

        // Create Message-Object from Request
        String requestBodyText = SimpleConnector.getTextFromInputStream(exchange.getRequestBody());
        Acknowledgement acknowledgement = objectMapper.readValue(requestBodyText, Acknowledgement.class);

        System.out.println(acknowledgement.getTaskExecution().getUser().getUserReference().getName() + " wants to execute " + acknowledgement.getTaskExecution().getTask());

        simpleConnector.acknowledgementReceived(acknowledgement);
        System.out.println("[SIMPLE::handle] Processing finished in AcknowledgementHandler...");


    }
}
