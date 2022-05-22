package com.focuson.demo.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * @author lijunyi
 * @date 2022/5/21 11:04 下午
 * @Description TODO
 */
public class AESUtil {

    public static final String AES_ALGO_NAME = "AES";
    public static final String AES_CIPHER_ALGO = "AES/CBC/PKCS5Padding";
    private  static String sKey = "smkldospdosldaaa";//key，可自行修改

    private static IvParameterSpec iv = new IvParameterSpec("0392039203920300".getBytes());



    /**
     * Signature, MessageDigest, Cipher, Mac, KeyStore
     *"AES/ECB/PKCS5Padding"
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        System.out.println(Arrays.toString(Security.getProviders()));
        System.out.println(Security.getAlgorithms("messageDigest"));
        System.out.println("keypairGenerator" + Security.getAlgorithms("keypairGenerator"));
        System.out.println("keyGenerator" + Security.getAlgorithms("keyGenerator"));
        System.out.println(Security.getAlgorithms("Signature"));
        System.out.println("Cipher:" + Security.getAlgorithms("Cipher"));
        String aesSecretKeyBase64 = generateAESKeyBase64(128);
        System.out.println("secret key:" + aesSecretKeyBase64);
        //test encrypt/decrypt
        System.out.println(128 /8);

        String waitTestingStr = "abbbc哈哈哈哈哈哈哈等待加密";
        System.out.println("pre encrypt:" + waitTestingStr);
        System.out.println(waitTestingStr.getBytes(StandardCharsets.UTF_8).length);
        byte[] encryptByte = encrypt(sKey.getBytes(), waitTestingStr.getBytes(StandardCharsets.UTF_8));

        String encryptStrBase64 = Base64.encodeBase64String(encryptByte);
        System.out.println("encrypt content:");
        System.out.println(encryptByte.length);
        System.out.println(encryptStrBase64);
        byte[] decryptByte = decrypt(sKey.getBytes(), Base64.decodeBase64(encryptStrBase64));
        System.out.println("decrypt content:" + new String(decryptByte,StandardCharsets.UTF_8));
        System.out.println(decryptByte.length);

    }


    private static byte[] decrypt(byte[] secretKey, byte[] encryptByte) throws Exception {
//        byte[] secretKey = Base64.decodeBase64(secretKeyBase64);
        return generalDecrypt(encryptByte, AES_CIPHER_ALGO, new SecretKeySpec(secretKey,AES_ALGO_NAME));
    }

    private static byte[] generalDecrypt(byte[] encryptByte, String algoName, SecretKeySpec secretKeySpec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(algoName);

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec,iv);
//        cipher.update(encryptByte);
        return cipher.doFinal(encryptByte);
    }

    /**
     * generate rsa key pair
     *
     * @param keySize
     * @return
     * @throws Exception
     */
    public static String generateAESKeyBase64(int keySize) throws Exception {
        KeyGenerator aesGenerator = KeyGenerator.getInstance("AES");
        aesGenerator.init(keySize);
        return Base64.encodeBase64String(aesGenerator.generateKey().getEncoded());
    }


    /**
     * 非对称加密
     *
     * @param secretKeyBase64
     * @param content
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] secretKey, byte[] content) throws Exception {
        return generalEncrypt(content, AES_CIPHER_ALGO, new SecretKeySpec(secretKey,AES_ALGO_NAME));
    }

    private static byte[] generalEncrypt(byte[] content, String algoName, SecretKeySpec secretKeySpec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(algoName);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec,iv);
//        cipher.update(content);
        return cipher.doFinal(content);
    }

}
