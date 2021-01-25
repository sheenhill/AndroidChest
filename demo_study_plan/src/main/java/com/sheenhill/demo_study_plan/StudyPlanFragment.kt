package com.sheenhill.demo_study_plan

import androidx.lifecycle.ViewModel
import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.fragment.K_DataBindingConfig

/* 学习打开功能 */
class StudyPlanFragment:K_BaseJetpackFragment(){
    lateinit var viewModel: StudyPlanViewModel

    override fun initViewModel() {
        viewModel= StudyPlanViewModel()
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_study_plan,BR.viewModel,viewModel)
    }
}