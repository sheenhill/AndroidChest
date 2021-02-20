package com.sheenhill.module_chest.study_plan

import android.view.WindowManager
import com.sheenhill.common.base.BaseDialog
import com.sheenhill.module_chest.R
import com.sheenhill.module_chest.databinding.DialogAddRecordBinding

class AddRecordDialog : BaseDialog<DialogAddRecordBinding, AddRecordDialog>() {
    override fun getLayoutId(): Int {
        return R.layout.dialog_add_record
    }

    override fun setLayout(params: WindowManager.LayoutParams): WindowManager.LayoutParams {
        super.setLayout(params)
        params.width = dp2px(210)
        return params
    }
}