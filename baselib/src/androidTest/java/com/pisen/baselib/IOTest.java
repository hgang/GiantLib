package com.pisen.baselib;

import android.os.Environment;
import android.test.AndroidTestCase;

import com.pisen.baselib.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by tanyixiu
 *         2016/2/22 15:08
 */
public class IOTest extends AndroidTestCase {

    private static final String FILE_NAME = Environment.getExternalStorageDirectory().getPath() + File.separator + "IOTest.txt";
    private static final String FILE_NAME_COPY = Environment.getExternalStorageDirectory().getPath() + File.separator + "IOTestcopy.txt";

    private void createFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFileCopy() {
        File file = new File(FILE_NAME_COPY);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testCloseURLConnection() throws MalformedURLException {
        URL url = new URL("http://www.baidu.com");
        final String[] test = {null};
        URLConnection connection = new HttpURLConnection(url) {
            @Override
            public void disconnect() {
                test[0] = "disconnect";
            }

            @Override
            public boolean usingProxy() {
                return false;
            }

            @Override
            public void connect() throws IOException {

            }
        };
        IOUtils.close(connection);
        assertEquals(test[0], "disconnect");
    }

    public void testCloseQuietly() {
        final String[] test = {null};
        Reader reader = new Reader() {
            @Override
            public void close() throws IOException {
                test[0] = "close";
            }

            @Override
            public int read(char[] buffer, int offset, int count) throws IOException {
                return 0;
            }
        };
        IOUtils.closeQuietly(reader);
        assertEquals(test[0], "close");
    }

    public void testToBufferedReader() {
        Reader reader = new Reader() {
            @Override
            public void close() throws IOException {

            }

            @Override
            public int read(char[] buffer, int offset, int count) throws IOException {
                return 0;
            }
        };

        Reader newRader = IOUtils.toBufferedReader(reader);
        assertEquals(true, newRader instanceof BufferedReader);
    }

    public void testToByteArray() throws IOException {
        createFile();
        final String data = "hello,testToByteArray";
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
        IOUtils.write(data.getBytes(), fileOutputStream);
        FileInputStream fileInputStream = new FileInputStream(FILE_NAME);

        byte[] result = IOUtils.toByteArray(fileInputStream);
        assertNotNull(result);
        IOUtils.closeQuietly(fileInputStream);
        IOUtils.closeQuietly(fileOutputStream);
    }

    public void testToCharArray() throws IOException {
        createFile();
        final String data = "hello,testToCharArray";
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
        IOUtils.write(data.getBytes(), fileOutputStream);
        FileInputStream fileInputStream = new FileInputStream(FILE_NAME);

        char[] result = IOUtils.toCharArray(fileInputStream);
        assertNotNull(result);
        IOUtils.closeQuietly(fileInputStream);
        IOUtils.closeQuietly(fileOutputStream);
    }

    public void testToString() throws IOException {
        createFile();
        final String data = "hello,testToString";
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
        IOUtils.write(data.getBytes(), fileOutputStream);
        FileInputStream fileInputStream = new FileInputStream(FILE_NAME);

        String result = IOUtils.toString(fileInputStream);
        assertEquals(true, data.equals(result));

        IOUtils.closeQuietly(fileInputStream);
        IOUtils.closeQuietly(fileOutputStream);
    }

    public void testReadLines() throws IOException {
        createFile();
        final List<String> list = new ArrayList<>();
        list.add("A,hello");
        list.add("B,testReadLines");
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
        IOUtils.writeLines(list, null, fileOutputStream);

        FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
        final List<String> readlist = IOUtils.readLines(fileInputStream);

        IOUtils.closeQuietly(fileOutputStream);
        IOUtils.closeQuietly(fileInputStream);

        assertEquals(list.size(), readlist.size());
    }

    public void testToInputStream() {
        final String string = "testToInputStream";
        InputStream inputStream = IOUtils.toInputStream(string);
        assertNotNull(inputStream);
    }

    public void testWrite() throws IOException {
        createFile();
        final String data = "hello,testWrite";
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
        IOUtils.write(data.getBytes(), fileOutputStream);
        byte[] readData = new byte[data.length()];
        FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
        IOUtils.read(fileInputStream, readData);
        assertEquals(true, data.equals(new String(readData)));
        IOUtils.closeQuietly(fileInputStream);
        IOUtils.closeQuietly(fileOutputStream);
    }

    public void testWriteLines() throws IOException {
        createFile();
        final List<String> list = new ArrayList<>();
        list.add("A,hello");
        list.add("B,testWriteLines");
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
        IOUtils.writeLines(list, null, fileOutputStream);

        FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
        final List<String> readlist = IOUtils.readLines(fileInputStream);

        IOUtils.closeQuietly(fileOutputStream);
        IOUtils.closeQuietly(fileInputStream);

        assertEquals(list.size(), readlist.size());
    }

    public void testCopy() throws IOException {
        createFile();
        final String data = "hello,testCopy";
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
        IOUtils.write(data.getBytes(), fileOutputStream);
        FileInputStream fileInputStream = new FileInputStream(FILE_NAME);

        createFileCopy();
        FileOutputStream fileOutputStreamOther = new FileOutputStream(FILE_NAME_COPY);
        IOUtils.copy(fileInputStream, fileOutputStreamOther);

        FileInputStream fileInputStreamOther = new FileInputStream(FILE_NAME_COPY);
        byte[] readData = new byte[data.length()];
        IOUtils.read(fileInputStreamOther, readData);

        assertEquals(true, data.equals(new String(readData)));
        IOUtils.closeQuietly(fileInputStream);
        IOUtils.closeQuietly(fileOutputStream);
        IOUtils.closeQuietly(fileInputStreamOther);
        IOUtils.closeQuietly(fileOutputStreamOther);

    }

    public void testContentEquals() throws IOException {
        createFile();
        final String data = "hello,testContentEquals";
        IOUtils.write(data.getBytes(), new FileOutputStream(FILE_NAME));
        byte[] readData = new byte[data.length()];
        FileInputStream fileInputStreamA = new FileInputStream(FILE_NAME);
        FileInputStream fileInputStreamB = new FileInputStream(FILE_NAME);
        IOUtils.read(fileInputStreamA, readData);
        IOUtils.read(fileInputStreamB, readData);
        assertEquals(true, IOUtils.contentEquals(fileInputStreamA, fileInputStreamB));
        IOUtils.closeQuietly(fileInputStreamA);
        IOUtils.closeQuietly(fileInputStreamB);
    }

    public void testRead() throws IOException {
        createFile();
        final String data = "hello,testRead";
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
        IOUtils.write(data.getBytes(), fileOutputStream);
        byte[] readData = new byte[data.length()];
        FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
        IOUtils.read(fileInputStream, readData);
        assertEquals(true, data.equals(new String(readData)));
        IOUtils.closeQuietly(fileInputStream);
        IOUtils.closeQuietly(fileOutputStream);
    }
}
