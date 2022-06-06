package dwfms.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import dwfms.collaboration.simple.AcknowledgementHandler;
import dwfms.collaboration.simple.ActionHandler;
import dwfms.collaboration.simple.SimpleConnector;
import dwfms.framework.DWFMS;
import dwfms.framework.TaskExecution;
import dwfms.framework.collaboration.network.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpInterface implements HttpHandler {

    DWFMS dwfms;
    ObjectMapper objectMapper;
    HttpServer httpServer;

    public HttpInterface(DWFMS dwfms, int port) throws IOException {

        this.dwfms = dwfms;
        this.objectMapper = new ObjectMapper();

        this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);

        httpServer.createContext("/ui/execute", this);
        httpServer.setExecutor(null); // creates a default executor
        httpServer.start();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println("[SIMPLE::handle] Message received in HttpInterfaceHandler...");

        // Create Message-Object from Request
        String requestBodyText = SimpleConnector.getTextFromInputStream(exchange.getRequestBody());
        TaskExecution taskExecution = objectMapper.readValue(requestBodyText, TaskExecution.class);

        System.out.println("I want to execute " + taskExecution.getTask());

        dwfms.executeTask(taskExecution);

        String response = "OK";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
}
