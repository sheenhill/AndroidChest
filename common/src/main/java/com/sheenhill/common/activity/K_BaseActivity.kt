package com.sheenhill.common.activity


import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sheenhill.common.network_state.NetworkLiveData
import com.sheenhill.common.network_state.NetworkState
import com.sheenhill.common.share_view_model.MainActivityViewModel
import com.sheenhill.common.util.LogUtil
import com.sheenhill.common.util.ToastUtils

abstract class K_BaseActivity : AppCompatActivity {
    private val shareViewModel by lazy { ViewModelProvider(this).get(MainActivityViewModel::class.java) }

    constructor(id: Int) : super(id)

    constructor() : super()

    init {
        INSTANCE = this
    }

    companion object {
        private lateinit var INSTANCE: AppCompatActivity

        fun getInstance(): AppCompatActivity {
            return INSTANCE;
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val flag = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) // 6.0 以上，状态栏支持字体变灰色
//        window?.decorView?.systemUiVisibility = flag
//        window?.statusBarColor = Color.TRANSPARENT
//        window?.navigationBarColor = Color.TRANSPARENT
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

    // 设置状态栏样式  亮底黑字
    fun setLightStatusBarStyle() {
        val flag = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) // 6.0 以上，状态栏支持字体变灰色
        window?.decorView?.systemUiVisibility = flag
        window?.statusBarColor = Color.TRANSPARENT
        window?.navigationBarColor = Color.TRANSPARENT
    }

    // 设置状态栏样式  深底白字
    fun setDarkStatusBarStyle() {
        val flag = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
//                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) // 6.0 以上，状态栏支持字体变灰色
        window?.decorView?.systemUiVisibility = flag
        window?.statusBarColor = Color.TRANSPARENT
        window?.navigationBarColor = Color.TRANSPARENT
    }
}

