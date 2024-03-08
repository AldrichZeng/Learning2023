package com.example.sshLearning;

import org.apache.commons.codec.binary.Base64;
import sun.security.rsa.RSACore;
import sun.security.rsa.RSAKeyFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 *  生成 rsa 密钥,通过 KeyPairGenerator 对象
 *
 *  尝试根据 密钥长度获取  每轮 加/解密 填充 允许的最大长度, 但是失败,先不尝试了
 */
public class RsaGenerate {


    /**
     * 解密 每轮的基准, 128 ,以 密钥 1024 位来算(与 私钥/公钥 解密无关)
     */
    private static int decodePaddingBase = 128;

    /**
     * 加密 每轮的基准, 117 ,以 密钥1024 来算(与 私钥/公钥 加密无关)
     */
    private static int encodePaddingBase = 117;


    /**
     * 密钥位数以 1024 位为基准
     */
    private static int baseSize = 1024;





    /**
     * KeyFactory 不行
     * KeyGenerator 不支持rsa
     * @param args
     * @throws NoSuchAlgorithmException
     */
    public static void main(String[] args) throws Exception {
        //  传入随机种子,生成 对称算法密钥对
        int keySize = 1024;
        String seedStr = "测试seed ajibahuihu";
        String testStr = "这是一条测试数据,请注意 https://blog.csdn.net/kzcming";

        process(keySize, seedStr, testStr);
    }


    /**
     * 根据密钥的长度, 每轮 加/解密 填充 允许的最大长度,也不相同
     * @param key
     * @throws InvalidKeyException
     */
    private static void makeSize(Key key) throws InvalidKeyException {
        RSAKey var6 = RSAKeyFactory.toRSAKey(key);
        int byteLength = RSACore.getByteLength(var6.getModulus());
        if(key instanceof PrivateKey) encodePaddingBase = byteLength;
        if(key instanceof PublicKey) decodePaddingBase = byteLength;

        //        if(key instanceof PrivateKey)
        //            init("1", key, JceSecurity.RANDOM,null);
    }






    private static void process(int keySize, String seedStr, String testStr) throws Exception {
        KeyPair keyPair = getKeyPair(seedStr,keySize);
        // 获得私钥,公钥
        PrivateKey aPrivate = keyPair.getPrivate();
        PublicKey aPublic = keyPair.getPublic();
        byte[] privateEncoded = aPrivate.getEncoded();
        byte[] publicEncoded = aPublic.getEncoded();
        // Base64 编码
        String privateKeyStr = Base64.encodeBase64String(privateEncoded);
        String publicKeyStr = Base64.encodeBase64String(publicEncoded);
        System.out.println("private:" + privateKeyStr);
        System.out.println();
        System.out.println("public:" + publicKeyStr);


        // 执行测试
        //        int divide = keySize/baseSize;
        //        if( divide != 1 ) {
        if(keySize == 2048) {
            //            makeSize(aPrivate);
            //            makeSize(aPublic);
            encodePaddingBase = encodePaddingBase * 2;
            decodePaddingBase = decodePaddingBase * 2;
        }

        // 公钥加密,私钥解密, 当然也可以反着,但是大家一般都这么弄
        byte[] bytes = encryptByPublicKey(testStr.getBytes(), publicKeyStr);
        byte[] bytes1 = decryptByPrivateKey(bytes, privateKeyStr);
        System.out.println(new String(bytes1));
    }

    /**
     * 获取对称加密密钥
     *
     * KeyPairGenerator 是生成对称加密密钥的 封装对象
     * @param seedStr   随机种子字符串
     * @param keySize   密钥长度, 必须是128 的倍数
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static KeyPair getKeyPair(String seedStr,int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(keySize,new SecureRandom(seedStr.getBytes()));
        return gen.generateKeyPair();
    }


    /**
     * 私钥解密
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        int i = 0;
        while (inputLen - offSet > 0) {
            byte[] cache;

            if (inputLen - offSet > decodePaddingBase) {
                cache = cipher.doFinal(data, offSet, decodePaddingBase);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * decodePaddingBase;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }


    /**
     * 公钥加密
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        int i = 0;
        while (inputLen - offSet > 0) {
            byte[] cache;
            if (inputLen - offSet > encodePaddingBase) {
                cache = cipher.doFinal(data, offSet, encodePaddingBase);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * encodePaddingBase;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }


}