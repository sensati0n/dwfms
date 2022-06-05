package dwfms.collaboration;

import dwfms.collaboration.simple.SimpleConnector;
import dwfms.framework.TaskExecution;
import dwfms.framework.references.InstanceReference;
import dwfms.framework.references.UserReference;

import java.io.IOException;

public class SimpleConnectorTest {

    public void sendMessageTest() throws IOException {

        SimpleConnector collaboration = new SimpleConnector();

        InstanceReference instanceReference = new InstanceReference();
        TaskExecution taskExecution = new TaskExecution(instanceReference, "Start");
        taskExecution.setUser(new UserReference("hans"));

        collaboration.sendMessage(instanceReference, taskExecution);


    }

    public void receiveMessageTest() throws IOException {

        SimpleConnector collaboration = new SimpleConnector();



    }

}
