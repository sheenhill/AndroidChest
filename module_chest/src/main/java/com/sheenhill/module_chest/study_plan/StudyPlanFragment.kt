package com.sheenhill.module_chest.study_plan

import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.fragment.K_DataBindingConfig
import com.sheenhill.module_chest.BR
import com.sheenhill.module_chest.R


class StudyPlanFragment : K_BaseJetpackFragment() {
    lateinit var viewModel: StudyPlanViewModel
    override fun initViewModel() {
        viewModel = getFragmentViewModel(StudyPlanViewModel::class.java)
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_study_plan, BR.viewModel, viewModel)
                .addBindingParam(BR.adapter, StudyRecordsAdapter())
                .addBindingParam(BR.diffCallback,StudyPlanDiff())
    }

}