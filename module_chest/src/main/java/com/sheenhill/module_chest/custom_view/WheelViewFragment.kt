package com.sheenhill.module_chest.custom_view

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.sheenhill.common.base.SingleTypeBaseRVAdapter
import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.fragment.K_DataBindingConfig
import com.sheenhill.module_chest.BR
import com.sheenhill.module_chest.R
import com.sheenhill.module_chest.databinding.ItemWheelViewBinding

class WheelViewFragment : K_BaseJetpackFragment() {
    lateinit var mViewModel: WheelViewModel
    override fun initViewModel() {
        mViewModel = getFragmentViewModel(WheelViewModel::class.java)
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_wheel_view, BR.viewModel, mViewModel)
                .addBindingParam(BR.adapter,wheelViewAdapter)
    }


    class WheelViewModel : ViewModel() {
        val arr1 = MutableLiveData(listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"))
        val arr2 = MutableLiveData(listOf("00", "10", "20", "30", "40", "50"))
    }

    val wheelViewAdapter = object : SingleTypeBaseRVAdapter<String, ItemWheelViewBinding>() {
        override fun getLayoutResId(viewType: Int): Int {
            return R.layout.item_wheel_view
        }

        override fun onBindItem(binding: ItemWheelViewBinding, item: String, holder: RecyclerView.ViewHolder?) {
            binding.data = item
        }
    }
}