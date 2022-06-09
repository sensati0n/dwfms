package dwfms.collaboration.example;

import dwfms.collaboration.example.consensus.RoundRobinConsensus;
import dwfms.collaboration.example.network.HttpNetwork;
import dwfms.collaboration.example.security.RSASecurity;
import dwfms.framework.action.DataUpdate;
import dwfms.framework.action.TaskExecution;
import dwfms.collaboration.BaseCollaboration;
import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.bpm.model.BaseModel;
import dwfms.framework.core.DWFMS;
import dwfms.framework.bpm.execution.Instance;

import java.net.URL;

public class RoundRobinCollaboration extends BaseCollaboration {

    public RoundRobinCollaboration(URL connection) {
        super(connection, new HttpNetwork(), new RoundRobinConsensus(), new RSASecurity());
    }

    @Override
    public void init(DWFMS dwfms) {
        super.dwfms = dwfms;
        super.getConsensusEngine().setCollaboration(this);
    }

    @Override
    public void sendTaskExecution(TaskExecution taskExecution) {
        String message = "";

        this.dwfms.getModel().getParticipants().forEach(p -> super.network.sendTaskExecution(message, p + "/action"));
    }

    @Override
    public void sendDataUpdate(DataUpdate dataUpdate) {

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
