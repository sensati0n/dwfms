package dwfms.collaboration.example.network;

import com.sun.net.httpserver.HttpServer;
import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.collaboration.network.INetwork;
import dwfms.ui.DeploymentHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpNetwork implements INetwork {

    //TODO: Usage introduces error, when application is shut down: "Build cancelled while executing task ':app:App.main()'"
    private HttpServer httpServer;
    private HttpClient httpClient = HttpClient.newHttpClient();

    private BaseCollaboration collaboration;

    public void init(BaseCollaboration collaboration, int port) {
        try {

            this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);

            httpServer.createContext("/ack", new AcknowledgementHandler(collaboration));
            httpServer.createContext("/action", new ActionHandler(collaboration));
            httpServer.createContext("/dply", new DeploymentHandler(collaboration));
            httpServer.setExecutor(null); // creates a default executor
            httpServer.start();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            this.httpServer.stop(0);
        }
    }

    @Override
    public void sendTaskExecution(String body, String to) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(to))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding());

    }

    @Override
    public String receiveTaskExecution() {
        return null;
    }

    @Override
    public void sendDataUpdate(String body, String to) {

    }

    @Override
    public String receiveDataUpdate() {
        return null;
    }

    @Override
    public void sendAcknowledgement(String body, String to) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(to))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding());

    }

    @Override
    public String receiveAcknowledgement() {

        return null;
    }

    @Override
    public void sendDeployment(String body, String to) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(to + "dply"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding());

    }

    @Override
    public String receiveDeployment() {
        return null;
    }
}
