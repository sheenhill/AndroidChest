package com.example.roy.recycleviewtest.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.widget.Toast.makeText;


public class ToastUtils {
    private static boolean isShow = true;//默认显示
    private static Toast mToast = null;//全局唯一的Toast

    /*private控制不应该被实例化*/
    private ToastUtils() {
        throw new UnsupportedOperationException("不能被实例化");
    }

    /**
     * 全局控制是否显示Toast
     *
     * @param isShowToast 默认true
     */
    public static void controlShow(boolean isShowToast) {
        isShow = isShowToast;
    }

    /**
     * 取消Toast显示
     */
    public static void cancelToast() {
        if (isShow && mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context context
     * @param message 消息内容
     */
    @SuppressLint("ShowToast")
    public static void showShort(Context context, CharSequence message) {
        if (isShow) {
            if (mToast == null) {
                mToast = makeText(context, message, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(message);
            }
            mToast.show();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context  Context
     * @param resId   资源ID:getResources().getString(R.string.xxxxxx);
     */
    public static void showShort(Context context, int resId) {
        if (isShow) {
            if (mToast == null) {
                mToast = makeText(context, resId, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(resId);
            }
            mToast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context Context
     * @param message 消息内容
     */
    @SuppressLint("ShowToast")
    public static void showLong(Context context, CharSequence message) {
        if (isShow) {
            if (mToast == null) {
                mToast = makeText(context, message, Toast.LENGTH_LONG);
            } else {
                mToast.setText(message);
            }
            mToast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context Context
     * @param resId   资源ID:getResources().getString(R.string.xxxxxx);
     */
    @SuppressLint("ShowToast")
    public static void showLong(Context context, int resId) {
        if (isShow) {
            if (mToast == null) {
                mToast = makeText(context, resId, Toast.LENGTH_LONG);
            } else {
                mToast.setText(resId);
            }
            mToast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context Context
     * @param message 消息内容
     * @param duration 单位:毫秒
     */
    @SuppressLint("ShowToast")
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow) {
            if (mToast == null) {
                mToast = makeText(context, message, duration);
            } else {
                mToast.setText(message);
            }
            mToast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  Context
     * @param resId    资源ID:getResources().getString(R.string.xxxxxx);
     * @param duration 单位:毫秒
     */
    public static void show(Context context, int resId, int duration) {
        if (isShow) {
            if (mToast == null) {
                mToast = makeText(context, resId, duration);
            } else {
                mToast.setText(resId);
            }
            mToast.show();
        }
    }

    /**
     * 自定义Toast的View
     *
     * @param context  Context
     * @param message  消息内容
     * @param duration 单位:毫秒
     * @param view     显示自己的View
     */
    public static void customToastView(Context context, CharSequence message, int duration, View view) {
        if (isShow) {
            if (mToast == null) {
                mToast = makeText(context, message, duration);
            } else {
                mToast.setText(message);
            }
            if (view != null) {
                mToast.setView(view);
            }
            mToast.show();
        }
    }

    /**
     * 自定义Toast的位置
     *
     * @param context  Context
     * @param message  消息内容
     * @param duration 单位:毫秒
     * @param gravity  上下左右
     * @param xOffset  X方向偏移
     * @param yOffset  Y方向偏移
     */
    public static void customToastGravity(Context context, CharSequence message, int duration, int gravity, int xOffset, int yOffset) {
        if (isShow) {
            if (mToast == null) {
                mToast = makeText(context, message, duration);
            } else {
                mToast.setText(message);
            }
            mToast.setGravity(gravity, xOffset, yOffset);
            mToast.show();
        }
    }

    /**
     * 自定义带图片和文字的Toast，最终的效果就是上面是图片，下面是文字
     *
     * @param context  Context
     * @param message  消息内容
     * @param iconResId 图片的资源id,如:R.drawable.icon
     * @param duration 单位:毫秒
     * @param gravity  上下左右
     * @param xOffset  X方向偏移
     * @param yOffset  Y方向偏移
     */
    public static void showToastWithImageAndText(Context context, CharSequence message, int iconResId, int duration, int gravity, int xOffset, int yOffset) {
        if (isShow) {
            if (mToast == null) {
                mToast = makeText(context, message, duration);
            } else {
                mToast.setText(message);
            }
            mToast.setGravity(gravity, xOffset, yOffset);
            LinearLayout toastView = (LinearLayout) mToast.getView();
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(iconResId);
            toastView.addView(imageView, 0);
            mToast.show();
        }
    }

    /**
     * 自定义Toast,针对类型CharSequence
     *
     * @param context  Context
     * @param message  消息内容
     * @param duration 单位:毫秒
     * @param view     View 放image
     * @param isGravity        true,表示后面的三个布局参数生效,false,表示不生效
     * @param gravity  上下左右
     * @param xOffset  X方向偏移
     * @param yOffset  Y方向偏移
     * @param isMargin         true,表示后面的两个参数生效，false,表示不生效
     * @param horizontalMargin 横向外边距
     * @param verticalMargin   纵向外边距
     */
    public static void customToastAll(Context context, CharSequence message, int duration, View view, boolean isGravity, int gravity, int xOffset, int yOffset, boolean isMargin, float horizontalMargin, float verticalMargin) {
        if (isShow) {
            if (mToast == null) {
                mToast = makeText(context, message, duration);
            } else {
                mToast.setText(message);
            }
            if (view != null) {
                mToast.setView(view);
            }
            if (isMargin) {
                mToast.setMargin(horizontalMargin, verticalMargin);
            }
            if (isGravity) {
                mToast.setGravity(gravity, xOffset, yOffset);
            }
            mToast.show();
        }
    }

    /**
     * 自定义Toast,针对类型resId
     *
     * @param context          Context
     * @param resId            图片的资源id,如:R.drawable.icon
     * @param duration         单位:毫秒
     * @param view             :应该是一个布局，布局中包含了自己设置好的内容
     * @param isGravity        true,表示后面的三个布局参数生效,false,表示不生效
     * @param gravity           上下左右
     * @param xOffset           X方向偏移
     * @param yOffset           Y方向偏移
     * @param isMargin         true,表示后面的两个参数生效，false,表示不生效
     * @param horizontalMargin 横向外边距
     * @param verticalMargin   纵向外边距
     */
    public static void customToastAll(Context context, int resId, int duration, View view, boolean isGravity, int gravity, int xOffset, int yOffset, boolean isMargin, float horizontalMargin, float verticalMargin) {
        if (isShow) {
            if (mToast == null) {
                mToast = makeText(context, resId, duration);
            } else {
                mToast.setText(resId);
            }
            if (view != null) {
                mToast.setView(view);
            }
            if (isMargin) {
                mToast.setMargin(horizontalMargin, verticalMargin);
            }
            if (isGravity) {
                mToast.setGravity(gravity, xOffset, yOffset);
            }
            mToast.show();
        }
    }
}
