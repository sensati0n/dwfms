package dwfms.framework.security;

import dwfms.collaboration.example.security.RSASecurity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    private static final Logger logger = LogManager.getLogger(UtilsTest.class);

    @Test
    public void testEncryptionAndDecryption() throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        KeyPair keyPair = RSASecurity.keyGen(2048);

        String testString = "Was wollt ihr hoeren? Mexico!";
        byte[] encryptedTestString = RSASecurity.encrypt(keyPair.getPublic(), testString);

        String decryptedEncryptedTestString = RSASecurity.decrypt(keyPair.getPrivate(), encryptedTestString);
        assertEquals(decryptedEncryptedTestString, testString);

    }

    @Test
    public void signature() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        KeyPair keyPair = RSASecurity.keyGen(2048);

        String testString = "Was wollt ihr hoeren? Mexico!";
        String signature = RSASecurity.sign(testString, keyPair.getPrivate());
        logger.trace(signature);

        KeyPair wrongKeyPair = RSASecurity.keyGen(2048);

        assertFalse(RSASecurity.verify(testString, signature, wrongKeyPair.getPublic()));
        assertTrue(RSASecurity.verify(testString, signature, keyPair.getPublic()));
    }

    @Test
    public void loadKeys() throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {

        KeyPair keyPair = RSASecurity.keyGen(512);

        String publicString = RSASecurity.keyToString(keyPair.getPublic());
        String privateString = RSASecurity.keyToString(keyPair.getPrivate());

        PublicKey publicKeyFromString = RSASecurity.stringToPublicKey(publicString);
        PrivateKey privateKeyFromString = RSASecurity.stringToPrivateKey(privateString);

        String testString = "Was wollt ihr hoeren? Mexico!";
        String signature = RSASecurity.sign(testString, privateKeyFromString);

        assertTrue(RSASecurity.verify(testString, signature, publicKeyFromString));

    }

}
