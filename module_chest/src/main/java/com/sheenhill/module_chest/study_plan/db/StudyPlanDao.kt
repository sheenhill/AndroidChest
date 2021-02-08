package com.sheenhill.module_chest.study_plan.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface StudyPlanDao {

    /**
     * 获取 PlanRecord 列表
     * 原子操作标签 Transaction
     */
    @Transaction
    @Query("SELECT * FROM `Plan`")
    suspend fun getAllPlanRecords():List<PlanRecord>

    /* 添加一条数据 */
    @Insert
    suspend fun addOneRecord(plan: Plan)

    /* 获取plan表全部数据 */
    @Query("SELECT * FROM `Plan`")
    suspend fun getAllPlan(): List<Plan>

    /* 获取Motto表全部数据 */
    @Query("SELECT * FROM `Motto`")
    suspend fun getAllMotto(): List<Motto>

    /* 查询引用频率低的格言 */
    @Query("SELECT * FROM motto WHERE cited_number = ( SELECT MIN(cited_number) FROM motto ) ")
    suspend fun selectOneLowCitedMotto(): List<Motto>

    /**  增加格言引用次数
     * update返回的只有被修改的行数，没法返回被修改的行号或者行号列表，只有组合起来
     * return(int)  受影响的行数
     */
    @Query("UPDATE motto SET cited_number = cited_number + 1 WHERE id =(:id)")
    suspend fun updateCitedNum(id: Int): Int

    /* 添加格言词库 */
    @Insert
    suspend fun addMotto(mottoList: List<Motto>): List<Long>
}