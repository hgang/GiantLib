package com.heg.baselib;
import android.content.Context;
import android.test.AndroidTestCase;

import com.heg.baselib.security.MD5Encryptor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: ldj
 * Date: 2016-02-17 14:30 
 */
public class MD5EncryptorTest extends AndroidTestCase{

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testMD532() {
        String s = "ABCD1234567890";
        String es = MD5Encryptor.encrypt32Byte(s);
        String expectedResult = "979c18140bbd38aab38290759bafcbcb";
        assertEquals(expectedResult, es);
    }

    public void testMD516() {
        String s = "ABCD1234567890";
        String es = MD5Encryptor.encrypt16Byte(s);
        String expectedResult = "0bbd38aab3829075";
        assertEquals(expectedResult,es);
    }

    private void createFile() {
        FileOutputStream outputStream = null;
        try {
            outputStream = getContext().openFileOutput("testFile", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            outputStream.write("121212121212".getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testMD5File() {
//        createFile();
//        FileInputStream fis = null;
//        try {
//            fis = getContext().openFileInput("testFile");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        String es = MD5Encryptor.encryptFile(fis);
//        String expectedResult = "1d1803570245aa620446518b2154f324";
//        assertEquals(expectedResult,es);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}