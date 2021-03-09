package com.sheenhill.module_network

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.fragment.K_DataBindingConfig
import com.sheenhill.common.util.LogUtil
import kotlinx.android.synthetic.main.fragment_test_net.view.*

class NetTestFragment : K_BaseJetpackFragment() {
    lateinit var viewModel: NetTestViewModel
    override fun initViewModel() {
        viewModel = getFragmentViewModel(NetTestViewModel::class.java)
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_test_net, BR.viewModel, viewModel)
                .addBindingParam(BR.listener, Listener())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel.testMsg.observe(mActivity, {
            viewModel.testMsgList.add(it)
            mBinding.root.ll_text.addView(Text(it))
            mBinding.root.scrollView.postDelayed({
                mBinding.root.scrollView.fullScroll(View.FOCUS_DOWN)
            }, 100)
            LogUtil.d("viewModel.testMsg  receive <<<<<< $it,list.size=${viewModel.testMsgList.size}")
        })
        viewModel.testHost.observe(mActivity,{
            if(it!="192.128.0.46")
            viewModel.testConnectServer()
        })
        return mBinding.root
    }

    fun Text(str: String): TextView {
        val tv = TextView(mActivity)
        tv.text = str
        return tv
    }

    class Listener() {
        fun addTestMsg(vm: NetTestViewModel) {
            vm.testMsg.value = "test"
        }

        fun testConnect(vm: NetTestViewModel){
            vm.testMsg.value="判断当前网络状态"
            vm.testMsg.value="..."
            vm.testMsg.value="搜索当前局域网服务器..."
            vm.receiverSocket()
        }
            }
}