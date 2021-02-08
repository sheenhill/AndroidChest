package com.sheenhill.module_chest.study_plan.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MottoDao {

    @Query("SELECT * FROM `Motto`")
    suspend fun getAllMotto(): List<Motto>

    // 查询引用频率低的格言
    @Query("SELECT * FROM motto WHERE cited_number = ( SELECT MIN(cited_number) FROM motto ) LIMIT 1 ")
    suspend fun selectOneLowCitedMotto(): List<Motto>

    /**增加格言引用次数
     * update返回的只有被修改的行数，没法返回被修改的行号或者行号列表，只有组合起来
     * return(int)  受影响的行数
     */
    @Query("UPDATE motto SET cited_number = cited_number + 1 WHERE id =(:id)")
    suspend fun updateCitedNum(id: Int): Int

    @Insert
    suspend fun addOnePlan(mottoList: List<Motto>): List<Long>
}