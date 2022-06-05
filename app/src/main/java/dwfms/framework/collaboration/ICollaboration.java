package dwfms.framework.collaboration;

import dwfms.framework.Action;
import dwfms.framework.DWFMS;
import dwfms.framework.IModel;
import dwfms.framework.TaskExecution;
import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.collaboration.network.Message;
import dwfms.framework.collaboration.CandidateLog;
import dwfms.framework.log.Event;
import dwfms.framework.references.InstanceReference;

public abstract class ICollaboration {

    protected DWFMS dwfms;
    protected CandidateLog candidateLog;

    public abstract void init(DWFMS dwfms);

    public abstract void sendMessage(InstanceReference reference, Action a);
    public abstract void sendAcknowledgement(Acknowledgement acknowledgement);

    public abstract InstanceReference deployProcessModel(IModel model);

    /**
     * When a message is received, it is always included in the candidate log.
     *
     * @param message
     */
    protected void messageReceived(Message message) {


        System.out.println("[FRAMEWORK::messageReceived] Message received. Parse and check conformance...");

        Event event = new Event();
        event.setActivity(message.getTaskExecution().getTask());

        InstanceReference instanceReference = message.getTaskExecution().getInstance();
        TaskExecution taskExecution = message.getTaskExecution();
        if(this.dwfms.getExecutionMachine().isConform(instanceReference, taskExecution)) {

            System.out.println("[FRAMEWORK::messageReceived] Event (" + message.getTaskExecution().getTask() + ", " + message.getTaskExecution().getUser().getUserReference() + ") is model conform. Let's add it to the candidate log");

            this.dwfms.updateMachine(null, taskExecution);
            this.candidateLog.addEvent(event);

            System.out.println("[FRAMEWORK::messageReceived] Send Acknowledgement...");
            this.sendAcknowledgement(new Acknowledgement());
        }

    }

    public abstract void acknowledgementReceived(Acknowledgement acknowledgement);

    public abstract void atAgreementReached(InstanceReference instance, Action a);


}
