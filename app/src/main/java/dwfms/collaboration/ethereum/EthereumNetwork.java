package dwfms.collaboration.ethereum;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import dwfms.collaboration.example.SimpleCollaboration;
import dwfms.framework.collaboration.network.INetwork;
import dwfms.framework.bpm.execution.Instance;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EthereumNetwork implements INetwork, HttpHandler {

    private static final Logger logger = LogManager.getLogger(EthereumNetwork.class);

    @Setter
    private EthereumCollaborationConnector collaborationConnector;

    private HttpClient httpClient = HttpClient.newHttpClient();
    private HttpServer httpServer;


    public EthereumNetwork(int port) {

        try {
            this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        httpServer.createContext("/dply", this);
        httpServer.setExecutor(null); // creates a default executor
        httpServer.start();
    }

    // Outsourced to Ethereum
    @Override public void sendTaskExecution(String body, String to) { }
    @Override public String receiveTaskExecution() { return null; }
    @Override public void sendDataUpdate(String body, String to) { }
    @Override public String receiveDataUpdate() { return null; }
    @Override public void sendAcknowledgement(String body, String to) { }
    @Override public String receiveAcknowledgement() { return null; }

    @Override
    public void sendDeployment(String body, String to) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(to + "dply"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding());

    }

    /**
     * this is done by handle()
     * @return
     */
    @Override public String receiveDeployment() { return null; }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // new deployment received

        logger.trace("Message received in Deployment Handler...");

        // Create Message-Object from Request
        String requestBodyText = SimpleCollaboration.getTextFromInputStream(exchange.getRequestBody());

        //TODO: which model?
        Instance instance = new Instance();
        instance.setInstanceRef(requestBodyText);
        collaborationConnector.instanceReceived(instance);
    }
}
