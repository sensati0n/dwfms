package dwfms.collaboration.simple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import dwfms.framework.action.Action;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.collaboration.network.Message;
import dwfms.framework.core.BaseModel;
import dwfms.framework.core.DWFMS;
import dwfms.framework.references.Instance;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;

public class SimpleConnector extends BaseCollaboration {

    private static final Logger logger = LogManager.getLogger(SimpleConnector.class);

    @Setter private int numberOfAgreementsRequired = 1;

    //TODO: Usage introduces error, when application is shut down: "Build cancelled while executing task ':app:App.main()'"
    HttpServer httpServer;
    HttpClient httpClient;

    Set<String> recipients;

    ObjectMapper objectMapper = new ObjectMapper();

    public SimpleConnector(URL connection) {
        super(connection);
    }


    @Override
    public void init(DWFMS dwfms) {
        super.dwfms = dwfms;
        this.recipients = dwfms.getModel().getParticipants();
        this.numberOfAgreementsRequired = this.recipients.size()-1;

        try {
            this.httpClient = HttpClient.newHttpClient();

            this.httpServer = HttpServer.create(new InetSocketAddress(connection.getPort()), 0);

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
        Message m = new Message(taskExecution, this.dwfms.getUser());
        m.sign(super.dwfms.getUser());
        try {
            message = objectMapper.writeValueAsString(m);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        send(message, "action");

    }


    @Override
    public void sendAcknowledgement(Acknowledgement acknowledgement) {

        //Build message object
        String message = "";
        Acknowledgement a = acknowledgement;
        a.sign(super.dwfms.getUser());
        try {
            message = objectMapper.writeValueAsString(a);
        } catch (JsonProcessingException e) {
            logger.error("Jackson Error while parsing Acknowledgement object: " + e.getMessage());
        }

        logger.trace("Acknowledgement String: " + message);

        send(message, "ack");

    }

    /**
     * In simple connector, we simply notify all participants and send the hash.
     * Participants are asked to load the respective model locally in their machine.
     * @param model
     * @return
     */
    @Override
    public Instance deployProcessModel(BaseModel model) {

        String newInstanceRef = UUID.randomUUID().toString();
        Instance instance = new Instance(newInstanceRef, model.getModelReference().getModel());

        //Build message object
        String message = "";
        try {
            message = objectMapper.writeValueAsString(instance);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        send(message, "dply");

        return instance;
    }

    @Override
    public void instanceReceived(Instance instance) {

    }

    @Override
    public void checkAgreement(TaskExecution taskExecution) {

        logger.trace("Check for agreement...");
        if(this.candidateLog.getNumberOfAcknowledgements().get(taskExecution.getTask()) > this.numberOfAgreementsRequired) {
            logger.debug("Agreement reached.");
            this.atAgreementReached(null, taskExecution);
        }
        else {
            logger.debug("Agreement not reached yet.");
        }

    }

    private void send(String body, String to) {
        for(String recipient : this.recipients) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(recipient + to))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding());
        }

    }


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
