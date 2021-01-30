package com.sheenhill.common.widget

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sheenhill.common.BR
import com.sheenhill.common.R
import com.sheenhill.common.base.MainActivityViewModel
import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.fragment.K_DataBindingConfig
import kotlinx.android.synthetic.main.dialog_image.view.*

class ImageShowFragment:K_BaseJetpackFragment() {
    lateinit var viewModel:MainActivityViewModel
    override fun initViewModel() {
        viewModel= MainActivityViewModel()
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.dialog_image,BR.viewModel,viewModel)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
arguments[]
        return mBinding.root
    }
}