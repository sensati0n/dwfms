package dwfms.collaboration.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dwfms.framework.collaboration.network.Message;

import java.io.IOException;

public class ActionHandler implements HttpHandler {

    ObjectMapper objectMapper = new ObjectMapper();
    SimpleConnector simpleConnector;

    public ActionHandler(SimpleConnector multipleConnector) {
        this.simpleConnector = multipleConnector;
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

        System.out.println("[SIMPLE::handle] Message received in ActionHandler...");

        // Create Message-Object from Request
        String requestBodyText = SimpleConnector.getTextFromInputStream(exchange.getRequestBody());
        Message message = objectMapper.readValue(requestBodyText, Message.class);

        System.out.println(message.getTaskExecution().getUser().getUserReference() + " wants to execute " + message.getTaskExecution().getTask());

        simpleConnector.messageReceived(message);
    }
}
