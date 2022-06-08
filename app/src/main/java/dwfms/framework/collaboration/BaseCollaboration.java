package dwfms.framework.collaboration;

import dwfms.framework.action.Action;
import dwfms.framework.core.BaseModel;
import dwfms.framework.core.DWFMS;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.collaboration.network.Message;
import dwfms.framework.collaboration.security.Utils;
import dwfms.framework.log.Event;
import dwfms.framework.references.Instance;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

public abstract class BaseCollaboration {

    private static final Logger logger = LogManager.getLogger(BaseCollaboration.class);

    protected DWFMS dwfms;
    protected URL connection;

    @Getter
    protected CandidateLog candidateLog = new CandidateLog();

    public abstract void init(DWFMS dwfms);

    public BaseCollaboration( URL connection ) {
        this.connection = connection;
    }


    public abstract void sendMessage(Instance reference, Action a);
    public abstract void sendAcknowledgement(Acknowledgement acknowledgement);

    public abstract Instance deployProcessModel(BaseModel model);

    /**
     * When a message is received, it is always included in the candidate log.
     *
     * @param message
     */
    public void messageReceived(Message message) {


        logger.debug("Message received. Parse and check conformance...");

        Event event = new Event(message.getTaskExecution());
        Instance instanceReference = message.getTaskExecution().getInstance();
        TaskExecution taskExecution = message.getTaskExecution();
        if(this.dwfms.getExecutionMachine().isConform(instanceReference, taskExecution)) {

            logger.debug("Event (" + message.getTaskExecution().getTask() + ", " + message.getTaskExecution().getUser().getUserReference().getName() + ") is model conform. Let's add it to the candidate log...");

            this.candidateLog.addEvent(event);

            logger.trace("Send Acknowledgement...");
            Acknowledgement acknowledgement = new Acknowledgement();
            acknowledgement.setTaskExecution(taskExecution);
            this.sendAcknowledgement(acknowledgement);
        }
        else {
            logger.warn("Event (" + message.getTaskExecution().getTask() + ", " + message.getTaskExecution().getUser().getUserReference().getName() + ") is not conform.");
        }
    }

    public abstract void instanceReceived(Instance instance);

    public void acknowledgementReceived(Acknowledgement acknowledgement) {

        //validate
        Utils.verify(acknowledgement.toString(), acknowledgement.getSignature().getSignature(), Utils.stringToPublicKey(acknowledgement.getTaskExecution().getUser().getPublicKey()));

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
