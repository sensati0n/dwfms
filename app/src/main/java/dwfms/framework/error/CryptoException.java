package dwfms.framework.error;

import dwfms.framework.Action;
import dwfms.framework.IExecutionMachine;

public class CryptoException extends RuntimeException {

    public CryptoException() {
        super("Something happend in crypto module...");
    }

}
