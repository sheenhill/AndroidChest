package com.sheenhill.rusuo


import android.os.Bundle
import com.sheenhill.common.activity.K_BaseActivity
import com.sheenhill.common.lifecycle_observer.MyObserver

class MainActivity : K_BaseActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ViewModelProvider(this).get(MainActivityViewModel::class.java)
        lifecycle.addObserver(MyObserver(javaClass.simpleName))
    }

}