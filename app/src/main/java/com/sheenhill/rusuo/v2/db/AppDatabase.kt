package com.sheenhill.rusuo.v2.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sheenhill.common.activity.K_BaseActivity
import com.sheenhill.rusuo.MainActivity

@Database(entities = arrayOf(Plan::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun planDao(): PlanDao

    companion object {
        val instance by lazy(LazyThreadSafetyMode.NONE) {
            Room.databaseBuilder(
                    K_BaseActivity.getInstance(),
                    AppDatabase::class.java, "plan"
            ).build()
        }
    }
}