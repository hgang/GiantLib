package com.heg.baselib.security;

import android.util.Base64;

import com.heg.baselib.utils.LogCat;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * <p>RSA加解密</p>
 * 提供公钥、密钥生成接口<br>
 * 提供数据加密、解密接口<br>
 * 支持通过十六进制公、私密钥字符串还原公钥、密钥<br>
 * 支持通过byte数组还原公钥、密钥<br>
 * @author ldj，日期（2016-02-16）
 * <p>ldj 2016-02-22: 规范格式</p>
 */
public final class RSACipher {
    public static final String DEFAULT_CHARSET = "utf-8";
    private static String ALGORITHM = "RSA";
    private static final int DEFAULT_KEY_LENGTH = 1024;

    /**
     * 随机生成RSA密钥对(默认密钥长度为1024)
     *
     * @return 密钥对
     */
    public static KeyPair generateRSAKeyPair() {
        return generateRSAKeyPair(DEFAULT_KEY_LENGTH);
    }

    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength 密钥长度，范围：512～2048<br>
     *                  一般1024,值越大安全性越高，相应加解密时间耗时越长
     * @return 密钥对，异常时返回null
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            LogCat.e(e.getCause(), "RSACipher generateRSAKeyPair Exception");
        }
        return null;
    }

    /**
     * 用公钥加密 <br>
     * 每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data      需加密数据的byte数据
     * @param publicKey 公钥
     * @return 加密后的byte型数据，异常时返回null
     */
    public static byte[] encrypt(byte[] data, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 传入编码数据并返回编码结果
            return cipher.doFinal(data);
        } catch (Exception e) {
            LogCat.e(e.getCause(), "RSACipher Exception");
        }

        return null;
    }

    /**
     * 加密
     *
     * @param data      源数据
     * @param publicKey 公钥
     * @return 加密后数据，异常时返回null
     */
    public static String encrypt(String data, PublicKey publicKey) {
        try {
            byte[] da = data.getBytes(DEFAULT_CHARSET);
            return new String(encrypt(da, publicKey));
        } catch (UnsupportedEncodingException e) {
            LogCat.e(e.getCause(), "RSACipher encrypt Exception");
        }

        return null;
    }

    /**
     * RSA加密
     *
     * @param data      源数据
     * @param publicKey 公钥
     * @return 加密后数据，异常时返回null
     */
    public static String encrypt(String data, byte[] publicKey) {
        String result = null;
        try {
            X509EncodedKeySpec x509 = new X509EncodedKeySpec(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PublicKey pk = keyFactory.generatePublic(x509);

            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, pk);

            result = Base64.encodeToString(cipher.doFinal(data.getBytes()), Base64.DEFAULT);
        } catch (Exception e) {
            LogCat.e(e.getCause(), "RSACipher encrypt Exception");
        }
        return result;
    }

    /**
     * 用私钥解密
     *
     * @param encryptedData 经过encryptedData()加密返回的byte数据
     * @param privateKey    私钥
     * @return 解密后数据，异常时返回null
     */
    public static byte[] decrypt(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            LogCat.e(e.getCause(), "RSACipher decrypt Exception");
        }

        return null;
    }

    /**
     * 对数据进行进行解码
     *
     * @param encryptedData 被加密数据
     * @param privateKey    对应的私钥
     * @return 解密数据，异常时返回null
     */
    public static String decrypt(String encryptedData, PrivateKey privateKey) {
        try {
            return new String(decrypt(encryptedData.getBytes(DEFAULT_CHARSET), privateKey));
        } catch (UnsupportedEncodingException e) {
            LogCat.e(e.getCause(), "RSACipher decrypt Exception");
        }
        return null;
    }

    /**
     * 通过公钥byte[](publicKey.getEncoded())将公钥还原，适用于RSA算法
     *
     * @param keyBytes 编码后的公钥字节数组
     * @return 公钥，异常时返回null
     */
    public static PublicKey getPublicKey(byte[] keyBytes) {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        return generatePublicKey(keySpec);
    }

    /**
     * 还原公钥
     *
     * @param keySpec 公钥参数
     * @return 公钥，异常时返回null
     */
    private static PublicKey generatePublicKey(KeySpec keySpec) {
        try {
            return KeyFactory.getInstance(ALGORITHM).generatePublic(keySpec);
        } catch (Exception e) {
            LogCat.e(e.getCause(), "RSACipher Exception");
        }

        return null;
    }

    /**
     * 使用16进制N、e值还原公钥
     *
     * @param modulus        密钥系数
     * @param publicExponent 密钥指数
     * @param modulusRadix   基数
     * @return 公钥，异常时返回null
     */
    public static PublicKey getPublicKey(String modulus, String publicExponent, int modulusRadix) {
        BigInteger bigIntModulus = new BigInteger(modulus, modulusRadix);
        BigInteger bigIntPublicExponent = new BigInteger(publicExponent, modulusRadix);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPublicExponent);
        return generatePublicKey(keySpec);
    }

    /**
     * 通过私钥byte[]将私钥还原，适用于RSA算法
     *
     * @param keyBytes 编码后的密钥数据
     * @return 私钥，异常时返回null
     */
    public static PrivateKey getPrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        PrivateKey privateKey = generatePrivateKey(keySpec);
        return privateKey;
    }

    /**
     * 使用16进制，N、d值还原私钥
     *
     * @param modulus         密钥系数
     * @param privateExponent 密钥指数
     * @param modulusRadix    基数
     * @return 私钥或null
     */
   /* public static PrivateKey getPrivateKey(String modulus, String privateExponent, int modulusRadix) {
        BigInteger bigIntModulus = new BigInteger(modulus, modulusRadix);
        BigInteger bigIntPrivateExponent = new BigInteger(privateExponent, modulusRadix);
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(bigIntModulus, bigIntPrivateExponent);
        return generatePrivateKey(keySpec);
    }*/

    /**
     * 通过私钥keySpec生成私钥
     *
     * @param keySpec 密钥参数
     * @return 私钥，异常时返回null
     */
    private static PrivateKey generatePrivateKey(KeySpec keySpec) {
        try {
            return KeyFactory.getInstance(ALGORITHM).generatePrivate(keySpec);
        } catch (Exception e) {
            LogCat.e(e.getMessage());
        }

        return null;
    }
}
