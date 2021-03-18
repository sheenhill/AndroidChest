package com.sheenhill.demo_lottery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sheenhill.common.activity.K_BaseActivity
import com.sheenhill.common.util.LogUtil

/* 单独为app时，入口是这个activity */
class MainActivity : K_BaseActivity(R.layout.activity_main){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLightStatusBarStyle()
        LogUtil.i(BuildConfig.LOG_TEST)
    }
}