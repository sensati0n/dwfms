package dwfms.framework.security;

import dwfms.framework.collaboration.security.Utils;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @Test
    public void testEncryptionAndDecryption() throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        KeyPair keyPair = Utils.keyGen(2048);

        String testString = "Was wollt ihr hoeren? Mexico!";
        byte[] encryptedTestString = Utils.encrypt(keyPair.getPublic(), testString);

        String decryptedEncryptedTestString = Utils.decrypt(keyPair.getPrivate(), encryptedTestString);
        assertEquals(decryptedEncryptedTestString, testString);

    }

    @Test
    public void signature() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        KeyPair keyPair = Utils.keyGen(2048);

        String testString = "Was wollt ihr hoeren? Mexico!";
        String signature = Utils.sign(testString, keyPair.getPrivate());
        System.out.println(signature);

        KeyPair wrongKeyPair = Utils.keyGen(2048);

        assertFalse(Utils.verify(testString, signature, wrongKeyPair.getPublic()));
        assertTrue(Utils.verify(testString, signature, keyPair.getPublic()));
    }

    @Test
    public void loadKeys() throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {

        KeyPair keyPair = Utils.keyGen(2048);

        String publicString = Utils.keyToString(keyPair.getPublic());
        String privateString = Utils.keyToString(keyPair.getPrivate());

        PublicKey publicKeyFromString = Utils.stringToPublicKey(publicString);
        PrivateKey privateKeyFromString = Utils.stringToPrivateKey(privateString);

        String testString = "Was wollt ihr hoeren? Mexico!";
        String signature = Utils.sign(testString, privateKeyFromString);

        assertTrue(Utils.verify(testString, signature, publicKeyFromString));

    }

}
