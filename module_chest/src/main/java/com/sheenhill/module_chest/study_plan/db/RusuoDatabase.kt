package com.sheenhill.module_chest.study_plan.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sheenhill.common.activity.K_BaseActivity

@Database(entities = [Plan::class, Motto::class], version = 1)
abstract class RusuoDatabase : RoomDatabase() {
    abstract fun planDao(): PlanDao
    abstract fun mottoDao(): MottoDao
    abstract fun studyPlanDao(): StudyPlanDao

    companion object {
        val instance by lazy(LazyThreadSafetyMode.NONE) {

            val MIGRATION_1_2 = object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
//                    database.execSQL("UPDATE `Motto` SET cited_number = cited_number + 1 WHERE uid =:uid")
//                    database.execSQL("SELECT uid FROM `Motto` WHERE cited_number = ( SELECT MIN(cited_number) FROM `Motto` ) LIMIT 1 ")

                }
            }

            Room.databaseBuilder(
                    K_BaseActivity.getInstance(),
                    RusuoDatabase::class.java, "rusuo.db")
                    .createFromAsset("database/rusuo.db")
                    .build()
        }
    }
}