package com.example.crimescene;

import android.util.Base64;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RsaAlgorithm {

    private static final String ALGORITHM = "RSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";


    // Creating new Public and Private Keys
    /*
    Map<String, Object> keyMap;
    keyMap = RsaAlgorithm.initKey();

    String publicKey = RsaAlgorithm.getPublicKey(keyMap);
    String privateKey = RsaAlgorithm.getPrivateKey(keyMap);
     */


    // Encryption
    /*
    // Input : (String) MESSAGE, (String) PUBLIC_KEY
    // Output : (Big Integer String) DATA

    byte[] rsaData = MESSAGE.getBytes();
    encodeData = RsaAlgorithm.encryptByPublicKey(rsaData, PUBLIC_KEY);
    String DATA = new BigInteger(1, encodeData).toString();
     */


    // Decryption
    /*
    // Input : (Big Integer String) ENCRYPTED_DATA, (String) PRIVATE_KEY
    // Output : (String) MESSAGE

    byte[] decodeData = RsaAlgorithm.decryptByPrivateKey(gotMessage.toByteArray(), PRIVATE_KEY);
    String data = new String(decodeData);
    return data;
     */


    public static byte[] decryptBASE64(String key) {
        return Base64.decode(key, Base64.DEFAULT);
    }

    public static String encryptBASE64(byte[] key) {
        return Base64.encodeToString(key, Base64.DEFAULT);
    }


    public static byte[] encryptByPublicKey(byte[] data, String PublicKey)
            throws Exception {

        byte[] keyBytes = decryptBASE64(PublicKey);

        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }


    public static byte[] decryptByPrivateKey(byte[] data, String PrivateKey)
            throws Exception {

        byte[] keyBytes = decryptBASE64(PrivateKey);

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }


    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);

        return encryptBASE64(key.getEncoded());
    }


    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);

        return encryptBASE64(key.getEncoded());
    }


    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(ALGORITHM);
        keyPairGen.initialize(2048);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

}