package dwfms.collaboration;

import dwfms.collaboration.simple.SimpleConnector;
import dwfms.framework.TaskExecution;
import dwfms.framework.User;
import dwfms.framework.references.Instance;
import dwfms.framework.references.UserReference;

import java.io.IOException;
import java.util.List;

public class SimpleConnectorTest {

    public void sendMessageTest() throws IOException {

        User hans = new User(new UserReference("hans"), null, null);

        SimpleConnector collaboration = new SimpleConnector(6666, List.of("http://localhost:6666"));

        Instance instanceReference = new Instance();
        TaskExecution taskExecution = new TaskExecution(instanceReference, "Start");
        taskExecution.setUser(hans);

        collaboration.sendMessage(instanceReference, taskExecution);


    }

    public void receiveMessageTest() throws IOException {

        SimpleConnector collaboration = new SimpleConnector(6666, List.of("http://localhost:6666"));



    }

}
