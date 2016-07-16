package com.heg.baselib;
import android.test.AndroidTestCase;
import android.util.Log;

import com.heg.baselib.security.Base64Util;
import com.heg.baselib.security.IDecryptor;
import com.heg.baselib.security.IEncryptor;

import java.io.UnsupportedEncodingException;

/**
 * Author: ldj
 * Date: 2016-02-18 12:30
 */
public class Ba64UtilTest extends AndroidTestCase{

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testBase64() {
        IEncryptor en = new Cipher();
        IDecryptor de = new Cipher();

        byte[] b = {1,2,3,4};
        String enStr = Base64Util.encode(b, en);
        Log.e("Test", enStr);
        String deStr = null;
        try {
            deStr = Base64Util.decode(enStr.getBytes("utf-8"), de);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assertEquals(new String(b), deStr);
    }

    private static class Cipher implements IEncryptor, IDecryptor {

        @Override
        public byte[] decrypt(byte[] data) {
            return reverse(data);
        }

        @Override
        public byte[] encrypt(byte[] data) {
           return reverse(data);
        }

        private byte[] reverse(byte[] data) {
            byte[] result = null;
            if(data != null && data.length >0) {
                int length = data.length;
                result = new byte[length];
                for(int i=0; i<length ; i++) {
                    result[i] = data[length-1-i];
                }
            }
            return result;
        }
    }

}