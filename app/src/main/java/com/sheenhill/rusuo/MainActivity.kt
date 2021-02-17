package com.sheenhill.rusuo


import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.sheenhill.common.activity.K_BaseActivity
import com.sheenhill.common.lifecycle_observer.MyObserver

class MainActivity : K_BaseActivity(R.layout.activity_main) {
    val ONE_MB = 1024 * 1024L;
    
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val flag = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) // 6.0 以上，状态栏支持字体变灰色
        window?.decorView?.systemUiVisibility = flag
        window?.statusBarColor = Color.TRANSPARENT
        window?.navigationBarColor = Color.TRANSPARENT
        lifecycle.addObserver(MyObserver(javaClass.simpleName))


       // 查询内部缓存（应该在协程中执行）
//        val storageManager :StorageManager=getInstance().getSystemService(StorageManager::class.java)
//        val appSpecificInternalDirUuid: UUID = storageManager.getUuidForPath(filesDir)
//        val availableBytes: Long =
//                storageManager.getAllocatableBytes(appSpecificInternalDirUuid)
//        LogUtil.d("RUSUO可用内部存储空间为：${availableBytes/ONE_MB}MB")

    }


}

