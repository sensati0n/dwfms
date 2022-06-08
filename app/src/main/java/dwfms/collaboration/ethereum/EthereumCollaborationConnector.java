package dwfms.collaboration.ethereum;

import dwfms.collaboration.example.security.RSASecurity;
import dwfms.framework.action.Action;
import dwfms.framework.action.DataUpdate;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.collaboration.consensus.BaseConsensusEngine;
import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.collaboration.network.INetwork;
import dwfms.framework.core.BaseModel;
import dwfms.framework.core.DWFMS;
import dwfms.framework.references.Instance;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Depends on the Smart Contract. The Smart Contract is part of the Collaboration-Tier
 *
 * No need to ask execution machine anything, because smart contract also checks process logic?
 */
public class EthereumCollaborationConnector extends BaseCollaboration {

    private DWFMS dwfms;
    private Web3j web3 ;

    private ClientTransactionManager ctm;

    //HELPER
    private Map<String, String> mapTxHashToTask = new HashMap<>();

    public EthereumCollaborationConnector(URL connection, INetwork network, BaseConsensusEngine engine, RSASecurity security) {
        super(connection, network, engine, security);
    }


    @Override
    public void init(DWFMS dwfms) {

        this.dwfms = dwfms;
        this.web3 = Web3j.build(new HttpService(String.valueOf(connection)));
        this.ctm = new ClientTransactionManager(web3, this.dwfms.getUser().getPublicKey());

    }

    /**
     * Where should we parse Actions to Messages?
     * We could alternatively create a Message/Transaction object in DWFMS
     * @param taskExecution
     */
    @Override
    public void sendTaskExecution(Instance instance, TaskExecution taskExecution) {

        String contractAddress = taskExecution.getInstance().getInstanceRef();
        String task = taskExecution.getTask();

        Miniksor m = Miniksor.load(contractAddress, this.web3, this.ctm, new DefaultGasProvider());

        // Call the smart contract function
        // The name of the function equals the task name per convention
        try {
            RemoteFunctionCall<TransactionReceipt> rfc = (RemoteFunctionCall<TransactionReceipt>) m.getClass().getMethod(task, null).invoke(m, null);
            rfc.sendAsync().thenAccept(result -> this.atAgreementReached(instance, taskExecution));

            /*
                transactionReceipt.getTo();
                transactionReceipt.getBlockNumber();
                transactionReceipt.toString();
             */
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendDataUpdate(Instance reference, DataUpdate dataUpdate) {

    }

    @Override
    public void sendAcknowledgement(Acknowledgement acknowledgement) {
    }

    @Override
    public void atAgreementReached(Instance instance, Action a) {
        // when a new block is found and a certain transaction is included
        // wait for 6 blocks? --> not in ProofOfAuthority
        this.dwfms.updateMachine(instance, a);

    }

    @Override
    public Instance deployProcessModel(BaseModel model) {

        // convert model to smart contract
        // we do that externally and assume that the wrapper are precompiled and available here as class

        // IModel --> Contract
        // model --> DocumentRegistry

        // here is one example 'process model', compiled and converted to java

        return new Instance(this.deployNewContract(Miniksor.class), model.getModelReference().getModel());

    }

    @Override
    public void instanceReceived(Instance instance) {

    }

    @Override
    public void acknowledgementReceived(Acknowledgement acknowledgement) {

    }

    @Override
    public void checkAgreement(TaskExecution taskExecution) {

    }

    private String deployNewContract(Class<? extends Contract> documentRegistryClass) {

        try {
            RemoteCall<Contract> rcDr = (RemoteCall<Contract>) documentRegistryClass.getDeclaredMethod("deploy", Web3j.class, TransactionManager.class, ContractGasProvider.class)
                    .invoke(null, this.web3, this.ctm, new DefaultGasProvider());
            Contract dr = rcDr.send();

            return dr.getContractAddress();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private void instanciateExistingContract(String contractAddress) {
        Miniksor miniksor = Miniksor.load("contractAddress", this.web3, this.ctm, new DefaultGasProvider());

    }

}
