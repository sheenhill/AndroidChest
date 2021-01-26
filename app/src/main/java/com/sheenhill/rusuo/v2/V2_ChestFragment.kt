package com.sheenhill.rusuo.v2

import androidx.navigation.NavController
import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.fragment.K_DataBindingConfig
import com.sheenhill.rusuo.BR
import com.sheenhill.rusuo.R

class V2_ChestFragment : K_BaseJetpackFragment() {
    private lateinit var viewModel: ChestFragmentViewModel
    override fun initViewModel() {
        viewModel = ChestFragmentViewModel()
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_chest_v2, BR.viewModel, viewModel)
                .addBindingParam(BR.listener, Listener())
                .addBindingParam(BR.navController, nav())
    }

    class Listener {
        fun jumpTest(navController: NavController) {
            navController.navigate(R.id.action_v2_ChestFragment_to_nav_graph)
        }
    }

}