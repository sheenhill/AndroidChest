package com.sheenhill.common.lifecycle_observer

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.sheenhill.common.util.LogUtil

class MyObserver(val root:String):LifecycleObserver {
    private val TAG = "Lifecycle_Test"

    @OnLifecycleEvent(value = Lifecycle.Event.ON_CREATE)
    fun create() {
        LogUtil.i("$root------${TAG}>>>>>>>create")
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    fun start() {
        LogUtil.i("$root------${TAG}>>>>>>>start")
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
    fun connect() {
        LogUtil.i("$root------${TAG}>>>>>>>resume")
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_PAUSE)
    fun disConnect() {
        LogUtil.i("$root------${TAG}>>>>>>>pause")
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_STOP)
    fun stop() {
        LogUtil.i("$root------${TAG}>>>>>>>stop")
    }
}