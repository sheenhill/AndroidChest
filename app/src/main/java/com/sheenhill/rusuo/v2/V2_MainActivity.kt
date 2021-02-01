package com.sheenhill.rusuo.v2

import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.ViewModelProvider
import com.sheenhill.common.K_BaseActivity
import com.sheenhill.common.base.MainActivityViewModel
import com.sheenhill.rusuo.R


class V2_MainActivity : K_BaseActivity(R.layout.activity_main_v2) {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        ViewModelProvider(this).get(MainActivityViewModel::class.java)

//        NetworkLiveData.getInstance().observe(this, Observer {
//            when (it) {
//                NetworkState.CONNECT -> {
//                    LogUtil.i("NETWORK_STATE>>>>>>>网络已链接")
//                }
//
//                NetworkState.NONE -> {
//                    LogUtil.i("NETWORK_STATE>>>>>>>网络已断开")
//                }
//
//                NetworkState.CELLULAR -> {
//                    LogUtil.i("NETWORK_STATE>>>>>>>移动网络已连接")
//                }
//
//                NetworkState.WIFI -> {
//                    LogUtil.i("NETWORK_STATE>>>>>>>WIFI已连接")
//                }
//            }
//        })

//        val networkCallback = netWorkStateListener()
//        val builder = NetworkRequest.Builder()
//                .addTransportType(android.net.NetworkCapabilities.TRANSPORT_CELLULAR)   // 蜂窝通讯
//                .addTransportType(android.net.NetworkCapabilities.TRANSPORT_WIFI)
//        val request = builder.build()
//
//        val connMgr = application.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
//        connMgr.registerNetworkCallback(request, networkCallback)
    }

//    companion object {
//        private lateinit var INSTANCE: Activity
//
//        fun getInstance(): Activity {
//            return this.INSTANCE;
//        }
//    }

}