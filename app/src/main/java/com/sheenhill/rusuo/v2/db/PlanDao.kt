package com.sheenhill.rusuo.v2.db

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