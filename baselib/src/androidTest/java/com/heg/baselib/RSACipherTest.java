package com.heg.baselib;

import android.test.AndroidTestCase;
import android.util.Log;

import com.heg.baselib.security.RSACipher;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Author: ldj
 * Date: 2016-02-18 09:48
 */
public class RSACipherTest extends AndroidTestCase {
    public void testRSA() {
        String data = "123456789adcd";
        KeyPair pair = RSACipher.generateRSAKeyPair();
        try {
            String enStr = RSACipher.encrypt(data, pair.getPublic());
            Log.e("test", enStr);
            String deStr = RSACipher.decrypt(enStr, pair.getPrivate());
            assertEquals(data, deStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testRSA2() {
        String data = "123456789adcd";
        KeyPair pair = RSACipher.generateRSAKeyPair();
        try {
            String enStr = RSACipher.encrypt(data, pair.getPublic().getEncoded());
            Log.e("test", enStr);
            String deStr = RSACipher.decrypt(enStr, pair.getPrivate());
            assertEquals(data, deStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testPublicKeyGenerate() {
        KeyPair pair = RSACipher.generateRSAKeyPair();
        BigInteger pm = ((RSAPublicKey) pair.getPublic()).getModulus();
        int radix = 10;
        PublicKey pk = RSACipher.getPublicKey(pm.toString(radix), ((RSAPublicKey) pair.getPublic()).getPublicExponent().toString(), radix);
        assertEquals(pk, pair.getPublic());
    }

    public void testPrivateKeyGenerate() {
       /* KeyPair pair = RSACipher.generateRSAKeyPair();
        BigInteger pm = ((RSAPrivateKey) pair.getPrivate()).getModulus();
        int radix = 20;
        PrivateKey pk = null;
        pk = RSACipher.getPrivateKey(pm.toString(radix), ((RSAPrivateKey) pair.getPrivate()).getPrivateExponent().toString(), radix);
        assertEquals(pk, pair.getPrivate());*/
    }

}
