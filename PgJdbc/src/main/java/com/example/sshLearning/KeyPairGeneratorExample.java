package com.example.sshLearning;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @author 匠承
 * @Date: 2024/3/8 16:13
 */
public class KeyPairGeneratorExample {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 生成密钥对，并转换为OpenSSH的格式
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // Generate RSA key pair
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();

        // Get the private and public keys
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        // 此时，已经拿到了密钥对。但是这是Java对象，无法直接在OpenSSH上使用。

        // Convert the private key to PKCS#8 PEM format
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        String privateKeyPem = "-----BEGIN PRIVATE KEY-----\n" +
                Base64.getEncoder().encodeToString(pkcs8EncodedKeySpec.getEncoded()) +
                "\n-----END PRIVATE KEY-----\n";

        // Save the keys to files
        // 私钥
        Files.write(Paths.get("/Users/aldrichzeng/IdeaProjects/Learning2023/Learning2023/PgJdbc/private.key"), privateKeyPem.getBytes(StandardCharsets.UTF_8));
        // 公钥
        System.out.println(encodePublicKey(publicKey, "zengyao"));
    }

    /**
     * Encode PublicKey (DSA or RSA encoded) to authorized_keys like string<br/>
     * 来自：https://stackoverflow.com/questions/3706177/how-to-generate-ssh-compatible-id-rsa-pub-from-java
     *
     * @param publicKey DSA or RSA encoded
     * @param user      username for output authorized_keys like string
     * @return authorized_keys like string
     * @throws IOException
     */
    public static String encodePublicKey(PublicKey publicKey, String user)
            throws IOException {
        String publicKeyEncoded;
        if (publicKey.getAlgorithm().equals("RSA")) {
            RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
            ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(byteOs);
            dos.writeInt("ssh-rsa".getBytes().length);
            dos.write("ssh-rsa".getBytes());
            dos.writeInt(rsaPublicKey.getPublicExponent().toByteArray().length);
            dos.write(rsaPublicKey.getPublicExponent().toByteArray());
            dos.writeInt(rsaPublicKey.getModulus().toByteArray().length);
            dos.write(rsaPublicKey.getModulus().toByteArray());
            publicKeyEncoded = new String(org.apache.commons.codec.binary.Base64.encodeBase64(byteOs.toByteArray()));
            return "ssh-rsa " + publicKeyEncoded + " " + user;
        } else if (publicKey.getAlgorithm().equals("DSA")) {
            DSAPublicKey dsaPublicKey = (DSAPublicKey) publicKey;
            DSAParams dsaParams = dsaPublicKey.getParams();

            ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(byteOs);
            dos.writeInt("ssh-dss".getBytes().length);
            dos.write("ssh-dss".getBytes());
            dos.writeInt(dsaParams.getP().toByteArray().length);
            dos.write(dsaParams.getP().toByteArray());
            dos.writeInt(dsaParams.getQ().toByteArray().length);
            dos.write(dsaParams.getQ().toByteArray());
            dos.writeInt(dsaParams.getG().toByteArray().length);
            dos.write(dsaParams.getG().toByteArray());
            dos.writeInt(dsaPublicKey.getY().toByteArray().length);
            dos.write(dsaPublicKey.getY().toByteArray());
            publicKeyEncoded = new String(org.apache.commons.codec.binary.Base64.encodeBase64(byteOs.toByteArray()));
            return "ssh-dss " + publicKeyEncoded + " " + user;
        } else {
            throw new IllegalArgumentException(
                    "Unknown public key encoding: " + publicKey.getAlgorithm());
        }
    }
}
