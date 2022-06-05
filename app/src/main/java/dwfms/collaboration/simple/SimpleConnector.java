package dwfms.collaboration.simple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import dwfms.framework.*;
import dwfms.framework.collaboration.ICollaboration;
import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.collaboration.network.Message;
import dwfms.framework.log.Event;
import dwfms.framework.references.InstanceReference;
import dwfms.framework.references.UserReference;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SimpleConnector extends ICollaboration implements HttpHandler {


    //TODO: Usage introduces error, when application is shut down: "Build cancelled while executing task ':app:App.main()'"
    HttpServer httpServer;
    HttpClient httpClient;

    ObjectMapper objectMapper = new ObjectMapper();


    public SimpleConnector() throws IOException {

    }

    @Override
    public void init(DWFMS dwfms) {
        super.dwfms = dwfms;

        try {
            this.httpClient = HttpClient.newHttpClient();

            this.httpServer = HttpServer.create(new InetSocketAddress(6666), 0);

            httpServer.createContext("/test", this);
            httpServer.setExecutor(null); // creates a default executor
            httpServer.start();
        }
        catch(IOException ioe) {
            this.httpServer.stop(0);
        }

    }

    @Override
    public void sendMessage(InstanceReference instance, Action a) {
        List<String> participants = super.dwfms.getParticipants();

        String message = "";
        TaskExecution taskExecution = (TaskExecution) a;
        taskExecution.setUser(new UserReference("hans"));
        Message m = new Message(taskExecution);
        try {
            message = objectMapper.writeValueAsString(m);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:6666/test"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(message))
                .build();

        CompletableFuture<HttpResponse<Void>> response = this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding());

    }

    @Override
    public void sendAcknowledgement(Acknowledgement acknowledgement) {

    }

    /**
     * In simple connector, we simply notify all participants and send the hash.
     * Participants are asked to load the respective model locally in their machine.
     * @param model
     * @return
     */
    @Override
    public InstanceReference deployProcessModel(IModel model) {

        return null;
    }

    /**
     * Framework method.
     * We must override, because framework cannot decide how we connect to other nodes
     * In case of SimpleConnector, we use handle method from httpserver
     * @param message
     */
    @Override
    public void messageReceived(Message message) {
        // ACK or MSG?

        // ACK
            // check for agreement reached
        // if(true) {
        //            this.atAgreementReached(message.getTaskExecution().getInstance(), message.getTaskExecution());
        //        }

        // MSG

        //when a message:taskExecution is received, a new event is added to candidateLog
        Event event = new Event();
        event.setActivity(message.getTaskExecution().getTask());
        this.candidateLog.addEvent(event);
        this.messageReceived(message);

        message.getTaskExecution().getInstance().getInstanceRef();
        //TODO: Check conformance
        //if conform
        // send acknowledgement

            //check agreements

    }

    @Override
    public void acknowledgementReceived(Acknowledgement acknowledgement) {

    }


    /**
     * This should update the event log right?
     * Cannot be implemented in framework, because its up to us to define, when the event log must be updates / when agreement is reached.
     * @param instance
     * @param a
     */
    @Override
    public void atAgreementReached(InstanceReference instance, Action a) {

        // update event log
        // can this be moved to framework code?

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

        System.out.println("[SIMPLE::handle] Message received in HttpHandler...");

        // Create Message-Object from Request
        String requestBodyText = getTextFromInputStream(exchange.getRequestBody());
        Message message = objectMapper.readValue(requestBodyText, Message.class);

        System.out.println(message.getTaskExecution().getUser().getUserReference() + " wants to execute " + message.getTaskExecution().getTask());

        super.messageReceived(message);

//        //TODO: Test: send request
//        exchange.sendResponseHeaders(200, requestBodyText.length());
//        OutputStream os = exchange.getResponseBody();
//        os.write(message.getTaskExecution().getTask().getBytes());
//        os.close();
    }


    private String getTextFromInputStream(InputStream is) throws IOException {
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName(StandardCharsets.UTF_8.name())
                ))) {

            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }

        return textBuilder.toString();
    }
}
