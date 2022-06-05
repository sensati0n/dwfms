package dwfms.collaboration.ethereum;

import dwfms.framework.*;
import dwfms.framework.collaboration.ICollaboration;
import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.collaboration.network.Message;
import dwfms.framework.references.InstanceReference;
import lombok.Data;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Depends on the Smart Contract. The Smart Contract is part of the Collaboration-Tier
 *
 * No need to ask execution machine anything, because smart contract also checks process logic?
 */
@Data
public class EthereumCollaborationConnector extends ICollaboration {

    private DWFMS dwfms;

    Web3j web3 = Web3j.build(new HttpService("http://localhost:8545"));

    private String myAddress;
    private ClientTransactionManager ctm;

    //HELPER
    private Map<String, String> mapTxHashToTask = new HashMap<>();


    public EthereumCollaborationConnector(String address) {
        try {

            Web3ClientVersion clientVersion = web3.web3ClientVersion().send();
            EthBlockNumber blockNumber = web3.ethBlockNumber().send();
            EthGasPrice gasPrice =  web3.ethGasPrice().send();

            this.myAddress = address;
            this.ctm = new ClientTransactionManager(web3, address);

        } catch(IOException ex) {
            throw new RuntimeException("Error whilst sending json-rpc requests", ex);
        }
    }

    @Override
    public void init(DWFMS dwfms) {
        this.dwfms = dwfms;
    }

    /**
     * Where should we parse Actions to Messages?
     * We could alternatively create a Message/Transaction object in DWFMS
     * @param a
     */
    @Override
    public void sendMessage(InstanceReference instance, Action a) {

        if(a instanceof TaskExecution) {
            TaskExecution taskExecution = (TaskExecution) a;
            String contractAddress = taskExecution.getInstance().getInstanceRef();
            String task = taskExecution.getTask();

            Miniksor m = Miniksor.load(contractAddress, this.web3, this.ctm, new DefaultGasProvider());

            // Call the smart contract function
            // The name of the function equals the task name per convention
            try {
                RemoteFunctionCall<TransactionReceipt> rfc = (RemoteFunctionCall<TransactionReceipt>) m.getClass().getMethod(task, null).invoke(m, null);
                rfc.sendAsync().thenAccept(result -> this.atAgreementReached(instance, a));

                /*
                    transactionReceipt.getTo();
                    transactionReceipt.getBlockNumber();
                    transactionReceipt.toString();
                 */
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void sendAcknowledgement(Acknowledgement acknowledgement) {

    }


    @Override
    public void atAgreementReached(InstanceReference instance, Action a) {
        // when a new block is found and a certain transaction is included
        // wait for 6 blocks? --> not in ProofOfAuthority

        this.dwfms.updateMachine(instance, a);

    }

    @Override
    public InstanceReference deployProcessModel(IModel model) {

        // convert model to smart contract
        // we do that externally and assume that the wrapper are precompiled and available here as class

        // IModel --> Contract
        // model --> DocumentRegistry

        // here is one example 'process model', compiled and converted to java

        return new InstanceReference(this.deployNewContract(Miniksor.class), model);

    }

    @Override
    public void messageReceived(Message message) {

    }

    @Override
    public void acknowledgementReceived(Acknowledgement acknowledgement) {

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

    private void testSendTransaction() {

        try {
            String myAddress = web3.ethAccounts().send().getAccounts().get(0);
            String theirAddress = web3.ethAccounts().send().getAccounts().get(1);

            String result = ctm.sendTransaction(new BigInteger("155"), new BigInteger("672197"), theirAddress, "data", new BigInteger("12000000000000000000")).getResult();

            System.out.println("RESULT: " + result);
            System.out.println("BALANCE: " + web3.ethGetBalance(myAddress, DefaultBlockParameterName.LATEST).send().getBalance().toString());


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
