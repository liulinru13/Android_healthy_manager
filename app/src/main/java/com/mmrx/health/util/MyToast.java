package com.mmrx.health.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * Toast包装类
 * @author Administrator
 *
 */
public class MyToast
{  
  
    private MyToast()
    {  
        /* cannot be instantiated */  
        throw new UnsupportedOperationException("cannot be instantiated");  
    }  
  
    public static boolean isShow = true;  
  
    /** 
     * shortTime Toast
     *  
     * @param context 
     * @param message 
     */  
    public static void showShort(Context context, CharSequence message)  
    {  
        if (isShow)  
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();  
    }  
  
    /** 
     * shortTime Toast
     *  
     * @param context 
     * @param message 
     */  
    public static void showShort(Context context, int message)  
    {  
        if (isShow)  
            Toast.makeText(context, message+"", Toast.LENGTH_SHORT).show();  
    }  
  
    /** 
     * longTime Toast
     *  
     * @param context 
     * @param message 
     */  
    public static void showLong(Context context, CharSequence message)  
    {  
        if (isShow)  
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();  
    }  
  
    /** 
     *longTime Toast
     *  
     * @param context 
     * @param message 
     */  
    public static void showLong(Context context, int message)  
    {  
        if (isShow)  
            Toast.makeText(context, message+"", Toast.LENGTH_LONG).show();  
    }  
  
    /** 
     * 自定义显示时间
     *  
     * @param context 
     * @param message 
     * @param duration 
     */  
    public static void show(Context context, CharSequence message, int duration)  
    {  
        if (isShow)  
            Toast.makeText(context, message, duration).show();  
    }  
  
    /** 
     * 自定义显示时间
     *  
     * @param context 
     * @param message 
     * @param duration 
     */  
    public static void show(Context context, int message, int duration)  
    {  
        if (isShow)  
            Toast.makeText(context, message+"", duration).show();  
    }  
  
}  