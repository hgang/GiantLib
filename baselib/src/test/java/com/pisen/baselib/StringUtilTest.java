package com.pisen.baselib;

import com.pisen.baselib.utils.StringUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * ${tags}
 *
 * @author chenkun
 * @version 1.0, 16/2/22 下午12:40
 */

public class StringUtilTest {
    @Test
    public void testIsStrEmpty() throws Exception {
        // System.out.println("==a");
        String str = "";
        Assert.assertEquals(StringUtil.isEmpty(str), true);
        String str2 = null;
        Assert.assertEquals(StringUtil.isEmpty(str2), true);
        //含空格
        String str3 = "   ";
        Assert.assertEquals(StringUtil.isEmpty(str2), true);
    }


    @Test
    public void testToInt() throws Exception {
        String str = "10";
        Assert.assertEquals(StringUtil.toInt(str, -1), 10);

    }

    @Test
    public void testToLong() throws Exception {
        String str = "123";
        Assert.assertEquals(StringUtil.toLong(str), 123L);
    }

    @Test
    public void testToBool() throws Exception {
        String b = "true";
        Assert.assertEquals(StringUtil.toBool(b), true);
    }

    @Test
    public void testRemoveBlank() throws Exception {
        String str = " 1  2  3 ";
        Assert.assertEquals(StringUtil.removeBlank(str), "123");
    }

    @Test
    public void testGetIDGenerator() throws Exception {
        String IDGenerator1 = StringUtil.getIDGenerator(10);
        String IDGenerator2 = StringUtil.getIDGenerator(10);
        Assert.assertEquals(IDGenerator1, IDGenerator2);
    }
}