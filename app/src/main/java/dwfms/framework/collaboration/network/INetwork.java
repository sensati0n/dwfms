package dwfms.framework.collaboration.network;

public interface INetwork {

    public void sendTaskExecution(String body, String to);
    public String receiveTaskExecution();

    public void sendDataUpdate(String body, String to);
    public String receiveDataUpdate();

    public void sendAcknowledgement(String body, String to);
    public String receiveAcknowledgement();

    public void sendDeployment(String body, String to);
    public String receiveDeployment();

}
