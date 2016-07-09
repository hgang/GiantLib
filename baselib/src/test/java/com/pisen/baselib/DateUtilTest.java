package com.pisen.baselib;

import android.test.AndroidTestCase;

import com.pisen.baselib.utils.DateUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;

/**
 *
 *
 * @author chenkun
 * @version 1.0, 16/2/18 下午2:14
 */

public class DateUtilTest extends AndroidTestCase {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetCurrentDate() throws Exception {
        // 获取当前时间
        Date date =  DateUtil.getCurrentDate();
        Assert.assertEquals(date,new Date());
    }

    @Test
    public void testGetCurrentDate1() throws Exception {
        String dateStr = DateUtil.getCurrentDate(DateUtil.DF_YYYY_MM_DD);
        Assert.assertEquals(dateStr,"2016-02-18");
    }

    @Test
    public void testDateToString() throws Exception {
        String dateStr = DateUtil.dateToString(new Date(), DateUtil.DF_YYYY_MM_DD);
        Assert.assertEquals(dateStr,"2016-02-18");
    }

    @Test
    public void testFormatDateTime1() throws Exception {
      // 1455779774534  对应时间 2016-02-18 15:16:14
        // 测试将毫秒值转换成指定格式的时间字符串
        String dateStr = DateUtil.formatDateTime(1455779774534l);
        Assert.assertEquals(dateStr,"2016-02-18 15:16:14");
    }

    @Test
    public void testFormatDateTime2() throws Exception {
        // 将毫秒值转换未指定格式时间字符串
        // 毫秒值 1455779774534l 对应的时间:2016-02-18 15:16:14
        String dateStr = DateUtil.formatDateTime(1455779774534l, DateUtil.DF_YYYY_MM_DD);
        Assert.assertEquals(dateStr,"2016-02-18");
    }

    @Test
    public void testGetWeekOfDate() throws Exception {
       Assert.assertEquals(DateUtil.getWeekOfDate(new Date()),"周四");
    }

    @Test
    public void testGetNowMonth() throws Exception {
        int currentM = DateUtil.getNowMonth();
        Assert.assertEquals(currentM,2);
    }

    @Test
    public void testGetNowDay() throws Exception {
        int currentM = DateUtil.getNowDay();
        Assert.assertEquals(currentM,18);
    }

    @Test
    public void testGetNowYear() throws Exception {
        int currentM = DateUtil.getNowYear();
        Assert.assertEquals(currentM,2016);
    }

    @Test
    public void testGetNowDaysOfMonth() throws Exception {
        //获取当前月 有多少天
        int nowdays = DateUtil.getNowDaysOfMonth();
        System.out.println(nowdays);
        //当前16年2月
        Assert.assertEquals(nowdays,29);
    }

    @Test
    public void testDaysOfMonth() throws Exception {
        //测试指定年的指定月有多少天
        Assert.assertEquals(DateUtil.daysOfMonth(2016, 2),29);
        Assert.assertEquals(DateUtil.daysOfMonth(2015, 2),28);
        Assert.assertEquals(DateUtil.daysOfMonth(2016, 1),31);
    }

    @Test
    public void testTimePassed() throws Exception {
        String result = DateUtil.timePassed("2015-01-18 14:45:00");
        Assert.assertEquals(result,"1年前");
    }

    @Test
    public void testCompareDate() throws Exception {
       // 比较 2015-01-18 14:45:00 和当前时间 先后
       int resultCode = DateUtil.compareDate(new Date(),
               DateUtil.formatDateTime("2015-01-18 14:45:00",
                       DateUtil.DF_YYYY_MM_DD_HH_MM_SS));
        Assert.assertEquals(resultCode,-1);
    }
}