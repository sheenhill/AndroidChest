package com.sheenhill.rusuo.v2

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sheenhill.common.base.MainActivityViewModel
import com.sheenhill.rusuo.R
import com.sheenhill.rusuo.util.LogUtil


class V2_MainActivity : AppCompatActivity(R.layout.activity_main_v2) {
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        ViewModelProvider(this).get(MainActivityViewModel::class.java)


//        LogUtil.d("是否有可用网络:${isNetSuccessFul(connectivityManager)}")
        val networkCallback = netWorkStateListener()
        val builder = NetworkRequest.Builder()
        val request = builder.build()
        val connMgr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
//        connMgr.registerNetworkCallback(request, networkCallback)
        return super.onCreateView(name, context, attrs)
    }

    private class netWorkStateListener : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            LogUtil.i("网络已链接")
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            LogUtil.i("网络已断开")
        }

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    LogUtil.i("wifi已经连接")
                } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    LogUtil.i("数据流量已经连接")
                } else {
                    LogUtil.i("其他网络")
                }
            }
        }

    }
}
//connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//Log.i("mw","是否有可用网络:"+isNetSuccessFul());
//
//}


//public boolean isNetSuccessFul(){
//    NetworkInfo network = connectivityManager.getActiveNetworkInfo();
//
//    if (network == null ||network.getState() != NetworkInfo.State.CONNECTED) {
//        return false;
//    }
//    if(ConnectivityManager.TYPE_WIFI == network.getType()){
//        Log.i("mw", "WIFI网络");
//    }
//
//    if(ConnectivityManager.TYPE_MOBILE == network.getType()){
//        Log.i("mw", "移动网络");
//    }
//
//    return true;
//}