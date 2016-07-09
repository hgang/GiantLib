package com.pisen.baselib;
import android.test.AndroidTestCase;

import com.pisen.baselib.security.DESCipher;

/**
 * Author: ldj
 * Date: 2016-02-17 14:30 
 */
public class DESCipherTest extends AndroidTestCase{

    //明文：a123456 DES加密后密文:KmfgTFHXsgE=
    public void testEncrypt() {
        String s = "a123456";
        String key ="Pi658098";
        byte[] iv= {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
        DESCipher.Transformation t = new DESCipher.Transformation("CBC","PKCS7Padding",iv);
        String es = DESCipher.encrpty(s, key,t);
        String expectedResult ="KmfgTFHXsgE=";// "979c18140bbd38aab38290759bafcbcb";
        assertEquals(expectedResult, es);
    }

    public void testDecrypt() {
        String s = "KmfgTFHXsgE=";
        String key ="Pi658098";
        byte[] iv= {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
        DESCipher.Transformation t = new DESCipher.Transformation("CBC","PKCS7Padding",iv);
        String es = DESCipher.decrpty(s, key, t);
        String expectedResult ="a123456";
        assertEquals(expectedResult,es);
    }
}