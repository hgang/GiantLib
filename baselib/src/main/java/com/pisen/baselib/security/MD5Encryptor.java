package com.pisen.baselib.security;

import com.pisen.baselib.utils.LogCat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>MD5信息摘要算法，对源数据进行摘要处理，形成唯一的判断标识
 * 主要用于文件完整性校验和密码不可逆加密</p>
 * 支持获取文件md5值<br>
 * 支持对字符串或byte数组进行32、16位处理
 * @author ldj，日期（2016-02-16）
 * <p>ldj 2016-02-22: 规范格式</p>
 */
public class MD5Encryptor {

    //算法
    private static final String ALGORITHM = "MD5";
    //默认字符编码
    private static final String DEFAULT_CHARSET = "utf-8";

    /**
     * 对数据进行MD5加密，返回16字节的十六进制字符串
     *
     * @param data 需加密的数据
     * @return 加密后的数据，失败时返回null
     */
    public static String encrypt16Byte(byte[] data) {
        String md5StrBuff = bytes2HexStr(encrypt(data));
        // 16位加密，从第9位到25位
        return md5StrBuff.substring(8, 24).toString().toLowerCase();

    }

    /**
     * 对数据进行MD5加密，返回16字节的十六进制字符串，data字符串默认编码方式为utf-8
     *
     * @param data 需加密的字符串
     * @return 加密后的数据，失败时返回null
     */
    public static String encrypt16Byte(String data) {
        try {
            return encrypt16Byte(data.getBytes(DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            LogCat.e(e.getCause(), "MD5Encryptor Exception");
        }

        return null;
    }

    /**
     * 对数据进行MD5加密，返回16字节的十六进制字符串
     *
     * @param data 需加密的数据
     * @return 加密后的数据，失败时返回null
     */
    public static String encrypt32Byte(byte[] data) {
        byte[] hash = encrypt(data);
        if (hash != null) {
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10)  hex.append("0");
                hex.append(Integer.toHexString(b & 0xFF));
            }

            return hex.toString().toLowerCase();
        }
        return null;
    }

    /**
     * 对数据进行MD5加密，返回16字节的十六进制字符串，data字符串默认编码方式为utf-8
     *
     * @param data 需加密的字符串
     * @return 加密后的数据，失败时返回null
     */
    public static String encrypt32Byte(String data) {
        try {
            return encrypt32Byte(data.getBytes(DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            LogCat.e(e.getCause(), "MD5Encryptor Exception");
        }

        return null;
    }


    /**
     * 进行数据加密操作
     *
     * @param data 需加密的数据
     * @return 加密后的数据，失败时返回null
     */
    private static byte[] encrypt(byte[] data) {
        byte[] result = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(ALGORITHM);
            md.reset();
            md.update(data);
            result = md.digest();
        } catch (NoSuchAlgorithmException e) {
            LogCat.e(e.getCause(), "MD5Encryptor Exception");
        }
        return result;
    }

    /**
     * byte转换为16进制
     *
     * @param data  源数据
     * @return  转码后数据。失败时返回null
     */
    private static String bytes2HexStr(byte[] data) {
        if(data == null || data.length <1) return null;

        StringBuffer buf = new StringBuffer();
        int length = data.length;
        for (int i = 0; i < length; i++) {
            if (Integer.toHexString(0xFF & data[i]).length() == 1) {
                buf.append("0").append(Integer.toHexString(0xFF & data[i]));
            } else {
                buf.append(Integer.toHexString(0xFF & data[i]));
            }
        }
        return new String(buf);
    }

    /**
     * 进行文件MD5值计算
     *
     * @param file 源文件
     * @return 文件MD5值或null（文件不存在）
     */
    public static String getFileMD5(File file) {
        if (file == null || !file.exists()) {
            return null;
        }

        String result = null;
        FileInputStream fis = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            fis = new FileInputStream(file);
            int count;
            byte[] buffer = new byte[1024];
            while ((count = fis.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
            result = bytes2HexStr(digest.digest());

        } catch (Exception e) {
            LogCat.e(e.getCause(), "MD5Encryptor Exception");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LogCat.e(e.getCause(), "MD5Encryptor close file stream error");
                }
            }
        }
        return result;
    }
}
