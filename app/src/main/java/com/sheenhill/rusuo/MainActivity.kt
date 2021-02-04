package com.sheenhill.rusuo


import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.sheenhill.common.activity.K_BaseActivity
import com.sheenhill.common.lifecycle_observer.MyObserver

class MainActivity : K_BaseActivity(R.layout.activity_main) {
    val ONE_MB = 1024 * 1024L;
    
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ViewModelProvider(this).get(MainActivityViewModel::class.java)
        lifecycle.addObserver(MyObserver(javaClass.simpleName))
        
//        LogUtil.i("${StorageManager(this,mainLooper)}")

       // 查询内部缓存（应该在协程中执行）
//        val storageManager :StorageManager=getInstance().getSystemService(StorageManager::class.java)
//        val appSpecificInternalDirUuid: UUID = storageManager.getUuidForPath(filesDir)
//        val availableBytes: Long =
//                storageManager.getAllocatableBytes(appSpecificInternalDirUuid)
//        LogUtil.d("RUSUO可用内部存储空间为：${availableBytes/ONE_MB}MB")

    }


}

