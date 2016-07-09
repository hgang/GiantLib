package com.pisen.baselib.security;

/**
 * 加密接口
 * @author ldj，日期（2016-02-16）
 */
public interface IEncryptor {

    /**
     * 对数据进行加密
     * @param data  需加密的数据
     * @return  加密后的数据
     */
    public byte[] encrypt(byte[] data);
}
