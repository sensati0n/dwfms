package dwfms.framework.collaboration;

import dwfms.framework.Action;
import dwfms.framework.DWFMS;
import dwfms.framework.IModel;
import dwfms.framework.TaskExecution;
import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.collaboration.network.Message;
import dwfms.framework.collaboration.security.Utils;
import dwfms.framework.log.Event;
import dwfms.framework.references.Instance;
import lombok.Getter;

public abstract class BaseCollaboration {

    protected DWFMS dwfms;
    @Getter
    protected CandidateLog candidateLog = new CandidateLog();

    public abstract void init(DWFMS dwfms);

    public abstract void sendMessage(Instance reference, Action a);
    public abstract void sendAcknowledgement(Acknowledgement acknowledgement);

    public abstract Instance deployProcessModel(IModel model);

    /**
     * When a message is received, it is always included in the candidate log.
     *
     * @param message
     */
    public void messageReceived(Message message) {


        System.out.println("[FRAMEWORK::messageReceived] Message received. Parse and check conformance...");

        Event event = new Event();
        event.setActivity(message.getTaskExecution().getTask());
        event.setResource(message.getTaskExecution().getUser().getUserReference().getName());
        Instance instanceReference = message.getTaskExecution().getInstance();
        TaskExecution taskExecution = message.getTaskExecution();
        if(this.dwfms.getExecutionMachine().isConform(instanceReference, taskExecution)) {

            System.out.println("[FRAMEWORK::messageReceived] Event (" + message.getTaskExecution().getTask() + ", " + message.getTaskExecution().getUser().getUserReference().getName() + ") is model conform. Let's add it to the candidate log");

            this.candidateLog.addEvent(event);

            System.out.println("[FRAMEWORK::messageReceived] Send Acknowledgement...");
            Acknowledgement acknowledgement = new Acknowledgement();
            acknowledgement.setTaskExecution(taskExecution);
            this.sendAcknowledgement(acknowledgement);
        }
        else {
            System.out.println("[FRAMEWORK::messageReceived] WARNING NOT CONFORM: Event (" + message.getTaskExecution().getTask() + ", " + message.getTaskExecution().getUser().getUserReference().getName() + ").");
        }
    }

    public abstract void instanceReceived(Instance instance);

    //protected abstract void registerHandler();

    public void acknowledgementReceived(Acknowledgement acknowledgement) {

        //validate
        Utils.verify(acknowledgement.toString(), acknowledgement.getSignature(), Utils.stringToPublicKey(acknowledgement.getTaskExecution().getUser().getPublicKey()));

        String task = acknowledgement.getTaskExecution().getTask();
        this.candidateLog.addAcknowledgementToEvent(task);

        this.checkAgreement(acknowledgement.getTaskExecution());

    };

    public abstract void checkAgreement(TaskExecution taskExecution);

    public void atAgreementReached(Instance instance, Action a) {
        TaskExecution taskExecution = (TaskExecution) a;
        this.dwfms.updateMachine(null, taskExecution);

    };


}
