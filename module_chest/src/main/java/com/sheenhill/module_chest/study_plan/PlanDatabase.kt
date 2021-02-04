package com.sheenhill.module_chest.study_plan

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sheenhill.common.activity.K_BaseActivity

@Database(entities = [Plan::class], version = 1)
abstract class PlanDatabase : RoomDatabase() {
    abstract fun planDao(): PlanDao

    companion object {
        val instance by lazy(LazyThreadSafetyMode.NONE) {
            Room.databaseBuilder(
                    K_BaseActivity.getInstance(),
                    PlanDatabase::class.java, "plan"
            ).build()
        }
    }
}