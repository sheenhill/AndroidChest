package com.sheenhill.lotterydemo;

import android.view.WindowManager;

import com.sheenhill.common.base.BaseDialog;
import com.sheenhill.lotterydemo.databinding.DialogLuckNumBinding;

public class LuckNumDialog extends BaseDialog<DialogLuckNumBinding, LuckNumDialog> {

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_luck_num;
    }

    @Override
    protected WindowManager.LayoutParams setLayout(WindowManager.LayoutParams params) {
        super.setLayout(params);
        params.height = dp2px(300);
        params.width = dp2px(200);
        return params;
    }
}
