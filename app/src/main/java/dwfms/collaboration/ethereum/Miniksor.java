package dwfms.collaboration.ethereum;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class Miniksor extends Contract {
    public static final String BINARY = "0x608060405260008060006101000a81548160ff02191690831515021790555060008060016101000a81548160ff02191690831515021790555060008060026101000a81548160ff02191690831515021790555060008060036101000a81548160ff02191690831515021790555060008060046101000a81548160ff02191690831515021790555060008060056101000a81548160ff0219169083151502179055503480156100ac57600080fd5b50610a7f806100bc6000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c806306e64907146100675780630f529ba2146100715780631b55ba3a1461007b57806332e7c5bf1461008557806383dfe5fd1461008f578063f446c1d014610099575b600080fd5b61006f6100a3565b005b61007961015f565b005b610083610232565b005b61008d61029c565b005b6100976103a8565b005b6100a16104b4565b005b600060059054906101000a900460ff16156100f3576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016100ea906105cb565b60405180910390fd5b600060049054906101000a900460ff16610142576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161013990610637565b60405180910390fd5b6001600060056101000a81548160ff021916908315150217905550565b600060049054906101000a900460ff16156101af576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101a6906106a3565b60405180910390fd5b600060029054906101000a900460ff16806101d65750600060039054906101000a900460ff165b610215576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161020c90610735565b60405180910390fd5b6001600060046101000a81548160ff021916908315150217905550565b60008054906101000a900460ff1615610280576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610277906107a1565b60405180910390fd5b60016000806101000a81548160ff021916908315150217905550565b600060029054906101000a900460ff16156102ec576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102e39061080d565b60405180910390fd5b600060039054906101000a900460ff161561033c576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161033390610879565b60405180910390fd5b600060019054906101000a900460ff1661038b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610382906108e5565b60405180910390fd5b6001600060026101000a81548160ff021916908315150217905550565b600060039054906101000a900460ff16156103f8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103ef90610951565b60405180910390fd5b600060029054906101000a900460ff1615610448576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161043f9061080d565b60405180910390fd5b600060019054906101000a900460ff16610497576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161048e906108e5565b60405180910390fd5b6001600060036101000a81548160ff021916908315150217905550565b600060019054906101000a900460ff1615610504576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104fb906109bd565b60405180910390fd5b60008054906101000a900460ff16610551576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161054890610a29565b60405180910390fd5b6001600060016101000a81548160ff021916908315150217905550565b600082825260208201905092915050565b7f456e642077617320616c72656164792065786563757465642100000000000000600082015250565b60006105b560198361056e565b91506105c08261057f565b602082019050919050565b600060208201905081810360008301526105e4816105a8565b9050919050565b7f44206d757374206265206578656375746564206265666f726521000000000000600082015250565b6000610621601a8361056e565b915061062c826105eb565b602082019050919050565b6000602082019050818103600083015261065081610614565b9050919050565b7f442077617320616c726561647920657865637574656421000000000000000000600082015250565b600061068d60178361056e565b915061069882610657565b602082019050919050565b600060208201905081810360008301526106bc81610680565b9050919050565b7f4569746865722042206f722043206d757374206265206578656375746564206260008201527f65666f7265210000000000000000000000000000000000000000000000000000602082015250565b600061071f60268361056e565b915061072a826106c3565b604082019050919050565b6000602082019050818103600083015261074e81610712565b9050919050565b7f53746172742077617320616c7265616479206578656375746564210000000000600082015250565b600061078b601b8361056e565b915061079682610755565b602082019050919050565b600060208201905081810360008301526107ba8161077e565b9050919050565b7f422077617320616c726561647920657865637574656421000000000000000000600082015250565b60006107f760178361056e565b9150610802826107c1565b602082019050919050565b60006020820190508181036000830152610826816107ea565b9050919050565b7f432077617320616c72656164792065786563757465642e000000000000000000600082015250565b600061086360178361056e565b915061086e8261082d565b602082019050919050565b6000602082019050818103600083015261089281610856565b9050919050565b7f4120776173206e6f74206578656375746564207965742e000000000000000000600082015250565b60006108cf60178361056e565b91506108da82610899565b602082019050919050565b600060208201905081810360008301526108fe816108c2565b9050919050565b7f432077617320616c726561647920657865637574656421000000000000000000600082015250565b600061093b60178361056e565b915061094682610905565b602082019050919050565b6000602082019050818103600083015261096a8161092e565b9050919050565b7f412077617320616c726561647920657865637574656421000000000000000000600082015250565b60006109a760178361056e565b91506109b282610971565b602082019050919050565b600060208201905081810360008301526109d68161099a565b9050919050565b7f537461727420776173206e6f74206578656375746564207965742e0000000000600082015250565b6000610a13601b8361056e565b9150610a1e826109dd565b602082019050919050565b60006020820190508181036000830152610a4281610a06565b905091905056fea2646970667358221220499cfca46e799ae1baf4d80c2d7927020c52fc7d472a7e11bf65bbd921bf0fa764736f6c634300080e0033";

    public static final String FUNC_START = "Start";

    public static final String FUNC_A = "A";

    public static final String FUNC_B = "B";

    public static final String FUNC_C = "C";

    public static final String FUNC_D = "D";

    public static final String FUNC_END = "End";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected Miniksor(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Miniksor(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Miniksor(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Miniksor(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> Start() {
        final Function function = new Function(
                FUNC_START,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> A() {
        final Function function = new Function(
                FUNC_A,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> B() {
        final Function function = new Function(
                FUNC_B,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> C() {
        final Function function = new Function(
                FUNC_C,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> D() {
        final Function function = new Function(
                FUNC_D,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> End() {
        final Function function = new Function(
                FUNC_END,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Miniksor load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Miniksor(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Miniksor load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Miniksor(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Miniksor load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Miniksor(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Miniksor load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Miniksor(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Miniksor> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Miniksor.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Miniksor> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Miniksor.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Miniksor> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Miniksor.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Miniksor> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Miniksor.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
