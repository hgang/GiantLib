package com.heg.baselib.security;

import android.util.Base64;

/**
 * <p>Base64辅助类，对加密、解密后的数据进行base64编解码</p>
 * @author ldj，日期（2016-02-16）
 */
public class Base64Util{
    //Base64编码默认flag
    private static final int DEFAULT_FLAG = Base64.DEFAULT;

    /**
     * 对源数据加密后，进行base64编码，采用默认的base64编码flag
     * @param data  源数据
     * @param encrypt   加密器
     * @return  解密后的base64编码结果
     */
    public static String encode(byte[] data, IEncryptor encrypt) {

        return encode(data, encrypt, DEFAULT_FLAG);
    }

    /**
     * 对源数据加密后，进行base64编码
     * @param data  源数据
     * @param encrypt   加密器
     * @param flags base64编码flags
     * @return  解密后的base64编码结果
     */
    public static String encode(byte[] data, IEncryptor encrypt, int flags) {
        if(encrypt != null) {
            data = encrypt.encrypt(data);
        }

        return Base64.encodeToString(data, flags);
    }

    /**
     * 对数据进行解码，首先进行base64解码,采用默认的base64 flag
     * @param data  解密的数据
     * @param decrypt   解密器
     * @return  解密后数据
     */
    public static String decode(byte[] data, IDecryptor decrypt) {

        return decode(data, decrypt, DEFAULT_FLAG);
    }

    /**
     * 对数据进行解码，首先进行base64解码
     * @param data  解密的数据
     * @param decrypt   解密器
     * @param flags base64 flags
     * @return  解密后数据
     */
    public static String decode(byte[] data, IDecryptor decrypt, int flags) {
        if(decrypt != null) {
            data = decrypt.decrypt(Base64.decode(data, flags));
        }

        return new String(data);
    }
}
