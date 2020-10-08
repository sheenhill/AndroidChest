package com.sheenhill.lotterydemo;

import com.sheenhill.common.base.BaseDialog;
import com.sheenhill.lotterydemo.databinding.DialogLuckNumBinding;

public class LuckNumDialog extends BaseDialog<DialogLuckNumBinding,LuckNumDialog> {

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_luck_num;
    }
}
