package dwfms.framework.error;

public class ReflectionException extends RuntimeException {

    public ReflectionException() {
        super("This exception is thrown, when a method is invoked by reflection, e.g. to call a smart contract function in the ethereum package.");
    }

}
