package com.sheenhill.module_chest.study_plan

import android.graphics.Rect
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.fragment.K_DataBindingConfig
import com.sheenhill.common.util.LogUtil
import com.sheenhill.common.util.dp2px
import com.sheenhill.module_chest.BR
import com.sheenhill.module_chest.R


class StudyPlanFragment : K_BaseJetpackFragment() {
    lateinit var viewModel: StudyPlanViewModel
    override fun initViewModel() {
        viewModel = getNavigationViewModel(StudyPlanViewModel::class.java,R.id.nav_module_chest)
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_study_plan, BR.viewModel, viewModel)
                .addBindingParam(BR.adapter, StudyRecordsAdapter())
                .addBindingParam(BR.diffCallback, StudyPlanDiff())
                .addBindingParam(BR.itemDecoration, itemDecoration)
                .addBindingParam(BR.listener, Listener())
                .addBindingParam(BR.navController, nav())
    }

    private val itemDecoration by lazy {
        object : RecyclerView.ItemDecoration() {
            val margin = dp2px(mActivity, 12)

            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildLayoutPosition(view)
                val count = parent.adapter!!.itemCount //  获取准确的Item总个数
                when (position) {
                    (count - 1) -> outRect.set(0, margin, 0, margin)
                    else -> outRect.set(0, margin, 0, 0)
                }
            }
        }
    }

    class Listener() {
        fun addRecord(navController: NavController) {
            navController.navigate(R.id.action_global_addRecordDialog)
        }
    }
}
