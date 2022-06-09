package dwfms.collaboration.example.security;

import dwfms.framework.collaboration.security.ISecurity;

public class NoSecurity implements ISecurity {
    @Override
    public String sign(String text, String secret) {
        return "";
    }

    @Override
    public boolean verify(String text, String signature, String publicKey) {
        return true;
    }
}
