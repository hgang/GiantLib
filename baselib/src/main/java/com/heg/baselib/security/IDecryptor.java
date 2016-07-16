package com.heg.baselib.security;

/**
 * 解码接口
 * @author ldj，日期（2016-02-17）
 */
public interface IDecryptor {

    /**
     * 解密数据
     * @param data  需解密的数据
     * @return  解密后的数据
     */
    public byte[] decrypt(byte[] data);
}
