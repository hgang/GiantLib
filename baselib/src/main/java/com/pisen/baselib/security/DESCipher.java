package com.pisen.baselib.security;

import android.text.TextUtils;
import android.util.Base64;

import com.pisen.baselib.utils.LogCat;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>DES算法加解密</p>
 * 提供数据加密、解密接口<br>
 * 提供默认IV向量、工作模式及填充方式<br>
 * 支持IV向量、工作模式、填充方式设置<br>
 * @author ldj，日期（2016-02-17）
 * <p>ldj 2016-02-22: 规范格式</p>
 */
public class DESCipher {

    private static final String ALGORITHM = "DES";

    public static class Transformation {
        //向量
        private byte[] iv;// = {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
        //算法模式
        private String mode;// = "CBC";
        //填充模式
        private String padding;// = "PKCS7Padding";
        public Transformation(String mode, String padding, byte[] iv) {
            this.mode = mode;
            this.padding = padding;
            this.iv = iv;
        }

        public byte[] getIv() {
            return iv;
        }

        public void setIv(byte[] iv) {
            this.iv = iv;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getPadding() {
            return padding;
        }

        public void setPadding(String padding) {
            this.padding = padding;
        }
        /**
         * 获取算法初始化变换式
         * @return  依照工作模式和填充模式生成的DES算法变换式
         */
        public String getTransformation() {
            return getTransformation(mode, padding);
        }

        /**
         * 获取算法初始化变换式
         * @param mode  工作模式
         * @param padding   填充模式
         * @return  获取变换式
         */
        public static String getTransformation(String mode, String padding) {
            return new StringBuffer().append(ALGORITHM).append("/").append(mode).append("/").append(padding).toString();
        }
    }

    /**
     * 利用秘钥对数据进行DES加密
     * @param data  源数据
     * @param encryptKey    秘钥
     * @param transformation    算法变换式，提供加密所需的初始数据
     * @return  密文,密文结果采用base64编码。异常时返回null
     */
    public static String encrpty(String data, String encryptKey, Transformation transformation) {
        if(TextUtils.isEmpty(data) || TextUtils.isEmpty(encryptKey) || transformation == null) {
            return null;
        }

        String ret = null;
        try {
            //返回实现指定转换的 Cipher 对象   “算法/模式/填充”
            Cipher cipher = Cipher.getInstance(transformation.getTransformation());
            //创建一个 DESKeySpec 对象，使用 8 个字节的key作为 DES 密钥的密钥内容。
            DESKeySpec desKeySpec = new DESKeySpec(encryptKey.getBytes("UTF-8"));
            //返回转换指定算法的秘密密钥的 SecretKeyFactory 对象。
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            //根据提供的密钥生成 SecretKey 对象。
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            //使用 iv 中的字节作为 IV 来构造一个 IvParameterSpec 对象。复制该缓冲区的内容来防止后续修改。
            IvParameterSpec ivPs = new IvParameterSpec(transformation.getIv());
            //用密钥和一组算法参数初始化此 Cipher;Cipher：加密、解密、密钥包装或密钥解包，具体取决于 opmode 的值。
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivPs);
            //加密同时解码成字符串返回
            ret = new String(Base64.encode(cipher.doFinal(data.getBytes("UTF-8")), Base64.NO_WRAP));
        } catch (Exception e) {
            LogCat.e(e.getCause(), "DESCipher Exception");
        }

        return ret;
    }

    /**
     * 利用秘钥对数据进行DES解密
     * @param data  密文（默认为base64编码后的密文）
     * @param decodeKey    秘钥
     * @param transformation    算法变换式，提供加密所需的初始数据
     * @return  明文,异常时返回null
     */
    public static String decrpty(String data, String decodeKey, Transformation transformation) {
        if(TextUtils.isEmpty(data) || TextUtils.isEmpty(decodeKey) || transformation == null) {
            return null;
        }

        String ret = null;
        try {
            //使用指定密钥构造IV
            IvParameterSpec iv = new IvParameterSpec(transformation.getIv());
            //根据给定的字节数组和指定算法构造一个密钥。
            SecretKeySpec skeySpec = new SecretKeySpec(decodeKey.getBytes(), ALGORITHM);
            //返回实现指定转换的 Cipher 对象
            Cipher cipher = Cipher.getInstance(transformation.getTransformation());
            //解密初始化
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            //解码返回
            byte[] byteMi = Base64.decode(data, Base64.NO_WRAP);
            byte decryptedData[] = cipher.doFinal(byteMi);
            ret = new String(decryptedData);
        } catch (Exception e) {
            LogCat.e(e.getCause(), "DESCipher Exception");
        }

        return ret;
    }
}
