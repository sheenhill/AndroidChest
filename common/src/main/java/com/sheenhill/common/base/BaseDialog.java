package com.sheenhill.common.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.sheenhill.common.R;

public abstract class BaseDialog<B extends ViewDataBinding,D extends BaseDialog>extends DialogFragment {
    
    // 通用
    protected Intent intent;
        protected boolean isDismiss;
        protected Callback callback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        B binding = DataBindingUtil.inflate(requireActivity().getLayoutInflater(), getLayoutId(), null, false);
        binding.setLifecycleOwner(this);
        builder.setView(binding.getRoot());
        initView(binding);
        initData(binding);
        initListener(binding);
        return builder.create();
    }

    /* 规定布局*/
    protected abstract @LayoutRes
    int getLayoutId();

    protected void initView(B binding) {
    }

    protected void initData(B binding) {
    }

    protected void initListener(B binding) {
    }

    protected void setDismiss(){
        dismiss();
    }


    // base 链式 实验成功
    public D startShow(@NonNull FragmentManager manager, @Nullable String tag) {
        isDismiss=true;
        show(manager, tag);
        return (D) this;
    }
    public D callback(Callback callback){
        this.callback=callback;
        return (D) this;
    }

    protected void setCallback(Callback callback) {
        this.callback = callback;
    }

    /**
     * 在 DialogFragment 中，dialog 窗口 被创建是在 onActivityCreate中，
     * 在此DecorView才被实例化。而我们要设置宽高的参数，
     * 必须在 DecorView实例化之后，不然是没有效果的。
     */
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            // 下面这些设置必须在此方法(onStart())中才有效
            Window window = dialog.getWindow();
            // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
            if (window != null) {
                window.setBackgroundDrawableResource(android.R.color.transparent);
                window.setWindowAnimations(R.style.DialogFromCenter);// 设置动画
                WindowManager.LayoutParams params = window.getAttributes();
                window.setAttributes(setLayout(params));
            }
        }
    }


    /**
     * fixme:dialogFragment的宽高需要用组件撑起来的问题
     *
     * 配置params,修改dialog大小，位置等
     * 默认：大小：params.WRAP_CONTENT  位置：Gravity.CENTER
     * @return 重新配置的params
     */
    protected WindowManager.LayoutParams setLayout(WindowManager.LayoutParams params) {
        params.gravity = Gravity.CENTER;
        return params;
    }

    protected int dp2px(int value) {
        final float v = getContext().getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }

    // 回调
    public interface Callback{
        void success();
    }
}
