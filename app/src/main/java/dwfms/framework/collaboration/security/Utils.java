package dwfms.framework.collaboration.security;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.*;

public class Utils {

    /**
     * Generates a new RSA key pair.
      * @param keySize usually 2048 or something
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair keyGen(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        return keyPair;
    }

    /**
     * Encrypts a plain text with a given public key into a byte array.
     * @param publicKey
     * @param plainText
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] encrypt(PublicKey publicKey, String plainText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainText.getBytes());
    }

    /**
     * decrypts a byte array of encrypted data into a plain text string with a given private key.
     * @param privateKey
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String decrypt(PrivateKey privateKey, byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher decriptCipher = Cipher.getInstance("RSA");
//        Cipher decriptCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        return  new String(decriptCipher.doFinal(data), Charset.forName("UTF-8"));

    }

    /**
     * Signs a plain text with a given private key.
     * @param plainText the text to be signed
     * @param privateKey the key  that is used to generate the signature
     * @return the signature as base64 encoded string
     * @throws Exception
     */
    public static String sign(String plainText, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes());

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    /**
     * verifies a signature
     * @param plainText the text that is signed
     * @param signature the corresponding signature to the plain text
     * @param publicKey the public key that corresponds to the private key that was used to sign the plain text
     * @return
     * @throws Exception
     */
    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes());

        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        return publicSignature.verify(signatureBytes);
    }

    public static String keyToString(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static PublicKey stringToPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
    }

    public static PrivateKey stringToPrivateKey(String secretKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyBytes = Base64.getDecoder().decode(secretKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(new PKCS8EncodedKeySpec(publicKeyBytes));
    }


}
