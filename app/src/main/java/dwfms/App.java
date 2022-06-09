/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package dwfms;

import dwfms.collaboration.ethereum.EthereumCollaborationConnector;
import dwfms.collaboration.example.security.RSASecurity;
import dwfms.collaboration.example.SimpleCollaboration;
import dwfms.collaboration.example.consensus.ThresholdConsensus;
import dwfms.collaboration.example.network.HttpNetwork;
import dwfms.framework.action.User;
import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.core.BaseModel;
import dwfms.framework.core.DWFMS;
import dwfms.framework.core.ITransformer;
import dwfms.framework.references.Instance;
import dwfms.model.BPMNToHybridExecutionMachineTransformer;
import dwfms.ui.HttpInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException, IOException {

        logger.trace("User: " + args[1]);
        logger.trace("Port: " + args[2]);

        DWFMS dWFMS = null;
        HttpInterface httpInterface;

        if(args[0].equals("simple")) {
            switch(args[1]) {
                case "hans":
                    dWFMS = setupSimpleDWFMS(ExampleDataFactory.hansSimple(), Integer.parseInt(args[2])+1);
                    break;
                case "peter":
                    dWFMS = setupSimpleDWFMS(ExampleDataFactory.peterSimple(), Integer.parseInt(args[2])+1);
                    break;
            }
        }
        else if(args[0].equals("eth")) {
            switch(args[1]) {
                case "hans":
                    dWFMS = setupEthereumDWFMS(ExampleDataFactory.hansEth());
                    break;
                case "peter":
                    dWFMS = setupEthereumDWFMS(ExampleDataFactory.peterEth());
                    break;
            }
        }

        httpInterface = new HttpInterface(dWFMS, Integer.parseInt(args[2]));

    }

    static DWFMS setupEthereumDWFMS(User user) throws MalformedURLException {

        // start ganache-cli with deterministic wallet mnemonic:
        // ganache-cli -l 60000000 -b 15 -d -m "shiver armed industry victory sight vague follow spray couple hat obscure yard"

        BaseModel model = ExampleDataFactory.exampleBPMNModel();
        ITransformer transformer = new BPMNToHybridExecutionMachineTransformer();
        BaseCollaboration collaboration = new EthereumCollaborationConnector(new URL("http://localhost:8545"), null, null, new RSASecurity());

        DWFMS dWFMS = DWFMS.builder()
                .model(model)
                .transformer(transformer)
                .collaboration(collaboration)
                .build();

        dWFMS.init(user);

        Instance reference = dWFMS.deployProcessModel();
        logger.debug("Contract Address: " + reference.getInstanceRef());

        return dWFMS;
    }


    private static DWFMS setupSimpleDWFMS(User user, int port) throws MalformedURLException {

        BaseModel model = ExampleDataFactory.exampleBPMNModel();
        ITransformer transformer = new BPMNToHybridExecutionMachineTransformer();
        BaseCollaboration collaboration = new SimpleCollaboration(new URL("http://localhost:" + port));

        DWFMS dWFMS = DWFMS.builder()
                .model(model)
                .transformer(transformer)
                .collaboration(collaboration)
                .build();

        dWFMS.init(user);

        return dWFMS;
    }



}
