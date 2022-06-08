package dwfms.framework.collaboration;

import dwfms.collaboration.example.security.RSASecurity;
import dwfms.framework.action.Action;
import dwfms.framework.action.DataUpdate;
import dwfms.framework.action.UserAction;
import dwfms.framework.collaboration.consensus.BaseConsensusEngine;
import dwfms.framework.collaboration.network.INetwork;
import dwfms.framework.collaboration.security.ISecurity;
import dwfms.framework.core.BaseModel;
import dwfms.framework.core.DWFMS;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.collaboration.security.Utils;
import dwfms.framework.log.Event;
import dwfms.framework.references.Instance;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

public abstract class BaseCollaboration {

    private static final Logger logger = LogManager.getLogger(BaseCollaboration.class);

    protected URL connection;

    @Getter
    protected DWFMS dwfms;

    protected INetwork network;
    @Setter
    private BaseConsensusEngine consensusEngine;

    @Getter
    private ISecurity security;

    @Getter
    protected CandidateLog candidateLog = new CandidateLog();

    public abstract void init(DWFMS dwfms);

    public BaseCollaboration(URL connection, INetwork network, BaseConsensusEngine consensusEngine, RSASecurity security) {
        this.connection = connection;
        this.network = network;
        this.consensusEngine = consensusEngine;
        this.security = security;
    }


    /**
     * The implementing class decides, how messages are sent
     * For the sending process, the INetwork is used
     * @param reference
     * @param taskExecution
     */
    public abstract void sendTaskExecution(Instance reference, TaskExecution taskExecution);
    public abstract void sendDataUpdate(Instance reference, DataUpdate dataUpdate);
    public abstract void sendAcknowledgement(Acknowledgement acknowledgement);

    public abstract Instance deployProcessModel(BaseModel model);

    /**
     * When an action is received, it is always included in the candidate log.
     *
     * @param taskExecution
     */
    public void taskExecutionReceived(TaskExecution taskExecution) {

        logger.debug("Message received. Parse, verify and check conformance...");

        security.verify("", taskExecution.getSignature().getSignature(), taskExecution.getUser().getPublicKey());

        if(this.dwfms.getExecutionMachine().isConform(taskExecution.getInstance(), taskExecution)) {

            logger.debug("Event (" + taskExecution.getTask() + ", " + taskExecution.getUser().getUserReference().getName() + ") is model conform. Let's add it to the candidate log...");
            this.candidateLog.addEvent(new Event(taskExecution));

            logger.trace("Send Acknowledgement...");
            Acknowledgement acknowledgement = new Acknowledgement();
            acknowledgement.setAction(taskExecution);
            this.sendAcknowledgement(acknowledgement);
        }
        else {
            logger.warn("Event (" + taskExecution.getTask() + ", " + taskExecution.getUser().getUserReference().getName() + ") is not conform.");
        }
    }

    public void acknowledgementReceived(Acknowledgement acknowledgement) {

        //validate
        security.verify(acknowledgement.toString(), acknowledgement.getSignature().getSignature(), ((UserAction) acknowledgement.getAction()).getUser().getPublicKey());

        String task = ((TaskExecution) acknowledgement.getAction()).getTask();
        this.candidateLog.addAcknowledgementToEvent(task);

        this.checkAgreement(((TaskExecution) acknowledgement.getAction()));

    };

    public abstract void instanceReceived(Instance instance);

    public void checkAgreement(TaskExecution taskExecution) {
        this.consensusEngine.checkAgreement(taskExecution);
    };

    public void atAgreementReached(Instance instance, Action a) {
        TaskExecution taskExecution = (TaskExecution) a;
        this.dwfms.updateMachine(null, taskExecution);

    };


}
