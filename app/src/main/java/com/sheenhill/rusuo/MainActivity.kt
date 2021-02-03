package com.sheenhill.rusuo


import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.storage.StorageManager
import android.os.storage.StorageManager.ACTION_MANAGE_STORAGE
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import androidx.room.Room
import com.sheenhill.common.activity.K_BaseActivity
import com.sheenhill.common.lifecycle_observer.MyObserver
import com.sheenhill.rusuo.util.LogUtil
import com.sheenhill.rusuo.v2.db.AppDatabase
import com.sheenhill.rusuo.v2.db.Plan
import kotlinx.coroutines.MainScope
import java.util.*

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

