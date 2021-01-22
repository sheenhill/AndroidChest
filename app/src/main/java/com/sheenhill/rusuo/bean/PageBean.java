package com.sheenhill.rusuo.bean;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * 页数状态类
 */
public class PageBean {
    private String pageNum;
    private boolean checked;

    public SpannableString getPageNum() {
        SpannableString spannableString=new SpannableString(pageNum);
        if (!checked)
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#cccccc")),0,pageNum.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        else
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#359DF2")),0,pageNum.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
