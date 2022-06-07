package dwfms.collaboration.simple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import dwfms.framework.*;
import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.collaboration.network.Message;
import dwfms.framework.references.Instance;
import lombok.Setter;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SimpleConnector extends BaseCollaboration {


    @Setter private int numberOfAgreementsRequired = 1;

    int port;
    //TODO: Usage introduces error, when application is shut down: "Build cancelled while executing task ':app:App.main()'"
    HttpServer httpServer;
    HttpClient httpClient;

    List<String> recipients;

    ObjectMapper objectMapper = new ObjectMapper();


    public SimpleConnector(int port, List<String> recipients) {
        this.recipients = recipients;
        this.port = port;
    }

    @Override
    public void init(DWFMS dwfms) {
        super.dwfms = dwfms;

        try {
            this.httpClient = HttpClient.newHttpClient();

            this.httpServer = HttpServer.create(new InetSocketAddress(this.port), 0);

            httpServer.createContext("/ack", new AcknowledgementHandler(this));
            httpServer.createContext("/action", new ActionHandler(this));
            httpServer.setExecutor(null); // creates a default executor
            httpServer.start();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            this.httpServer.stop(0);
        }

    }

    @Override
    public void sendMessage(Instance instance, Action a) {

        //Build message object
        String message = "";
        TaskExecution taskExecution = (TaskExecution) a;
        Message m = new Message(taskExecution);
        try {
            message = objectMapper.writeValueAsString(m);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        for(String recipient : this.recipients) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(recipient + "action"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(message))
                    .build();

            this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding());
        }

    }


    @Override
    public void sendAcknowledgement(Acknowledgement acknowledgement) {

        //Build message object
        String message = "";
        Acknowledgement m = acknowledgement;
        try {
            message = objectMapper.writeValueAsString(m);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("ACK STRING: " + message);

        for(String recipient : this.recipients) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(recipient + "ack"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(message))
                    .build();

            this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding());
        }

    }

    /**
     * In simple connector, we simply notify all participants and send the hash.
     * Participants are asked to load the respective model locally in their machine.
     * @param model
     * @return
     */
    @Override
    public Instance deployProcessModel(IModel model) {

        String newInstanceRef = UUID.randomUUID().toString();
        Instance instance = new Instance(newInstanceRef, model.getHash());

        //Build message object
        String message = "";
        try {
            message = objectMapper.writeValueAsString(instance);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:6666/dply"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(message))
                .build();

        CompletableFuture<HttpResponse<Void>> response = this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding());

        return instance;
    }

    @Override
    public void instanceReceived(Instance instance) {

    }

    @Override
    public void checkAgreement(TaskExecution taskExecution) {

        System.out.println("CHECK AGREEMENT");
        if(this.candidateLog.getNumberOfAcknowledgements().get(taskExecution.getTask()) > this.numberOfAgreementsRequired) {
            System.out.println("Agreement reached.");
            this.atAgreementReached(null, taskExecution);

        }
        else {
            System.out.println("Agreement not reached yet.");
        }

    }


    /**
     * Framework method.
     * We must override, because framework cannot decide how we connect to other nodes
     * In case of SimpleConnector, we use handle method from httpserver
     * @param message
     */
  /*  @Override
    public void messageReceived(Message message) {
        super.messageReceived(message);
        // ACK or MSG?


        // ACK
            // check for agreement reached
        // if(true) {
        //            this.atAgreementReached(message.getTaskExecution().getInstance(), message.getTaskExecution());
        //        }

        // MSG

        //when a message:taskExecution is received, a new event is added to candidateLog
        //Event event = new Event();
        //event.setActivity(message.getTaskExecution().getTask());
        //this.candidateLog.addEvent(event);
        //this.messageReceived(message);

        //message.getTaskExecution().getInstance().getInstanceRef();
        //TODO: Check conformance
        //if conform
        // send acknowledgement

            //check agreements

    }
    */



    public static String getTextFromInputStream(InputStream is) throws IOException {
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
