package com.focuson.demo.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * @author lijunyi
 * @date 2022/5/21 11:04 下午
 * @Description TODO
 */
public class RsaUtil {

    public static final String RSA_ALGO_NAME = "RSA";

    public static final String RSA_SIGNATURE_ALGO_NAME = "SHA256WITHRSA";

    /**
     * Signature, MessageDigest, Cipher, Mac, KeyStore
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        System.out.println(Arrays.toString(Security.getProviders()));
        System.out.println(Security.getAlgorithms("messageDigest"));
        System.out.println(Security.getAlgorithms("keypairGenerator"));
        System.out.println(Security.getAlgorithms("Signature"));
        System.out.println("Cipher:" + Security.getAlgorithms("Cipher"));
        String[] rsaKeyPairBase64 = generateRSAKeyPairBase64(2048);
        //pub key
        String rsaPublicKey = rsaKeyPairBase64[0];
        System.out.println(rsaPublicKey);
        //pri key
        String rsaPrivateKey = rsaKeyPairBase64[1];
        System.out.println(rsaPrivateKey);

        //test encrypt/decrypt
        String waitTestingStr = "加密前,源字符串";
        System.out.println("pre encrypt:" + waitTestingStr);
        byte[] encryptByte = encrypt(rsaPublicKey.getBytes(), waitTestingStr.getBytes());
        System.out.println("encrypt content:");
        System.out.println(new String(encryptByte));
        byte[] decryptByte = decrypt(rsaPrivateKey.getBytes(), encryptByte);
        System.out.println("decrypt content:" + new String(decryptByte));

        //test signature and verify
        String signature = signWithBase64(rsaPrivateKey.getBytes(), waitTestingStr.getBytes());
        System.out.println("signature:"+signature);

        boolean verifyResult = verifyWithBase64Signature(rsaPublicKey.getBytes(), waitTestingStr, signature);
        System.out.println("verifyResult:"+verifyResult);
    }

    private static boolean verifyWithBase64Signature(byte[] publicKeyBase64, String waitTestingStr, String signature) throws Exception {
        return verify(Base64.decodeBase64(publicKeyBase64), waitTestingStr, Base64.decodeBase64(signature));
    }

    private static boolean verify(byte[] publicKey, String waitTestingStr, byte[] signature) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory instance = KeyFactory.getInstance(RSA_ALGO_NAME);
        PublicKey key = instance.generatePublic(x509EncodedKeySpec);
        return generalVerify(waitTestingStr, signature, key,RSA_SIGNATURE_ALGO_NAME);
    }

    private static boolean generalVerify(String waitTestingStr, byte[] signature, PublicKey key, String algoName) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signatureInstance = Signature.getInstance(algoName);
        signatureInstance.initVerify(key);
        signatureInstance.update(waitTestingStr.getBytes(StandardCharsets.UTF_8));
        return signatureInstance.verify(signature);
    }

    public static String signWithBase64(byte[] privateKeyBase64, byte[] content) throws Exception {
        return Base64.encodeBase64String(sign(privateKeyBase64, content));
    }

    private static byte[] sign(byte[] privateKeyBase64, byte[] content) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyBase64));
        KeyFactory instance = KeyFactory.getInstance(RSA_ALGO_NAME);
        PrivateKey privateKey = instance.generatePrivate(pkcs8EncodedKeySpec);
        return generalSign(content, privateKey, RSA_SIGNATURE_ALGO_NAME);
    }

    private static byte[] generalSign(byte[] content, PrivateKey privateKey, String algoName) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signatureInstance = Signature.getInstance(algoName);
        signatureInstance.initSign(privateKey);
        signatureInstance.update(content);
        return signatureInstance.sign();
    }

    private static byte[] decrypt(byte[] privateKeyBase64, byte[] encryptByte) throws Exception {

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyBase64));
        KeyFactory instance = KeyFactory.getInstance(RSA_ALGO_NAME);
        PrivateKey privateKey = instance.generatePrivate(pkcs8EncodedKeySpec);
        return generalDecrypt(encryptByte, RSA_ALGO_NAME, privateKey);
    }

    private static byte[] generalDecrypt(byte[] encryptByte, String algoName, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(algoName);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        cipher.update(encryptByte);
        return cipher.doFinal();
    }

    /**
     * generate rsa key pair
     *
     * @param keySize
     * @return
     * @throws Exception
     */
    public static KeyPair generateRSAKeyPair(int keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * generate rsa key pair with BASE64
     *
     * @param keySize
     * @return index0:public key,index 1: private key
     * @throws Exception
     */
    public static String[] generateRSAKeyPairBase64(int keySize) throws Exception {
        KeyPair keyPair = generateRSAKeyPair(keySize);
        return new String[]{Base64.encodeBase64String(keyPair.getPublic().getEncoded()), Base64.encodeBase64String(keyPair.getPrivate().getEncoded())};
    }

    /**
     * 非对称加密
     *
     * @param publicKeyBase64
     * @param content
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] publicKeyBase64, byte[] content) throws Exception {
        String algoName = "RSA";
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyBase64));
        KeyFactory instance = KeyFactory.getInstance(algoName);
        PublicKey publicKey = instance.generatePublic(x509EncodedKeySpec);
        return generalEncrypt(content, algoName, publicKey);
    }

    private static byte[] generalEncrypt(byte[] content, String algoName, PublicKey publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(algoName);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        cipher.update(content);
        return cipher.doFinal();
    }

}
