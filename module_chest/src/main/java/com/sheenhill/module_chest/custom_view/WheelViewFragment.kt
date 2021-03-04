package com.sheenhill.module_chest.custom_view

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.fragment.K_DataBindingConfig
import com.sheenhill.module_chest.BR
import com.sheenhill.module_chest.R
import kotlinx.android.synthetic.main.fragment_wheel_view.view.*

class WheelViewFragment : K_BaseJetpackFragment() {
    lateinit var mViewModel: WheelViewModel

    override fun initViewModel() {
        mViewModel = getFragmentViewModel(WheelViewModel::class.java)
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_wheel_view, BR.viewModel, mViewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding.root.test_2.setWheelListener(object : MyWheelView.WheelListener {
            override fun popValue(str: String) {
                mViewModel.mData.set(str)
            }
        })
        return mBinding.root
    }


    class WheelViewModel : ViewModel() {
        val arr1 = MutableLiveData(listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"))
        val arr2 = MutableLiveData(listOf("00", "10", "20", "30", "40", "50"))
        val mData=ObservableField<String>()
    }



    class MyItemDecoration:RecyclerView.ItemDecoration(){
        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDrawOver(c, parent, state)
        }
    }
}