package dwfms.collaboration.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import dwfms.collaboration.example.security.RSASecurity;
import dwfms.collaboration.example.network.AcknowledgementHandler;
import dwfms.collaboration.example.network.ActionHandler;
import dwfms.framework.action.DataUpdate;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.collaboration.Signature;
import dwfms.framework.collaboration.consensus.BaseConsensusEngine;
import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.collaboration.network.INetwork;
import dwfms.framework.core.BaseModel;
import dwfms.framework.core.DWFMS;
import dwfms.framework.references.Instance;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.http.HttpClient;
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

    public SimpleConnector(URL connection, INetwork network, BaseConsensusEngine consensusEngine, RSASecurity security) {
        super(connection, network, consensusEngine, security);
        consensusEngine.setCollaboration(this);
        super.setConsensusEngine(consensusEngine);
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
    public void sendTaskExecution(Instance instance, TaskExecution taskExecution) {

        //Sign
        String signature = super.getSecurity().sign(taskExecution.toString(), super.getDwfms().getUser().getPrivateKey());
        taskExecution.setSignature(new Signature(signature, super.getDwfms().getUser()));

        //Build message object
        String message = "";

        try {
            message = objectMapper.writeValueAsString(taskExecution);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        for(String recipient : this.dwfms.getModel().getParticipants()) {
            this.network.sendTaskExecution(message, recipient + "action");

        }


    }

    @Override
    public void sendDataUpdate(Instance reference, DataUpdate dataUpdate) {

    }

    public void sendAcknowledgement(Acknowledgement acknowledgement) {

        //Sign
        String signature = super.getSecurity().sign(acknowledgement.toString(), super.getDwfms().getUser().getPrivateKey());
        acknowledgement.setSignature(new Signature(signature, super.getDwfms().getUser()));


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

        for(String recipient : this.recipients) {
            this.network.sendAcknowledgement(message, recipient+"ack");
        }

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

        this.network.sendDeployment(message, "dply");

        return instance;
    }

    @Override
    public void instanceReceived(Instance instance) {

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
