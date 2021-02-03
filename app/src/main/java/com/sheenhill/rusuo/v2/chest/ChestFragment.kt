package com.sheenhill.rusuo.v2.chest

import android.annotation.SuppressLint
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.fragment.K_DataBindingConfig
import com.sheenhill.common.util.LogUtil
import com.sheenhill.rusuo.BR
import com.sheenhill.rusuo.R

class ChestFragment: K_BaseJetpackFragment() {
    private lateinit var viewModel: ChestFragmentViewModel
    override fun initViewModel() {
        viewModel = ChestFragmentViewModel()
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_chest, BR.viewModel, viewModel)
                .addBindingParam(BR.listener, Listener())
                .addBindingParam(BR.navController, nav())
    }

    class Listener {
        fun jumpTest(navController: NavController) {
            navController.navigate(R.id.action_ChestFragment_to_nav_graph)
        }

        fun toStudyPlan(view:View){
            view.findNavController().navigate(R.id.action_v2_MainFragment_to_studyPlanFragment)
        }

        @SuppressLint("RestrictedApi")
        fun backIndex(navController: NavController){
            LogUtil.i("backIndex")
            val list1=navController.backStack.toList()
            for( i in list1){
                LogUtil.i("navController.backStack${i}>>>>>${i.destination.displayName}")
            }
            navController.navigateUp()  // 栈中已经没了这个fragment,可是页面还在显示，是循环跳转了
            val list=navController.backStack.toList()
            for( i in list){
                LogUtil.i("navController.backStack${i}>>>>>${i.destination.displayName}")
            }

        }
    }

}