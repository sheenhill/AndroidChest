package com.sheenhill.rusuo.v2.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Plan(
        @PrimaryKey(autoGenerate = true) val uid: Int,
        @ColumnInfo(name = "date") val time: String,
        @ColumnInfo(name = "total_time") val totalTime: Float, // 计划总时长
        @ColumnInfo(name = "study_time") val studyTime: Float, // 本次时长
        @ColumnInfo(name = "spare_time") val spareTime: Float, // 剩余时长
        @ColumnInfo(name = "note") val note: String?, // 记录
)
