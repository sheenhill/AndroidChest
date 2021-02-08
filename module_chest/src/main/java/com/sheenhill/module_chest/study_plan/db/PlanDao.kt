package com.sheenhill.module_chest.study_plan.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlanDao {

    @Query("SELECT * FROM `Plan`")
    suspend fun getAll(): List<Plan>

    @Insert
    suspend fun addOneTime(plan: Plan)
}