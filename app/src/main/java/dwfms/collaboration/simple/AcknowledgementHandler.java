package dwfms.collaboration.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dwfms.App;
import dwfms.framework.collaboration.network.Acknowledgement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class AcknowledgementHandler implements HttpHandler {

    private static final Logger logger = LogManager.getLogger(AcknowledgementHandler.class);

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

        logger.trace("Message received in AcknowledgementHandler...");

        // Create Message-Object from Request
        String requestBodyText = SimpleConnector.getTextFromInputStream(exchange.getRequestBody());
        Acknowledgement acknowledgement = objectMapper.readValue(requestBodyText, Acknowledgement.class);

        logger.debug(acknowledgement.getTaskExecution().getUser().getUserReference().getName() + " wants to execute " + acknowledgement.getTaskExecution().getTask());

        simpleConnector.acknowledgementReceived(acknowledgement);
        logger.trace("Processing finished in AcknowledgementHandler...");


    }
}
