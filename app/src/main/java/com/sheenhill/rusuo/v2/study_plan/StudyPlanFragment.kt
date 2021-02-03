package com.sheenhill.rusuo.v2.study_plan

import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.fragment.K_DataBindingConfig
import com.sheenhill.rusuo.BR
import com.sheenhill.rusuo.R

class StudyPlanFragment : K_BaseJetpackFragment() {
    lateinit var viewModel: StudyPlanViewModel
    override fun initViewModel() {
        viewModel = getFragmentViewModel(StudyPlanViewModel::class.java)
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_study_plan, BR.viewModel, viewModel)
                .addBindingParam(BR.adapter,StudyRecordsAdapter())
    }

    class Listener{

    }
}