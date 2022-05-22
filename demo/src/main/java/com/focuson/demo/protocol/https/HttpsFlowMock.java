package com.focuson.demo.protocol.https;

/**
 * @author lijunyi
 * @date 2022/5/22 7:00 下午
 * @Description TODO
 */
public class HttpsFlowMock {
    /**
     * 签名的本质其实就是加密，但是由于签名无需还原成明文，因此可以在加密前进行哈希处理。所以签名其实就是哈希+加密，而验签就是哈希+解密+比较。
     *
     * 　　签名过程：对明文做哈希，拼接头信息，用私钥进行加密，得到签名。
     *
     * 　　验签过程：用公钥解密签名，然后去除头信息，对明文做哈希，比较2段哈希值是否相同，相同则验签成功
     * @param args
     */
    public static void main(String[] args) {
        //client request server 443 ,support hash algo,生成随机数R1

        //server response a cert(contain domain,publicKey),e.g zhihu.com /指纹:,被高级CA XXX所信任的,生成随机数r2

        //client verify cert,
        //由验证链向上追溯,发现这个证书有XXX做授权,XXX是操作系统内置的,使用操作系统的public key对cert的hash摘要去验签
        //验证zhihu.com的cert签名是由XXX授予的,信任cert


        //生成随机数r3,由r1,r2,r3生成一个AES key(只是例子,实际也可以是其他算法)拿到public key对 起加密发给server

        //server拿到后响应ok

        //客户端使用aes secret key加密报文发送给server,server拿到报文使用secret key去解密



    }
}
