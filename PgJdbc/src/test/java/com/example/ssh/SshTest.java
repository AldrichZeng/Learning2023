package com.example.ssh;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;


import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2024/3/8 15:01
 */
public class SshTest {
    @Test
    public void test1() throws NoSuchAlgorithmException, IOException {
        // 1. 创建密钥对生成器，指定算法为RSA
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

        // 2. 初始化密钥对生成器，可以设置密钥长度
        keyGen.initialize(2048); // 一般常见的密钥长度有1024, 2048, 4096等

        // 3. 生成密钥对
        KeyPair keyPair = keyGen.generateKeyPair();

        // 4. 获取公钥和私钥
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 5. 打印密钥信息（可选）
        System.out.println("Public Key: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        System.out.println("Private Key: " + Base64.getEncoder().encodeToString(privateKey.getEncoded()));

        transferPrivateKey((RSAPrivateCrtKey)privateKey);
        transferPublicKey((RSAPublicKey)publicKey);
    }


    public static void transferPublicKey(RSAPublicKey rsapubkey) throws IOException, NoSuchAlgorithmException {
        byte[] n = rsapubkey.getModulus().toByteArray(); // Java is 2sC bigendian
        byte[] e = rsapubkey.getPublicExponent().toByteArray(); // and so is SSH
        byte[] tag = "ssh-rsa".getBytes(); // charset very rarely matters here
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(tag.length); dos.write(tag);
        dos.writeInt(e.length); dos.write(e);
        dos.writeInt(n.length); dos.write(n);
        byte[] encoded = os.toByteArray();
        // now hash that (you don't really need Apache)
        // assuming SHA256-base64 (see below)
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] result = digest.digest(encoded);
        String output = Base64.getEncoder().encodeToString(result);
        System.out.println(output);
    }
    public static void transferPrivateKey(RSAPrivateCrtKey pkey){

        byte[] alg = "ssh-rsa".getBytes(), none = "none".getBytes();
        byte[] nbyt = pkey.getModulus().toByteArray(), ebyt = pkey.getPublicExponent().toByteArray();
        int rand = new Random().nextInt();

        ByteBuffer pub = ByteBuffer.allocate(nbyt.length+50); // always enough, but not too much over
        for( byte[] x : new byte[][]{alg,ebyt,nbyt} )
        { pub.putInt(x.length); pub.put(x); }

        ByteBuffer prv = ByteBuffer.allocate(nbyt.length*4+50); // ditto
        prv.putInt(rand); prv.putInt(rand);
        for( byte[] x : new byte[][]{alg,nbyt,ebyt,pkey.getPrivateExponent().toByteArray(),
                pkey.getCrtCoefficient().toByteArray(),pkey.getPrimeP().toByteArray(),pkey.getPrimeQ().toByteArray()} )
        { prv.putInt(x.length); prv.put(x); }
        prv.putInt(0); // no comment
        for( int i = 0; prv.position()%8 != 0; ) prv.put((byte)++i); // 8 apparently default? IDK

        ByteBuffer all = ByteBuffer.allocate(100+pub.position()+prv.position()); // ditto
        all.put("openssh-key-v1".getBytes()); all.put((byte)0);
        all.putInt(none.length); all.put(none); // cipher
        all.putInt(none.length); all.put(none); // pbkdf
        all.putInt(0); all.putInt(1); // parms, count
        all.putInt(pub.position()); all.put(pub.array(),0,pub.position());
        all.putInt(prv.position()); all.put(prv.array(),0,prv.position());
        byte[] result = Arrays.copyOf(all.array(),  all.position());

        System.out.print ("-----BEGIN OPENSSH PRIVATE KEY-----\n"
                + Base64.getMimeEncoder(68,"\n".getBytes()).encodeToString(result)
                + "\n-----END OPENSSH PRIVATE KEY-----\n");
    }

    //public static String toOpenSSHFormat(PublicKey publicKey) {
    //
    //    StringBuilder sshKey = new StringBuilder();
    //
    //    SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(publicKey.getEncoded());
    //
    //    // 获取模数n和指数e
    //    RSAPublicKey key = (RSAPublicKey) publicKey;
    //    BigInteger modulus = key.getModulus();
    //    BigInteger publicExponent = key.getPublicExponent();
    //
    //    // 将模数和指数编码为OpenSSH格式使用的字节串
    //    byte[] nBytes = modulus.toByteArray();
    //    byte[] eBytes = publicExponent.toByteArray();
    //
    //    // OpenSSH格式前缀
    //    sshKey.append("ssh-rsa ");
    //
    //    // 对模数和指数进行Base64编码，并去掉可能多余的前导零字节
    //    sshKey.append(Base64.getEncoder().encodeToString(nBytes));
    //    sshKey.append(' ');
    //    sshKey.append(Base64.getEncoder().encodeToString(eBytes));
    //
    //    return sshKey.toString();
    //}

    //public static String privateKeyToOpenSSH(RSAPrivateCrtKey rsaPrivateKey) throws Exception {
    //    // 获取RSA私钥的所有参数
    //    BigInteger modulus = rsaPrivateKey.getModulus();
    //    BigInteger publicExponent = rsaPrivateKey.getPublicExponent();
    //    BigInteger privateExponent = rsaPrivateKey.getPrivateExponent();
    //    BigInteger primeP = rsaPrivateKey.getPrimeP();
    //    BigInteger primeQ = rsaPrivateKey.getPrimeQ();
    //    BigInteger primeExponentP = rsaPrivateKey.getPrimeExponentP();
    //    BigInteger primeExponentQ = rsaPrivateKey.getPrimeExponentQ();
    //    BigInteger crtCoefficient = rsaPrivateKey.getCrtCoefficient();
    //
    //    // 构建ASN.1序列
    //    ASN1EncodableVector vec = new ASN1EncodableVector();
    //    vec.add(new ASN1ObjectIdentifier("1.2.840.113549.1.1.1")); // RSA OID
    //    vec.add(new DERSequence(new ASN1EncodableVector(
    //            new ASN1Integer(publicExponent),
    //            new ASN1Integer(modulus),
    //            new ASN1Integer(privateExponent),
    //            new ASN1Integer(primeP),
    //            new ASN1Integer(primeQ),
    //            new ASN1Integer(primeExponentP),
    //            new ASN1Integer(primeExponentQ),
    //            new ASN1Integer(crtCoefficient)
    //    )));
    //
    //    // 创建PrivateKeyInfo对象
    //    PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo(new DERSequence(vec));
    //
    //    // 将PrivateKeyInfo转换为PEM格式（OpenSSH私钥实质上是PEM编码的）
    //    StringWriter stringWriter = new StringWriter();
    //    PEMWriter pemWriter = new PEMWriter(new OutputStreamWriter(stringWriter));
    //    pemWriter.writeObject(privateKeyInfo);
    //    pemWriter.close();
    //
    //    return stringWriter.toString();
    //}

}
