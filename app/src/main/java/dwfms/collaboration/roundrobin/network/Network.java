package dwfms.collaboration.roundrobin.network;

import dwfms.collaboration.roundrobin.RoundRobin;
import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.collaboration.network.INetwork;
import dwfms.framework.collaboration.network.Message;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Network implements INetwork {

    private HttpClient httpClient;
    private BaseCollaboration collaboration;

    public Network(BaseCollaboration collaboration) {
        this.httpClient = HttpClient.newHttpClient();
        this.collaboration = collaboration;
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
       return "";
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
        return "";
    }

    @Override
    public void sendDeployment(String body, String to) {

    }

    @Override
    public String receiveDeployment() {
        return null;
    }
}
