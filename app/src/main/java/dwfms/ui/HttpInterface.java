package dwfms.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import dwfms.framework.core.DWFMS;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;

@NoArgsConstructor
public class HttpInterface {

    private static final Logger logger = LogManager.getLogger(HttpInterface.class);

    @Getter
    private DWFMS dwfms;
    @Getter
    private ObjectMapper objectMapper;
    private HttpServer httpServer;

    public HttpInterface(DWFMS dwfms, int port) throws IOException {

        this.dwfms = dwfms;
        this.objectMapper = new ObjectMapper();

        this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);

        httpServer.createContext("/ui/execute", new TaskExecutionHandler(dwfms));
        httpServer.createContext("/ui/deploy", new DeploymentHandler(dwfms.getCollaboration()));
        httpServer.setExecutor(null); // creates a default executor
        httpServer.start();

        logger.trace("Started UI-HttpServer on port: " + port);
    }

}

