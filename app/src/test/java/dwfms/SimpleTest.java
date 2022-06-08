package dwfms;

import dwfms.collaboration.example.security.RSASecurity;
import dwfms.collaboration.example.SimpleConnector;
import dwfms.collaboration.example.consensus.ThresholdConsensus;
import dwfms.collaboration.example.network.HttpNetwork;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.action.User;
import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.core.BaseModel;
import dwfms.framework.core.DWFMS;
import dwfms.framework.core.ITransformer;
import dwfms.framework.log.Event;
import dwfms.framework.references.Instance;
import dwfms.model.BPMNToHybridExecutionMachineTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleTest {

    private static final Logger logger = LogManager.getLogger(SimpleTest.class);

    @Test
    public void simpleTest() throws InterruptedException, MalformedURLException {

        // Initialize dWFMS
        User hans = ExampleDataFactory.hansSimple();

        BaseModel model = ExampleDataFactory.exampleBPMNModel();
        ITransformer transformer = new BPMNToHybridExecutionMachineTransformer();
        BaseCollaboration collaboration = new SimpleConnector(new URL("http://localhost:3001"), new HttpNetwork(), new ThresholdConsensus(1), new RSASecurity());

        DWFMS dWFMS = DWFMS.builder()
                .model(model)
                .transformer(transformer)
                .collaboration(collaboration)
                .build();

        dWFMS.init(hans);

        // Deploy a process model
        Instance reference = dWFMS.deployProcessModel();

        TaskExecution executeStart = new TaskExecution(reference, "Start");
        executeStart.setUser(hans);

        //It is conformed that we can execute Start in the beginning.
        assertTrue(dWFMS.getExecutionMachine().isConform(reference, executeStart));
        dWFMS.executeTask(executeStart);
        TimeUnit.SECONDS.sleep(2);

        //The candidate log is updated
        assertEquals(1, dWFMS.getCollaboration().getCandidateLog().getEvents().size());
        assertTrue(dWFMS.getCollaboration().getCandidateLog().getEvents().contains(new Event(1, "Start", "hans")));
        assertEquals(1, dWFMS.getCollaboration().getCandidateLog().getNumberOfAcknowledgements().get("Start"));

        //But the execution machine not yet
        assertTrue(dWFMS.getExecutionMachine().isConform(reference, executeStart));

        TaskExecution executeA = new TaskExecution(reference, "A");
        executeA.setUser(hans);
        assertFalse(dWFMS.getExecutionMachine().isConform(reference, executeA));

        fakeSecondAcknowledgement(dWFMS.getCollaboration());

        //The candidate log is updated again
        assertEquals(2, dWFMS.getCollaboration().getCandidateLog().getNumberOfAcknowledgements().get("Start"));

        //But the execution machine not yet
        assertFalse(dWFMS.getExecutionMachine().isConform(reference, executeStart));
        assertTrue(dWFMS.getExecutionMachine().isConform(reference, executeA));

//
//        dWFMS.executeTask(new TaskExecution(reference, "A"));
//        TimeUnit.SECONDS.sleep(2);
//        TimeUnit.SECONDS.sleep(2);
//        TimeUnit.SECONDS.sleep(2);
//
//        dWFMS.executeTask(new TaskExecution(reference, "C"));

        logger.debug("App stops...");


    }


    private void fakeSecondAcknowledgement(BaseCollaboration collab) throws InterruptedException {

        TaskExecution executeStart = new TaskExecution(null, "Start");
        executeStart.setUser(ExampleDataFactory.hansSimple());
        Acknowledgement acknowledgement = new Acknowledgement();
        acknowledgement.setAction(executeStart);

        collab.sendAcknowledgement(acknowledgement);
        TimeUnit.SECONDS.sleep(2);

    }
}
