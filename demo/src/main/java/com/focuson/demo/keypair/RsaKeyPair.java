package com.focuson.demo.keypair;

import java.security.NoSuchAlgorithmException;

/**
 * @author lijunyi
 * @date 2022/3/27 10:21 下午
 * @Description TODO
 */
public class RsaKeyPair {

    public static void main(String[] args) throws NoSuchAlgorithmException {
//        KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
//        KeyPair keyPair = rsa.generateKeyPair();
//        System.out.println(keyPair.getPrivate().);
//        System.out.println(keyPair.getPublic());
        int a = 10;
        //0000 1010
        //
        System.out.println(a & -a);
        System.out.println(a & (~a + 1));
    }
}
