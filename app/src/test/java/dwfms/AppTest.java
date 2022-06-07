/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package dwfms;

import dwfms.collaboration.simple.SimpleConnector;
import dwfms.framework.*;
import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.log.Event;
import dwfms.framework.references.Instance;
import dwfms.model.BPMNToHybridExecutionMachineTransformer;
import dwfms.model.bpmn.BPMNModel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


class AppTest {


    @Test
    void appTest() throws NoSuchAlgorithmException, IOException, InterruptedException {

        /**
         * APP FOR hans
         */
        User hans = ExampleDataFactory.hansSimple();

        IModel model_hans = new BPMNModel();
        ITransformer transformer_hans = new BPMNToHybridExecutionMachineTransformer();
        BaseCollaboration collaboration_hans = new SimpleConnector(3001, List.of("http://localhost:3001/", "http://localhost:4001/"));

        DWFMS dWFMS_hans = DWFMS.builder()
                .model(model_hans)
                .transformer(transformer_hans)
                .collaboration(collaboration_hans)
                .build();

        dWFMS_hans.init(hans);


        /**
         * APP FOR peter
         */
        User peter = ExampleDataFactory.peterSimple();

        IModel model_peter = new BPMNModel();
        ITransformer transformer_peter = new BPMNToHybridExecutionMachineTransformer();
        BaseCollaboration collaboration_peter = new SimpleConnector(4001, List.of("http://localhost:3001/", "http://localhost:4001/"));

        DWFMS dWFMS_peter = DWFMS.builder()
                .model(model_peter)
                .transformer(transformer_peter)
                .collaboration(collaboration_peter)
                .build();

        dWFMS_peter.init(peter);

        // model hash fc2d41015d1e84374e0e6ec5dc491c10556c2aa7133e7cdcc3dcd708569587b6
        Instance reference = dWFMS_hans.deployProcessModel();

        TaskExecution executeStart = new TaskExecution(reference, "Start");
        executeStart.setUser(hans);
        //It is conformed that we can execute A in the beginning.
        assertTrue(dWFMS_hans.getExecutionMachine().isConform(reference, executeStart));
        dWFMS_hans.executeTask(executeStart);
        TimeUnit.SECONDS.sleep(10);

        //The candidate log is updated
        assertEquals(1, dWFMS_hans.getCollaboration().getCandidateLog().getEvents().size());
        assertTrue(dWFMS_hans.getCollaboration().getCandidateLog().getEvents().contains(new Event(1, "Start", "hans")));
        assertEquals(1, dWFMS_hans.getCollaboration().getCandidateLog().getNumberOfAcknowledgements().get("Start"));

        //Also the candidate log of peter?
        assertEquals(1, dWFMS_peter.getCollaboration().getCandidateLog().getEvents().size());
        assertTrue(dWFMS_peter.getCollaboration().getCandidateLog().getEvents().contains(new Event(1, "Start", "hans")));
        assertEquals(1, dWFMS_peter.getCollaboration().getCandidateLog().getNumberOfAcknowledgements().get("Start"));

    }

    @Test
    void test() throws InterruptedException, IOException {

        User hans = ExampleDataFactory.hansSimple();

        IModel model = new BPMNModel();
        ITransformer transformer = new BPMNToHybridExecutionMachineTransformer();
        BaseCollaboration collaboration = new SimpleConnector(6666, List.of("http://localhost:6666/"));

        DWFMS dWFMS = DWFMS.builder()
                .model(model)
                .transformer(transformer)
                .collaboration(collaboration)
                .build();

        dWFMS.init(hans);

        // model hash fc2d41015d1e84374e0e6ec5dc491c10556c2aa7133e7cdcc3dcd708569587b6
        Instance reference = dWFMS.deployProcessModel();

        TaskExecution executeStart = new TaskExecution(reference, "Start");
        executeStart.setUser(hans);
        //It is conformed that we can execute A in the beginning.
        assertTrue(dWFMS.getExecutionMachine().isConform(reference, executeStart));
        dWFMS.executeTask(executeStart);
        TimeUnit.SECONDS.sleep(10);

        //The candidate log is updated
        assertEquals(1, dWFMS.getCollaboration().getCandidateLog().getEvents().size());
        assertTrue(dWFMS.getCollaboration().getCandidateLog().getEvents().contains(new Event(1, "Start", "hans")));
        assertEquals(1, dWFMS.getCollaboration().getCandidateLog().getNumberOfAcknowledgements().get("Start"));

        //But the execution machine not yet
        assertTrue(dWFMS.getExecutionMachine().isConform(reference, executeStart));

        TaskExecution executeA = new TaskExecution(reference, "A");
        executeA.setUser(hans);
        assertFalse(dWFMS.getExecutionMachine().isConform(reference, executeA));

        sendSecondAcknowledgement(dWFMS.getCollaboration());

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

        System.out.println("App stops...");

    }

    private void sendSecondAcknowledgement(BaseCollaboration collab) throws IOException, InterruptedException {

        TaskExecution executeStart = new TaskExecution(null, "Start");
        executeStart.setUser(ExampleDataFactory.hansSimple());
        Acknowledgement acknowledgement = new Acknowledgement();
        acknowledgement.setTaskExecution(executeStart);

        collab.sendAcknowledgement(acknowledgement);
        TimeUnit.SECONDS.sleep(10);

    }

}
