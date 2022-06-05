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
    public static final String BINARY = "0x608060405260008060006101000a81548160ff02191690831515021790555060008060016101000a81548160ff02191690831515021790555060008060026101000a81548160ff02191690831515021790555060008060036101000a81548160ff02191690831515021790555034801561007857600080fd5b5061072b806100886000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80630f529ba21461005157806332e7c5bf1461005b57806383dfe5fd14610065578063f446c1d01461006f575b600080fd5b610059610079565b005b61006361014c565b005b61006d610256565b005b610077610360565b005b600060039054906101000a900460ff16156100c9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016100c090610427565b60405180910390fd5b600060019054906101000a900460ff16806100f05750600060029054906101000a900460ff165b61012f576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610126906104b9565b60405180910390fd5b6001600060036101000a81548160ff021916908315150217905550565b600060019054906101000a900460ff161561019c576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161019390610525565b60405180910390fd5b600060029054906101000a900460ff16156101ec576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101e390610591565b60405180910390fd5b60008054906101000a900460ff16610239576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610230906105fd565b60405180910390fd5b6001600060016101000a81548160ff021916908315150217905550565b600060029054906101000a900460ff16156102a6576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161029d90610669565b60405180910390fd5b600060019054906101000a900460ff16156102f6576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102ed90610525565b60405180910390fd5b60008054906101000a900460ff16610343576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161033a906105fd565b60405180910390fd5b6001600060026101000a81548160ff021916908315150217905550565b60008054906101000a900460ff16156103ae576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103a5906106d5565b60405180910390fd5b60016000806101000a81548160ff021916908315150217905550565b600082825260208201905092915050565b7f442077617320616c726561647920657865637574656421000000000000000000600082015250565b60006104116017836103ca565b915061041c826103db565b602082019050919050565b6000602082019050818103600083015261044081610404565b9050919050565b7f4569746865722042206f722043206d757374206265206578656375746564206260008201527f65666f7265210000000000000000000000000000000000000000000000000000602082015250565b60006104a36026836103ca565b91506104ae82610447565b604082019050919050565b600060208201905081810360008301526104d281610496565b9050919050565b7f422077617320616c726561647920657865637574656421000000000000000000600082015250565b600061050f6017836103ca565b915061051a826104d9565b602082019050919050565b6000602082019050818103600083015261053e81610502565b9050919050565b7f432077617320616c72656164792065786563757465642e000000000000000000600082015250565b600061057b6017836103ca565b915061058682610545565b602082019050919050565b600060208201905081810360008301526105aa8161056e565b9050919050565b7f4120776173206e6f74206578656375746564207965742e000000000000000000600082015250565b60006105e76017836103ca565b91506105f2826105b1565b602082019050919050565b60006020820190508181036000830152610616816105da565b9050919050565b7f432077617320616c726561647920657865637574656421000000000000000000600082015250565b60006106536017836103ca565b915061065e8261061d565b602082019050919050565b6000602082019050818103600083015261068281610646565b9050919050565b7f412077617320616c726561647920657865637574656421000000000000000000600082015250565b60006106bf6017836103ca565b91506106ca82610689565b602082019050919050565b600060208201905081810360008301526106ee816106b2565b905091905056fea26469706673582212209875cd2b02940a7432cd9c3c1973e2e11022c2df6086ed12471f0413f3b47cd864736f6c634300080e0033";

    public static final String FUNC_A = "A";

    public static final String FUNC_B = "B";

    public static final String FUNC_C = "C";

    public static final String FUNC_D = "D";

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
