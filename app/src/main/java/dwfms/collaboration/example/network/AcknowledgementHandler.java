package dwfms.collaboration.example.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dwfms.collaboration.example.SimpleCollaboration;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.collaboration.network.Acknowledgement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class AcknowledgementHandler implements HttpHandler {

    private static final Logger logger = LogManager.getLogger(AcknowledgementHandler.class);

    ObjectMapper objectMapper = new ObjectMapper();
    BaseCollaboration collaboration;

    public AcknowledgementHandler(BaseCollaboration collaboration) {
        this.collaboration = collaboration;
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
        String requestBodyText = SimpleCollaboration.getTextFromInputStream(exchange.getRequestBody());
        Acknowledgement acknowledgement = objectMapper.readValue(requestBodyText, Acknowledgement.class);

        logger.debug(((TaskExecution) acknowledgement.getAction()).getUser().getUserReference().getName() + " wants to execute " + ((TaskExecution) acknowledgement.getAction()).getTask());

        collaboration.acknowledgementReceived(acknowledgement);
        logger.trace("Processing finished in AcknowledgementHandler...");

    }
}
