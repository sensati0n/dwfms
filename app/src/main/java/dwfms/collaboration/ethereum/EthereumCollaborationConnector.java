package dwfms.collaboration.ethereum;

import dwfms.ExampleDataFactory;
import dwfms.collaboration.example.security.NoSecurity;
import dwfms.collaboration.example.security.RSASecurity;
import dwfms.framework.action.Action;
import dwfms.framework.action.DataUpdate;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.action.User;
import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.collaboration.consensus.BaseConsensusEngine;
import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.collaboration.network.INetwork;
import dwfms.framework.core.BaseModel;
import dwfms.framework.core.DWFMS;
import dwfms.framework.references.Instance;
import dwfms.framework.references.UserReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Depends on the Smart Contract. The Smart Contract is part of the Collaboration-Tier
 *
 * No need to ask execution machine anything, because smart contract also checks process logic?
 */
public class EthereumCollaborationConnector extends BaseCollaboration {

    private static final Logger logger = LogManager.getLogger(EthereumCollaborationConnector.class);

    private Web3j web3 ;

    private ClientTransactionManager ctm;

    //HELPER
    private Map<String, String> mapTxHashToTask = new HashMap<>();

    public EthereumCollaborationConnector(URL connection, int myPort) {
        super(connection, new EthereumNetwork(myPort), null, new NoSecurity());
    }

    @Override
    public void init(DWFMS dwfms) {

        super.dwfms = dwfms;

        //TODO: Refactor the architecture to set this in constructor maybe
        //Is this enough or must we set the consensus engine again?
        ((EthereumNetwork) super.network).setCollaborationConnector(this);
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
            logger.trace("Send Task Execution: (" + taskExecution.getTask() + ", " + taskExecution.getUser().getUserReference().getName() +")");
            RemoteFunctionCall<TransactionReceipt> rfc = (RemoteFunctionCall<TransactionReceipt>) m.getClass().getMethod(task, null).invoke(m, null);

            // We now listen for events
            // rfc.sendAsync().thenAccept(result -> this.atAgreementReached(instance, taskExecution));
            rfc.sendAsync();

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
    public Instance deployProcessModel(BaseModel model) {

        logger.trace("Deploy new process.");
        Instance instance = new Instance(this.deployNewContract(Miniksor.class), model.getModelReference().getModel());

        logger.trace("Notify participants.");
        this.getDwfms().getModel().getParticipants().forEach(recipient -> {
            this.network.sendDeployment(instance.getInstanceRef(), recipient);
        });

        return instance;

    }

    @Override
    public void instanceReceived(Instance instance) {
        logger.debug("New instance received: " + instance);

        subscribeOnEvents(instance.getInstanceRef());
    }

    private void subscribeOnEvents(String address) {

        logger.debug("Subscribe for events on: " + address);

        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, address);
        web3.ethLogFlowable(filter).subscribe(this::eventReceived);
    }

    private void eventReceived(Log event) {
        List<Type> args = FunctionReturnDecoder.decode(event.getData(),
                Miniksor.TASKFINISHED_EVENT.getParameters());

        logger.debug("New event received for instance" + event.getAddress() + ": (" + args.get(0).getValue().toString() + args.get(1).getValue().toString() + ")");

        TaskExecution taskExecution = new TaskExecution(null, args.get(0).getValue().toString());
        taskExecution.setUser(ExampleDataFactory.getUserByPublicKey(args.get(1).getValue().toString()));
        Acknowledgement acknowledgement = new Acknowledgement(taskExecution);
        this.acknowledgementReceived(acknowledgement);
    }

    @Override
    public void acknowledgementReceived(Acknowledgement acknowledgement) {
        logger.trace("Acknowledgement received");
        this.checkAgreement(acknowledgement.getAction());
    }

    /**
     * We override check agreement, because we do not have a consensus engine.
     * The consensus engine is outsourced, hence whenever we receive an acknowledgement, agreement is already reached.
     * An acknowledgement corresponds to a smart contract event.
     *
     * Here, we could decide that we want to wait for 6 more blocks, depending on the configuration of the blockchain.
     *
     * //TODO: If we do not use a smart contract, but raw transaction instead this might be changed.
     *
     * @param taskExecution
     */
    @Override
    public void checkAgreement(TaskExecution taskExecution) {
        this.atAgreementReached(taskExecution.getInstance(), taskExecution);
    }


    //  ****************************
    //
    //      HELPER METHODS
    //
    //  ****************************


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

        logger.trace("Instantiate existing process with contract address: " + contractAddress);

        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddress);
        web3.ethLogFlowable(filter).subscribe(event -> {
            List<Type> args = FunctionReturnDecoder.decode(event.getData(),
                    Miniksor.TASKFINISHED_EVENT.getParameters());
            logger.debug("New event received: (" + args.get(0).getValue().toString() + args.get(1).getValue().toString() + ")");
        });


    }

}
