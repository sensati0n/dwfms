package dwfms.collaboration;

import dwfms.collaboration.example.security.RSASecurity;
import dwfms.collaboration.example.SimpleCollaboration;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.action.User;
import dwfms.framework.references.Instance;
import dwfms.framework.references.UserReference;

import java.io.IOException;
import java.net.URL;

public class SimpleConnectorTest {

    public void sendMessageTest() throws IOException {

        User hans = new User(new UserReference("hans"), null, null);

        SimpleCollaboration collaboration = new SimpleCollaboration(new URL("http://localhost:6666"));

        Instance instanceReference = new Instance();
        TaskExecution taskExecution = new TaskExecution(instanceReference, "Start");
        taskExecution.setUser(hans);

        collaboration.sendTaskExecution(instanceReference, taskExecution);

    }

    public void receiveMessageTest() throws IOException {

        SimpleCollaboration collaboration = new SimpleCollaboration(new URL("http://localhost:6666"));

    }

}
