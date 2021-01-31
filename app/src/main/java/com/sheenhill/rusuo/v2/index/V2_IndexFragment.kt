package com.sheenhill.rusuo.v2.index

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.sheenhill.common.base.MainActivityViewModel
import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.fragment.K_DataBindingConfig
import com.sheenhill.rusuo.BR
import com.sheenhill.rusuo.R
import kotlinx.android.synthetic.main.fragment_index_v2.view.*

class V2_IndexFragment : K_BaseJetpackFragment() {
    private lateinit var viewModel: IndexFragmentViewModel
    override fun initViewModel() {
        viewModel = IndexFragmentViewModel()
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_index_v2, BR.viewModel, viewModel)
                .addBindingParam(BR.mainVM,getActivityViewModel(MainActivityViewModel::class.java))
                .addBindingParam(BR.listener, Listener())
                .addBindingParam(BR.navController, nav())
                .addBindingParam(BR.adapter, V2_BingPicAdapter(this))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        prepareTransitions()
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.message.observe(this, {
            if (it == "jump") {
                viewModel.message.value = ""  // 数据倒灌
                Listener().toChestFragment(nav())
            }
        })
    }

     class Listener {
        fun toChestFragment(navController: NavController) {
            navController.navigate(R.id.action_v2_MainFragment_to_ChestFragment)
        }
    }

    /**
     * Prepares the shared element transition to the pager fragment, as well as the other transitions
     * that affect the flow.
     */
    private fun prepareTransitions() {
        exitTransition = TransitionInflater.from(context)
                .inflateTransition(R.transition.grid_exit_transition)

        // A similar mapping is set at the ImagePagerFragment with a setEnterSharedElementCallback.
        setExitSharedElementCallback(
                object : SharedElementCallback() {
                    override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                        // Locate the ViewHolder for the clicked position.
                        val selectedViewHolder: RecyclerView.ViewHolder = mBinding.root.rv_pic_display
                                .findViewHolderForAdapterPosition(1)
                                ?: return

                        // Map the first shared element name to the child ImageView.
                        sharedElements[names[0]] = selectedViewHolder.itemView.findViewById(R.id.pic)
                    }
                })
    }
}
