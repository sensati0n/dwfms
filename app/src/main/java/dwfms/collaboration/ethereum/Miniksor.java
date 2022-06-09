package dwfms.collaboration.ethereum;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
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
    public static final String BINARY = "0x608060405260008060006101000a81548160ff02191690831515021790555060008060016101000a81548160ff02191690831515021790555060008060026101000a81548160ff02191690831515021790555060008060036101000a81548160ff02191690831515021790555060008060046101000a81548160ff02191690831515021790555060008060056101000a81548160ff0219169083151502179055503480156100ac57600080fd5b50610ee6806100bc6000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c806306e64907146100675780630f529ba2146100715780631b55ba3a1461007b57806332e7c5bf1461008557806383dfe5fd1461008f578063f446c1d014610099575b600080fd5b61006f6100a3565b005b610079610196565b005b6100836102a0565b005b61008d610341565b005b610097610484565b005b6100a16105c7565b005b600060059054906101000a900460ff16156100f3576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016100ea90610715565b60405180910390fd5b600060049054906101000a900460ff16610142576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161013990610781565b60405180910390fd5b6001600060056101000a81548160ff0219169083151502179055507f659ca8f177d3b05486dd56ac6602c20d4334d998c8b0fabb9e95dcc356232aa93360405161018c919061082e565b60405180910390a1565b600060049054906101000a900460ff16156101e6576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101dd906108a8565b60405180910390fd5b600060029054906101000a900460ff168061020d5750600060039054906101000a900460ff165b61024c576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102439061093a565b60405180910390fd5b6001600060046101000a81548160ff0219169083151502179055507f659ca8f177d3b05486dd56ac6602c20d4334d998c8b0fabb9e95dcc356232aa93360405161029691906109a6565b60405180910390a1565b60008054906101000a900460ff16156102ee576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102e590610a20565b60405180910390fd5b60016000806101000a81548160ff0219169083151502179055507f659ca8f177d3b05486dd56ac6602c20d4334d998c8b0fabb9e95dcc356232aa9336040516103379190610a8c565b60405180910390a1565b600060029054906101000a900460ff1615610391576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161038890610b06565b60405180910390fd5b600060039054906101000a900460ff16156103e1576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103d890610b72565b60405180910390fd5b600060019054906101000a900460ff16610430576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161042790610bde565b60405180910390fd5b6001600060026101000a81548160ff0219169083151502179055507f659ca8f177d3b05486dd56ac6602c20d4334d998c8b0fabb9e95dcc356232aa93360405161047a9190610c4a565b60405180910390a1565b600060039054906101000a900460ff16156104d4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104cb90610cc4565b60405180910390fd5b600060029054906101000a900460ff1615610524576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161051b90610b06565b60405180910390fd5b600060019054906101000a900460ff16610573576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161056a90610bde565b60405180910390fd5b6001600060036101000a81548160ff0219169083151502179055507f659ca8f177d3b05486dd56ac6602c20d4334d998c8b0fabb9e95dcc356232aa9336040516105bd9190610d30565b60405180910390a1565b600060019054906101000a900460ff1615610617576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161060e90610daa565b60405180910390fd5b60008054906101000a900460ff16610664576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161065b90610e16565b60405180910390fd5b6001600060016101000a81548160ff0219169083151502179055507f659ca8f177d3b05486dd56ac6602c20d4334d998c8b0fabb9e95dcc356232aa9336040516106ae9190610e82565b60405180910390a1565b600082825260208201905092915050565b7f456e642077617320616c72656164792065786563757465642100000000000000600082015250565b60006106ff6019836106b8565b915061070a826106c9565b602082019050919050565b6000602082019050818103600083015261072e816106f2565b9050919050565b7f44206d757374206265206578656375746564206265666f726521000000000000600082015250565b600061076b601a836106b8565b915061077682610735565b602082019050919050565b6000602082019050818103600083015261079a8161075e565b9050919050565b7f456e640000000000000000000000000000000000000000000000000000000000600082015250565b60006107d76003836106b8565b91506107e2826107a1565b602082019050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610818826107ed565b9050919050565b6108288161080d565b82525050565b60006040820190508181036000830152610847816107ca565b9050610856602083018461081f565b92915050565b7f442077617320616c726561647920657865637574656421000000000000000000600082015250565b60006108926017836106b8565b915061089d8261085c565b602082019050919050565b600060208201905081810360008301526108c181610885565b9050919050565b7f4569746865722042206f722043206d757374206265206578656375746564206260008201527f65666f7265210000000000000000000000000000000000000000000000000000602082015250565b60006109246026836106b8565b915061092f826108c8565b604082019050919050565b6000602082019050818103600083015261095381610917565b9050919050565b7f4400000000000000000000000000000000000000000000000000000000000000600082015250565b60006109906001836106b8565b915061099b8261095a565b602082019050919050565b600060408201905081810360008301526109bf81610983565b90506109ce602083018461081f565b92915050565b7f53746172742077617320616c7265616479206578656375746564210000000000600082015250565b6000610a0a601b836106b8565b9150610a15826109d4565b602082019050919050565b60006020820190508181036000830152610a39816109fd565b9050919050565b7f5374617274000000000000000000000000000000000000000000000000000000600082015250565b6000610a766005836106b8565b9150610a8182610a40565b602082019050919050565b60006040820190508181036000830152610aa581610a69565b9050610ab4602083018461081f565b92915050565b7f422077617320616c726561647920657865637574656421000000000000000000600082015250565b6000610af06017836106b8565b9150610afb82610aba565b602082019050919050565b60006020820190508181036000830152610b1f81610ae3565b9050919050565b7f432077617320616c72656164792065786563757465642e000000000000000000600082015250565b6000610b5c6017836106b8565b9150610b6782610b26565b602082019050919050565b60006020820190508181036000830152610b8b81610b4f565b9050919050565b7f4120776173206e6f74206578656375746564207965742e000000000000000000600082015250565b6000610bc86017836106b8565b9150610bd382610b92565b602082019050919050565b60006020820190508181036000830152610bf781610bbb565b9050919050565b7f4200000000000000000000000000000000000000000000000000000000000000600082015250565b6000610c346001836106b8565b9150610c3f82610bfe565b602082019050919050565b60006040820190508181036000830152610c6381610c27565b9050610c72602083018461081f565b92915050565b7f432077617320616c726561647920657865637574656421000000000000000000600082015250565b6000610cae6017836106b8565b9150610cb982610c78565b602082019050919050565b60006020820190508181036000830152610cdd81610ca1565b9050919050565b7f4300000000000000000000000000000000000000000000000000000000000000600082015250565b6000610d1a6001836106b8565b9150610d2582610ce4565b602082019050919050565b60006040820190508181036000830152610d4981610d0d565b9050610d58602083018461081f565b92915050565b7f412077617320616c726561647920657865637574656421000000000000000000600082015250565b6000610d946017836106b8565b9150610d9f82610d5e565b602082019050919050565b60006020820190508181036000830152610dc381610d87565b9050919050565b7f537461727420776173206e6f74206578656375746564207965742e0000000000600082015250565b6000610e00601b836106b8565b9150610e0b82610dca565b602082019050919050565b60006020820190508181036000830152610e2f81610df3565b9050919050565b7f4100000000000000000000000000000000000000000000000000000000000000600082015250565b6000610e6c6001836106b8565b9150610e7782610e36565b602082019050919050565b60006040820190508181036000830152610e9b81610e5f565b9050610eaa602083018461081f565b9291505056fea264697066735822122084fbc92fc65630b6c10f81e28734551f01ada3615da66b3ce142954543ac43a264736f6c634300080e0033";

    public static final String FUNC_START = "Start";

    public static final String FUNC_A = "A";

    public static final String FUNC_B = "B";

    public static final String FUNC_C = "C";

    public static final String FUNC_D = "D";

    public static final String FUNC_END = "End";

    public static final Event TASKFINISHED_EVENT = new Event("TaskFinished",
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

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

    public List<TaskFinishedEventResponse> getTaskFinishedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TASKFINISHED_EVENT, transactionReceipt);
        ArrayList<TaskFinishedEventResponse> responses = new ArrayList<TaskFinishedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TaskFinishedEventResponse typedResponse = new TaskFinishedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.task = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.humanResource = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TaskFinishedEventResponse> taskFinishedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TaskFinishedEventResponse>() {
            @Override
            public TaskFinishedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TASKFINISHED_EVENT, log);
                TaskFinishedEventResponse typedResponse = new TaskFinishedEventResponse();
                typedResponse.log = log;
                typedResponse.task = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.humanResource = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TaskFinishedEventResponse> taskFinishedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TASKFINISHED_EVENT));
        return taskFinishedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> Start() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_START,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> A() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_A,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> B() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_B,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> C() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_C,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> D() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_D,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> End() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
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

    public static class TaskFinishedEventResponse extends BaseEventResponse {
        public String task;

        public String humanResource;
    }
}
