package com.sheenhill.module_chest.study_plan

import android.view.WindowManager
import com.sheenhill.common.base.BaseDialog
import com.sheenhill.module_chest.R
import com.sheenhill.module_chest.databinding.DialogTextShowBinding

class ShowTextDialog : BaseDialog<DialogTextShowBinding, ShowTextDialog>() {
    override fun getLayoutId(): Int {
        return R.layout.dialog_text_show
    }

    override fun initData(binding: DialogTextShowBinding) {
        super.initData(binding)
        binding.text=arguments?.getString("text","")
    }

    override fun setLayout(params: WindowManager.LayoutParams): WindowManager.LayoutParams {
        super.setLayout(params)
        params.width = dp2px(210)
        return params
    }
}