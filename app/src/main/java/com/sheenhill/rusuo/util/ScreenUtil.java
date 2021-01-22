package com.sheenhill.rusuo.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 作者    没有感情的S686
 * 时间    2018/12/15 21:21
 * 文件    Beauty_12_13
 * 描述      宽（px）/密度=屏幕实际宽度（dp），高（px）/密度=屏幕实际高度（dp）
 *          获取屏幕宽高，减去margin,padding,从而可以获取组件宽高
 */
public class ScreenUtil {
    private ScreenUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    /**
     * 获得屏幕宽度
     * @param context activity or fragment
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();//描述关于显示器的一般信息，如大小、密度和字体缩放。
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }
    /**
     * 获得屏幕高度
     * @param context activity or fragment
     * @return 屏幕高度
     */
    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }
    /**
     * 获得屏幕密度
     * @param context activity or fragment
     * @return 屏幕密度
     */
    public static float getDensity(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }
}
