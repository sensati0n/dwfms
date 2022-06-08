package dwfms.framework.error;

public class CryptoException extends RuntimeException {

    public CryptoException() {
        super("Something happend in crypto module...");
    }

}
