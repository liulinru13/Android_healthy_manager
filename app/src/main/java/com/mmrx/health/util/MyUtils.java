package com.mmrx.health.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by mmrx on 2015/5/7.
 */
public class MyUtils {

    public static String getSystemDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }
}
