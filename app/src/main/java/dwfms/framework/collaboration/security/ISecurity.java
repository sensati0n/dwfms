package dwfms.framework.collaboration.security;

public interface ISecurity {

    public String sign(String text, String secret);
    public boolean verify(String text, String signature, String publicKey);

}
