package dwfms.collaboration.example.network;

import dwfms.framework.collaboration.network.INetwork;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpNetwork implements INetwork {

    private HttpClient httpClient = HttpClient.newHttpClient();

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

    }

    @Override
    public String receiveDeployment() {
        return null;
    }
}
