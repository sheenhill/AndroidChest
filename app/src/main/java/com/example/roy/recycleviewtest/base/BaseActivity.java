package com.example.roy.recycleviewtest.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import com.example.roy.recycleviewtest.R;
import com.example.roy.recycleviewtest.util.LogUtil;
import com.example.roy.recycleviewtest.util.ToastUtils;


import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder unbinder;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(getClass().getSimpleName());//获取当前activity名字
//        initSystemBarTint();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }


    protected abstract @LayoutRes
    int getLayoutId();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化事件
     */
    protected abstract void initEvent();

    /**
     * 子类可以重写改变状态栏颜色
     */
    protected int setStatusBarColor() {
        return getColorPrimary();
    }

    /**
     * 子类可以重写决定是否使用透明状态栏
     */
//    protected boolean translucentStatusBar() {
//        return false;
//    }

    /**
     *  设置状态栏颜色
     *  当需要在项目中实现用户更改主题色时才用此动态修改状态栏样式的适配方案
     *  本项目用res/value-23/styles.xml来实现适配6.0以上状态栏深色字体的样式
     */
//    protected void initSystemBarTint() {
//        Window window = getWindow();
//        if (translucentStatusBar()) {
//            // 设置状态栏全透明
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.setStatusBarColor(Color.TRANSPARENT);
//            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            }
//            return;
//        }
//        // 沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            //5.0以上使用原生方法
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
////            window.setStatusBarColor(setStatusBarColor());
//            window.setStatusBarColor(Color.WHITE);  // todo: 此处修改了statusBarColor颜色非动态
//        }
////        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////            //4.4-5.0 不实现
////        }
//    }

    /**
     * 获取主题色
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data; // todo：查询该data是什么
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showToast(String msg) {
        ToastUtils.showShort(this, msg);
    }

    //TODO: 需实现LoadDialog功能
//    protected LoadDialog mLoadDialog;

//    public void showLoading() {
//        if (!isFinishing()) {
//            dismissLoading();
//            mLoadDialog = new LoadDialog(this);
//            mLoadDialog.show();
//        }
//    }
//
//    public void dismissLoading() {
//        try {
//            if (!isFinishing()
//                    && mLoadDialog != null
//                    && mLoadDialog.isShowing()) {
//                mLoadDialog.dismiss();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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

    /**
     * 启动下一个activity,put传值
     * @param context 所在activity
     * @param clz     要跳转的activity
     */
    public static void actionStart(Context context,Class<?> clz){
        Intent intent=new Intent(context, clz);
        context.startActivity(intent);
    }
    /**
     * 启动下一个activity,put传值
     * @param context 所在activity
     * @param clz     要跳转的activity
     * @param data1   传的第一个值，key为param。  以此类推，最多转三个值
     */
    public static void actionStart(Context context,Class<?> clz,String data1){
        Intent intent=new Intent(context, clz);
        intent.putExtra("param1",data1);
        context.startActivity(intent);
    }
    /**
     * 启动下一个activity,put传值
     * @param context 所在activity
     * @param clz     要跳转的activity
     * @param data1   传的第一个值，key为param。  以此类推，最多转三个值
     * @param data2   传的第二个值，key为param。
     */
    public static void actionStart(Context context,Class<?> clz,String data1,String data2){
        Intent intent=new Intent(context, clz);
        intent.putExtra("param1",data1);
        intent.putExtra("param2",data2);
        context.startActivity(intent);
    }
    /**
     * 启动下一个activity,put传值
     * @param context 所在activity
     * @param clz     要跳转的activity
     * @param data1   传的第一个值，key为param。  以此类推，最多转三个值
     * @param data2   传的第二个值，key为param。
     * @param data3   传的第三个值，key为param。
     */
    public static void actionStart(Context context,Class<?> clz,String data1,String data2,String data3){
        Intent intent=new Intent(context, clz);
        intent.putExtra("param1",data1);
        intent.putExtra("param2",data2);
        intent.putExtra("param3",data3);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(unbinder != null && unbinder != Unbinder.EMPTY){
            unbinder.unbind();
            unbinder = null;
        }
    }
}
