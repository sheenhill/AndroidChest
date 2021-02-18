package com.sheenhill.module_chest.study_plan

import com.sheenhill.common.base.BaseDialog
import com.sheenhill.module_chest.R
import com.sheenhill.module_chest.databinding.DialogAddRecordBinding

class AddRecordDialog:BaseDialog<DialogAddRecordBinding,AddRecordDialog>() {
    override fun getLayoutId(): Int {
        return R.layout.dialog_add_record
    }
}