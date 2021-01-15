package com.sheenhill.lotterydemo.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sheenhill.lotterydemo.R;


/**
 *
 * 初次启动隐私权限弹窗
 * Created by n on 2020/4/21.
 */

public class PrivacyDialog extends Dialog implements View.OnClickListener {

    // 内容
    private TextView contentTxt;
    // 标题
    private TextView titleTxt;
    // 确认键
    private TextView submitTxt;
    // 取消键
    private TextView cancelTxt;
    // 内容文本
    private String content;
    // 点击监听器
    private OnCloseListener listener;
    private OnCloseListener userlistener;
    // 确认键文本
    private String positiveName;
    // 取消键文本
    private String negativeName;
    // 标题文本
    private String title;

    // 构造方法
    public PrivacyDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.content = content;
    }

    // 设置标题
    public PrivacyDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    // 设置确认键
    public PrivacyDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    //设置取消键
    public PrivacyDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    //设置案按键监听
    public PrivacyDialog setListen(OnCloseListener listen) {
        this.listener = listen;
        return this;
    }

    //用户协议和隐私正常点击监听
    public PrivacyDialog setUserPrivacy(OnCloseListener listen) {
        this.userlistener = listen;
        return this;
    }

    //创建回调
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_privacy);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        getWindow().setLayout((ViewGroup.LayoutParams.MATCH_PARENT), ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(R.drawable.border_black_l);
        initView();
    }

    //初始化页面
    private void initView() {
        contentTxt = findViewById(R.id.content);
        titleTxt = findViewById(R.id.title);

        submitTxt = findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);
        contentTxt.setText(content);
        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }
        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
        }
        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        }
        setSpStringBuilder();
    }

    /***
     * 设置隐私政策点击和颜色
     */
    private void setSpStringBuilder() {
        final SpannableStringBuilder style = new SpannableStringBuilder();

//设置文字
        style.append(content);
        //用户协议
        int  startUser = content.indexOf("《");
        int  endUser = content.indexOf("》") + 1;
        //隐私政策
        int  startPrivacy = content.lastIndexOf("《");
        int  endPrivacy = content.lastIndexOf("》") + 1;
//设置部分文字点击事件
        ClickableSpan clickableSpanUser = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (userlistener != null) {
                        userlistener.userPrivacyOnClick(0);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        };
        style.setSpan(clickableSpanUser, startUser, endUser, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan clickableSpanPrivacr = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (userlistener != null) {
                    userlistener.userPrivacyOnClick(1);
                }
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        };
        style.setSpan(clickableSpanPrivacr, startPrivacy, endPrivacy, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


//设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(Color.parseColor("#648ee1"));
        ForegroundColorSpan foregroundColorSpan2 = new ForegroundColorSpan(Color.parseColor("#648ee1"));
        style.setSpan(foregroundColorSpan1, startUser, endUser, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(foregroundColorSpan2, startPrivacy, endPrivacy, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

//配置给TextView
        contentTxt.setMovementMethod(LinkMovementMethod.getInstance());
        contentTxt.setText(style);
    }

    //按键回调
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cancel:
                if (listener != null) {
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.submit:
                if (listener != null) {
                    listener.onClick(this, true);
                }
                this.dismiss();
                break;
            default:
        }
    }

    //按键接口
    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);

        // type   0: 用户协议  1：隐私政策
        void userPrivacyOnClick(int type);
    }
}
