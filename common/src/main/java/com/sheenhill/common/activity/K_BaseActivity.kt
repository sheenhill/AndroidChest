package com.sheenhill.common.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.sheenhill.common.network_state.NetworkLiveData
import com.sheenhill.common.network_state.NetworkState
import com.sheenhill.common.share_view_model.MainActivityViewModel
import com.sheenhill.common.util.LogUtil
import com.sheenhill.common.util.ToastUtils

abstract class K_BaseActivity(activity: Int) : AppCompatActivity(activity) {
    private val shareViewModel by lazy { ViewModelProvider(this).get(MainActivityViewModel::class.java) }


    init {
        INSTANCE = this
    }

    companion object {
        private lateinit var INSTANCE: Activity

        fun getInstance(): Activity {
            return INSTANCE;
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var netState = NetworkState.CONNECT
        // 网络状态监听
        NetworkLiveData.getInstance().observe(this, {
            if (netState != it)
                when (it) {
                    NetworkState.WIFI -> {
                        LogUtil.i("NETWORK_STATE>>>>>>>wifi已链接")
                        shareViewModel.networkState.value = it
                    }
                    NetworkState.CELLULAR -> {
                        LogUtil.i("NETWORK_STATE>>>>>>>4G已链接")
                        shareViewModel.networkState.value = it
                    }
                    NetworkState.NONE -> {
                        LogUtil.i("NETWORK_STATE>>>>>>>网络断开")
                        ToastUtils.showShort(this, "没有网络了")
                        shareViewModel.networkState.value = it
                    }
                }
        })
    }
}

