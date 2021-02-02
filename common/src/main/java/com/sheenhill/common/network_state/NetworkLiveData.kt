package com.sheenhill.common.network_state


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.sheenhill.common.activity.K_BaseActivity


/**
 * 用广播发送网络状态事件的方式被弃用了
 * 现在使用ConnectivityManager.NetworkCallback
 * 暂时没写兼容性代码，需要详见连接其中一个回答：https://stackoverflow.com/questions/36421930/connectivitymanager-connectivity-action-deprecated#
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class NetworkLiveData : LiveData<Int>() {

    private var networkCallback: ConnectivityManager.NetworkCallback

    private var request: NetworkRequest

    private var manager: ConnectivityManager

    init {
        networkCallback = NetworkCallbackImpl()
        request = NetworkRequest.Builder().build()
        manager = K_BaseActivity.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onActive() {
        super.onActive()
        manager.registerNetworkCallback(request, networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        manager.unregisterNetworkCallback(networkCallback)
    }

    object NetworkLiveDataHolder {
        val INSTANCE = NetworkLiveData()
    }

    companion object {
        fun getInstance(): NetworkLiveData {
            return NetworkLiveDataHolder.INSTANCE
        }
    }

    class NetworkCallbackImpl : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            getInstance().postValue(NetworkState.CONNECT)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            getInstance().postValue(NetworkState.NONE)
        }

        // 隔3s左右发送一次
        override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                getInstance().postValue(NetworkState.WIFI)
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                getInstance().postValue(NetworkState.CELLULAR)
            }
        }
    }
}