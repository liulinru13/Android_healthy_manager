package com.mmrx.health.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
 
/** 
 * �����Ļ��صĸ����� 
 *  
 *  
 *  
 */ 
public class ScreenUtils {
    private ScreenUtils() {
        /** cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * �����Ļ�߶�
     *
     * @param
     * @return
     */
    public static int getScreenWidth() {
        //   WindowManager wm = (WindowManager) context
        //           .getSystemService(Context.WINDOW_SERVICE);
        //   DisplayMetrics outMetrics = new DisplayMetrics();
        //   wm.getDefaultDisplay().getMetrics(outMetrics);
        DisplayMetrics displayMetrics = Resources.getSystem()
                .getDisplayMetrics();
        //return outMetrics.widthPixels;
        return displayMetrics.widthPixels;
    }

    /**
     * �����Ļ���
     *
     * @param
     * @return
     */
    public static int getScreenHeight() {
        //    WindowManager wm = (WindowManager) context
        //            .getSystemService(Context.WINDOW_SERVICE);
        //    DisplayMetrics outMetrics = new DisplayMetrics();
        //    wm.getDefaultDisplay().getMetrics(outMetrics);
        DisplayMetrics displayMetrics = Resources.getSystem()
                .getDisplayMetrics();
        return displayMetrics.heightPixels;
        //   return outMetrics.heightPixels;
    }

    /**
     * ���״̬���ĸ߶�
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * ��ȡ��ǰ��Ļ��ͼ������״̬��
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * ��ȡ��ǰ��Ļ��ͼ��������״̬��
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }
}
 