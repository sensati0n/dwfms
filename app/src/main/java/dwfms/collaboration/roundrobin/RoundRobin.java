package dwfms.collaboration.roundrobin;

import dwfms.collaboration.example.security.RSASecurity;
import dwfms.framework.action.DataUpdate;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.collaboration.consensus.BaseConsensusEngine;
import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.collaboration.network.INetwork;
import dwfms.framework.core.BaseModel;
import dwfms.framework.core.DWFMS;
import dwfms.framework.references.Instance;

import java.net.URL;

public class RoundRobin extends BaseCollaboration {

    public RoundRobin(URL connection, INetwork network, BaseConsensusEngine consensusEngine, RSASecurity security) {
        super(connection, network, consensusEngine, security);
    }

    @Override
    public void init(DWFMS dwfms) {
        super.dwfms = dwfms;
    }

    @Override
    public void sendTaskExecution(Instance instance, TaskExecution taskExecution) {
        String message = "";

        this.dwfms.getModel().getParticipants().forEach(p -> super.network.sendTaskExecution(message, p + "/action"));
    }

    @Override
    public void sendDataUpdate(Instance reference, DataUpdate dataUpdate) {

    }

    @Override
    public void sendAcknowledgement(Acknowledgement acknowledgement) {
        String message = "";

        super.network.sendAcknowledgement(message, "to");

    }

    @Override
    public Instance deployProcessModel(BaseModel model) {
        return null;
    }

    @Override
    public void instanceReceived(Instance instance) {

    }

    @Override
    public void checkAgreement(TaskExecution taskExecution) {

    }
}
