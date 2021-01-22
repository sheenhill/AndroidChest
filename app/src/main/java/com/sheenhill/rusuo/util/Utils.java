package com.sheenhill.rusuo.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 验证手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1[34578]\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断是否为空
     *
     * @return
     */
    public static boolean isNull(Object object) {
        try {
            return null == object || "".equals(object.toString()) || "null".equals(object.toString())
                    || "[]".equals(object.toString());
        } catch (Exception e) {
        }
        return true;
    }

    /**
     * 判断是否为空
     *
     * @return false 为空,true 为非空
     */
    public static boolean judgeNull(Object obj) {
        if (isNull(obj)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 设置Textview 中划线
     */
    public static void setTextMiddleLine(View view) {
        if (view instanceof TextView) {
            TextPaint paint = ((TextView) view).getPaint();
            paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            paint.setAntiAlias(true);
        }
    }

    /**
     * 判断内容是否为空，防止空指针出现,
     * <p>
     * 返回String类型
     */
    public static String content(Object object) {
        if (!isNull(object)) {
            return object + "";
        }
        return "";
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context mContext) {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 关闭软键盘
     */
    public static void hideSoftKeyBoard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
